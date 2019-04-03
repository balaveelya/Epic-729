package epic729.simulation

import io.gatling.core.Predef._
import io.gatling.core.Predef.Simulation

import scala.concurrent.duration._
import epic729.config.Config
import epic729.scenario.{CreateAndStartAssessment, GetStudentProfile, Login, PostAnswerAndNextQuestion,MathInventoryAPI}
import epic729.util.{Scenariotrait, SimulationTrait}


class EpicSimulation extends  Simulation with SimulationTrait {

  val prefix = s"users_${Config.TARGET_ENV}"+".csv"

  val path = System.getProperty("user.dir")+"\\src\\test\\scala\\Epic729\\data\\"

  val userFeeder = createFeeder(path+prefix)

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
