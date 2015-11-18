@rem *****************************************
@rem Build scripts : mvn packaging
@rem *****************************************

@rem cd .. works for calling from Scripts dir, change if necessary
@cd ..
@rem compiling and packaging (ignoring previous versions)
@mvn clean install -U