# scalac plugin for akka actors

Traverses source tree and extracts case statements in receive method

## Building (sbt 0.10.x)
    $ sbt package

## Using

Command line:

    $ scalac -Xplugin:path/to/akka-api/target/scala-2.9.0.1/akkaapi_2.9.0-1-0.1.0-SNAPSHOT.jar ...

sbt:

    scalacOptions   += "-Xplugin:path/to/akka-api/target/scala-2.9.0.1/akkaapi_2.9.0-1-0.1.0-SNAPSHOT.jar"

