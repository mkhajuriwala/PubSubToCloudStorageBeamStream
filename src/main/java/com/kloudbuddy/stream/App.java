package com.kloudbuddy.stream;

import org.apache.beam.sdk.Pipeline;
import org.apache.beam.sdk.io.gcp.pubsub.PubsubIO;
import org.apache.beam.sdk.options.PipelineOptionsFactory;
import org.apache.beam.sdk.transforms.MapElements;
import org.apache.beam.sdk.transforms.windowing.FixedWindows;
import org.apache.beam.sdk.transforms.windowing.Window;
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
        pipeline
                // 1) Read string messages from a Pub/Sub topic.
                .apply("Read PubSub Messages", PubsubIO.readStrings().fromSubscription(options.getInputTopic()))
                .apply(MapElements.into(TypeDescriptor.of(String.class))
                        .via(input -> input.toLowerCase().concat(String.valueOf(System.currentTimeMillis()))))
                // 2) Group the messages into fixed-sized minute intervals.
                //.apply(Window.into(FixedWindows.of(Duration.standardMinutes(options.getWindowSize()))))
                // 3) Write one file to GCS for every window of messages.
                .apply("Publish to pubsub",PubsubIO.writeStrings().to(options.getOutputTopic()));
        //.apply("Write Files to GCS", newWriteOneFilePerWindow(options.getOutput(), numShards));
        // Execute the pipeline and wait until it finishes running.
        pipeline.run().waitUntilFinish();
    }
}
