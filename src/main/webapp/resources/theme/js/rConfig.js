function load() {
    createDefaultTable();

}
var upObj;
function createDefaultTable() {
    var RConfListJson = document.getElementById("getdata").innerHTML;
    // alert(RConfListJson);
    var RConfList = JSON.parse(RConfListJson);
    //  alert(RConfList);
    RConfList.forEach(function (item, i, arr) {
        // alert(item.rConfType+' '+item.name+' '+item.host+' '+item.port+' '+item.activeFlag);
        var table = document.getElementById("listOfInstances");
        var rowCount = table.rows.length;
        var row = table.insertRow(rowCount);
        row.insertCell(0).innerHTML = '<a onClick="CallUpdateForm(this)" role="button" data-toggle="modal" class="btn btn-primary btn-lg"><span class="network-name">Update</span></a>';
        row.insertCell(1).innerHTML = item.name;
        row.insertCell(2).innerHTML = item.rConfType;
        row.insertCell(3).innerHTML = item.activeFlag;
        if(item.host === undefined){
            row.insertCell(4).innerHTML = '-----';
        }else{
            row.insertCell(4).innerHTML = item.host;
        }
        if(item.port===undefined){
            row.insertCell(5).innerHTML = '-----';
        }else {
            row.insertCell(5).innerHTML = item.port;
        }

        row.insertCell(6).innerHTML = '<a onClick="deleteRow(this)" role="button" data-toggle="modal" class="btn btn-primary btn-lg"><span class="network-name">Delete</span></a>';
    });
}
function addRow() {

    var table = document.getElementById("listOfInstances");
    var rowCount = table.rows.length;
    var row = table.insertRow(rowCount);
    var RConf = {
        rConfType: document.getElementById("addRType").value,
        name: document.getElementById("name").value,
        activeFlag: document.getElementById("enabledField").checked,
        host: document.getElementById("host").value,
        port: document.getElementById("port").value

    };
    row.insertCell(0).innerHTML = '<a onClick="CallUpdateForm(this)" role="button" data-toggle="modal" class="btn btn-primary btn-lg"><span class="network-name">Update</span></a>';
    row.insertCell(1).innerHTML = RConf.name;
    row.insertCell(2).innerHTML = RConf.rConfType;
    row.insertCell(3).innerHTML = RConf.activeFlag;
    row.insertCell(4).innerHTML = RConf.host;
    row.insertCell(5).innerHTML = RConf.port;
    row.insertCell(6).innerHTML = '<a onClick="deleteRow(this)" role="button" data-toggle="modal" class="btn btn-primary btn-lg"><span class="network-name">Delete</span></a>';

    var str = JSON.stringify(RConf);
    // alert("/rConf/" + str);
    var xhr = new XMLHttpRequest();
    xhr.open("PUT", "/rConf/" + str, true); // If async=false, then you'll miss progress bar support.
    xhr.send();
}
function deleteRow(obj) {

    var index = obj.parentNode.parentNode.rowIndex;
    var table = document.getElementById("listOfInstances");
    var cell = table.rows[index].cells[1];
    var name = cell.innerHTML;
    // alert("/rConf/" + name);
    table.deleteRow(index);

    var xhr = new XMLHttpRequest();
    xhr.open("DELETE", "/rConf/" + name, true); // If async=false, then you'll miss progress bar support.
    xhr.send();

}
function CallUpdateForm(obj) {
    var index = obj.parentNode.parentNode.rowIndex;
    var table = document.getElementById("listOfInstances");
    var row = table.rows[index];

    //get old data
    var oldRConf = {
        rConfType: row.cells[2].innerHTML,
        name: row.cells[1].innerHTML,
        activeFlag: row.cells[3].innerHTML,
        host: row.cells[4].innerHTML,
        port: row.cells[5].innerHTML
    };
    upObj=obj;
    if (oldRConf.rConfType == 'RserveConf') {
        $('#UpdateModal').on('shown.bs.modal', function () {
            fillUpdateForm(oldRConf,'Rserve','visible');

        }).modal('show');
    }
    if (oldRConf.rConfType == 'FakeRConf') {

        $('#UpdateModal').on('shown.bs.modal', function () {
            fillUpdateForm(oldRConf,'FakeR','hidden');

        }).modal('show');
    }
    if (oldRConf.rConfType == 'RenjinConf') {
        $('#UpdateModal').on('shown.bs.modal', function () {
            fillUpdateForm(oldRConf,'Renjin','hidden');
        }).modal('show');
    }
}
function update(obj) {
    $('#UpdateModal').modal('hide');

    var index = obj.parentNode.parentNode.rowIndex;
    var table = document.getElementById("listOfInstances");
    var row = table.rows[index];
    var oldName = row.cells[1].innerHTML;
    var RConf = {
        rConfType: row.cells[2].innerHTML,
        name: document.getElementById("upName").value,
        activeFlag: document.getElementById("upEnabledField").checked,
        host: document.getElementById("upHost").value,
        port: document.getElementById("upPort").value
    };
    // alert(newName + ' '+newHost+' '+newPort+' ' + newActiveFlag);
    row.cells[1].innerHTML = RConf.name;
    row.cells[4].innerHTML = RConf.host;
    row.cells[5].innerHTML = RConf.port;
    row.cells[3].innerHTML = RConf.activeFlag;

    var str = JSON.stringify(RConf);
    updateOnServer(oldName, str);

}
function updateOnServer(name, str) {
    // alert("/rConf/" + name + '/' + str);
    var xhr = new XMLHttpRequest();
    xhr.open("POST", "/rConf/" + name + '/' + str, true); // If async=false, then you'll miss progress bar support.
    xhr.send();
}
function fillUpdateForm(oldRConf,type,cssStyle){
    //set old data in form
    document.getElementById("upName").value = oldRConf.name;
    document.getElementById("upHost").value = oldRConf.host;
    document.getElementById("upPort").value = oldRConf.port;
    document.getElementById("upType").innerHTML=type;
    $('#hostRow').css('visibility', cssStyle);
    $('#portRow').css('visibility', cssStyle);
}
function closeUpdateForm(){
    $('#UpdateModal').modal('hide');
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
