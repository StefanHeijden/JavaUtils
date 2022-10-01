### Build Java code
---

`mvn clean install`

The install goal will compile, test, and package your project’s code and then copy it into the local dependency repository, ready for another project to reference it as a dependency.

Speaking of dependencies, now it’s time to declare dependencies in the Maven build.

### Run project
---
+ To run this project run the following command.

    `java -cp target/java-utils-0.1.0.jar utilities.StartClass`
