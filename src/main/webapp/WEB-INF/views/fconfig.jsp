<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>File Configuration</title>
    <spring:url value="/resources/angular2app/" var="angularPath"/>
    <spring:url value="/resources/js/fConfig.js" var="fConfJs"/>
    <script type="text/javascript" src="${fConfJs}"></script>
    <!-- jQuery -->
    <spring:url value="/resources/js/jquery.js" var="jqueryJs"/>
    <script type="text/javascript" src="${jqueryJs}"></script>


    <!-- Bootstrap Core JavaScript -->
    <spring:url value="/resources/js/bootstrap.min.js" var="mainJs"/>
    <script type="text/javascript" src="${mainJs}"></script>

    <%--<!-- Material-Design Lite -->--%>
    <%--<spring:url value="/resources/css/output.css" var="schemeCss"/>--%>
    <%--<link href="${schemeCss}" rel="stylesheet"/>--%>
    <script src="${angularPath}lib/mdl/material.min.js"></script>
    <link rel="stylesheet" href="https://fonts.googleapis.com/icon?family=Material+Icons">

    <!-- Bootstrap Core CSS -->
    <spring:url value="/resources/css/bootstrap.min.css" var="mainCss"/>
    <link href="${mainCss}" rel="stylesheet"/>

    <%--<!-- Custom CSS -->--%>
    <%--<spring:url value="/resources/css/landing-page.css" var="landingCss"/>--%>
    <%--<link href="${landingCss}" rel="stylesheet"/>--%>

    <!-- Table CSS -->
    <spring:url value="/resources/css/RConfTable.css" var="TableCss"/>
    <link href="${TableCss}" rel="stylesheet"/>
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

    <script>
        jQuery(document).ready(function () {
            $("#addFType").on('change', function () {
                switch ($(this).val()) {
                    case 'TestFConf':
                        $('#addServiceAccount').css('visibility', 'hidden');
                        $('#addDatabaseUrl').css('visibility', 'hidden');
                        document.getElementById("serviceAccount").value = '-----';
                        document.getElementById("databaseUrl").value = '-----';
                        break;
                    case 'FirebaseConf':
                        $('#addServiceAccount').css('visibility', 'visible');
                        $('#addDatabaseUrl').css('visibility', 'visible');
                        document.getElementById("serviceAccount").value = '-----';
                        document.getElementById("databaseUrl").value = '-----';
                        break;

                }
            });

        });
    </script>
</head>

<body onload="load()">


