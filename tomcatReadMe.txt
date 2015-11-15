Для использования .bat файлов локальной сборки, работающих с Tomcat, необходимо:
 1. Установить maven:
	- скачать и распаковать архив apache-maven (тестировалось с Apache Maven 3.3.3)
	- проверить значение environment variable JAVA_HOME - она должна существовать и указывать на папку ${you_jdk_directory}
	- проверить значение environment variable PATH - в ней должно содержаться, среди прочего, указание на папку ${you_jdk_directory}\bin (крайне желательно, чтобы только одно: из-за значений, созданных до вас, например, Oracle, могут возникать ошибки) 
	- добавить в  environment variable PATH указание на папку ${your_maven_directory}\bin
 2. Установить Tomcat:
	- скачать и распаковать архив apache-tomcat (тестировалось с Apache Tomcat 7.0.65)
	- установить значение environment variable CATALINA_HOME, указание на распакованный архив
 3. Запустить из командной строки
	- для деплоя на http://localhost:8080/analyzeme/ : tomcat_first.bat
	- для деплоя на на http://localhost:8080/contextPath/ : tomcat_ultimate.bat (желаемая contextPath запрашивается во время выполнения .bat файла)
	WARNING: contextPath для стартовой страницы проекта должна состоять из одного имени (аналогично имени .war файла), поддержка конструкций типа http://localhost:8080/sth/sth2/... может быть добавлена по запросу
 
 Результат работы доступен по указанному адресу до принудительного андеплоя
