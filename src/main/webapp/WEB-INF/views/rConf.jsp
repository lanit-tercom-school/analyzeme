<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%--
  Created by IntelliJ IDEA.
  User: asus
  Date: 17.04.2016
  Time: 14:20
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>RConfiguration</title>

    <spring:url value="/resources/js/rConf.js" var="rConfJs"/>
    <script type="text/javascript" src="${rConfJs}"></script>
    <!-- jQuery -->
    <spring:url value="/resources/js/jquery.js" var="jqueryJs"/>
    <script type="text/javascript" src="${jqueryJs}"></script>
    <!-- Bootstrap Core JavaScript -->
    <spring:url value="/resources/js/bootstrap.min.js" var="mainJs"/>
    <script type="text/javascript" src="${mainJs}"></script>

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

<!-- Navigation -->
<nav class="navbar navbar-default navbar-fixed-top topnav" role="navigation">
    <div class="container topnav">
        <!-- Brand and toggle get grouped for better mobile display -->
        <div class="navbar-header">
            <a href="index" type="button" class="btn btn-success btn-lg">AnalyzeMe</a>
            <a href="demo" type="button" class="btn btn-info btn-lg">Try now</a>
            <a href="projects" type="button" class="btn btn-info btn-lg">Projects</a>

        </div>

        <!-- /.navbar-collapse -->
    </div>
    <!-- /.container -->
</nav>
<!-- Header -->
<a id="about"></a>
<div class="intro-header2">
    <div class="container">
        <div class="row">

            <div id="AddingForm">
                <table>
                    <tr>
                        <td>Name:</td>
                        <td><input type="text" id="name"></td>
                    </tr>
                    <tr>
                        <td>Select type:</td>
                        <td>
                            <select id="Rtype">
                                <option value="FakeRConf">FakeR</option>
                                <option value="RServConf">RServ</option>
                                <option value="RenjinConf">Renjin</option>
                            </select>
                        </td>
                    </tr>
                    <tr>
                        <td>Host:</td>
                        <td><input type="text" id="host"></td>
                    </tr>
                    <tr>
                        <td>Port:</td>
                        <td><input type="text" id="port"></td>
                    </tr>
                    <tr>
                        <td>Turned on:</td>
                        <td><input type="checkbox" id="enabledField"></td>
                    </tr>
                    <tr>
                        <td>&nbsp;</td>
                    </tr>
                </table>
                <button id="add" onclick="addRow()">Add instance</button>
            </div>


            <div id="instancesData">
                <table id="listOfInstances" border="1">
                    <tr>
                        <td>&nbsp;</td>
                        <td><b>Name</b></td>
                        <td><b>Type</b></td>
                        <td><b>Using Host</b></td>
                        <td><b>Using Port</b></td>
                        <td><b>Turned on</b></td>
                    </tr>
                </table>
                &nbsp;<br/>
            </div>

            <!--
            <div id="myDynamicTable">
            <input type="button" id="create" value="Click here" onclick="Javascript:addTable()">
            to create a Table and add some data using JavaScript
            </div>-->

        </div>
    </div>
    <!-- /.container -->

</div>
<!-- /.intro-header -->

<footer>
    <div class="container">
        <div class="row">
            <div class="col-lg-12">
                <%--<ul class="list-inline">
                    <li>
                        <a href="#">Home</a>
                    </li>
                    <li class="footer-menu-divider">&sdot;</li>
                    <li>
                        <a href="#about">About</a>
                    </li>
                    <li class="footer-menu-divider">&sdot;</li>
                    <li>
                        <a href="#services">Services</a>
                    </li>
                    <li class="footer-menu-divider">&sdot;</li>
                    <li>
                        <a href="#contact">Contact</a>
                    </li>
                </ul>--%>
                <p class="copyright text-muted small">Copyright &copy; lanit-tercom.school 2015. All Rights Reserved</p>
            </div>
        </div>
    </div>
</footer>
</body>

</html>