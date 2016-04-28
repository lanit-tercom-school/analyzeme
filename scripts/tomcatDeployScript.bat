@REM ********************
@REM Deploy script for Tomcat
@REM ********************
@ECHO OFF
SET projectFolder="ROOT"
@RD /S /Q "%CATALINA_HOME%\webapps\%projectFolder%"
@RD /s /Q "%CATALINA_HOME%\work\Catalina\localhost\%projectFolder%"
del "%CATALINA_HOME%\webapps\%projectFolder%.war"
@ECHO Tomcat's %projectFolder% cleared
@REM path to your project:
SET analyzeme="C:\Repositories\analyzeme\"

CD /D "%analyzeme%"
@ECHO Wanna start build?
pause
@echo Deploying to localhost:8080/%projectFolder%
@call mvn clean install -U
@call %CATALINA_HOME%\bin\startup.bat
@copy target\analyzeme.war %CATALINA_HOME%\webapps
@rename %CATALINA_HOME%\webapps\analyzeme.war %projectFolder%.war
