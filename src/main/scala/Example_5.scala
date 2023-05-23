import viz.PlotTargets.desktopBrowser
import viz.extensions.*
import viz.dsl.DslPlot.*
//import viz.dsl.vegaLite.*
import viz.dsl.DslPlot.*
import cats.syntax.all.*
import io.circe.*
import io.circe.parser.*
import io.circe.syntax.*
import viz.PlotTarget
import upickle.default.*
import viz.vega.plots.SimpleBarChartLite
import viz.vega.plots.BarChart
import viz.dsl.vegaLite.*
import fs2.text
import viz.dsl.vega.TitleClass
import viz.vega.plots.BarChart.{*, given}
import viz.dsl.vega.LabelAlignEnum
import viz.dsl.vega.TitleOrientEnum
import viz.dsl.vega.VegaDsl

/**
 * 
 * Typesafe plotting.
 * 
  * I was originally quite excited about this. I thought I could use the vega schema to help with tab completion and discoverability, if it was modelleed as case classes.
  * 
  * It does work... but I have not found it easy to use in real life. Everything that actually matters, ends up stringly encoded anyway.
  * 
  * I'm on the fence, about whether this is really a "worst of both worlds" situation - we have the cognitive overhead of typesafety _and_ a stringly typed API.
  * 
  * Feel free to give it a try and form your own opinion. Javadocs are helpful... 
  * 
  * 
  * 
  */

@main
def Example_5() =
    case class LinePlottable(x: Double, y: Double)

    extension (p: Seq[LinePlottable])
        def toPlotDataset: URLData =
            val inlineDataset: InlineDataset = for (p1 <- p) yield {
                // enc.encodeObject(p1).toMap.mapValues(_.some).toMap
                Map("x" -> Encoder.encodeDouble(p1.x).some, "y" -> Encoder.encodeDouble(p1.y).some)
            }
            URLData(
              values = inlineDataset.some
            )
    end extension

    val plotMe = Seq(
      LinePlottable(1, 2),
      LinePlottable(2, 3),
      LinePlottable(3, 8),
      LinePlottable(4, 5),
      LinePlottable(5, 6),
      LinePlottable(6, 10)
    )
    
    VegaLiteDsl(
      data = plotMe.toPlotDataset.some,
      encoding = EdEncoding(
        x = XClass(
          field = "x".some,
          `type` = Type.quantitative.some
        ).some,
        y = YClass(
          field = "y".some,
          `type` = Type.quantitative.some
        ).some
      ).some, 
      transform = None,
      mark = "line".some,          
    ).plot
    
end Example_5
