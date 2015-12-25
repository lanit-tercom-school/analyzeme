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
    <script src="http://d3js.org/d3.v3.min.js" charset="utf-8"></script>
    <!-- Created by Sergey -->
    <script src="http://code.jquery.com/jquery-2.1.4.min.js"></script>
    <!-- Стили для Драг энд Дропа -->
    <style>
        /*Note: Without it does not work properly*/
        .container {
            width: 200px;
            height: 40px;
            background-color: #ccc;
            margin: 0px auto;
            padding: 10px;
            font-size: 30px;
            color: #fff;
            position: absolute;
            text-align: center;
            left: 50%;
            margin-left: -100px;
        }

        .popup {
            width: 100%;
            min-height: 100%;
            background-color: rgba(0, 0, 0, 0.5);
            overflow: hidden;
            position: fixed;
            top: 0px;
        }

        #dropbox {
            width: 600px;
            height: 400px;
            padding: 10px;
            background-color: #c5c5c5;
            border-radius: 5px;
            box-shadow: 0px 0px 10px #000;
            position: absolute;
            left: 50%;
            top: 50%;
            margin-left: -300px;
            margin-top: -200px;
            text-align: center;
        }
    </style>
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


<%--<nav class="navbar navbar-default navbar-fixed-top topnav" role="navigation">
    <div class="container topnav">
        <!-- Brand and toggle get grouped for better mobile display -->
        <div class="navbar-header">
            &lt;%&ndash;<button type="button" class="navbar-toggle" data-toggle="collapse" data-target="#bs-example-navbar-collapse-1">
                <span class="sr-only">Toggle navigation</span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
            </button>&ndash;%&gt;
            <a class="navbar-brand topnav" href="index">AnalyzeMe</a>
    &lt;%&ndash;<ul class="nav navbar-nav">
      <li><button type="button" class="btn btn-primary navbar-btn"><i class="glyphicon glyphicon-log-in"></i>   Try now</button></li>
    </ul>&ndash;%&gt;
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
</nav>--%>
<!-- Div for display Graph -->
<div class="intro-header">
    <!-- /.container -->
    <svg id="svgVisualize" width="500" height="500" style="border:1px solid Red;"></svg>
    <button onclick="InitChart()">Go!</button>
   <%-- <svg id="svgVisualizeFix" width="500" height="500" style="border:1px solid Red;"></svg>
    <button onclick="InitChartFix()">Go Fix data!</button>
--%>         <a href="javascript:PopUpShow()">Upload</a>
        <div class="popup" id="popup">
        <div id="dropbox">
            Drag and drop a file here...
        </div>
    </div>
    <div id="status"></div>
</div>
<!-- Header -->
<%--<a name="about"></a>
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
                            <a href="#" class="btn btn-primary btn-lg"><i class="glyphicon glyphicon-log-in"></i> <span class="network-name">   Try now</span></a>
                        </li>
                        <li>
                            <a href="https://github.com/lanit-tercom-school/analyzeme" class="btn btn-default btn-lg"><i class="fa fa-github fa-fw"></i> <span class="network-name">Source code</span></a>
                        </li>
                    </ul>
                </div>
            </div>
        </div>

    </div>
    <!-- /.container -->

</div>--%>
<!-- /.intro-header -->
<%--    <!-- Page Content -->

	<a  name="services"></a>
    <div class="content-section-a">

        <div class="container">
            <div class="row">
                <div class="col-lg-5 col-sm-6">
                    <hr class="section-heading-spacer">
                    <div class="clearfix"></div>
                    <h2 class="section-heading">Death to the Stock Photo:<br>Special Thanks</h2>
                    <p class="lead">A special thanks to <a target="_blank" href="http://join.deathtothestockphoto.com/">Death to the Stock Photo</a> for providing the photographs that you see in this template. Visit their website to become a member.</p>
                </div>
                <div class="col-lg-5 col-lg-offset-2 col-sm-6">
                    <img class="img-responsive" src="/resources/theme/img/ipad.png" alt="">
                </div>
            </div>

        </div>
        <!-- /.container -->

    </div>
    <!-- /.content-section-a -->

    <div class="content-section-b">

        <div class="container">

            <div class="row">
                <div class="col-lg-5 col-lg-offset-1 col-sm-push-6  col-sm-6">
                    <hr class="section-heading-spacer">
                    <div class="clearfix"></div>
                    <h2 class="section-heading">3D Device Mockups<br>by PSDCovers</h2>
                    <p class="lead">Turn your 2D designs into high quality, 3D product shots in seconds using free Photoshop actions by <a target="_blank" href="http://www.psdcovers.com/">PSDCovers</a>! Visit their website to download some of their awesome, free photoshop actions!</p>
                </div>
                <div class="col-lg-5 col-sm-pull-6  col-sm-6">
                    <img class="img-responsive" src="../img/dog.png" alt="">
                </div>
            </div>

        </div>
        <!-- /.container -->

    </div>
    <!-- /.content-section-b -->

    <div class="content-section-a">

        <div class="container">

            <div class="row">
                <div class="col-lg-5 col-sm-6">
                    <hr class="section-heading-spacer">
                    <div class="clearfix"></div>
                    <h2 class="section-heading">Google Web Fonts and<br>Font Awesome Icons</h2>
                    <p class="lead">This template features the 'Lato' font, part of the <a target="_blank" href="http://www.google.com/fonts">Google Web Font library</a>, as well as <a target="_blank" href="http://fontawesome.io">icons from Font Awesome</a>.</p>
                </div>
                <div class="col-lg-5 col-lg-offset-2 col-sm-6">
                    <img class="img-responsive" src="../img/phones.png" alt="">
                </div>
            </div>

        </div>
        <!-- /.container -->

    </div>
    <!-- /.content-section-a -->

	<a  name="contact"></a>
    <div class="banner">

        <div class="container">

            <div class="row">
                <div class="col-lg-6">
                    <h2>Connect to Start Bootstrap:</h2>
                </div>
                <div class="col-lg-6">
                    <ul class="list-inline banner-social-buttons">
                        <li>
                            <a href="https://twitter.com/SBootstrap" class="btn btn-default btn-lg"><i class="fa fa-twitter fa-fw"></i> <span class="network-name">Twitter</span></a>
                        </li>
                        <li>
                            <a href="https://github.com/IronSummitMedia/startbootstrap" class="btn btn-default btn-lg"><i class="fa fa-github fa-fw"></i> <span class="network-name">Github</span></a>
                        </li>
                        <li>
                            <a href="#" class="btn btn-default btn-lg"><i class="fa fa-linkedin fa-fw"></i> <span class="network-name">Linkedin</span></a>
                        </li>
                    </ul>
                </div>
            </div>

        </div>
        <!-- /.container -->

    </div>
    <!-- /.banner -->--%>
<!-- Footer -->
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
    var Data= [{
        "x": 1,
        "y": 5
    }, {
        "x": 20,
        "y": 20
    }, {
        "x": 40,
        "y": 10
    }, {
        "x": 60,
        "y": 40
    }, {
        "x": 80,
        "y": 5
    }, {
        "x": 100,
        "y": 60
    }];
    <!-- function for display graph  -->
    function InitChart() {
alert("Data is fix");
        var vis = d3.select("#svgVisualize");
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
<!-- Drag and Drop Script -->
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
