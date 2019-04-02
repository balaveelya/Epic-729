package epic729.simulation

import io.gatling.core.Predef._
import io.gatling.core.Predef.Simulation

import scala.concurrent.duration._
import epic729.config.Config
import epic729.scenario.{CreateAndStartAssessment, GetStudentProfile, Login, PostAnswerAndNextQuestion,MathInventoryAPI}
import epic729.util.{Scenariotrait, SimulationTrait}


class EpicSimulation extends  Simulation with SimulationTrait {

  val userFeeder = createFeeder(Config.csvPath+"users_int.csv")

  val epicScenario = scenario("Epic 729 API scenario").feed(userFeeder)
    .repeat(Config.iterations) {
        exec(Login.LoginFlow())
        .exec(MathInventoryAPI.Actionflow)
    }

  var testSetup = setUp(
    epicScenario.inject(
      heavisideUsers(Config.NUM_USERS) over(Config.RAMP_UP second)
    )
  )

}
