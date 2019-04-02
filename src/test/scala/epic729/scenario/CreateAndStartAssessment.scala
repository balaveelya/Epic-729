package epic729.scenario

import io.gatling.core.Predef._
import io.gatling.http.Predef._
import epic729.config.Config

object CreateAndStartAssessment {

  var testId = ""

  val createStartAssessment = exec(
    http("Creating Assessment").post(Config.httpUrl+"/users/${refId}/actions")
      .header("Accept","*/*")
      .header("Authorization","${token}")
      .header("ActivityId","${refId}")
      .check(status.is(201))
      .check(jsonPath("$.testId").saveAs("testId"))
  ).exec(session => {
    testId = session("testId").as[String].trim
    println("%%%%%%%%%%% test ID  =====>>>>>>>>>> " + testId)
    session}
  )
}
