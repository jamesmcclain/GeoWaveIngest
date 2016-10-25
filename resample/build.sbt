name := "resample"

libraryDependencies ++= Seq(
  "com.azavea.geotrellis" %% "geotrellis-geotools" % "1.0.0-SNAPSHOT",
  "com.azavea.geotrellis" %% "geotrellis-proj4" % "1.0.0-SNAPSHOT",
  "com.azavea.geotrellis" %% "geotrellis-raster" % "1.0.0-SNAPSHOT",
  "com.azavea.geotrellis" %% "geotrellis-spark" % "1.0.0-SNAPSHOT",
  "com.azavea.geotrellis" %% "geotrellis-vector" % "1.0.0-SNAPSHOT"
)

fork in Test := false
parallelExecution in Test := false
