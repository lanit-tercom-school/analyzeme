@rem *****************************************
@rem Build scripts : mvn packaging
@rem *****************************************

@rem works for calling from analyzeme (project name) dir with pom.xml, change here if necessary
@rem compiling and packaging (ignoring previous versions)
@mvn clean install -U