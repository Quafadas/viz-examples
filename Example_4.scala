import fs2._
import cats.effect.*
import scala.concurrent.duration._
import java.awt.Desktop
import java.io.File
import cats.effect.IOApp
import cats.effect.IO
import scala.concurrent.duration.FiniteDuration.apply
import java.util.concurrent.TimeUnit
import scala.concurrent.duration.FiniteDuration
import cats.effect.IO.asyncForIO
import cats.effect.std.Random

import viz.PlotTargets.websocket
import ujson.Value
import viz.PlotTarget
import viz.NamedTupleReadWriter.given_ReadWriter_T
import viz.Plottable.ppujson
import viz.vegaFlavour

/*
Having explored some of the abstractions, let do something fun, and see if we can produce an animation of browian motion with fs2. Also I get to learn some fs2.

We're going to emit N "steps" per move, and plot M moves. Moves will be metered so that we have one every S milliseconds.

Sometimnes I Have to run it twice to get started
 */

object Example4_BrownianMotion extends IOApp.Simple:

  val moves = 30 // number of moves to plot
  val steps = 2 // steps per move
  val meter = 100L // emit meter in Milliseconds

  def chart(data: Seq[(y: Int, x: Int)]) = (
    schema = "https://vega.github.io/schema/vega-lite/v5.json",
    description =
      "A step chart constrained to +/-20 for an easy demo of brownian motion. We'll replace the data values in place",
    data = (values = data),
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
  )

  def run =
    println("started")
    Stream
      .eval(Random.scalaUtilRandom[IO])
      .flatMap { random =>
        val next = random.nextBoolean.map(if (_) 1 else -1)
        val sample = Stream
          .eval(next)
          // .evalTap(i => IO.println(s"individual $i"))
          .repeatN(steps)
          .scan(0)(_ + _) // Accumulate the randomness in this "move of N steps"
          .drop(1) // Drop the first zero
          .compile
          .to(Chunk)

        Stream
          .repeatEval(sample)
          // .evalTap(i => IO.println(s"one Chunk $i"))
          .scan(List.fill[Int](steps)(0))((in, c) => c.toList.map(_ + in.last)) // Accumulate this move and prior move
          .metered(FiniteDuration(meter, TimeUnit.MILLISECONDS))
          .sliding(moves, 1)
          .map(c => c.toList.flatten)
          // .evalTap(IO.println)
          .evalTap(plotBrownian)
      }
      .compile
      .drain

  def plotBrownian(data: List[Int]) =
    val dataObj = data.zipWithIndex.map(d => (y = d._1, x = d._2))
    val charted = chart(dataObj)
    val asJson: Value = upickle.default.writeJs(charted)

    IO.pure(
      asJson.plot
    ).map { _ =>
      println("plotted")
    }
