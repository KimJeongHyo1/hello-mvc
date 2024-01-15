FROM tomcat:9-jdk17
COPY target/hello-mvc.war /usr/local/tomcat/webapps/mvc.war
COPY src/main/resources/configs/Wallet_G6TQWS0RD531W8SK /Wallet_G6TQWS0RD531W8SK