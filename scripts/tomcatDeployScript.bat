@REM *************************
@REM Deploy script for Tomcat
@REM *************************
@ECHO OFF
@REM *************************
@REM project will deploy in localhost:8080/%projectFolder%
SET projectFolder="ROOT"
@REM *************************
@RD /S /Q "%CATALINA_HOME%\webapps\%projectFolder%"
@RD /s /Q "%CATALINA_HOME%\work\Catalina\localhost\%projectFolder%"
del "%CATALINA_HOME%\webapps\%projectFolder%.war"
@ECHO Tomcat's %projectFolder% cleared

cd ..
@ECHO Wanna start build?
pause
@echo Deploying to localhost:8080/%projectFolder%
@call mvn clean install -U
@call %CATALINA_HOME%\bin\startup.bat
@copy target\analyzeme.war %CATALINA_HOME%\webapps
@rename %CATALINA_HOME%\webapps\analyzeme.war %projectFolder%.war
