```
$SPARK_HOME/bin/spark-submit \
   --class geotrellis.spark.etl.MultibandIngest \
   --master 'local[*]' \
   --driver-memory 16G \
   geotrellis-spark-etl-assembly-1.0.0-SNAPSHOT.jar \
   --input "file:///tmp/json/input.json" \
   --output "file:///tmp/json/output.json" \
   --backend-profiles "file:///tmp/json/backend-profiles.json"
```

```
$SPARK_HOME/bin/spark-submit \
   --class com.azavea.geotrellis.scratch.Scratch \
   --master 'local[*]' \
   --driver-memory 16G \
   scratch/target/scala-2.11/scratch-assembly-0.jar /tmp/catalog iraq 12
```
