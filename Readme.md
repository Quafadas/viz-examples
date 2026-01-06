# Plotting stuff in scala

This repository is a record and examples of my random hobby project of on-off thinking about plotting stuff in scala.

Almond workbooks don't work yet whilst we wait for scala 3.7.0.

```cs launch --fork almond:0.14.1 --scala 3.6.3 -- --force --install```

For the others, they run best in scala-cli, you'll need, well...
[scala-cli](https://scala-cli.virtuslab.org)

`scala-cli run -w . --main-class Example_1`

For websockets;
```coursier launch io.github.quafadas:dedav4s_3:0.9.0 -M viz.websockets.serve -- 8085```

to convert a notebook to a presentation.

```sh
./nbconvert/bin/jupyter-nbconvert --to slides --theme=dark notebooks/Example_5.ipynb --post serve --TagRemovePreprocessor.enabled=True --TagRemovePreprocessor.remove_cell_tags remove_cell --TagRemovePreprocessor.remove_all_outputs_tags remove_output
```
Customisation. note that ipynb is just JSON. You can add the following JSON snippets to cells to control slide type and output removal.

```json
{
  "cell_type": "code",
  "execution_count": 26,
  "metadata": {
    "slideshow": {
      "slide_type": "subslide"
    },
    "tags": [
      "remove_output"
    ]
  },
  "outputs": []
}
```
