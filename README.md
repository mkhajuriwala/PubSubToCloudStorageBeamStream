# PubSubToCloudStorageBeamStream
Streaming data from PubSub to Cloud Storage using Apache Beam

java -jar target/pubsubToCloudStorageBeamStream-bundled-1.1.jar --inputTopic="projects/mkloud/topics/pubSubStream" --windowSize=2 --output="gs://mhk-dataflow-bucket/streamOutput" --jobName="StreamJob" --runner=DataflowRunner --gcpTempLocation="gs://mhk-dataflow-bucket/temp" --region=northamerica-northeast2

mvn compile exec:java \
-Dexec.mainClass=com.examples.pubsub.streaming.PubSubToGcs \
-Dexec.cleanupDaemonThreads=false \
-Dexec.args=" \
--project=$PROJECT_ID \
--region=$REGION \
--inputTopic=projects/$PROJECT_ID/topics/$TOPIC_ID \
--output=gs://$BUCKET_NAME/samples/output \
--runner=DataflowRunner \
--windowSize=2"