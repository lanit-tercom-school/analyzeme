<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
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

    <!-- Add all projects from memory to table -->
    <script type="text/javascript">

        function addAllProjectsToTable(id) {
            rowNumber = 0;
            var request = new XMLHttpRequest();
            request.onreadystatechange = function () {
                if (request.readyState == 4 && request.status == 200) {

                    try {
                        var data = JSON.parse(request.responseText);

                        for (i = 0; i < data.Projects.length; i++) {
                            if (data.Projects[i].isActive) {

                                var tbody = document.getElementById(id).getElementsByTagName("TBODY")[0];
                                var row = document.createElement("TR")

                                var td1 = document.createElement("TD")
                                td1.innerHTML = '<i class = "glyphicon glyphicon-remove" type="button" onclick="killRow(this);">';

                                td1.appendChild(document.createTextNode(""))
                                var td2 = document.createElement("TD")
                                td2.appendChild(document.createTextNode(data.Projects[i].projectName))
                                var td3 = document.createElement("TD")
                                td3.appendChild(document.createTextNode(data.Projects[i].creationDate))
                                var td4 = document.createElement("TD")
                                td4.appendChild(document.createTextNode(data.Projects[i].lastChangeDate))
                                row.appendChild(td1);
                                row.appendChild(td2);
                                row.appendChild(td3);
                                row.appendChild(td4);
                                tbody.appendChild(row);

                                projectsIds[i] = data.Projects[i].projectId; //add projectId to the array
                                rowNumber = rowNumber + 1; //increase current number of lines
                                rowsNumbers[rowNumber] = projectsIds[i]; //add projectId to the array with active projects
                            }
                        }
                    } catch (err) {
                        //alert(err.message + " in " + request.responseText);
                        return;
                    }
                }
            };
            request.open("GET", "/projects/info", true);
            request.send();

        }
    </script>
    <!-- Click row -> project's page -->
    <script type="text/javascript">
        function clickEvent(event) {
            var target;
            if (!event) {
                var event = window.event;
            }
            if (event.target) {
                target = event.target;
            } else if (event.srcElement) {
                target = event.srcElement;
            }
            if (target) {
                if (target.nodeType == 3) {
                    target = target.parentNode;
                }
                if (target.tagName.toLowerCase() == "td") {
                    var row = target.parentNode;
                    var td;
                    location.href = "/project/" + rowsNumbers[row.rowIndex];
                }
            }
        }

    </script>
</head>

<body onload="addAllProjectsToTable('myTable'); return false; " onload="clickLink('myTable'); return false; ">

<!-- Navigation -->
<nav class="navbar navbar-default navbar-fixed-top topnav" role="navigation">
    <div class="container topnav">
        <!-- Brand and toggle get grouped for better mobile display -->
        <div class="navbar-header">
            <a href="index" type="button" class="btn btn-info btn-lg" href="index">AnalyzeMe</a>
            <a href="demo" type="button" class="btn btn-info btn-lg">Try now</a>
            <a href="projects" type="button" class="btn btn-success btn-lg">Projects</a>
        </div>

        <!-- /.navbar-collapse -->
    </div>
    <!-- /.container -->
</nav>

<!-- Make a table -->
<a name="about"></a>

<div class="intro-header2">
    <div class="container">
        <div class="row clearfix">
            <div class="col-md-12 column">

                <table class="table table-bordered table-hover" id="myTable" onclick="clickEvent(event)">
                    <thead>
                    <tr>
                        <th class="text-center">

                        </th>
                        <th class="text-center">
                            Title
                        </th>
                        <th class="text-center">
                            Creation date
                        </th>
                        <th class="text-center">
                            Last modified
                        </th>
                    </tr>
                    </thead>
                    <tbody>
                    </tbody>
                </table>
            </div>
        </div>

        <!-- New project button -->
        <a class="btn btn-default pull-left" onclick="addNewProject('myTable');return false;">New Project</a>

    </div>

</div>

<!-- Make array with all project's projectId
     Make array with active project's projectId (index = rowIndex : value = projectId
     rowNumber is a number of rows in the table
     functions create project and add row to the table-->

<script type="text/javascript">
    var projectsIds = new Array();

    var rowsNumbers = new Array();
    var rowNumber = 0;

    function addNewProject(id) {

        <!-- Show a window for Project name -->
        var projectName = prompt('New Project', "Project1");

        if (projectName) {
            createProject(projectName);

            setTimeout(function () {
                addRow(projectName, 'myTable');
            }, 30);
        }
    }

    function createProject(projectName) {
        var xhr = new XMLHttpRequest();
        xhr.open("PUT", "1/project/new/create", true);
        xhr.setRequestHeader("project_name", projectName.toString())
        xhr.send(null);
    }

    function addRow(projectName, id) {
        var request = new XMLHttpRequest();
        request.onreadystatechange = function () {
            if (request.readyState == 4 && request.status == 200) {
                try {
                    var data = JSON.parse(request.responseText);

                    if (projectName.toString() != null) {
                        <!-- Make a new row -->
                        var tbody = document.getElementById(id).getElementsByTagName("TBODY")[0];
                        var row = document.createElement("TR")
                        var td1 = document.createElement("TD")
                        //td1.innerHTML = '<input class="form-control" type="text" name="title0"/>';
                        td1.innerHTML = '<i class = "glyphicon glyphicon-remove" type="button" onclick="killRow(this);">';
                        td1.appendChild(document.createTextNode(""))
                        var td2 = document.createElement("TD")
                        td2.appendChild(document.createTextNode(data.Projects[data.Projects.length - 1].projectName))
                        var td3 = document.createElement("TD")
                        td3.appendChild(document.createTextNode(data.Projects[data.Projects.length - 1].creationDate))
                        var td4 = document.createElement("TD")
                        td4.appendChild(document.createTextNode(data.Projects[data.Projects.length - 1].lastChangeDate))
                        row.appendChild(td1);
                        row.appendChild(td2);
                        row.appendChild(td3);
                        row.appendChild(td4);
                        tbody.appendChild(row);


                        projectsIds[data.Projects.length - 1] = data.Projects[data.Projects.length - 1].projectId;
                        rowNumber = rowNumber + 1;
                        rowsNumbers[rowNumber] = projectsIds[data.Projects.length - 1];

                    }
                    else alert("Change name");

                } catch (err) {
                    alert(err.message + " in " + request.responseText);
                    return;
                }
            }
        };

        request.open("GET", "/projects/info", true);
        request.send();
    }

</script>


<!-- functions delete project and delete row from the table-->
<script type="text/javascript">

    function killRow(src) {
        var dRow = src.parentElement.parentElement;

        del(rowsNumbers[dRow.rowIndex]);
        var currentRowIndex = dRow.rowIndex;
        rowNumber = rowNumber - 1;
        document.all("myTable").deleteRow(currentRowIndex);

        for (k = currentRowIndex; k <= rowNumber; k++) {
            rowsNumbers[k] = rowsNumbers[k + 1];
        }

    }
    function del(Name) {

        var xhr = new XMLHttpRequest();

        xhr.open("DELETE", "1/project/" + Name + "/delete", true);
        xhr.send(null);

    }

</script>

<!-- jQuery -->
<spring:url value="/resources/js/jquery.js" var="jqueryJs"/>
<script src="${jqueryJs}"></script>

<!-- Bootstrap Core JavaScript -->
<spring:url value="/resources/js/bootstrap.min.js" var="mainJs"/>
<script src="${mainJs}"></script>

</body>

</html>
