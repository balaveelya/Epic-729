package epic729.scenario

import io.gatling.core.Predef._
import io.gatling.http.Predef._
import epic729.config.Config
import epic729.util.Scenariotrait

object Login extends Scenariotrait {

  var token = ""

  val defaultUrl = "http://hmh-identity-provider.int.br.hmheng.io"

  val environmentUrls = Map(
    ENV_DEV -> "http://hmh-identity-provider.dev.br.hmheng.io",
    ENV_INT -> defaultUrl,
    ENV_CERT -> "https://hmh-identity-provider.cert.br.hmheng.io",
    ENV_PROD -> "https://hmh-identity-provider.br.hmheng.io"
  )

  val defaultRedirectUri = "http://int.hmhone.app.hmhco.com/arvo/logged-in/"

  val redirectURIs = Map(
    ENV_INT -> defaultRedirectUri,
    ENV_CERT -> "https://cert.hmhco.com/arvo/logged-in/",
    ENV_PROD -> "https://www.hmhco.com/one/logged-in/"
  )

  override def getCommonRelativeURL(env: String): String = {
    environmentUrls.getOrElse(env, defaultUrl)
  }

  override def getRelativeURL(env: String): String = {
    getCommonRelativeURL(env)
  }

  def getRedirectURI(env: String):String = {
    redirectURIs.getOrElse(env, defaultRedirectUri)
  }

  def LoginFlow() = exec {
    login
  }

  def login() = exec { session =>
    session.set("authorize_url",getBaseUrl()+"/authorize")
      .set("redirect_uri", getRedirectURI(Config.TARGET_ENV))
      .set("client_id", Config.getProperty("client_id", "152ced50-1369-4b19-8b26-8f3d5d9bfd6a.hmhco.com"))
  }.doIfOrElse(session => session.contains("token")) {
    exec { session =>
      session
    }
  } {
    exec { session =>
      val username = session("username").as[String]
      val password = session("password").as[String]
      println("====>" + username + "====>" + password)
      session.set("nonce", "99999")
    }.exec(
      http("Authorize - /authorize")
        .post("${authorize_url}")
        .queryParam("client_id", "${client_id}")
        .queryParam("scope", "openid")
        .queryParam("response_type", "id_token token")
        .queryParam("nonce", "${nonce}")
        .queryParam("state", "${redirect_uri}")
        .queryParam("redirect_uri", "${redirect_uri}")
        .queryParam("connection", "${connection}")
        .formParam("username", "${username}")
        .formParam("password", "${password}")
        .disableFollowRedirect
        .check(
          status.is(302),
          headerRegex("Location", "${redirect_uri}#access_token=(.*?)&")
            .find.transform(string => java.net.URLDecoder.decode(string, "UTF-8")).saveAs("token")
        )
    ).exec(session => {
      token = session("token").as[String].trim
      session
    }
    )
  }

}
