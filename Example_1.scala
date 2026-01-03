import io.github.quafadas.plots.SetupVegaBrowser.{*, given}
import io.circe.syntax.*

/**
  * Let's assume you have a spec you want to plot in a file.
  */
@main
def example1_FromFile =
  val rp = VegaPlot.fromResource("radialPlot.vega.json")
  /**
     * This will create a second plot that will fill the browser window
     */
  rp.plot(
    _.title := "A Radial Plot from File"
  )

  val piePlot = VegaPlot.fromString("""{
  "$schema": "https://vega.github.io/schema/vega-lite/v6.json",
  "title": "A Pie Chart",
  "description": "A simple pie chart with embedded data.",
  "width": "container",
  "height": "container",
  "data": {
    "values": [
      {"category": "cat1", "value": 4}
    ]
  },
  "mark": "arc",
  "encoding": {
    "theta": {"field": "value", "type": "quantitative"},
    "color": {"field": "category", "type": "nominal"}
  }}""")

  piePlot.plot(
    _.title := "Pie Chart: Madeup data}",
    _.data.values := List(
      (category = "cat1", value = 4),
      (category = "cat2", value = 6),
      (category = "cat3", value = 10)
    ).asJson
  )
