import geotrellis.proj4._
import geotrellis.raster._
import geotrellis.raster.io.geotiff._
import geotrellis.raster.io.geotiff.tags.GeographicCSTypes._
import geotrellis.raster.rasterize.Rasterizer._
import geotrellis.raster.rasterize._
import geotrellis.raster.resample._
import geotrellis.vector._


object Resample extends App {

  val d = 10
  val (tileWidth, tileHeight) = (20, 20)
  val (xmin, xmax) = (0D, 100D)
  val (ymin, ymax) = (0D, 100D)
  val polygon = Polygon(Point(0, 0), Point(0, 100), Point(100, 50), Point(0, 0))
  val crs = CRS.fromEpsgCode(GCS_WGS_84)
  val rasterOptions = Options(includePartial = true, sampleType = PixelIsArea)

  val geoTiff = {
    val extent = Extent(xmin, ymin, xmax, ymax)
    val rasterExtent = RasterExtent(extent, tileWidth, tileHeight)
    val tile = DoubleArrayTile.empty(tileWidth, tileHeight)
    Rasterizer.foreachCellByPolygon(polygon, rasterExtent, rasterOptions) { (col: Int, row: Int) =>
      tile.setDouble(col, row, d)
    }
    GeoTiff(tile, extent, crs)
  }

  println("GeoTiff:")
  println(geoTiff.asciiDrawDouble(3))

  Seq(NearestNeighbor, Bilinear, CubicConvolution, CubicSpline, Lanczos) foreach { r =>
    val sourceExtent = geoTiff.extent
    val targetExtent = RasterExtent(sourceExtent, 10, 10)
    val tile = geoTiff.tile.resample(sourceExtent, targetExtent, r)
    val resampledGeoTiff = GeoTiff(tile, sourceExtent, crs)
    println(s"$r:")
    println(resampledGeoTiff.asciiDrawDouble(3))
  }

}
