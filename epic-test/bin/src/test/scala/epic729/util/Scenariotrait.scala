package epic729.util

import epic729.config.Config

trait Scenariotrait {

  val ENV_DEV = "dev"
  val ENV_INT = "int"
  val ENV_CERT = "cert"
  val ENV_PROD = "prod"


  def getBaseUrl(): String = {
    if (!Config.BASE_URL.isEmpty) {
      Config.BASE_URL
    } else {
      if (Config.COMMON_BASE_URL) {
        getCommonRelativeURL(Config.TARGET_ENV)
      } else {
        println(getRelativeURL(Config.TARGET_ENV))
        getRelativeURL(Config.TARGET_ENV)
      }
    }
  }

  def getRelativeURL(env: String): String

  def getCommonRelativeURL(env: String): String
}
