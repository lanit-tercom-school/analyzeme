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

    <style>
        #ButtonList{
            position: relative;
            left: -400px;
        }
    </style>

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
            <a href="index" type="button" class="btn btn-info btn-lg">AnalyzeMe</a>
            <a href="https://github.com/lanit-tercom-school/analyzeme" class="btn btn-info btn-lg"><i
                    class="fa fa-github fa-fw"></i> Source code</a>


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
                <!-- Modal FileAlreadyExist http://bootstrap-ru.com/javascript.php#modals-->
                <!-- keyboard="true"  close window by pressing Esc on keyboard-->
                <div class="modal" id="FileAlreadyExistModal" tabindex="-1" role="dialog"
                     aria-labelledby="FileAlreadyExistModalLabel" aria-hidden="true">

                    <div class="modal-body">
                        <p>File already exist</p>
                    </div>
                    <div class="modal-footer">
                        <button class="btn" data-dismiss="modal" aria-hidden="true" onclick="ChangeName()">Change name
                        </button>
                        <button class="btn btn-primary" aria-hidden="true" onclick="Overwrite()">Overwrite</button>
                    </div>
                </div>
                <!-- Modal  of change name-->
                <div class="modal" id="changeNameModal" tabindex="-1" role="dialog"
                     aria-labelledby="changeNameModalLabel"
                     aria-hidden="true">

                    <div class="modal-body">
                        <p>Write new name</p>
                        <p><label>
                            <input type="TEXT" name="fileName" value="" size="20" id="fileNameInput" color='red'>
                        </label></p>
                    </div>
                    <div class="modal-footer">
                        <button class="btn" data-dismiss="modal" aria-hidden="true" onclick="DoChangingName()" >
                            Ok
                        </button>
                        <button class="btn btn-primary" aria-hidden="true" onclick="CancelChangingName()">Cancel</button>
                    </div>
                </div>
                <!-- Div for Upload file -->
                <div>
                    <button onclick="PopUpShow()">Upload and display</button>

                    <div class="popup" id="popup">
                        <div id="dropbox">
                            Drag and drop a file here...
                        </div>
                    </div>
                    <div id="status"></div>
                    <div id="ButtonList"></div>
                </div>
                <!-- Div for display Graph -->
                <div>
                    <div>
                        <svg id="svgVisualize" width="500" height="500" style="border:1px solid Red;"></svg>
                    </div>

                </div>
                <!-- Div for GlobalMin button -->
                <div>
                    <button id="GlobalMinButton" onclick="AnalyzeButton(fileName,'GlobalMinimum')">Calculate Global Min</button>

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

        $('#FileAlreadyExistModal').on('hide.bs.modal', function () {
            //Adding text to status
            document.getElementById("status").innerHTML = "Uploading " + file.name;
            var formData = new FormData();
            formData.append("file", file);
            var xhr = new XMLHttpRequest();
            xhr.upload.addEventListener("progress", uploadProgress, false);
            xhr.addEventListener("load", uploadComplete, false);
            xhr.open("POST", "UploadServlet", true); // If async=false, then you'll miss progress bar support.
            xhr.onreadystatechange = function () {
                fileName = xhr.getResponseHeader("fileName");
                Data = JSON.parse(xhr.getResponseHeader('Data'));
            };
            xhr.send(formData);

        });
        $('#changeNameModal').on('hide.bs.modal', function () {
            file.name = document.getElementById("fileNameInput").value;
            $('#FileAlreadyExistModal').modal('hide');
        });

        if (file.name == fileList[0]) {

            $('#FileAlreadyExistModal').modal('show');

        }else {
            //Adding text to status
            document.getElementById("status").innerHTML = "Uploading " + file.name;
            var formData = new FormData();
            formData.append("file", file);
            var xhr = new XMLHttpRequest();
            xhr.upload.addEventListener("progress", uploadProgress, false);
            xhr.addEventListener("load", uploadComplete, false);
            xhr.open("POST", "UploadServlet", true); // If async=false, then you'll miss progress bar support.
            xhr.onreadystatechange = function () {
                fileName = xhr.getResponseHeader("fileName");
                Data = JSON.parse(xhr.getResponseHeader('Data')).Data;
            };
            xhr.send(formData);
        }
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
        alert(Data);
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
        //AJAX request for updating Data
        $.ajax({
            type: "GET",
            async: true,
            url: "GetDataServlet",
            data: {'fileName': fileName},
            success: function (data, textStatus, request) {
                Data = JSON.parse(request.getResponseHeader('Data')).Data;
                DrawGraph(Data);
            },
            error: function (response, textStatus, errorThrown) {
                alert(response.statusText);
            }
        });

    }

    function ChangeName() {
         $("#changeNameModal").modal('show');
    }
    function Overwrite() {
        $('#FileAlreadyExistModal').modal('hide');
    }
    function DoChangingName(){
        $('#FileAlreadyExistModal').modal('show');
}
    function CancelChangingName() {
         $("#FileAlreadyExistModal").modal('show');
    }

</script>


<script>
    function AnalyzeButton(fileName,functionType) {
        //AJAX request for getting minimum of Data
        $(document).on("click", "#GlobalMinButton", function () {
            $.ajax({
                type: "GET",
                async: true,
                url: "AnalyzeServlet",
                data: {'fileName': fileName, 'functionType':functionType},
                success: function (data, textStatus, request) {

                    alert(request.getResponseHeader('minimum'));
                },
                error: function (response, textStatus, errorThrown) {
                    alert(response.statusText);
                }
            });
        })

    }
</script>
</body>

</html>
