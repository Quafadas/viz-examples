import viz.vegaFlavour
import viz.PlotTargets.desktopBrowser
import viz.Plottable.given_PlatformPlot_ResourcePath

/**
  * Let's assume you have a spec you want to plot in a file.
  */
@main
def example1_FromFile =
  val rp = os.resource / "radialPlot.vega.json"
  rp.plot

  /**
     * This will create a second plot that will fill the browser window
     */
  rp.plot(
    List(viz.Utils.fillDiv)
  )
