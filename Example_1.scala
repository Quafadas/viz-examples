import io.github.quafadas.plots.SetupVegaBrowser.{*, given}

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
