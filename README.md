# Alfresco Database Queries Report

Alfresco Database Queries Report is a Spring-Boot application developed to run a set of pre-defined database queries against Alfresco Content Services application. Example of such queries are:

  - Identify Database Size
  - Identify Large Folders
  - Identify Large Transactions
  - List Nodes by MimeType and Disk Space
  - List Nodes by Contet Store
  - List Nodes by Content Type
  - List Site Activities
  - List Workflows and Tasks
  - Solr Memory Configuration
  - Export Report
  - etc

  Works with Postgres, MySQL, Oracle and MS-SQL databases.

### Technologies

This project uses a number of open source projects such as:

  - Build using Maven
  - Developed using Spring-Boot (https://projects.spring.io/spring-boot/)
  - Using Thymeleaf server-side Java template engine for manipulating object on HTML pages (http://www.thymeleaf.org/)
  - Using Bootstrap for Front-End Web development (http://getbootstrap.com/)

### Installation

Alresco DB Queries project requires Maven to build. The build process generates a jar file that can be executed as a stand-alone application.

The steps to build the application are:
 - Download the application - https://git.alfresco.com/premier/alfresco-db-queries/repository/archive.zip?ref=master
 - Unzip it
 - Adjust application.properties file
        Set database details
        Set port for web server
 - Compile it


Compile the project using Maven
```sh
$ cd alfresco-db-queries
$ mvn clean install
```

The generated jar file should be located in the target folder i.e. target/alfresco-db-0.0.1-SNAPSHOT.jar
Copy the application.properties file to the same folder as the jar file and execute the jar file using:

```sh
$ java -jar target/alfresco-db-0.0.1-SNAPSHOT.jar
```

When providing the application to other parties make sure you provide both the jar file and the application.properties file.
### Todos

 - Write additional queries
 - Testing, testing and more testing

