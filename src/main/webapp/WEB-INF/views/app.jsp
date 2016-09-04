<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<html>
    <head>
        <spring:url value="/resources/angular2app/" var="angularPath"/>
        <!-- Set the base href -->
        <script>document.write('<base href="/app/" />');</script><!--' + document.location + '-->
        <title>AnalyzeMe</title>
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <link rel="stylesheet" href="${angularPath}app/css/styles.css">

        <!-- 1. Load libraries -->
        <script src="${angularPath}lib/es6-shim.min.js"></script>
        <script src="${angularPath}lib/shims_for_IE.js"></script>

        <!--
        <link rel="stylesheet" href="${angularPath}lib/mdl/material.min.css">
        <script src="${angularPath}lib/mdl/material.min.js"></script>
        <link rel="stylesheet" href="https://fonts.googleapis.com/icon?family=Material+Icons">
        -->

        <spring:url value="/resources/css/output.css" var="schemeCss"/>
        <link href="${schemeCss}" rel="stylesheet"/>
        <script src="${angularPath}lib/mdl/material.min.js"></script>
        <link rel="stylesheet" href="https://fonts.googleapis.com/icon?family=Material+Icons">


        <script src="${angularPath}lib/angular2-polyfills.js"></script>
        <script src="${angularPath}lib/Rx.umd.js"></script>
        <script src="${angularPath}lib/angular2-all.umd.js"></script>
        <script src="${angularPath}lib/router.dev.js"></script>

        <!-- 2. Load our 'modules' -->
        <script src='${angularPath}app/app-utils.js'></script>
        <script src='${angularPath}app/highcharts-utils.js'></script>
        <script src='${angularPath}app/oups.component.js'></script>

        <script src='${angularPath}app/project.service.js'></script>
        <script src='${angularPath}app/file.service.js'></script>
        <script src='${angularPath}app/workspace.service.js'></script>

        <script src='${angularPath}app/new-project.component.js'></script>

        <script src='${angularPath}app/dropbox-upload.component.js'></script>
        <script src='${angularPath}app/file-info.component.js'></script>
        <script src='${angularPath}app/files-list.component.js'></script>

        <script src='${angularPath}app/plot.component.js'></script>
        <script src='${angularPath}app/r-editor.component.js'></script>
        <script src='${angularPath}app/work-area.component.js'></script>

        <script src='${angularPath}app/work-project.component.js'></script>
        <script src='${angularPath}app/projects-list.component.js'></script>
        <script src='${angularPath}app/project-page.component.js'></script>

        <script src='${angularPath}app/navigation.component.js'></script>
        <script src='${angularPath}app/footer.component.js'></script>
        <script src='${angularPath}app/app.component.js'></script>
        <script src='${angularPath}app/main.js'></script>
        <%--<!--d3-->--%>
        <%--<script src="${angularPath}lib/d3.v3.min.js" charset="utf-8"></script>--%>
        <!--ace editor-->
        <script src="${angularPath}lib/ace/ace.js" charset="utf-8"></script>
        <script src="${angularPath}lib/ace/mode-r.js" charset="utf-8"></script>
        <!-- jQuery -->
        <script src="${angularPath}lib/jquery.min.js"></script>
        <!--hightchart -->
        <script src="${angularPath}lib/highcharts/highcharts.js"></script>
        <script src="${angularPath}lib/highcharts/highcharts.modules.exporting.js"></script>
    </head>

    <!-- 3. Display the application -->
    <body>
        <my-app>Loading...</my-app>
    </body>
</html>
