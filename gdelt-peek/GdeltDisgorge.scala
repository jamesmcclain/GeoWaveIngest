package com.daystrom_data_concepts.gdelt

import mil.nga.giat.geowave.adapter.vector.FeatureDataAdapter
import mil.nga.giat.geowave.core.geotime.ingest._
import mil.nga.giat.geowave.core.store.operations.remote.options.DataStorePluginOptions
import mil.nga.giat.geowave.core.store.query.QueryOptions
import mil.nga.giat.geowave.datastore.accumulo._
import mil.nga.giat.geowave.datastore.accumulo.operations.config.AccumuloRequiredOptions
import mil.nga.giat.geowave.mapreduce.input.{GeoWaveInputKey, GeoWaveInputFormat}
import org.apache.hadoop.mapreduce.Job
import org.apache.log4j.Logger
import org.apache.spark.SparkConf
import org.apache.spark.SparkContext
import org.apache.spark.SparkContext._
import org.geotools.feature.simple._
import org.opengis.feature.simple._


object GdeltDisgorge {

  val log = Logger.getLogger(GdeltDisgorge.getClass)

  def getAccumuloOperationsInstance(
    zookeepers: String,
    accumuloInstance: String,
    accumuloUser: String,
    accumuloPass: String,
    geowaveNamespace: String
  ): BasicAccumuloOperations = {
    return new BasicAccumuloOperations(
      zookeepers,
      accumuloInstance,
      accumuloUser,
      accumuloPass,
      geowaveNamespace)
  }

  def main(args: Array[String]) : Unit = {
    if (args.length < 5) {
      log.error("Invalid arguments, expected: <Zookeepers> <AccumuloInstance> <AccumuloUser> <AccumuloPass> <GeoWaveNamespace>");
      System.exit(-1)
    }

    val sparkConf = new SparkConf().setAppName("GeoWaveInputFormat")
    val sparkContext = new SparkContext(sparkConf)

    val job = Job.getInstance(sparkContext.hadoopConfiguration)
    val config = job.getConfiguration
    val configOptions = {
      val aro = new AccumuloRequiredOptions
      aro.setZookeeper(args(0))
      aro.setInstance(args(1))
      aro.setUser(args(2))
      aro.setPassword(args(3))
      aro.setGeowaveNamespace(args(4))

      val dspo = new DataStorePluginOptions
      dspo.setFactoryOptions(aro)

      dspo.getFactoryOptionsAsMap
    }

    val basicOperations = getAccumuloOperationsInstance(args(0), args(1), args(2), args(3), args(4))
    val adapter = new FeatureDataAdapter(Gdelt.createGdeltFeatureType)
    val customIndex = (new SpatialDimensionalityTypeProvider).createPrimaryIndex

    GeoWaveInputFormat.setDataStoreName(config, "accumulo")
    GeoWaveInputFormat.setStoreConfigOptions(config, configOptions)
    // GeoWaveInputFormat.setQuery(config, new SpatialQuery(utah))
    GeoWaveInputFormat.setQueryOptions(config, new QueryOptions(adapter, customIndex))

    val array = sparkContext.newAPIHadoopRDD(config,
      classOf[GeoWaveInputFormat[SimpleFeature]],
      classOf[GeoWaveInputKey],
      classOf[SimpleFeature])
      .take(1)

    println(array.toList)
  }

}
