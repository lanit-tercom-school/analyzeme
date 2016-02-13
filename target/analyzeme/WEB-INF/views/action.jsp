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
    <!-- Drag and Drop CSS (/*Note: Without it does not work properly*/) -->
    <spring:url value="/resources/css/DragAndDrop.css" var="DragAndDropCss"/>
    <link href="${DragAndDropCss}" rel="stylesheet"/>
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
    <script src="http://d3js.org/d3.v3.min.js" charset="utf-8"></script>
    <script src="http://code.jquery.com/jquery-2.1.4.min.js"></script>

</head>
<body>
<!-- Navigation -->
<nav class="navbar navbar-default navbar-fixed-top topnav" role="navigation">
    <div class="container-fluid">
        <div class="navbar-header">
            <button type="button" class="navbar-toggle" data-toggle="collapse" data-target=".navbar-collapse">
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
            </button>
            <a class="navbar-brand" href="index">
                <img src="https://pp.vk.me/c631917/v631917040/1fcb/2w3rDEwlJag.jpg" style="width: 40px;height:40px;">
            </a>
        </div>
        <div>
            <ul class="nav navbar-nav navbar-collapse collapse">
                <li>
                    <a href="index">AnalyzeMe</a>
                </li>
                <li class="active">
                    <a href="action">Try now</a>
                </li>
            </ul>
        </div>
        <!-- /.navbar-collapse -->
    </div>
    <!-- /.container-fluid -->
</nav>

<div class="intro-header">
    <!-- /.container -->
    <!-- Div for Upload file -->
    <div>
        <button onclick="PopUpShow()">Upload</button>

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
            <svg id="svgVisualize" width="500" height="500" style="border:1px solid Red;"></svg>
        </div>
        <div>
            <button id="DisplayGraphButton" onclick="Display()">Display</button>
        </div>
    </div>
    <!-- Div for GlobalMin button -->
    <div>
        <button id="GlobalMinButton" onclick="GlobalMin()">Calculate Global Min</button>
    </div>
</div>

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
<!-- Script for display Graph -->
<spring:url value="/resources/js/DrawGraph.js" var="DrawGraphJs"/>
<script src="${DrawGraphJs}"></script>
<!-- script for Display button -->
<script>
    var Data;
    var fileName;
    //AJAX request for getting Data
    function Display() {
        $(document).on("click", "#DisplayGraphButton", function () {
            $.ajax({
                type: "GET",
                async: true,
                url: "GetDataServlet",
                data: {'fileName': fileName},
                success: function (data, textStatus, request) {
                    Data = JSON.parse(request.getResponseHeader('Data'));
                    DrawGraph();
                },
                error: function (request, textStatus, errorThrown) {
                    alert("Error");
                }
            });

        })
    }
</script>
<!-- Script for GlobalMin button -->
<script>
    //A
    function GlobalMin() {
        alert("Button returns fixed value from servlet");
        //AJAX request for getting minimum of Data
        $(document).on("click", "#GlobalMinButton", function () {
            $.ajax({
                type: "GET",
                async: true,
                url: "GlobalMinServlet",
                data: {'fileName': fileName},
                success: function (data, textStatus, request) {
                    alert(request.getResponseHeader('minimum'));
                },
                error: function (request, textStatus, errorThrown) {
                    alert("Error");
                }
            });
        })
    }
</script>
<!-- DragAndDrop script-->
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
        //save fileName for future
        fileName = files[0].name;
        PopUpHide();

    }
    //Uploads file
    function uploadFile(file) {
        //Adding text to status
        document.getElementById("status").innerHTML = "Uploading " + file.name;
        var formData = new FormData();
        formData.append("file", file);
        var xhr = new XMLHttpRequest();
        xhr.upload.addEventListener("progress", uploadProgress, false);
        xhr.addEventListener("load", uploadComplete, false);
        xhr.open("POST", "UploadServlet", true); // If async=false, then you'll miss progress bar support.
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
    }
</script>

</body>

</html>
