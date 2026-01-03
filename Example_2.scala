import io.github.quafadas.table.{*, given}
import io.github.quafadas.plots.SetupVegaBrowser.{*, given}
import io.circe.syntax.*

/**
  * I often start from an existng vega example. Follow the SpecUrls to find them or visit the vega examples...
  */
@main
def example2_FromTable =
  val data = CSV.resource("titanic.csv").toVector
  data.take(15).ptbln

  val histogram = VegaPlot.fromResource("histogram.vl.json")

  histogram.plot(
    _.data.values := data.filter(_.Sex == "male").asJson,
    _.title := s"Age Distribution of Male passengers",
    _.encoding.x.field := "Age",
    _.encoding.x.bin.step := 5
  )

  histogram.plot(
    _.data.values := data.filter(_.Sex == "female").asJson,
    _.title := s"Age Distribution of Female passengers",
    _.encoding.x.field := "Age",
    _.encoding.x.bin.step := 5
  )
