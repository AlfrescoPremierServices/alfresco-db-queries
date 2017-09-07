# Alfresco Database Queries Report

Alfresco Database Queries Report is a Spring-Boot application developed to run a set of pre-defined database queries against Alfresco Content Services application. The following reports can be generated:
  - Identify Database Size
  - Identify Large Folders
  - Identify Large Transactions
  - List Nodes by MimeType and Disk Space
  - List Nodes by Contet Store
  - List Nodes by Content Type
  - List Site Activities
  - List Workflows and Tasks
  - Solr Memory Configuration
  - JMX changes
  - Export Report

  Works with Postgres, MySQL, Oracle and MS-SQL databases.

### Technologies

This project has been developed with a number of open source projects such as:
  - Built using Maven
  - Developed using Spring-Boot (https://projects.spring.io/spring-boot/)
  - Using mybatis for SQL mapping
  - Using Thymeleaf server-side Java template engine for manipulating object on HTML pages (http://www.thymeleaf.org/)
  - Using Bootstrap for Front-End Web development (http://getbootstrap.com/)
  - Datatables for table formatting (https://datatables.net/)


### Installation

Alresco DB Queries project requires Maven and java JDK to build. The build process generates a jar file that can be executed as a stand-alone application.

The steps to build the application are:
 - Download the application - https://git.alfresco.com/premier/alfresco-db-queries/repository/archive.zip?ref=master
 - Unzip it
 - Rename folder to "alfresco-db-queries"
 - Adjust alfresco-db-queries/src/main/resources/application.properties file
    - Set database details
    - Set port for web server
    - Adjust Solr cache values coming from solrcore.properties
    - Set alf_auth_status to false if table does not exists in your database
 - Compile and build application

### Compiling and building the executable jar file
Compile and build the application using Maven
```sh
$ cd alfresco-db-queries
$ mvn install:install-file -Dfile=./lib/ojdbc6.jar -DgroupId=com.oracle -DartifactId=ojdbc6 -Dversion=11.2.0.3 -Dpackaging=jar
$ mvn compile install
```

The generated jar file should be located in the target folder i.e. target/alfresco-db-0.0.1-SNAPSHOT.jar.

### Running the application

Copy the application.properties file to the same folder as the jar file and execute the jar file:

```sh
$ cd target
$ java -jar alfresco-db-0.0.1-SNAPSHOT.jar
```
Finally connect to the running application on [http://localhost:8888](http://localhost:8888) or the port specified by "server.port" parameter in application.properties. 

**Note** : When providing the application to other parties make sure you provide both the jar file and the application.properties file.

### Screenshots

- Home Page

![alt text](images/home.png)

- Database Size Report

![alt text](images/db-info.png)


### Todos

 - Write additional queries
 - Testing, testing and more testing

