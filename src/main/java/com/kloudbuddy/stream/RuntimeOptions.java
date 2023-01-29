package com.kloudbuddy.stream;

import jdk.jfr.Description;
import org.apache.beam.runners.dataflow.options.DataflowPipelineOptions;
import org.apache.beam.sdk.options.Default;
import org.apache.beam.sdk.options.PipelineOptions;
import org.apache.beam.sdk.options.StreamingOptions;
import org.apache.beam.sdk.options.Validation;

public interface RuntimeOptions extends PipelineOptions, StreamingOptions {

    @Description("The Cloud Pub/Sub topic to read from.")
    @Validation.Required
    String getInputTopic();
    void setInputTopic(String value);
    @Description("Output file's window size in number of minutes.")
    @Default.Integer(1)
    Integer getWindowSize();
    void setWindowSize(Integer value);
    @Description("Path of the output file including its filename prefix.")
    @Validation.Required
    String getOutput();
    void setOutput(String value);
    @Description("The Cloud Pub/Sub topic to write to.")
    @Validation.Required
    String getOutputTopic();
    void setOutputTopic(String value);
}
