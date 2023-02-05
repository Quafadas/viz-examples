import viz.PlotTargets.desktopBrowser
import viz.*
import ujson.*

case class FromFilePlot(path: os.Path, override val mods: Seq[ujson.Value => Unit] = List()) extends WithBaseSpec(mods)  :
  override lazy val baseSpec: ujson.Value = ujson.read(os.read(path))

@main
def example8_FromFile() =  
  // just plot what's in the file...  
  FromFilePlot(os.pwd / "src" / "main" / "scala" / "bar.vega.json")

  // Or use the file as a scaffold, to pipe something else in
  val data = Arr(
    Obj("category" -> "Tee", "amount" -> 2.5), 
    Obj("category" -> "Hee", "amount" -> 3.5)
  
    )
  println(data)
  FromFilePlot(
    os.pwd / "src" / "main" / "scala" /"bar.vega.json", 
    List(
      (spec: Value) => spec("data")(0)("values") = data
    )
  )