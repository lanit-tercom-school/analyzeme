<%--
  Created by IntelliJ IDEA.
  User: Ольга
  Date: 04.11.2015
  Time: 18:45
  To change this template use File | Settings | File Templates.
--%>

<!DOCTYPE html>
<html>
<head>
    <title>HTML5 drag'n'drop file upload with Servlet</title>
    <script src="http://code.jquery.com/jquery-2.1.4.min.js"></script>
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
            xhr.open("POST", "upload/1/1", true); // If async=false, then you'll miss progress bar support.
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
<div class="container">
    <a href="javascript:PopUpShow()">Upload</a>
</div>
<div class="popup" id="popup">
    <div id="dropbox">
        Drag and drop a file here...
    </div>
</div>
<div id="status"></div>
</body>
</html>
