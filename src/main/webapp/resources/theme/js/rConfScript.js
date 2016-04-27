function load() {
    CreateDefaultTable();
}

function CreateDefaultTable() {
    var RConfListJson = document.getElementById("getdata").innerHTML;
    // alert(RConfListJson);
    var RConfList = JSON.parse(RConfListJson);
    //  alert(RConfList);
    RConfList.forEach(function (item, i, arr) {
        // alert(item.rConfType+' '+item.name+' '+item.host+' '+item.port+' '+item.activeFlag);
        var table = document.getElementById("listOfInstances");
        var rowCount = table.rows.length;
        var row = table.insertRow(rowCount);
        row.insertCell(0).innerHTML = '<input type="button" value = "Delete" onClick="deleteRow(this)">';
        row.insertCell(1).innerHTML = item.name;
        row.insertCell(2).innerHTML = item.rConfType;
        row.insertCell(3).innerHTML = item.host;
        row.insertCell(4).innerHTML = item.port;
        row.insertCell(5).innerHTML = item.activeFlag;
        row.insertCell(6).innerHTML = '<input type="button" value = "Update" onClick="CallUpdateForm(this)">';
    });
}
function addRow() {
    var name = document.getElementById("name");
    var RType = document.getElementById("Rtype");
    var port = document.getElementById("port");
    var host = document.getElementById("host");
    var enabled = document.getElementById("enabledField");
    var table = document.getElementById("listOfInstances");
    var rowCount = table.rows.length;
    var row = table.insertRow(rowCount);

    row.insertCell(0).innerHTML = '<input type="button" value = "Delete" onClick="deleteRow(this)">';
    row.insertCell(1).innerHTML = name.value;
    row.insertCell(2).innerHTML = RType.value;
    row.insertCell(3).innerHTML = host.value;
    row.insertCell(4).innerHTML = port.value;
    row.insertCell(5).innerHTML = enabled.checked;
    row.insertCell(6).innerHTML = '<input type="button" value = "Update" onClick="CallUpdateForm(this)">';
    var RConf = {
        rConfType: RType.value,
        name: name.value,
        activeFlag: enabled.checked,
        host: host.value,
        port: port.value

    };

    var str = JSON.stringify(RConf);
    alert("/rConf/" + str);
    var xhr = new XMLHttpRequest();
    xhr.open("PUT", "/rConf/" + str, true); // If async=false, then you'll miss progress bar support.
    xhr.send();
}
function deleteRow(obj) {

    var index = obj.parentNode.parentNode.rowIndex;
    var table = document.getElementById("listOfInstances");
    var cell = table.rows[index].cells[1];
    var name = cell.innerHTML;
    alert("/rConf/" + name);
    table.deleteRow(index);

    var xhr = new XMLHttpRequest();
    xhr.open("DELETE", "/rConf/" + name, true); // If async=false, then you'll miss progress bar support.
    xhr.send();

}
function CallUpdateForm(obj) {
    var index = obj.parentNode.parentNode.rowIndex;
    var table = document.getElementById("listOfInstances");
    var row = table.rows[index];
    var cell = row.cells[2];
    var RType = cell.innerHTML;
    var oldName = row.cells[1].innerHTML;
    if (RType == 'RserveConf') {
        //alert(RType);
        $('#UpdateRserveModal').on('hidden.bs.modal', function () {
            var newName = document.getElementById("UpRserveName").value;
            var newHost = document.getElementById("UpRserveHost").value;
            var newPort = document.getElementById("UpRservePort").value;
            var newActiveFlag = document.getElementById("UpRserveEnabledField").checked;
            // alert(newName + ' '+newHost+' '+newPort+' ' + newActiveFlag);
            row.cells[1].innerHTML = newName;
            row.cells[3].innerHTML = newHost;
            row.cells[4].innerHTML = newPort;
            row.cells[5].innerHTML = newActiveFlag;

            var RConf = {
                rConfType: RType,
                name: newName,
                activeFlag: newActiveFlag,
                host: newHost,
                port: newPort
            };
            var str = JSON.stringify(RConf);
            UpdateOnServer(oldName, str);

            alert("Rserv Update");

        }).modal('show');
    }
    if (RType == 'FakeRConf') {

        $('#UpdateFakeRModal').on('hidden.bs.modal', function () {
            var newName = document.getElementById("UpFakeRName").value;
            var newActiveFlag = document.getElementById("UpFakeREnabledField").checked;
            // alert(newName +' '+ newActiveFlag);
            row.cells[1].innerHTML = newName;
            row.cells[5].innerHTML = newActiveFlag;


            var RConf = {
                rConfType: RType,
                name: newName,
                activeFlag: newActiveFlag

            };
            var str = JSON.stringify(RConf);
            UpdateOnServer(oldName, str);


            //alert("FakeR Update");

        }).modal('show');
    }
    if (RType == 'RenjinConf') {
        $('#UpdateRenjinModal').on('hidden.bs.modal', function () {
            var newName = document.getElementById("UpRenjinName").value;
            var newActiveFlag = document.getElementById("UpRenjinEnabledField").checked;
            // alert(newName +' '+ newActiveFlag);
            row.cells[1].innerHTML = newName;
            row.cells[5].innerHTML = newActiveFlag;

            var RConf = {
                rConfType: RType,
                name: newName,
                activeFlag: newActiveFlag

            };
            var str = JSON.stringify(RConf);
            UpdateOnServer(oldName, str);

            //alert("Renjin Update");

        }).modal('show');
    }
}

function UpdateOnServer(name, str) {
    alert("/rConf/" + name + '/' + str);
    var xhr = new XMLHttpRequest();
    xhr.open("POST", "/rConf/" + name + '/' + str, true); // If async=false, then you'll miss progress bar support.
    xhr.send();
}
//
//function addTable() {
//
//    var myTableDiv = document.getElementById("myDynamicTable");
//
//    var table = document.createElement('TABLE');
//    table.border = '1';
//
//    var tableBody = document.createElement('TBODY');
//    table.appendChild(tableBody);
//
//    for (var i = 0; i < 3; i++) {
//        var tr = document.createElement('TR');
//        tableBody.appendChild(tr);
//
//        for (var j = 0; j < 4; j++) {
//            var td = document.createElement('TD');
//            td.width = '75';
//            td.appendChild(document.createTextNode("Cell " + i + "," + j));
//            tr.appendChild(td);
//        }
//    }
//    myTableDiv.appendChild(table);
//
//}
//
