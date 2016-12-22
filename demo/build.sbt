name := "demo"
libraryDependencies ++= Seq(
  "org.locationtech.geotrellis" %% "geotrellis-accumulo" % "1.0.0-SNAPSHOT",
  "org.locationtech.geotrellis" %% "geotrellis-geotools" % "1.0.0-SNAPSHOT",
  "org.locationtech.geotrellis" %% "geotrellis-geowave" % "1.0.0-SNAPSHOT",
  "org.locationtech.geotrellis" %% "geotrellis-s3" % "1.0.0-SNAPSHOT",
  "org.locationtech.geotrellis" %% "geotrellis-spark" % "1.0.0-SNAPSHOT",
  "org.locationtech.geotrellis" %% "geotrellis-vector" % "1.0.0-SNAPSHOT",
  "mil.nga.giat" % "geowave-adapter-raster" % "0.9.3" % "provided",
  "mil.nga.giat" % "geowave-core-store" % "0.9.3" % "provided",
  "mil.nga.giat" % "geowave-datastore-accumulo" % "0.9.3" % "provided"
  exclude("javax.servlet", "servlet-api")
  exclude("org.mortbay.jetty", "servlet-api"),
  "org.apache.hadoop" % "hadoop-client" % Version.hadoop % "provided",
  "org.apache.spark" %% "spark-core" % Version.spark % "provided"
)

fork in Test := false
parallelExecution in Test := false
