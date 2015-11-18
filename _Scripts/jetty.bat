@rem *****************************************
@rem Jetty Build Script - first version
@rem *****************************************

@echo This batch file
@call src\before_deploy.bat
@mvn jetty:run

@rem *****************************************
@rem Comments for pom.xml
@rem *****************************************
@rem <properties> 
@rem		<jetty.version>9.3.6.v20151106</jetty.version>
@rem </properties>
@rem *****************************************
@rem <dependency>
@rem 	<groupId>org.eclipse.jetty</groupId>
@rem 	<artifactId>jetty-server</artifactId>
@rem 	<version>${jetty.version}</version>
@rem 	<scope>compile</scope>
@rem </dependency>
@rem <dependency>
@rem 	<groupId>org.eclipse.jetty</groupId>
@rem 	<artifactId>jetty-client</artifactId>
@rem 	<version>${jetty.version}</version>
@rem 	<scope>compile</scope>
@rem </dependency>
@rem <dependency>
@rem 	<groupId>org.eclipse.jetty</groupId>
@rem 	<artifactId>jetty-webapp</artifactId>
@rem 	<version>${jetty.version}</version>
@rem 	<scope>compile</scope>
@rem </dependency>
@rem *****************************************
@rem <plugin>
@rem 	<groupId>org.eclipse.jetty</groupId>
@rem 	<artifactId>jetty-maven-plugin</artifactId>
@rem 	<version>${jetty.version}</version>
@rem 	<configuration>
@rem 		<webApp>
@rem 			<contextPath>/analyzeme</contextPath>
@rem 		</webApp>
@rem 		<stopPort>9999</stopPort>
@rem 		<stopKey>foo</stopKey>
@rem 	</configuration>
@rem </plugin>
@rem <plugin>
@rem 	<groupId>org.apache.maven.plugins</groupId>
@rem 	<artifactId>maven-war-plugin</artifactId>
@rem 	<version>2.3</version>
@rem 	<configuration>
@rem 		<webXml>${basedir}/src/main/webapp/WEB-INF/web.xml</webXml>
@rem 	</configuration>
@rem </plugin>
@rem *****************************************