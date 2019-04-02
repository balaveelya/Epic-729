package epic729.scenario

import epic729.config.Config
import io.gatling.core.structure.ChainBuilder
import io.gatling.http.Predef._
import io.gatling.core.Predef.{exec, _}

object MathInventoryAPI {

  def Actionflow: ChainBuilder = exitBlockOnFail(
    group("729 Epic on MI") {
        exec(GetStudentProfile.getStudentProfile).pause(Config.PAUSE_NAVIGATION_SEC)
        .exec(CreateAndStartAssessment.createStartAssessment).pause(Config.PAUSE_NAVIGATION_SEC)
        .exec(PostAnswerAndNextQuestion.postAnswerNextquestion).pause(Config.PAUSE_NAVIGATION_SEC)
    })

}
