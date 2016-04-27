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

    <spring:url value="/resources/js/rConfScript.js" var="rConfJs"/>
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

<body onload="load()">

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
            <div id="getdata" style="display: none;"> ${RConfList}</div>

            <a href="#AddModal" role="button" data-toggle="modal" class="btn btn-primary btn-lg"><span
                    class="network-name">Add row</span></a>

            <div class="modal" id="AddModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel"
                 aria-hidden="true">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
                    <h3 id="myModalLabel">Форма добавления</h3>
                </div>
                <div class="modal-body">
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
                                    <option value="RserveConf">Rserve</option>
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
                            <td><input type="checkbox" id="enabledField" checked></td>
                        </tr>
                        <tr>
                            <td>&nbsp;</td>
                        </tr>
                    </table>

                </div>
                <div class="modal-footer">
                    <button id="add" onclick="addRow()" class="btn" data-dismiss="modal" aria-hidden="true">Add
                        instance
                    </button>

                </div>
            </div>
            <div class="modal" id="UpdateRserveModal" tabindex="-1" role="dialog" aria-labelledby="updateModalLabel"
                 aria-hidden="true">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
                    <h3>Форма обновления Rserve</h3>
                </div>
                <div class="modal-body">
                    <table>
                        <tr>
                            <td>Name:</td>
                            <td><input type="text" id="UpRserveName"></td>
                        </tr>
                        <tr>
                            <td>Type:</td>
                            <td>
                                <button id="UpRserveRtype" value="RServConf"><span
                                        class="network-name"><b>Rserve</b></span></button>
                            </td>
                        </tr>
                        <tr>
                            <td>Host:</td>
                            <td><input type="text" id="UpRserveHost"></td>
                        </tr>
                        <tr>
                            <td>Port:</td>
                            <td><input type="text" id="UpRservePort"></td>
                        </tr>
                        <tr>
                            <td>Turned on:</td>
                            <td><input type="checkbox" id="UpRserveEnabledField" checked></td>
                        </tr>
                        <tr>
                            <td>&nbsp;</td>
                        </tr>
                    </table>

                </div>
                <div class="modal-footer">
                    <button id="closeRserve" class="btn" data-dismiss="modal" aria-hidden="true">Ok</button>

                </div>
            </div>
            <div class="modal" id="UpdateFakeRModal" tabindex="-1" role="dialog" aria-labelledby="updateModalLabel"
                 aria-hidden="true">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
                    <h3 id="updateFakeRModalLabel">Форма обновления FakeR</h3>
                </div>
                <div class="modal-body">
                    <table>
                        <tr>
                            <td>Name:</td>
                            <td><input type="text" id="UpFakeRName"></td>
                        </tr>
                        <tr>
                            <td>Type:</td>
                            <td>
                                <button id="UpFakeRRtype" value="FakeRConf"><span
                                        class="network-name"><b>FakeR</b></span></button>
                            </td>
                        </tr>

                        <tr>
                            <td>Turned on:</td>
                            <td><input type="checkbox" id="UpFakeREnabledField" checked></td>
                        </tr>
                        <tr>
                            <td>&nbsp;</td>
                        </tr>
                    </table>

                </div>
                <div class="modal-footer">
                    <button id="closeFakeR" class="btn" data-dismiss="modal" aria-hidden="true">Ok</button>

                </div>
            </div>
            <div class="modal" id="UpdateRenjinModal" tabindex="-1" role="dialog" aria-labelledby="updateModalLabel"
                 aria-hidden="true">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
                    <h3 id="updateModalLabel">Форма обновления Renjin</h3>
                </div>
                <div class="modal-body">
                    <table>
                        <tr>
                            <td>Name:</td>
                            <td><input type="text" id="UpRenjinName"></td>
                        </tr>
                        <tr>
                            <td>Type:</td>
                            <td>
                                <button id="UpRtype" value="RenjinConf"><span class="network-name"><b>Renjin</b></span>
                                </button>
                            </td>
                        </tr>
                        <tr>
                            <td>Turned on:</td>
                            <td><input type="checkbox" id="UpRenjinEnabledField" checked></td>
                        </tr>
                        <tr>
                            <td>&nbsp;</td>
                        </tr>
                    </table>

                </div>
                <div class="modal-footer">
                    <button id="closeRenjin" class="btn" data-dismiss="modal" aria-hidden="true">Ok</button>

                </div>
            </div>

            <div id="instancesData">
                <table id="listOfInstances" border="1">
                    <tr>
                        <td>&nbsp;</td>
                        <td><span class="network-name"><b>Name</b></span></td>
                        <td><span class="network-name"><b>Type</b></span></td>
                        <td><span class="network-name"><b>Using Host</b></span></td>
                        <td><span class="network-name"><b>Using Port</b></span></td>
                        <td><span class="network-name"><b>Turned on</b></span></td>
                        <td>&nbsp;</td>
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