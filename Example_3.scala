import io.github.quafadas.plots.SetupVegaBrowser.{*, given}

/**
  * I often start from an existng vega example. Follow the SpecUrls to find them or visit the vega examples...
  */
@main
def example4_FromNamedTuple =
  (
    schema = "https://vega.github.io/schema/vega-lite/v5.json",
    description = "A step chart constrained to +/-20 for an easy demo of brownian motion",
    data = (values = List(
      (x = 1, y = 1),
      (x = 2, y = 5),
      (x = 3, y = 7),
      (x = 4, y = 4)
    )),
    mark = (
      `type` = "line",
      interpolate = "step-after"
    ),
    encoding = (
      x = (
        field = "x",
        `type` = "quantitative",
        axis = (labels = false)
      ),
      y = (
        field = "y",
        `type` = "quantitative",
        scale = (domain = List(-20, 20))
      )
    ),
    width = "container",
    height = "container"
  ).plot()
