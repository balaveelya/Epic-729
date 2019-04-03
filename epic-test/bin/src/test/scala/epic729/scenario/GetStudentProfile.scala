package epic729.scenario

import io.gatling.core.Predef._
import io.gatling.http.Predef._
import epic729.config.Config

object GetStudentProfile {

  var refId = ""

  val getStudentProfile = exec(
    http("Getting Student Profile").get(Config.httpUrl + "/student")
      .header("Accept", "*/*")
      .header("Authorization", "${token}")
      .check(jsonPath("$.refId").saveAs("refId"))
  ).exec(session => {
    refId = session("refId").as[String].trim
    println("%%%%%%%%%%% ref ID    =====>>>>>>>>>> " + refId)
    session
  }
  )

}