//> using lib "com.lihaoyi::os-lib:0.8.0"
//> using lib "io.github.quafadas::dedav4s:0.8.1"

import viz.PlotTargets.desktopBrowser
import viz.extensions.*
import viz.dsl.DslPlot.*
import viz.dsl.vegaLite.*
import viz.dsl.DslPlot.*
import cats.syntax.all.*
import io.circe.Json
import viz.PlotTarget
import upickle.default.*

//> using lib "org.scala-js::scalajs-dom::2.3.0"

trait XYPlottable(val x : Double, val y : Double)
case class Mysterious(thing: Double, yMagic: Double, description:String) extends XYPlottable(x = thing, y = yMagic) derives ReadWriter

extension [P <: XYPlottable](p: Seq[P])
  def toPlotDataset: URLData = 
    val inlineDataset : InlineDataset = for(p1 <- p) yield {
        //enc.encodeObject(p1).toMap.mapValues(_.some).toMap
        Map("x"-> Json.fromDouble(p1.x), "y" -> Json.fromDouble(p1.y) )
      }
    URLData(
      values = inlineDataset.some
    )

extension [P <: XYPlottable]( xyPlottable: Seq[P] )(using pt: PlotTarget)
  def linePlot: Unit = 
    val data = xyPlottable.toPlotDataset.some
    val plotDef = VegaLiteDsl(
      $schema = "https://vega.github.io/schema/vega-lite/v5.json".some,
      description = "a Test".some,
      data = data,
      mark = "line".some,
      encoding = EdEncoding(
        x = XClass(field = "x".some, `type`= "quantitative".some).some,
        y = YClass(field = "y".some, `type`= "quantitative".some).some
      ).some,
      height = "container".some,
      width = "container".some
    )
    plotDef.plot(using pt)

  def barPlot(): Unit = 
    val data = xyPlottable.toPlotDataset.some
    val plotDef = VegaLiteDsl(
      $schema = "https://vega.github.io/schema/vega-lite/v5.json".some,
      description = "a Test".some,
      data = data,
      mark = "line".some,
      encoding = EdEncoding(
        x = XClass(field = "x".some, `type`= "quantitative".some).some,
        y = YClass(field = "y".some, `type`= "quantitative".some).some
      ).some,
      height = "container".some,
      width = "container".some
    )
    plotDef.plot(using pt)

    
@main
def ContrivedExample() =  
  val interesting = Mysterious(1.0,2.0, "hi")
  val plotMe = Seq(    
    Mysterious(1.0,2.0, "uno"),
    Mysterious(2.5,3.5, "dos"),
    Mysterious(0,10, "tres")
  )
  println(upickle.default.writeJs(plotMe))
  plotMe.barPlot()


