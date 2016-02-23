<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<!DOCTYPE html>
<html lang="en">

<head>

    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="description" content="">
    <meta name="author" content="">

    <title>AnalyzeMe</title>

    <!-- Bootstrap Core CSS -->
    <spring:url value="/resources/css/bootstrap.min.css" var="mainCss" />
    <link href="${mainCss}" rel="stylesheet" />

    <!-- Custom CSS -->
    <spring:url value="/resources/css/landing-page.css" var="landingCss" />
    <link href="${landingCss}" rel="stylesheet" />

    <!-- Custom Fonts -->
    <spring:url value="/resources/font-awesome/css/font-awesome.min.css" var="fontAwesomeCss" />
    <link href="${fontAwesomeCss}" rel="stylesheet" type="text/css"/>
    <spring:url value="http://fonts.googleapis.com/css?family=Lato:300,400,700,300italic,400italic,700italic" var="fontCss" />
    <link href="${fontCss}" rel="stylesheet" type="text/css"/>

    <!-- HTML5 Shim and Respond.js IE8 support of HTML5 elements and media queries -->
    <!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
    <!--[if lt IE 9]>
        <script src="https://oss.maxcdn.com/libs/html5shiv/3.7.0/html5shiv.js"></script>
        <script src="https://oss.maxcdn.com/libs/respond.js/1.4.2/respond.min.js"></script>
    <![endif]-->

</head>

<body>

    <!-- Navigation -->
    <nav class="navbar navbar-default navbar-fixed-top topnav" role="navigation">
        <div class="container topnav">
            <!-- Brand and toggle get grouped for better mobile display -->
            <div class="navbar-header">
                <!--<span class="sr-only">Toggle navigation</span>
                    <span class="icon-bar"></span>
                    <span class="icon-bar"></span>
                    <span class="icon-bar"></span>-->
                <a href="index" type="button" class="btn btn-info btn-lg" href="index">AnalyzeMe</a>
				<a href="https://github.com/lanit-tercom-school/analyzeme" class="btn btn-info btn-lg"><i class="fa fa-github fa-fw"></i> Source code</a>
                <a href="action" type="button" class="btn btn-info btn-lg">Try now</a>


            </div>
			
            <!-- Collect the nav links, forms, and other content for toggling -->

	<!--
            <div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">
                <ul class="nav navbar-nav navbar-right">
                    <li>
                        <a href="#about">About</a>
                    </li>
                    <li>
                        <a href="#services">Services</a>
                    </li>
                    <li>
                        <a href="#contact">Contact</a>
                    </li>
                </ul>
            </div>
	-->
            <!-- /.navbar-collapse -->
        </div>
        <!-- /.container -->
    </nav>


    <!-- Header -->
    <a name="about"></a>
    <div class="intro-header">
        <div class="container">

            <div class="row">
                <div class="col-lg-12">
                    <div class="intro-message">
                        <h1>AnalyzeMe</h1>
                        <h3>A web service for data analysis</h3>
                        <hr class="intro-divider">
                        <ul class="list-inline intro-social-buttons">
                            <li>
                                <a href="action" class="btn btn-primary btn-lg"><i class="glyphicon glyphicon-log-in"></i> <span class="network-name">   Try now </span></a>
                            </li>
                            <li>
                                <a href="project" class="btn btn-primary btn-lg"><i class="glyphicon glyphicon-log-in"></i> <span class="network-name">  Project </span></a>
                            </li>
							
                        </ul>
                    </div>
                </div>
            </div>

        </div>
        <!-- /.container -->

    </div>
    <!-- /.intro-header -->



    <!-- Footer -->
    <footer>
        <div class="container">
            <div class="row">
                <div class="col-lg-12">
                    
                    <p class="copyright text-muted small">Copyright &copy; lanit-tercom.school 2015. All Rights Reserved</p>
                </div>
            </div>
        </div>
    </footer>

    <!-- jQuery -->
    <spring:url value="/resources/js/jquery.js" var="jqueryJs" />
    <script src="${jqueryJs}"></script>

    <!-- Bootstrap Core JavaScript -->
    <spring:url value="/resources/js/bootstrap.min.js" var="mainJs" />
    <script src="${mainJs}"></script>

</body>

</html>
