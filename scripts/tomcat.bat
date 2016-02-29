@rem *****************************************
@rem Tomcat Build Script - third version
@rem *****************************************

@echo This batch file
@if "%~1"=="" (Goto :default)
@if not "%~1"=="" (Goto :yourPath)

:default
@call src\before_deploy.bat
@call %CATALINA_HOME%\bin\startup.bat
@copy target\analyzeme.war %CATALINA_HOME%\webapps
@Goto :finish

:yourPath
@set project_name=%~1
@echo Deploying to localhost:8080/%project_name%
@call src\before_deploy.bat
@call %CATALINA_HOME%\bin\startup.bat
@copy target\analyzeme.war %CATALINA_HOME%\
@rename %CATALINA_HOME%\analyzeme.war %project_name%.war
@move %CATALINA_HOME%\%project_name%.war %CATALINA_HOME%\webapps

:finish

@rem *****************************************
@rem Comments for pom.xml
@rem *****************************************
@rem Tomcat plugin needs configuring, it is a lot easier way to deploy
@rem *****************************************
