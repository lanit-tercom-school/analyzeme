function load() {
    createDefaultTable();
}
var upObj;
var delObj;
function createDefaultTable() {
    //todo : получать данные из modal and view напрямую а не через элемент на странице
    var RConfListJson = document.getElementById("getdata").innerHTML;
    // alert(RConfListJson);
    var RConfList = JSON.parse(RConfListJson);
    //  alert(RConfList);
    RConfList.forEach(function (item, i, arr) {
        // alert(item.rConfType+' '+item.name+' '+item.host+' '+item.port+' '+item.activeFlag);
        var newRConf = {
            rConfType: item.rConfType,
            name: item.name,
            activeFlag: item.activeFlag,
            host: item.host,
            port: item.port
        };
        if (newRConf.host === undefined) {
            newRConf.host = '-----';
        }
        if (newRConf.port === undefined) {
            newRConf.port = '-----';
        }
        addRow(newRConf);
    });
}
function addRConf() {
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
    addRow(RConf);
    var str = JSON.stringify(RConf);
    // alert("/rConf/" + str);
    var xhr = new XMLHttpRequest();
    xhr.open("PUT", "/rConf/" + str, true); // If async=false, then you'll miss progress bar support.
    xhr.send();
}
function CallDeleteForm(obj) {
    delObj = obj;
    var index = obj.parentNode.parentNode.rowIndex;
    var table = document.getElementById("listOfInstances");
    document.getElementById("delName").innerHTML = table.rows[index].cells[0].innerHTML;
    $('#deleteModal').modal('show');

}
function deleteRconf(obj) {
    $('#deleteModal').modal('hide');
    var index = obj.parentNode.parentNode.rowIndex;
    var table = document.getElementById("listOfInstances");
    var cell = table.rows[index].cells[0];
    var name = cell.innerHTML;
    // alert("/rConf/" + name);
    table.deleteRow(index);

    var xhr = new XMLHttpRequest();
    xhr.open("DELETE", "/rConf/" + name, true); // If async=false, then you'll miss progress bar support.
    xhr.send();

}
function closeDeleteForm() {
    $('#deleteModal').modal('hide');
}
function CallUpdateForm(obj) {
    var index = obj.parentNode.parentNode.rowIndex;
    var table = document.getElementById("listOfInstances");
    var row = table.rows[index];

    //get old data
    var oldRConf = {
        rConfType: row.cells[1].innerHTML,
        name: row.cells[0].innerHTML,
        activeFlag: row.cells[2].innerHTML,
        host: row.cells[3].innerHTML,
        port: row.cells[4].innerHTML
    };
    upObj = obj;
    if (oldRConf.rConfType == 'Rserve') {
        $('#UpdateModal').on('shown.bs.modal', function () {
            fillUpdateForm(oldRConf, 'Rserve', 'visible');

        }).modal('show');
    }
    if (oldRConf.rConfType == 'FakeR') {

        $('#UpdateModal').on('shown.bs.modal', function () {
            fillUpdateForm(oldRConf, 'FakeR', 'hidden');

        }).modal('show');
    }
    if (oldRConf.rConfType == 'Renjin') {
        $('#UpdateModal').on('shown.bs.modal', function () {
            fillUpdateForm(oldRConf, 'Renjin', 'hidden');
        }).modal('show');
    }
}
function update(obj) {
    $('#UpdateModal').modal('hide');

    var index = obj.parentNode.parentNode.rowIndex;
    var table = document.getElementById("listOfInstances");
    var row = table.rows[index];
    var oldName = row.cells[0].innerHTML;
    var RConf = {
        rConfType: getFullRConf(row.cells[1].innerHTML),
        name: document.getElementById("upName").value,
        activeFlag: document.getElementById("upEnabledField").checked,
        host: document.getElementById("upHost").value,
        port: document.getElementById("upPort").value
    };
    // alert(newName + ' '+newHost+' '+newPort+' ' + newActiveFlag);
    updateRow(RConf, row);
    var str = JSON.stringify(RConf);
    sendUpdateRequest(oldName, str);

}
function updateRow(RConf, row) {
    row.cells[0].innerHTML = RConf.name;
    if (RConf.activeFlag) {
        row.cells[2].innerHTML = '<span class="glyphicon glyphicon-ok"></span>';
    } else {
        row.cells[2].innerHTML = '<span class="glyphicon glyphicon-remove"></span>';
    }
    row.cells[3].innerHTML = RConf.host;
    row.cells[4].innerHTML = RConf.port;
}
function sendUpdateRequest(name, str) {
    // alert("/rConf/" + name + '/' + str);
    var xhr = new XMLHttpRequest();
    xhr.open("POST", "/rConf/" + name + '/' + str, true); // If async=false, then you'll miss progress bar support.
    xhr.send();
}
function fillUpdateForm(oldRConf, type, cssStyle) {
    //set old data in form
    document.getElementById("upName").value = oldRConf.name;
    document.getElementById("upHost").value = oldRConf.host;
    document.getElementById("upPort").value = oldRConf.port;
    document.getElementById("upType").innerHTML = type;
    $('#hostRow').css('visibility', cssStyle);
    $('#portRow').css('visibility', cssStyle);
}
function closeUpdateForm() {
    $('#UpdateModal').modal('hide');
}

function addRow(RConf) {
    var table = document.getElementById("listOfInstances");
    var rowCount = table.rows.length;
    var row = table.insertRow(rowCount);
    row.insertCell(0).innerHTML = RConf.name;
    row.insertCell(1).innerHTML = rConfToTable(RConf.rConfType);
    if (RConf.activeFlag) {
        row.insertCell(2).innerHTML = '<span class="glyphicon glyphicon-ok"></span>';
    } else {
        row.insertCell(2).innerHTML = '<span class="glyphicon glyphicon-remove"></span>';
    }
    row.insertCell(3).innerHTML = RConf.host;
    row.insertCell(4).innerHTML = RConf.port;
    row.insertCell(5).innerHTML = '<a onClick="CallUpdateForm(this)" role="button" data-toggle="modal" class="btn btn-primary btn-lg">' +
        '<span class="network-name"><span class="glyphicon glyphicon-pencil"></span></span></a>';
    row.insertCell(6).innerHTML = '<a onClick="CallDeleteForm(this)" role="button" data-toggle="modal" class="btn btn-primary btn-lg">' +
        '<span class="network-name"><span class="glyphicon glyphicon-trash"></span> </span></a>';

}

function getFullRConf(st) {
    if (st == 'Rserve') {
        return 'RserveConf';
    } else if (st == 'FakeR') {
        return 'FakeRConf'
    } else if (st == 'Renjin') {
        return 'RenjinConf';
    }
    return null;
}
function rConfToTable(st) {
    if (st == 'RserveConf') {
        return 'Rserve';
    } else if (st == 'FakeRConf') {
        return 'FakeR'
    } else if (st == 'RenjinConf') {
        return 'Renjin';
    }
    return null;
}
