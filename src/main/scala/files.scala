//> using lib "com.lihaoyi::os-lib:0.8.0"
//> using lib "io.circe::circe-literal:0.14.3"
//> using lib "io.github.quafadas::dedav4s:0.8.1"

import io.circe.literal._
import viz.PlotTargets.desktopBrowser
import viz.extensions.*
import viz.dsl.DslPlot.*
import viz.dsl.vegaLite.*
import viz.dsl.DslPlot.*
import cats.syntax.all.*
import io.circe.Json
import io.circe.Encoder
import io.circe.Decoder
import viz.PlotTarget
//> using lib "org.scala-js::scalajs-dom::2.3.0"

trait XYPlottable extends Selectable:
  val x: Double
  val y: Double

trait LinePlottable extends XYPlottable
  
trait BarPlottable extends XYPlottable

case class Coords(x: Double, y: Double) extends BarPlottable derives Encoder.AsObject, Decoder

case class Mysterious(thing: Double, yMagic: Double, description:String) extends LinePlottable derives Encoder.AsObject, Decoder {
  override val x = thing 
  override val y = yMagic 
}
  

extension [P <: XYPlottable](p: Seq[P])//(using enc: Encoder.AsObject[P])
  def toPlotDataset: URLData = 
    val inlineDataset : InlineDataset = for(p1 <- p) yield {
        //enc.encodeObject(p1).toMap.mapValues(_.some).toMap
        Map("x"-> Encoder.encodeDouble(p1.x).some, "y" -> Encoder.encodeDouble(p1.y ).some)
      }
    URLData(
      values = inlineDataset.some
    )

extension [P <: XYPlottable]( xyPlottable: Seq[P] )(using enc: Encoder.AsObject[P])(using pt: PlotTarget)
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
def hello() =  
  val interesting = Mysterious(1.0,2.0, "hi")  
  val plotMe = Seq(    
    Mysterious(1.0,2.0, "uno"),
    Mysterious(2.5,3.5, "dos")
  ).barPlot()
