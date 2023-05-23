import viz.PlotTargets.desktopBrowser
import viz.*
import ujson.*
import viz.vega.plots.SpecUrl

case class FromFilePlot(path: os.Path, override val mods: Seq[ujson.Value => Unit] = List()) extends WithBaseSpec(mods)  :
  override lazy val baseSpec: ujson.Value = ujson.read(os.read(path))


/**
  * If you're coming from having some library of specs available, you can use them directly.
  * 
  * Any class that extends "WithBaseSpec" and overrides the "baseSpec" value, can be used inside the display mechanisms of dedav
  * 
  * The core plots make an HTTP request to the vega library, instead of reading a file. 
  */
@main
def example8_FromFile() =  
  FromFilePlot(os.pwd / "src" / "main" / "resources" / "radialPlot.vega.json", List(Utils.fillDiv))
