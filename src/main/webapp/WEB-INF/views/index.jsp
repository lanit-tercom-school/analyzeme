<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<!DOCTYPE html>
<html lang="en">

<head>
    <spring:url value="/resources/angular2app/" var="angularPath"/>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="description" content="">
    <meta name="author" content="">

    <title>AnalyzeMe</title>

    <!-- Material-Design Lite -->
    <spring:url value="/resources/css/output.css" var="schemeCss"/>
    <link href="${schemeCss}" rel="stylesheet"/>
    <!--<link rel="stylesheet" href="${angularPath}lib/mdl/material.min.css">-->
    <script src="${angularPath}lib/mdl/material.min.js"></script>
    <link rel="stylesheet" href="https://fonts.googleapis.com/icon?family=Material+Icons">

    <!-- Bootstrap Core CSS -->
    <spring:url value="/resources/css/bootstrap.min.css" var="mainCss"/>
    <link href="${mainCss}" rel="stylesheet"/>

    <!-- Custom CSS -->
    <spring:url value="/resources/css/landing-page.css" var="landingCss"/>
    <link href="${landingCss}" rel="stylesheet"/>

    <!-- Custom Fonts -->
    <spring:url value="/resources/font-awesome/css/font-awesome.min.css" var="fontAwesomeCss"/>
    <link href="${fontAwesomeCss}" rel="stylesheet" type="text/css"/>
    <spring:url value="http://fonts.googleapis.com/css?family=Lato:300,400,700,300italic,400italic,700italic"
                var="fontCss"/>
    <link href="${fontCss}" rel="stylesheet" type="text/css"/>

    <!-- HTML5 Shim and Respond.js IE8 support of HTML5 elements and media queries -->
    <!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
    <!--[if lt IE 9]>
    <script src="https://oss.maxcdn.com/libs/html5shiv/3.7.0/html5shiv.js"></script>
    <script src="https://oss.maxcdn.com/libs/respond.js/1.4.2/respond.min.js"></script>
    <![endif]-->

</head>

<body>

<div class="mdl-layout mdl-js-layout mdl-layout--fixed-header">
    <header class="mdl-layout__header">
        <div class="mdl-layout__header-row">
            <span class="mdl-layout-title"></span>
            <!-- Navigation -->
            <nav class="mdl-navigation" role="navigation">
                <a href="/index" class="mdl-navigation__link">AnalyzeMe</a>
                <!-- next link will point to angular app-->
                <a href="/app/demo" class="mdl-navigation__link">Try now</a>
                <!--<a href="/app" class="mdl-navigation__link">Projects</a> -->
                <a href="/data/spb" class="mdl-navigation__link">Preview</a>
                <a href="/config" class="mdl-navigation__link">R Configurations</a>
            </nav>
        </div>
    </header>
    <main class="mdl-layout__content" style="overflow-y: hidden;">
        <div class="page-content">
            <!-- ---- -->
            <!-- Header -->
            <div class="intro-header">
                <div class="container">
                    <div class="row">
                        <div class="col-lg-12">
                            <div class="intro-message">
                                <h1>AnalyzeMe</h1>

                                <h3>A web service for data analysis</h3>
                                <hr class="intro-divider" style="margin: 1em auto;">
                                <ul class="list-inline intro-social-buttons">
                                    <li>
                                        <a href="/app/demo" class="btn btn-primary btn-lg"><i class="glyphicon glyphicon-log-in"></i>
                                            <span class="network-name">   Try now </span></a>
                                    </li>
                                    <li>
                                        <a href="https://github.com/lanit-tercom-school/analyzeme" class="btn btn-primary btn-lg"><i
                                                class="fa fa-github fa-fw"></i> Source code</a>
                                    </li>

                                </ul>
                            </div>
                        </div>
                    </div>

                </div>
                <!-- /.container -->

            </div>
            <!-- /.intro-header -->
            <!-- ---- -->
        </div>
    </main>
</div>

<!-- jQuery -->
<spring:url value="/resources/js/jquery.js" var="jqueryJs"/>
<script src="${jqueryJs}"></script>

<!-- Bootstrap Core JavaScript -->
<spring:url value="/resources/js/bootstrap.min.js" var="mainJs"/>
<script src="${mainJs}"></script>

</body>

</html>
