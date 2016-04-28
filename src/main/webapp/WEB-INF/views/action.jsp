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

    <spring:url value="/resources/css/drag-and-drop.css" var="dragAndDropCss"/>
    <link href="${dragAndDropCss}" rel="stylesheet"/>

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
    <script src="http://d3js.org/d3.v3.min.js" charset="utf-8"></script>
    <script src="http://code.jquery.com/jquery-2.1.4.min.js"></script>


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
            <a href="/index" type="button" class="btn btn-info btn-lg">AnalyzeMe</a>
            <a href="/action" type="button" class="btn btn-success btn-lg">Try now</a>
            <a href="/projects" type="button" class="btn btn-info btn-lg">Projects</a>
            <a href="/REditorPage" type="button" class="btn btn-info btn-lg">Edit R</a>
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
            <!-- Sidebar -->
            <div class="col-md-2">
                <ul>
                    <div>
                        <p>

                        <h3>${project.projectName}<br/> File list</h3></p>
                        <a type="button" class="btn btn-primary btn-lg" onclick="PopUpShow()">Upload and display</a>

                    </div>
                    <div id="ButtonList"></div>
                </ul>
            </div>
            <!-- Page Content -->
            <div class="col-md-10">
                <!-- Div for Upload file -->
                <div>

                    <div class="popup" id="popup">
                        <div id="dropbox">
                            Drag and drop a file here...
                        </div>
                    </div>
                    <div id="status"></div>
                </div>
                <!-- Div for display Graph -->
                <div>
                    <div>
                        <svg id="svgVisualize" width="500" height="500" style="border:1px solid white;"></svg>
                    </div>

                </div>

                <!-- dropdown for Analyze buttons -->
                <div class="btn-group">
                    <button type="button" class="btn btn-default dropdown-toggle" data-toggle="dropdown"
                            aria-haspopup="true" aria-expanded="false">
                        Analyze function list <span class="caret"></span>
                    </button>
                    <ul id="menu1" class="dropdown-menu" role="menu" aria-labelledby="drop4">
                        <li>
                            <a type="button" class="btn btn-primary btn-lg"
                               onclick="AnalyzeButton(fileName,'GlobalMinimum')">Calculate Global Min</a>
                        </li>
                        <li>
                            <a type="button" class="btn btn-primary btn-lg"
                               onclick="AnalyzeButton(fileName,'GlobalMaximum')">Calculate Global Max</a>
                        </li>
                    </ul>
                </div>
            </div>
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
<!-- jQuery -->
<spring:url value="/resources/js/jquery.js" var="jqueryJs"/>
<script src="${jqueryJs}"></script>
<!-- Bootstrap Core JavaScript -->
<spring:url value="/resources/js/bootstrap.min.js" var="mainJs"/>
<script src="${mainJs}"></script>

<script>
    //Data what will display
    var Data;
    //variable for saving name of file
    var fileName;
    //Array what save all file name
    var fileList = [];
    //Size of fileList
    var size = 0;

