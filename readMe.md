# analyzeme [![Build Status](https://travis-ci.org/lanit-tercom-school/analyzeme.svg?branch=dev)](https://travis-ci.org/lanit-tercom-school/analyzeme) [![codecov.io](https://codecov.io/github/lanit-tercom-school/analyzeme/coverage.svg?branch=dev)](https://codecov.io/github/lanit-tercom-school/analyzeme?branch=dev) [![Codacy Badge](https://api.codacy.com/project/badge/Grade/21aa2175753b48be8fd0609f720b141a)](https://www.codacy.com/app/lanit-tercom-school/analyzeme?utm_source=github.com&amp;utm_medium=referral&amp;utm_content=lanit-tercom-school/analyzeme&amp;utm_campaign=Badge_Grade)

####Сборка проекта под Windows

#####Перед использованием билд-скриптов

Установить maven:

1. скачать и распаковать архив apache-maven (тестировалось с Apache Maven 3.3.3) http://maven.apache.org/download.cgi

2. проверить значение environment variable JAVA\_HOME - она должна существовать и указывать на папку ${you\_jdk\_directory}

3. проверить значение environment variable PATH - в ней должно содержаться, среди прочего, указание на папку ${you\_jdk\_directory}\bin (крайне желательно, чтобы только одно: из-за значений, созданных до вас, например, Oracle, могут возникать ошибки)

4. добавить в  environment variable PATH указание на папку ${your\_maven\_directory}\bin

#####Сборка с использованием tomcat

1.  Установить tomcat
    * скачать (http://tomcat.apache.org/download-80.cgi) и распаковать архив apache-tomcat (тестировалось с Apache Tomcat 7.0.65)
    *  установить значение environment variable CATALINA\_HOME, указание на распакованный архив

2.  Запустить из командной строки в **папке, содержащей pom.xml**
    * scripts\tomcat.bat - результат будет доступен по адресу localhost:8080/analyzeme до принудительного андеплоя
    * scripts\tomcat.bat contextPath - результат будет доступен по адресу localhost:8080/contextPath (contextPath должна быть аналогична названию файла, конструкции типа sth/sth не поддерживаются)

ИЛИ (когда tomcat установлен)
    
Задайте значение переменной **projectFolder** (опционально) в скрипте **scripts\tomcatDeployScript.bat** и запустите его из папки **/scripts/**
   * **projectFolder** - папка, в которую будет деплоиться проект(например, при значении **'analyzeme'** будет деплоить в **localhost:8080/analyzeme/**)
   * projectFolder изначально имеет значение ROOT, что означает деплой в **localhost:8080/**


#####Сборка с использованием jetty

1.  Установить jetty
     * скачать и распаковать архив http://www.eclipse.org/jetty/downloads.php
Cборка написана для **версии** Jetty 9.3.6.v20151106. В случае использования другой версии нужно перед запуском билд-скрипта отредактировать pom.xml - см.
 ```
 <properties>
         <jetty.version>9.3.6.v20151106</jetty.version>
  </properties>
 ```
2. Запустить из командной строки  в **папке, содержащей pom.xml**
    * scripts\jetty.bat - результат будет доступен по адресу localhost:default_jetty_port/analyzeme (обычно default_jetty_port - 8080)

    * scripts\jetty.bat port - результат будет доступен по адресу localhost:port/analyzeme
    
При возникновении ошибки 404 при заходе на страницу проекта, нужно поменять в файле pom.xml настройки jetty - установить webapp.contextPath в '/' 

####Сборка проекта под Linux

#####Перед использованием билд-скриптов

Установить maven:

sudo apt-get install maven

#####Сборка с использованием tomcat

1. Установить tomcat

   https://www.digitalocean.com/community/tutorials/how-to-install-apache-tomcat-8-on-ubuntu-16-04

2. Выполнить следующие команды:

   mvn clean install -U

   sudo $CATALINA_HOME/bin/startup.sh

   sudo cp target/analyzeme.war $CATALINA_HOME/webapps

analyzeme
2015
