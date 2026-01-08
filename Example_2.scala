import io.github.quafadas.table.{*, given}
import io.github.quafadas.plots.SetupVegaBrowser.{*, given}
import io.circe.syntax.*

@main
def example2_FromTable =
  val data: Vector[
    (PassengerId: Int,
     Survived: Boolean,
     Pclass: Int,
     Name: String,
     Sex: String,
     Age: Option[Double],
     SibSp: Int,
     Parch: Int,
     Ticket: String,
     Fare: Double,
     Cabin: Option[String],
     Embarked: Option[String]
    )
  ] = CSV.resource("titanic.csv").toVector
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

  histogram.plot(
    _.data.values := data.asJson,
    _.title := s"Age Distribution of All passengers",
    _.encoding.x.field := "Age",
    _.encoding.x.bin.step := 5,
    _.encoding += (color = (field = "Sex", `type` = "nominal")).asJson
  )

  histogram.plot(
    _.data.values := data.asJson,
    _.title := s"Age Distribution of All passengers by Class",
    _.encoding.x.field := "Age",
    _.encoding.x.bin.step := 5,
    _.encoding += (color = (field = "Sex", `type` = "nominal")).asJson,
    _.encoding += (column = (field = "Pclass", `type` = "nominal")).asJson
  )
