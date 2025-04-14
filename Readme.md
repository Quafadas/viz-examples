# Plotting stuff in scala

This repository is a record and examples of my random hobby project of on-off thinking about plotting stuff in scala.

Almond workbooks don't work yet whilst we wait for scala 3.7.0.

```cs launch --fork almond:0.14.1 --scala 3.6.3 -- --force --install```

For the others, they run best in scala-cli, you'll need, well...
[scala-cli](https://scala-cli.virtuslab.org)

`scala-cli run -w . --main-class Example_1`

For websockets;
```coursier launch io.github.quafadas:dedav4s_3:0.9.0 -M viz.websockets.serve -- 8085```

