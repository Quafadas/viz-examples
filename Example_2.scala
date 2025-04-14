import viz.vegaFlavour
import viz.PlotTargets.desktopBrowser
import viz.Plottable.ppSpecUrl
import viz.vega.plots.SpecUrl

/**
  * I often start from an existng vega example. Follow the SpecUrls to find them or visit the vega examples...
  */
@main
def example2_FromOnlineSpec =
  SpecUrl.BarChart.plot(
    List(viz.Utils.fillDiv)
  )

  SpecUrl.PieChart.plot(
    List(viz.Utils.fillDiv)
  )