</script>
<!-- Drag and Drop script -->
<script>
    //Function displays PopUp
    function PopUpShow() {
        $("#popup").show();
    }
    //Function hides PopUp
    function PopUpHide() {
        $("#popup").hide();
    }
    //Function - event handler: it is invoked when the document is loaded.
    window.onload = function () {
        PopUpHide();
        var dropbox = document.getElementById("dropbox");
        dropbox.addEventListener("dragenter", noop, false);
        dropbox.addEventListener("dragexit", noop, false);
        dropbox.addEventListener("dragover", noop, false);
        dropbox.addEventListener("drop", dropUpload, false);
    };
    //Function cancels the event
    function noop(event) {
        event.stopPropagation();
        event.preventDefault();
    }
    //Handles drop event
    function dropUpload(event) {
        noop(event);
        var files = event.dataTransfer.files;
        for (var i = 0; i < files.length; i++) {
            uploadFile(files[i]);
        }

        PopUpHide();

    }
    //Uploads file
    function uploadFile(file) {
        if (isFileExist(file.name)) {
            alert("file alreary exist");
        }
        var projectParams = "guest/" + "${project.uniqueName}";
        //checks if project object is empty
        if (projectParams.length <= "guest/".length) {
            projectParams = "demo";
        }
        //Adding text to status
        document.getElementById("status").innerHTML = "Uploading " + file.name;
        var formData = new FormData();
        formData.append("file", file);
        var xhr = new XMLHttpRequest();
        xhr.upload.addEventListener("progress", uploadProgress, false);
        xhr.addEventListener("load", uploadComplete, false);
        xhr.open("POST", "/upload/" + projectParams, true); // If async=false, then you'll miss progress bar support.
        xhr.onreadystatechange = function () {
            fileName = xhr.getResponseHeader("fileName");
            Data = JSON.parse(xhr.getResponseHeader('Data')).Data;
        };
        xhr.send(formData);

    }
    //Calculates upload progress
    function uploadProgress(event) {
        // Note: doesn't work with async=false.
        var progress = Math.round(event.loaded / event.total * 100);
        document.getElementById("status").innerHTML = "Progress " + progress + "%";
    }
    //Shows result after upload complete
    function uploadComplete(event) {
        document.getElementById("status").innerHTML = event.target.responseText;
        DrawGraph(Data);
        //create new element
        var newLi = document.createElement('li');
        //add new button
        newLi.innerHTML = '<button onclick="UpdateData(fileList[' + size + ']) "<span class=\"network-name\">"' + fileName + '"</span></button>';
        ButtonList.appendChild(newLi);
        //increase counter of number of file
        size = +size + 1;
        //Add fileName into array of fileNames
        fileList.push(fileName);

    }

</script>
<!-- script for display graph  -->
<script>

    function DrawGraph(Data) {
        var vis = d3.select("#svgVisualize");
        //clear Graph
        vis.selectAll("*").remove();

        var xRange = d3.scale.linear().range([40, 400]).domain([d3.min(Data, function (d) {
            return (d.x);
        }),
            d3.max(Data, function (d) {
                return d.x;
            })]);
        var yRange = d3.scale.linear().range([400, 40]).domain([d3.min(Data, function (d) {
            return d.y;
        }),
            d3.max(Data, function (d) {
                return d.y;
            })]);
        var xAxis = d3.svg.axis().scale(xRange);
        var yAxis = d3.svg.axis().scale(yRange).orient("left");
        vis.append("svg:g").call(xAxis).attr("transform", "translate(0,400)");
        vis.append("svg:g").call(yAxis).attr("transform", "translate(40,0)");

        var circles = vis.selectAll("circle").data(Data);
        circles
                .enter()
                .insert("circle")
                .attr("cx", function (d) {
                    return xRange(d.x);
                })
                .attr("cy", function (d) {
                    return yRange(d.y);
                })
                .attr("r", 10)
                .style("fill", "red");
    }
</script>
<!-- script for updating data -->
<script>
    function UpdateData(newFileName) {

        fileName = newFileName;

        //   AJAX request for updating Data
        $.ajax({
            type: "Get",
            async: true,
            url: "/file/" + fileName + "/data",
            success: function (data, textStatus, request) {
                Data = JSON.parse(request.getResponseHeader('Data')).Data;
                DrawGraph(Data);
            },
            error: function (response, textStatus, errorThrown) {
                alert(response.statusText);
            }
        });

    }


</script>
<!-- analyze button script-->
<script>
    function AnalyzeButton(fileName, functionType) {
        //AJAX request for getting minimum of Data
        $.ajax({
            type: "get",
            async: true,
            url: "/file/" + fileName + "/" + functionType,
            success: function (data, textStatus, request) {
                alert(request.getResponseHeader('value'));
            },
            error: function (response, textStatus, errorThrown) {
                alert(response.statusText);
            }
        });


    }
</script>
<!--isFileExist function -->
<script>
    function isFileExist(fileName) {
        for (var i = 0; i < size; i++) {
            if (fileList[i] == fileName) return true;
        }
        return false;
    }
</script>
</body>

</html>
