<%--
  Created by IntelliJ IDEA.
  User: Ольга
  Date: 04.11.2015
  Time: 18:45
  To change this template use File | Settings | File Templates.
--%>

<!DOCTYPE html>
<html lang="en">
<head>
  <title>HTML5 drag'n'drop file upload with Servlet</title>
  <script>
    window.onload = function() {
      var dropbox = document.getElementById("dropbox");
      dropbox.addEventListener("dragenter", noop, false);
      dropbox.addEventListener("dragexit", noop, false);
      dropbox.addEventListener("dragover", noop, false);
      dropbox.addEventListener("drop", dropUpload, false);
    }

    function noop(event) {
      event.stopPropagation();
      event.preventDefault();
    }

    function dropUpload(event) {
      noop(event);
      var files = event.dataTransfer.files;

      for (var i = 0; i < files.length; i++) {
        upload(files[i]);
      }
    }

    function upload(file) {
      document.getElementById("status").innerHTML = "Uploading " + file.name;

      var formData = new FormData();
      formData.append("file", file);

      var xhr = new XMLHttpRequest();
      xhr.upload.addEventListener("progress", uploadProgress, false);
      xhr.addEventListener("load", uploadComplete, false);
      xhr.open("POST", "UploadServlet", true); // If async=false, then you'll miss progress bar support.
      xhr.send(formData);
    }

    function uploadProgress(event) {
      // Note: doesn't work with async=false.
      var progress = Math.round(event.loaded / event.total * 100);
      document.getElementById("status").innerHTML = "Progress " + progress + "%";
    }

    function uploadComplete(event) {
      document.getElementById("status").innerHTML = event.target.responseText;
    }
  </script>
  <style>
    #dropbox {
      width: 300px;
      height: 200px;
      border: 1px solid gray;
      border-radius: 5px;
      padding: 5px;
      color: gray;
    }
  </style>
</head>
<body>
<div id="dropbox">Drag and drop a file here...</div>
<div id="status"></div>
</body>
</html>