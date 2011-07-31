System Requirements
-----------------------
1) Java 1.6 or more
2) Maven version 3
3) Tomcat version 6 or more


How to install?
--------------
1) Checkout the source from google code project
svn checkout http://myjobs.googlecode.com/svn/trunk/ myjobs-read-only

2) Build the war
mvn clean install

3) Copy the target/myjobs.war into your tomcat/webapps directory
ALTERNATIVELY you can run the application from maven by issuing the command
mvn clean tomcat:runwar

How to use the system?
----------------------
1) Open the browser
http://127.0.0.1:8080/myjobs

2) Register a new user OR login with username/password roger/roger OR rafael/rafael


