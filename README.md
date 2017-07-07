# dragonfruit-server
火龙果-服务端

##Base Architecture
Java restful web service

1.Database:	mongodb

2.servlet container: org.eclipse.Jetty

3.restful service: JBoss restEasy

4.bean container: spring-bean

5.java runtime:	java8

6.log framework: slf4j + logback

##Required Environments

1.Mongodb service

2.jre version 1.8

3.maven building tool

##How To USE

1.Clone the code into your computer.

2.Locate to the project directory. 

3.Execute command [ mvn install -Dmaven.test.skip=true ].

4.Extract the tar.gz file in directory /target/ .

5.Locate to the extracted directory /conf/ , modify the configuration file [ AppConfig.properties ] with your properties.

6.Locate to the extracted directory / execute the script startup.bat/startup.sh in console.