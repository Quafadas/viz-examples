import java.awt.Desktop
import java.io.File

/*

There's kind of a pattern to what we've done so far, find some existing spec, change it a bit, and plot. 

here is another example that follows that exact pattern - let's get some spec from an example on the internet, and pump it into a temp file
which we open in a browser

*/
@main
def example3_Abstractions() =  
  val aSpec = ujson.read(requests.get("https://vega.github.io/vega/examples/bar-chart.vg.json").text())
  
  aSpec("data")(0)("values") = ujson.Arr(
      ujson.Obj("category" -> "testing", "amount" -> 5),
      ujson.Obj("category" -> "testing1", "amount" -> 6),
  )
  val f = os.temp( header(aSpec, "Testing"), suffix = ".html", deleteOnExit=true )
  openBrowserToFile(f.toIO)
  

// Some helpers
def openBrowserToFile(f: File) = Desktop.getDesktop().browse(f.toURI())

def header(spec : ujson.Value, title: String = "") = {
	
	raw"""<!DOCTYPE html>
	<html>
	<head>
	  <!-- Import Vega & Vega-Lite (does not have to be from CDN) -->
	  <script src="https://cdn.jsdelivr.net/npm/vega@5"></script>
	  <script src="https://cdn.jsdelivr.net/npm/vega-lite@5"></script>
	  <!-- Import vega-embed -->
	  <script src="https://cdn.jsdelivr.net/npm/vega-embed@5"></script>
	  <style>
		div#vis {
			width: 95vw;
			height:90vh;
			style="position: fixed; left: 0; right: 0; top: 0; bottom: 0"
		}	
		div#title {
			width: 100vw;
			height: 5vh;
			text-align: center;
		}
	</style>
	<title>$title</title>
	  
	</head>
	<body>

		<div id="title"><h1>$title</h1></div>
		<div id="vis"></div>

	<script type="text/javascript">
	  const spec = ${ujson.write(spec, indent=2)};  
	  const view = new vega.View(vega.parse(spec), {
		renderer: "canvas", // renderer (canvas or svg)
		container: "#vis", // parent DOM container
		hover: true // enable hover processing
	  });
	  view.runAsync();
	</script>
	</body>
	</html> """
}