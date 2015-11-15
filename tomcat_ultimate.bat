@rem *****************************************
@rem Tomcat Build Script - second version
@rem *****************************************

@echo This batch file
@set /p project_name="Enter the path to deploy (Example: analyzeme) "
@echo Deploying to localhost:8080/%project_name%
@call before_deploy.bat
@call %CATALINA_HOME%\bin\startup.bat
@copy target\analyzeme.war %CATALINA_HOME%\
@rename %CATALINA_HOME%\analyzeme.war %project_name%.war
@move %CATALINA_HOME%\"%project_name%".war %CATALINA_HOME%\webapps

@rem *****************************************
@rem Comments for pom.xml
@rem *****************************************
@rem Tomcat plugin needs configuring, it is a lot easier way to deploy
@rem *****************************************
