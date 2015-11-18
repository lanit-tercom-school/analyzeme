Для использования .bat файлов локальной сборки, работающих с Jetty, необходимо:
 1. Установить maven:
	- скачать и распаковать архив apache-maven (тестировалось с Apache Maven 3.3.3)
	- проверить значение environment variable JAVA_HOME - она должна существовать и указывать на папку ${you_jdk_directory}
	- проверить значение environment variable PATH - в ней должно содержаться, среди прочего, указание на папку ${you_jdk_directory}\bin (крайне желательно, чтобы только одно: из-за значений, созданных до вас, например, Oracle, могут возникать ошибки) 
	- добавить в  environment variable PATH указание на папку ${your_maven_directory}\bin
 2. Установить Jetty:
	- скачать и распаковать архив со страницы Jetty Downloads
	WARNING: сборка написана для версии Jetty 9.3.6.v20151106. В случае использования другой версии нужно перед запуском билд-скрипта отредактировать pom.xml - см.
	<properties>
		<jetty.version>9.3.6.v20151106</jetty.version>
	 </properties>
 3. Запустить из командной строки (в папке Scripts) jetty.bat
 
 Строкой, означающей успешную работу скрипта, в командной строке должна быть: 
 [INFO] Started Jetty Server
 Результат работы скрипта можно увидеть по адресу http://localhost:8080/analyzeme/ (*)
 Для завершения работы скрипта - Ctrl+C в командной строке. Завершение работы скрипта автоматически сбрасывает проект с Jetty
 
 (*) при желании можно изменить в pom.xml - см. contextPath в:
  <plugin>
		<groupId>org.eclipse.jetty</groupId>
		<artifactId>jetty-maven-plugin</artifactId>
		<version>${jetty.version}</version>
		<configuration>
			<webApp>
				<contextPath>/analyzeme</contextPath>
			</webApp>
			<stopPort>9999</stopPort>
			<stopKey>foo</stopKey>
		</configuration>
  </plugin>