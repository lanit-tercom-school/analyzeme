ƒл€ использовани€ .bat файлов локальной сборки, работающих с Tomcat, необходимо:
 1. ”становить maven:
	- скачать и распаковать архив apache-maven (тестировалось с Apache Maven 3.3.3)
	- проверить значение environment variable JAVA_HOME - она должна существовать и указывать на папку ${you_jdk_directory}
	- проверить значение environment variable PATH - в ней должно содержатьс€, среди прочего, указание на папку ${you_jdk_directory}\bin (крайне желательно, чтобы только одно: из-за значений, созданных до вас, например, Oracle, могут возникать ошибки) 
	- добавить в  environment variable PATH указание на папку ${your_maven_directory}\bin
 2. ”становить Tomcat:
	- скачать и распаковать архив apache-tomcat (тестировалось с Apache Tomcat 7.0.65)
	- установить значение environment variable CATALINA_HOME, указание на распакованный архив
 3. «апустить из командной строки
	- дл€ депло€ на http://localhost:8080/analyzeme/ : tomcat_first.bat
	- дл€ депло€ на на http://localhost:8080/contextPath/ : tomcat_ultimate.bat (желаема€ contextPath запрашиваетс€ во врем€ выполнени€ .bat файла)
	WARNING: contextPath дл€ стартовой страницы проекта должна состо€ть из одного имени (аналогично имени .war файла), поддержка конструкций типа http://localhost:8080/sth/sth2/... может быть добавлена по запросу
 
 –езультат работы доступен по указанному адресу до принудительного андепло€