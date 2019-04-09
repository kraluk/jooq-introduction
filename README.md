# jOOQ Playground

## Technicalities

Stuff used in this project:
* Gradle 5
* **Java 11**
* Ratpack
* jOOQ
* H2 + [Sakila DB schema][1]
* Spock Framework
* for more details, please take a look at the `build.gradle` file :)

## How to run it?

1. Generate jOOQ's schema:
   ```bash
   ./gradlew generateSakilaJooqSchemaSource
   ```
   If there will be need to regenerate the schema by each build, comment out or remove line `project.tasks.getByName("compileJava").dependsOn -= "generateSakilaJooqSchemaSource"` from the `jooq.gradle` file located in the root of this project.

2. Run the app using Ratpack Gradle plugin:
   ```bash
   ./gradlew run
   ```
   or to run in the [reload mode][2] (not working well under Java 9+):
   ```bash
   ./gradlew run -t
   ```
   
## Possible problems running the app on Java 9+

1. As [Ratpack][3] uses [Guice][4] and [Netty][5], they can complain a lot complain due to some Java 9+ limitations and to bypass them, there is a need to use the following JVM parameters:
   ```text
   --add-opens java.base/java.lang=ALL-UNNAMED
   --add-exports java.base/jdk.internal.misc=ALL-UNNAMED
   --illegal-access=warn   
   -Dio.netty.tryReflectionSetAccessible=true
   ```
   Running the app via Gradle plugin should not require using these parameters explicit because they are already configured in the `build.gradle` configuration. 

[1]: https://www.jooq.org/sakila
[2]: https://ratpack.io/manual/current/gradle.html#development_time_reloading
[3]: https://ratpack.io/manual/current/java9-support.html
[4]: https://www.github.com/google/guice/issues/1133
[5]: https://www.github.com/ratpack/ratpack/issues/1410