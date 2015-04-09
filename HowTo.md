README for checkout and run the system

## System Requirements ##

  * Java 1.6 or more
  * Maven Version 3
  * Tomcat version 6 or more


---


## How to install? ##
  * Checkout the source from google code project
` svn checkout http://myjobs.googlecode.com/svn/trunk/ myjobs-read-only `

  * Build the war
` mvn clean install `

  * Copy the target/myjobs.war into your tomcat/webapps directory
ALTERNATIVELY you can run the application from maven by issuing the command
` mvn clean tomcat:runwar `


---


## How to use the system? ##

  * Open the browser
http://127.0.0.1:8080/myjobs

  * Register a new user OR login with username/password roger/roger OR rafael/rafael