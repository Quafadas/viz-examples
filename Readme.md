# Plotting stuff in scala

My hobby project of on-off thinking about plotting stuff in scala. To install almond for the notebooks.

```cs launch --fork almond:0.14.5 --scala 3.8.4 -- --force --install```

To get LSP support worrking the notebooks, install this VS Code extension

https://github.com/Quafadas/Almond_Mill_Experiment/releases/latest

For the others, they run best in scala-cli, you'll need, well...
[scala-cli](https://scala-cli.virtuslab.org)

`scala-cli run -w . --main-class Example_1`

For websockets;
```cs launch io.github.quafadas:dedav4s_3:0.10.5 -M viz.websockets.serve -- 8085```

to convert a notebook to a presentation.

```sh
./nbconvert/bin/jupyter-nbconvert --to slides --theme=dark notebooks/titanic.ipynb --post serve --TagRemovePreprocessor.enabled=True --TagRemovePreprocessor.remove_cell_tags remove_cell --TagRemovePreprocessor.remove_all_outputs_tags remove_output
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
