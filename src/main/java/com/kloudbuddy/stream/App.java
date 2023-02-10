package com.kloudbuddy.stream;

import org.apache.beam.sdk.Pipeline;
import org.apache.beam.sdk.io.gcp.pubsub.PubsubIO;
import org.apache.beam.sdk.io.gcp.pubsub.PubsubMessage;
import org.apache.beam.sdk.options.PipelineOptionsFactory;
import org.apache.beam.sdk.transforms.MapElements;
import org.apache.beam.sdk.transforms.windowing.FixedWindows;
import org.apache.beam.sdk.transforms.windowing.Window;
import org.apache.beam.sdk.values.PCollection;
import org.apache.beam.sdk.values.TypeDescriptor;
import org.joda.time.Duration;

import java.util.Locale;

public class App {
    public static void main(String[] args){
        int numShards = 1;
        RuntimeOptions options =
                PipelineOptionsFactory.fromArgs(args).withValidation().as(RuntimeOptions.class);
        options.setStreaming(true);
        Pipeline pipeline = Pipeline.create(options);
        PCollection<String> pubSubMessages = pipeline
                // 1) Read string messages from a Pub/Sub topic.
                .apply("Read PubSub Messages", PubsubIO.readStrings().fromSubscription(options.getInputTopicSubscription()));
                // 2.1.1) Add timestamp to String
        pubSubMessages.apply("Add timestamp to Payload",MapElements.into(TypeDescriptor.of(String.class))
                        .via(input -> input.toLowerCase().concat(String.valueOf(System.currentTimeMillis()))))
                // 2.1.2) Publish Messages to PubSub
                .apply("Publish to pubsub",PubsubIO.writeStrings().to(options.getOutputTopic()));
                // 2.2.1) Group the messages into fixed-sized minute intervals.
                pubSubMessages.apply("Group messages into Fixed Windows",Window.into(FixedWindows.of(Duration.standardMinutes(options.getWindowSize()))))
                // 2.2.2) Write one file to GCS for every window of messages.
                .apply("Write Files to GCS", new WriteOneFilePerWindow(options.getOutput(), numShards));
        // Execute the pipeline and wait until it finishes running.
        pipeline.run().waitUntilFinish();
    }
}
