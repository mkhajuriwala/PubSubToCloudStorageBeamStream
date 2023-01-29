# PubSubToCloudStorageBeamStream
Streaming data from PubSub to PubSub using Apache Beam

java -jar target/pubsubToCloudStorageBeamStream-bundled-1.1.jar --inputTopic="projects/mkloud/topics/pubSubStream" --windowSize=2 --output="gs://mhk-dataflow-bucket/streamOutput" --jobName="StreamJob" --runner=DataflowRunner --gcpTempLocation="gs://mhk-dataflow-bucket/temp" --region=northamerica-northeast2

java -jar target/pubsubToCloudStorageBeamStream-bundled-1.1.jar --inputTopic="projects/mkloud/subscriptions/pubSubInputStreamSubscription" --windowSize=2 --output="gs://mhk-dataflow-bucket/streamOutput" --jobName="StreamJob" --runner=DataflowRunner --gcpTempLocation="gs://mhk-dataflow-bucket/temp" --region=northamerica-northeast1 --outputTopic="projects/mkloud/topics/pubSubOutputStream"
