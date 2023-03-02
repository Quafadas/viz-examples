//> using lib "com.lihaoyi::os-lib:0.9.0"
//> using lib "io.github.quafadas::dedav4s:0.8.2"
//> using lib "com.lihaoyi::upickle:3.0.0-M2"

import viz.PlotTargets.desktopBrowser
import viz.extensions.*
import viz.dsl.DslPlot.*
//import viz.dsl.vegaLite.*
import viz.dsl.DslPlot.*
import cats.syntax.all.*
import io.circe.*
import io.circe.syntax.*
import viz.PlotTarget
import upickle.default.*
import viz.vega.plots.SimpleBarChartLite
import viz.vega.plots.BarChart
import viz.dsl.vega.Legend
import fs2.text
import viz.dsl.vega.TitleClass
import viz.vega.plots.BarChart.{*, given}

/**
  * Plotting scalas performance vs the other 19 languages in the quicktype test suite. 
  * 
  * Note that this sort of "blends" styles. It uses strongly typed parts of the vega schema to help with tab completion and discoverability, but inserts them into an example.
  * 
  */

@main
def Example_6() =

    def chooseColor(isScala: Boolean, bigBox: Boolean) =
        (isScala, bigBox) match
            case (true, _)     => "red"
            case (false, true) => "green"
            case _             => "steelblue"

    case class QuicktypeTestResult(language: String,
                                   minutes: Int,
                                   seconds: Int,
                                   bigBox: Boolean = false,
                                   isScala: Boolean = false
    ) extends BarPlottable(category = language, amount = minutes * 60 + seconds)
        with MarkColour(colour = chooseColor(isScala, bigBox))
        derives ReadWriter

    val plotMe = Seq(
      QuicktypeTestResult("typescript", 2, 32),
      QuicktypeTestResult("swift", 4, 0),
      QuicktypeTestResult("scala 3", 2, 54, isScala = true),
      QuicktypeTestResult("rust", 6, 37, bigBox = true),
      QuicktypeTestResult("ruby", 1, 1),
      QuicktypeTestResult("python", 2, 34),
      QuicktypeTestResult("php", 0, 53),
      QuicktypeTestResult("objective-c", 2, 38),
      QuicktypeTestResult("kotlin", 7, 39, bigBox = true),
      QuicktypeTestResult("json-ts-csharp", 2, 21),
      QuicktypeTestResult("java", 7, 12),
      QuicktypeTestResult("javascript", 1, 45),      
      QuicktypeTestResult("haskell", 10, 21),
      QuicktypeTestResult("javascript-prop-types", 1, 45),
      QuicktypeTestResult("go", 1, 35),
      QuicktypeTestResult("flow", 2, 49),
      QuicktypeTestResult("dart", 2, 21),
      QuicktypeTestResult("csharp", 9, 33),
      QuicktypeTestResult("c++", 3, 43)
    ).sortBy(_.amount)

    val xAxis = viz.dsl.vega.Axis(
      scale = "xscale",
      orient = "bottom",
      labelAngle = -90.0.some,
      labelFontSize = 18.0.some,
      labelAlign = "right".some
    )

    val title = TitleClass(
      text = "Time to run Quicktype test suite (seconds): shorter bars are better".some,
      fontSize = 24.0.some,
    )

    BarChart(
      List(
        BarChart.removeYAxis,
        BarChart.takeColourFromData,
        viz.Utils.fillDiv,
        spec => spec("data")(0)("values") = writeJs(plotMe),
        spec => spec("title") = ujson.read(title.asJson.toString),
        spec => spec("axes")(0) = ujson.read(xAxis.asJson.toString), // prettier if ujson-circe makes it to scala 3... todo.        
        spec => spec("marks")(1) = ujson.read("""{
      "type": "text",
      "from": {"data": "table"},
      "encode": {
        "enter":{
        "text": {"field": "amount"},
          "x": {"scale": "xscale", "field": "category", "band": 0.5},
          "y": {"scale": "yscale", "field": "amount", "offset": -2},
          "fontSize": {"value": 18},
          "align": {"value":"center"}                          
        }
      }
    } """.strip())
      )
    )