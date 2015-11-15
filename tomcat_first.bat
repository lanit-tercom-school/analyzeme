@rem *****************************************
@rem Tomcat Build Script - first version
@rem *****************************************

@echo This batch file
@call before_deploy.bat
@call %CATALINA_HOME%\bin\startup.bat
@copy target\analyzeme.war %CATALINA_HOME%\webapps

@rem *****************************************
@rem Comments for pom.xml
@rem *****************************************
@rem Tomcat plugin needs configuring, this is a lot easier way to deploy
@rem *****************************************