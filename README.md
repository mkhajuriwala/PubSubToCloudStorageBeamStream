# PubSubToCloudStorageBeamStream
Streaming data from PubSub to Cloud Storage using Apache Beam

java -jar target/pubsubToCloudStorageBeamStream-bundled-1.1.jar --inputTopicSubscription="projects/mkloud/subscriptions/pubSubInputStreamSubscription" --windowSize=2 --output="gs://mhk-dataflow-bucket/streamOutput" --jobName="StreamJob" --runner=DataflowRunner --gcpTempLocation="gs://mhk-dataflow-bucket/temp" --region=northamerica-northeast1 --outputTopic="projects/mkloud/topics/pubSubOutputStream" --zone=northamerica-northeast1-b

gcloud dataflow flex-template build gs://mhk-dataflow-bucket-1/dataflow/templates/pubsub-to-cloud-storage-stream-flex-template.json \
--image-gcr-path "northamerica-northeast1-docker.pkg.dev/mkloud/mkloud-artifacts/dataflow/pubsub-to-cloud-storage:latest" \
--sdk-language "JAVA" \
--flex-template-base-image JAVA11 \
--jar "target/pubsubToCloudStorageBeamStream-bundled-1.1.jar" \
--env FLEX_TEMPLATE_JAVA_MAIN_CLASS="com.kloudbuddy.stream.App"


gcloud dataflow flex-template run "pubsub-to-cloud-storage-`date +%Y%m%d-%H%M%S`" --template-file-gcs-location="gs://mhk-dataflow-bucket-1/dataflow/templates/pubsub-to-cloud-storage-stream-flex-template.json" --parameters inputTopicSubscription="projects/mkloud/subscriptions/pubSubInputStreamSubscription" --parameters outputTopic="projects/mkloud/topics/pubSubOutputStream" --parameters output="gs://mhk-dataflow-bucket-1/streamOutput" --enable-streaming-engine --service-account-email="dataflow-sa@mkloud.iam.gserviceaccount.com" --staging-location="gs://mhk-dataflow-bucket-1/pubsub-to-cloud-storage/staging/" --temp-location="gs://mhk-dataflow-bucket-1/pubsub-to-cloud-storage/tempLocation/" --region="us-central1" 