<div class="mdl-layout mdl-js-layout mdl-layout--fixed-header">
    <header class="mdl-layout__header">
        <div class="mdl-layout__header-row">
            <span class="mdl-layout-title"></span>
            <!-- Navigation -->
            <nav class="mdl-navigation" role="navigation">
                <a href="/index" class="mdl-navigation__link">AnalyzeMe</a>
                <a href="/app/demo" class="mdl-navigation__link">Try now</a>
                <!-- <a href="/app" class="mdl-navigation__link">Projects</a> -->
                <a href="/data/spb" class="mdl-navigation__link">Preview</a>
                <a href="/rconfig" class="mdl-navigation__link">RConfigurations</a>
                <a href="/fconfig" class="mdl-navigation__link">FileConfigurations</a>
            </nav>
        </div>
    </header>
    <main class="mdl-layout__content" style="overflow-y: hidden;">
        <div class="page-content">

            <a id="about"></a>
            <div class="intro-header2">
                <div class="container">
                    <div class="row">
                    </div>
                    <div class="row">
                        <div id="getdataf" style="display: none;"> ${FConfList}</div>
                        <h3> File configuration </h3>

                        <a href="#AddModalF" role="button" data-toggle="modal"
                           class="btn btn-primary btn-lg"><span
                                class="network-name">Add</span></a>

                        <%--Add modalF--%>
                        <div class="modal" id="AddModalF" tabindex="-1" role="dialog" aria-labelledby="myModalLabel"
                             aria-hidden="true">
                            <div class="modal-header">
                                <button type="button" class="close" data-dismiss="modal"
                                        aria-hidden="true">&times;</button>
                                <h3 class="modal-title">Add new File Configuration</h3>
                            </div>

                            <div class="modal-body">
                                <table class="form">
                                    <tr>
                                        <td>Name:</td>
                                        <td><input type="text" id="namef"><span class="redStar"><span
                                                class="glyphicon glyphicon-asterisk"></span></span></td>

                                    </tr>
                                    <tr>
                                        <td>Select type:</td>
                                        <td>
                                            <select id="addFType">
                                                <option value="FirebaseConf">Firebase</option>
                                                <option value="TestRConf">TestR</option>
                                            </select>
                                        </td>
                                    </tr>
                                    <tr>
                                        <td>Turned on:</td>
                                        <td><input type="checkbox" id="enabledField" checked></td>
                                    </tr>
                                    <tr id="addServiceAccount">
                                        <td>serviceAccount:</td>
                                        <td><input type="text" id="serviceAccount"></td>
                                    </tr>
                                    <tr id="addDatabaseUrl">
                                        <td>databaseUrl:</td>
                                        <td><input type="text" id="databaseUrl">
                                        </td>
                                    </tr>

                                </table>

                            </div>
                            <div class="modal-footer">
                                <a onclick="addFConf()" data-dismiss="modal" role="button" data-toggle="modal"
                                   class="btn btn-primary btn-lg"><span
                                        class="network-name">Add </span></a>
                                <a data-dismiss="modal" role="button" data-toggle="modal"
                                   class="btn btn-primary btn-lg"><span
                                        class="network-name">Cancel</span></a>

                            </div>
                        </div>
                        <%--update modalF--%>
                        <div class="modal" id="UpdateModalf" tabindex="-1" role="dialog"
                             aria-labelledby="updateModalLabel"
                             aria-hidden="true">
                            <div class="modal-header">
                                <button onclick="closeUpdateFormF()" type="button" class="close">&times;</button>
                                <h3 class="modal-title">Update File Configuration</h3>
                            </div>
                            <div class="modal-body">
                                <table class="form">
                                    <tr>
                                        <td>Name:</td>
                                        <td><input type="text" id="upName"><span class="redStar"><span
                                                class="glyphicon glyphicon-asterisk"></span></span></td>
                                    </tr>
                                    <tr>
                                        <td>Type:</td>
                                        <td>
                                            <span id="upType" class="network-name"><b>Firebase</b></span>
                                        </td>
                                    </tr>

                                    <tr>
                                        <td>Turned on:</td>
                                        <td><input type="checkbox" id="upEnabledField" checked></td>
                                    </tr>

                                    <tr id="serviceAccountRow">
                                        <td>ServiceAccount:</td>
                                        <td><input type="text" id="upServiceAccount"></td>
                                    </tr>
                                    <tr id="databaseUrlRow">
                                        <td>DatabaseUrl:</td>
                                        <td><input type="text" id="upDatabaseUrl"></td>
                                    </tr>

                                </table>

                            </div>
                            <div class="modal-footer">

                                <a onclick="updateF(upObj)" class="btn btn-primary btn-lg"><span
                                        class="network-name" aria-hidden="true">Ok</span></a>

                                <a onclick="closeUpdateFormF()" role="button" class="btn btn-primary btn-lg"><span
                                        class="network-name">Cancel</span></a>

                            </div>
                        </div>
                        <%--delete modalF--%>
                        <div id="deleteModalf" class="modal" tabindex="-1" role="dialog"
                             aria-labelledby="mySmallModalLabel">
                            <div class="modal-header">
                                <button onclick="closeDeleteFormF()" type="button" class="close">&times;</button>
                                <h3 class="modal-title" id="myModalLabel">Delete File Configuration?</h3>
                            </div>
                            <div class="modal-body">
                                <div class="modal-content">
                                    Are you really want delete <span id="delNamef">File Configuration</span>?
                                </div>
                            </div>
                            <div class="modal-footer">
                                <a onclick="deleteFconf(delObj)" class="btn btn-primary btn-lg"><span
                                        class="network-name" aria-hidden="true">Yes</span></a>

                                <a onclick="closeDeleteFormF()" role="button" class="btn btn-primary btn-lg"><span
                                        class="network-name">No</span></a>
                            </div>
                        </div>
                        <%--main tablef--%>
                        <div id="instancesData">
                            <table id="listOfInstancesf" border="1">
                                <tr>
                                    <th><b>Name</b></th>
                                    <th><b>Type</b></th>
                                    <th><b>Status</b></th>
                                    <th colspan="2"><b>Settings</b></th>
                                    <th>&nbsp;</th>
                                    <th>&nbsp;</th>
                                </tr>

                            </table>

                        </div>


                    </div>
                </div>
                <!-- /.container -->

            </div>
            <!-- /.intro-header -->
        </div>
    </main>
</div>

</body>

</html>
