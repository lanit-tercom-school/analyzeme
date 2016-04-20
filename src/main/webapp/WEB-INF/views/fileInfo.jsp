<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>AnalyzeMe</title>
</head>
<body>
<div>
    <h3>FileInfo</h3>
    <form>
        <input id="promptId" width="60" type="text" value="fileId">
        <input id="sub" type="submit" onclick="FileInfo();return false;" value="GetFileInfo">
    </form>
</div>
<div>
    <h3>FileFields or FullFileInfo</h3>
    <form>
        <input id="promptName" width="60" type="text" value="filenameForUser">
        <input id="promptProject" width="60" type="text" value="projectId">
        <input id="sub1" type="submit" onclick="FileFields();return false;" value="GetFileFields">
        <input id="sub2" type="submit" onclick="GetFullFileInfo();return false;" value="GetFullFileInfo">
    </form>
</div>
<div>
    <h3>Result</h3>

    <p>You have entered:</p>

    <p id="usertxt"></p>

    <p>Info:</p>

    <div id="result"></div>
</div>
<script>
    function setInputId() {
        var inpTxt = document.getElementById("promptId").value;
        document.getElementById("usertxt").innerHTML = inpTxt;
        return inpTxt
    }

    function setInputName() {
        var inpTxt = document.getElementById("promptName").value;
        document.getElementById("usertxt").innerHTML = inpTxt;
        return inpTxt
    }

    function setInputProject() {
        var inpProject = document.getElementById("promptProject").value;
        var pr = document.createElement("p");
        pr.innerHTML = inpProject;
        document.getElementById("usertxt").appendChild(pr);
        return inpProject
    }

    function FileInfo() {
        var inpTxt = setInputId();
        var xhr = new XMLHttpRequest();
        xhr.open("GET", "file/" + inpTxt.toString() + "/getInfo/", true);
        xhr.onreadystatechange = function () {
            if (xhr.readyState == 4 && xhr.status == 200) {
                try {
                    var data = JSON.parse(xhr.responseText);
                    //{"uniqueName":"0_10.json","nameForUser":"0_10.json",
                    //"isActive":"true","uploadingDate":"Wed Apr 20 18:25:28 MSK 2016"}
                    var result = document.getElementById("result");
                    result.innerHTML = '';
                    var newElem = document.createElement("p");
                    newElem.innerHTML = 'uniqueName: ' + data.uniqueName;
                    result.appendChild(newElem);

                    newElem = document.createElement("p");
                    newElem.innerHTML = 'isActive: ' + data.isActive;
                    result.appendChild(newElem);

                    newElem = document.createElement("p");
                    newElem.innerHTML = 'nameForUser: ' + data.nameForUser;
                    result.appendChild(newElem);

                    newElem = document.createElement("p");
                    newElem.innerHTML = 'uploadingDate: ' + data.uploadingDate;
                    result.appendChild(newElem);
                } catch (err) {
                    alert(err.message + " in " + xhr.responseText);
                    return;
                }
            }
            else {
                document.getElementById("result").innerHTML = "error";
            }
        };
        xhr.send();
    }


    function FileFields() {
        var inpName = setInputName();
        var inpPr = setInputProject();

        var xhr = new XMLHttpRequest();
        xhr.open("GET", "file/1/" + inpPr.toString() + "/" + inpName.toString() + "/getFields/", true);
        xhr.onreadystatechange = function () {
            if (xhr.readyState == 4 && xhr.status == 200) {
                try {
                    var data = JSON.parse(xhr.responseText);
                    //example : {"dataname":"0_10.json","fields":
                    // [{"fieldName":"x","fieldId":"x"},{"fieldName":"y","fieldId":"y"}]}
                    var result = document.getElementById("result");
                    result.innerHTML = '';
                    var newElem = document.createElement("p");
                    newElem.innerHTML = 'dataName: ' + data.dataname;
                    result.appendChild(newElem);

                    newElem = document.createElement("p");
                    newElem.innerHTML = 'fields: ';
                    result.appendChild(newElem);

                    for (i = 0; i < data.fields.length; i++) {
                        newElem = document.createElement("p");
                        newElem.innerHTML = 'fieldId: ' + data.fields[i].fieldId + '; fieldName :' + data.fields[i].fieldName;
                        result.appendChild(newElem);
                    }

                } catch (err) {
                    alert(err.message + " in " + xhr.responseText);
                    return;
                }
            }
            else {
                document.getElementById("result").innerHTML = "error";
            }
        };
        xhr.send();
    }


    function GetFullFileInfo() {
        var inpName = setInputName();
        var inpPr = setInputProject();

        var xhr = new XMLHttpRequest();
        xhr.open("GET", "file/1/" + inpPr.toString() + "/" + inpName.toString() + "/getFullInfo/", true);
        xhr.onreadystatechange = function () {
            if (xhr.readyState == 4 && xhr.status == 200) {
                try {
                    var data = JSON.parse(xhr.responseText);
                    // example : {"uniqueName":"0_10.json","nameForUser":"0_10.json","isActive":"true",
                    // "fields":[{"fieldName":"x","fieldId":"x"},{"fieldName":"y","fieldId":"y"}],
                    // "uploadingDate":"Wed Apr 20 18:25:28 MSK 2016"}
                    var result = document.getElementById("result");
                    result.innerHTML = '';

                    var newElem = document.createElement("p");
                    newElem.innerHTML = 'uniqueName: ' + data.uniqueName;
                    result.appendChild(newElem);

                    newElem = document.createElement("p");
                    newElem.innerHTML = 'isActive: ' + data.isActive;
                    result.appendChild(newElem);

                    newElem = document.createElement("p");
                    newElem.innerHTML = 'nameForUser: ' + data.nameForUser;
                    result.appendChild(newElem);

                    newElem = document.createElement("p");
                    newElem.innerHTML = 'uploadingDate: ' + data.uploadingDate;
                    result.appendChild(newElem);

                    newElem = document.createElement("p");
                    newElem.innerHTML = 'fields: ';
                    result.appendChild(newElem);

                    for (i = 0; i < data.fields.length; i++) {
                        newElem = document.createElement("p");
                        newElem.innerHTML = 'fieldId: ' + data.fields[i].fieldId + '; fieldName :' + data.fields[i].fieldName;
                        result.appendChild(newElem);
                    }

                } catch (err) {
                    alert(err.message + " in " + xhr.responseText);
                    return;
                }
            }
            else {
                document.getElementById("result").innerHTML = "error";
            }
        };
        xhr.send();
    }
</script>
</body>
</html>
