# flink-fn
A collection of flink functions


### Setup: Download and Start Flink

Download a binary from the downloads page. You can pick any Hadoop/Scala combination you like. If you plan to just use the local file system, any Hadoop version will work fine.
Go to the download directory.

### Unpack the downloaded archive.
```
$ cd ~/Downloads        # Go to download directory
$ tar xzf flink-*.tgz   # Unpack the downloaded archive
$ cd flink-1.5.0
```

### Start a Local Flink Cluster
```
$ ./bin/start-cluster.sh  # Start Flink
```
Check the Dispatcher’s web frontend at http://localhost:8081 and make sure everything is up and running. The web frontend should report a single available TaskManager instance.

Dispatcher: Overview

You can also verify that the system is running by checking the log files in the logs directory:
```
$ tail log/flink-*-standalonesession-*.log
```

### Build the Flink program :
```
$ mvn clean package
```

### Submit the Flink program :
```
$ ./bin/flink run flink-fn-1.0-SNAPSHOT.jar

The .out file will print the counts at the end of each time window as long as words are floating in, e.g.:
```
$ tail -f log/flink-*-taskexecutor-*.out
hello : 2
```