package epic729.scenario

import io.gatling.core.Predef._
import io.gatling.http.Predef._
import epic729.config.Config

object PostAnswerAndNextQuestion {

  var questionId = ""

  val postAnswerNextquestion =
    exec(
      http("Posting Answer").post(Config.httpUrl+"/users/${refId}/answers")
        .header("Accept","*/*")
        .header("Authorization","${token}")
        .header("content-type","application/json")
        .body(StringBody("""{ "abilityTierId": "0" }""")).asJSON
        .body(StringBody("""{ "practice": "0" }""")).asJSON
        .body(StringBody("""{ "probability": "0" }""")).asJSON
        .body(StringBody("""{ "questionId": "${questionId}" }""")).asJSON
        .body(StringBody("""{ "responseTime": "0" }""")).asJSON
        .body(StringBody("""{ "stageId": "0" }""")).asJSON
        .body(StringBody("""{ "studentAnswer": "1" }""")).asJSON
        .body(StringBody("""{ "timeElapsed": "0" }""")).asJSON
        .check(jsonPath("$.questionId").saveAs("questionId"))
        .check(status.is(201))
    ).pause(2).
      exec(session => {
        questionId = session("questionId").as[String].trim
        println("%%%%%%%%%%% question ID =====>>>>>>>>>> " + questionId)
        session
      }
      )
}