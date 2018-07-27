package com.samaitra

import org.apache.flink.api.scala._
import org.apache.flink.streaming.api.scala.StreamExecutionEnvironment
import org.apache.flink.streaming.api.scala.extensions._
import org.apache.ignite.sink.flink.IgniteSink

import scala.collection.JavaConverters._


object WordCount {

    def main(args: Array[String]) {

        val env = StreamExecutionEnvironment.getExecutionEnvironment

        val igniteSink = new IgniteSink[java.util.Map[String, Int]]("testCache", "example-ignite.xml")

        igniteSink.setAllowOverwrite(true)
        igniteSink.setAutoFlushFrequency(1)

        // get input data
        val text = env.fromElements(
            "hello hello")


        val counts = text
            // split up the lines in pairs (2-tuples) containing: (word,1)
            .flatMap(_.toLowerCase.split("\\W+"))
            .filter(_.nonEmpty)
            .map((_, 1))
            // group by the tuple field "0" and sum up tuple field "1"
            .keyBy(0)
            .sum(1)
            // Convert to key/value format before ingesting to Ignite
            .mapWith { case (k: String, v: Int) => Map(k -> v).asJava }

        //counts.print()
        counts.addSink(igniteSink)

        try
            env.execute("Streaming WordCount1")
        catch {
            case e: Exception =>

            // Exception handling.
        } finally igniteSink.close()

    }
}