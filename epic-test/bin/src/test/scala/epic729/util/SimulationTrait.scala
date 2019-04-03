package epic729.util

import io.gatling.core.Predef._
import io.gatling.core.feeder.{FeederSupport, RecordSeqFeederBuilder}
import io.gatling.http.Predef._

trait SimulationTrait {

  def createFeeder(filenamePrefix: String): RecordSeqFeederBuilder[String] = {
    csv(filenamePrefix).records.circular
  }

}
