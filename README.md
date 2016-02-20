## Stopwatch
Simple and lightweight java performance measuring (stopwatch) library.

It works with taking measure points (like a real stopwatch) implemented in the executed code and then calculating the duration and in case of defined SLA (max execution time) even detecting bottlenecks.

[![Build Status](https://travis-ci.org/kobalj/stopwatch.svg?branch=master)](https://travis-ci.org/kobalj/stopwatch)
[![Coverage Status](https://coveralls.io/repos/github/kobalj/stopwatch/badge.svg?branch=master)](https://coveralls.io/github/kobalj/stopwatch?branch=master)
[![License](https://img.shields.io/badge/license-GPLv3-blue.svg)](LICENSE)
[![Maven Central](https://maven-badges.herokuapp.com/maven-central/si.kobalj.utilities/stopwatch/badge.svg)](https://maven-badges.herokuapp.com/maven-central/si.kobalj.utilities/stopwatch)

## Code Example

As first, get the library from maven central:
```xml
<dependency>
    <groupId>si.kobalj.utilities</groupId>
    <artifactId>stopwatch</artifactId>
    <version>1.0.1</version>
</dependency>
```

After that, the first step is to build the Stopwatch object (implemented are two stopwatch classes. A generic one and one that does duration  accumulation on the level of measure point name). The sample uses the generic one:

```Java
IStopWatch stopWatch = CStopWatchFactory.getStopWatchBuilder().build();

// in case we want global SLA (maximal allowed duration of execution in milliseconds):
IStopWatch stopWatch = CStopWatchFactory.getStopWatchBuilder().setGlobalSLA(50).build();

// if we want SLA for a specific measure point:
IStopWatch stopWatch = CStopWatchFactory.getStopWatchBuilder().setSLA("MARK1", 50).build();
```

After that we can add multiple measure points inside the code (the GLOBAL one should be used to measure the execution for the whole code):

```Java
stopWatch.startGlobal();

// execution

stopWatch.stopGlobal();
```

In between we can use as many measure points we want. The measure point name can be defined as wanted:

```Java
stopWatch.start("MARK1");

// execution

stopWatch.stop("MARK1");
```
Once the execution is done, we have to log the measure points into our logging facility. The library has implemented a generic performance logger that uses the java logging facility. Here a sample:

```Java
CGenericMetrcicsLogger logger = new CGenericMetrcicsLogger.CGenericMetrcicsLoggerBuilder().build();
logger.log(stopWatch);
```

The log then looks the following:   
PERF_LOG: GET_ACC=102ms;GET_CHRG=150ms;CALC_CHRG=350ms;GLOBAL=553ms

In addition to logging the stopwatch, we can add environment data to it:

```Java
CGenericMetrcicsLogger logger = new CGenericMetrcicsLogger.CGenericMetrcicsLoggerBuilder().build();
CEnvironmentData envData = new CEnvironmentData();
envData.addData("id", "U1212");
envData.addData("val", 123);
        
logger.log(stopWatch, envData);
```

The log then looks the following:   
PERF_LOG: GET_ACC=101ms;GET_CHRG=150ms;CALC_CHRG=350ms;GLOBAL=551ms|| ENV_DATA: val=123&id=U1212

## Licence

Licensed under the [GPLv3 license](LICENSE).