# Alfresco Database Queries Report

Alfresco Database Queries Report is a Spring-Boot application to run a set of pre-defined database queries against Alfresco Content Services application. Example of such queries are:

  - Identify Large Folders
  - Identify Large Transactions
  - List Nodes by MimeType and Disk Space
  - List Nodes by Contet Store
  - List Nodes by Content Type
  - List Site Activities
  - Export Reports to File
  - etc

### Tech

This project uses a number of open source projects to work properly:

  - Build using Maven
  - Developed using Spring-Boot (https://projects.spring.io/spring-boot/)
  - Using Thymeleaf server-side Java template engine for manipulating object on HTML pages (http://www.thymeleaf.org/)
  - Works with Postgres, MySQL, Oracle and MS-SQL databases...or at least it will do when finished
  - Using Bootstrap for Front-End Web development (http://getbootstrap.com/)

### Installation

Alresco DB Queries project requires Maven to build. Once built it generates a jar file that can be executed as a stand-alone application.
Before building the executable jar file adjust the application settings in application.properties file.
Build the project using Maven


```sh
$ cd alfresco-db-queries
$ mvn clean install
```

The generated jar file should be located in the target folder i.e. target/alfresco-db-0.0.1-SNAPSHOT.jar
To execute the jar file use:

```sh
$ java -jar target/alfresco-db-0.0.1-SNAPSHOT.jar
```

### Todos

 - Write additional queries
 - Convert queries to other database languages
 - Create export report

License
----

MIT


**Free Software, Hell Yeah!**
