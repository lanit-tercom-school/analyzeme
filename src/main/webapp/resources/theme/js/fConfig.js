function load() {
    createDefaultTableF();
}
var upObj;
var delObj;
function createDefaultTableF() {
    //todo : получать данные из modal and view напрямую а не через элемент на странице
    var FConfListJson = document.getElementById("getdataf").innerHTML;
    // alert(FConfListJson);
    var FConfList = JSON.parse(FConfListJson);
    //  alert(FConfList);
    FConfList.forEach(function (item, i, arr) {
        // alertfConfType+' '+item.name+' '+item.ServiceAccount+' '+item.serviceAccount+' '+item.activeFlag);
        var newFConf = {
      fConfType: item.fConfType,
            name: item.name,
            activeFlag: item.activeFlag,
            serviceAccount: item.serviceAccount,
            databaseUrl: item.databaseUrl
        };
        if (newFConf.serviceAccount === undefined) {
           newFConf.serviceAccount = '-----';
        }
        if (newFConf.databaseUrl === undefined) {
           newFConf.databaseUrl = '-----';
        }
        addRowF(newFConf);
    });
}
function addFConf() {
    var table = document.getElementById("listOfInstancesf");
    var rowCount = table.rows.length;
    var row = table.insertRow(rowCount);
    var FConf = {
  fConfType: document.getElementById("addFType").value,
        name: document.getElementById("namef").value,
        activeFlag: document.getElementById("enabledField").checked,
        serviceAccount: document.getElementById("serviceAccount").value,
        databaseUrl: document.getElementById("databaseUrl").value
    };
    addRowF(FConf);
    var str = JSON.stringify(FConf);
    // alert("/FConf/" + str);
    var xhr = new XMLHttpRequest();
    xhr.open("PUT", "/fConf/" + str, true); // If async=false, then you'll miss progress bar support.
    xhr.send();
}
function CallDeleteFormf(obj) {
    delObj = obj;
    var index = obj.parentNode.parentNode.rowIndex;
    var table = document.getElementById("listOfInstancesf");
    document.getElementById("delNamef").innerHTML = table.rows[index].cells[0].innerHTML;
    $('#deleteModalf').modal('show');

}
function deleteFConf(obj) {
    $('#deleteModalf').modal('hide');
    var index = obj.parentNode.parentNode.rowIndex;
    var table = document.getElementById("listOfInstancesf");
    var cell = table.rows[index].cells[0];
    var name = cell.innerHTML;
    // alert("/FConf/" + name);
    table.deleteRow(index);

    var xhr = new XMLHttpRequest();
    xhr.open("DELETE", "/fConf/" + name, true); // If async=false, then you'll miss progress bar support.
    xhr.send();

}
function closeDeleteFormF() {
    $('#deleteModalf').modal('hide');
}
function CallUpdateFormf(obj) {
    var index = obj.parentNode.parentNode.rowIndex;
    var table = document.getElementById("listOfInstancesf");
    var row = table.rows[index];

    //get old data
    var oldFConf = {
  fConfType: row.cells[1].innerHTML,
        name: row.cells[0].innerHTML,
        activeFlag: row.cells[2].innerHTML,
        serviceAccount: row.cells[3].innerHTML,
        databaseUrl: row.cells[4].innerHTML
    };
    upObj = obj;
    if (oldFConf.fConfType == 'Firebase') {
        $('#UpdateModalf').on('shown.bs.modal', function () {
            fillUpdateFormf(oldFConf, 'Firebase', 'visible');

        }).modal('show');
    }
    if (oldFConf.fConfType == 'TestF') {

        $('#UpdateModalf').on('shown.bs.modal', function () {
            fillUpdateFormf(oldFConf, 'TestF', 'hidden');

        }).modal('show');
    }
}
function updateF(obj) {
    $('#UpdateModalf').modal('hide');

    var index = obj.parentNode.parentNode.rowIndex;
    var table = document.getElementById("listOfInstancesf");
    var row = table.rows[index];
    var oldName = row.cells[0].innerHTML;
    var FConf = {
  fConfType: getFullFConf(row.cells[1].innerHTML),
        name: document.getElementById("upName").value,
        activeFlag: document.getElementById("upEnabledField").checked,
        serviceAccount: document.getElementById("upServiceAccount").value,
        databaseUrl: document.getElementById("upDatabaseUrl").value
    };
    // alert(newName + ' '+newServiceAccount+' '+newDatabaseUrl+' ' + newActiveFlag);
    updateRowf(FConf, row);
    var str = JSON.stringify(FConf);
    sendUpdateRequest(oldName, str);

}
function updateRowf(FConf, row) {
    row.cells[0].innerHTML = FConf.name;
    if (FConf.activeFlag) {
        row.cells[2].innerHTML = '<span class="glyphicon glyphicon-ok"></span>';
    } else {
        row.cells[2].innerHTML = '<span class="glyphicon glyphicon-remove"></span>';
    }
    row.cells[3].innerHTML = FConf.serviceAccount;
    row.cells[4].innerHTML = FConf.databaseUrl;
}
function sendUpdateRequest(name, str) {
    // alert("/FConf/" + name + '/' + str);
    var xhr = new XMLHttpRequest();
    xhr.open("POST", "/fConf/" + name + '/' + str, true); // If async=false, then you'll miss progress bar support.
    xhr.send();
}
function fillUpdateFormf(oldFConf, type, cssStyle) {
    //set old data in form
    document.getElementById("upName").value = oldFConf.name;
    document.getElementById("upServiceAccount").value = oldFConf.serviceAccount;
    document.getElementById("upDatabaseUrl").value = oldFConf.databaseUrl;
    document.getElementById("upType").innerHTML = type;
    $('#serviceAccountRow').css('visibility', cssStyle);
    $('#databaseUrlRow').css('visibility', cssStyle);
}
function closeUpdateFormF() {
    $('#UpdateModalf').modal('hide');
}

function addRowF(FConf) {
    var table = document.getElementById("listOfInstancesf");
    var rowCount = table.rows.length;
    var row = table.insertRow(rowCount);
    row.insertCell(0).innerHTML = FConf.name;
    row.insertCell(1).innerHTML = fConfToTable(FConf.fConfType);
    if (FConf.activeFlag) {
        row.insertCell(2).innerHTML = '<span class="glyphicon glyphicon-ok"></span>';
    } else {
        row.insertCell(2).innerHTML = '<span class="glyphicon glyphicon-remove"></span>';
    }
    row.insertCell(3).innerHTML = FConf.serviceAccount;
    row.insertCell(4).innerHTML = FConf.databaseUrl;
    row.insertCell(5).innerHTML = '<a onClick="CallUpdateFormf(this)" role="button" data-toggle="modal" class="btn btn-primary btn-lg">' +
        '<span class="network-name"><span class="glyphicon glyphicon-pencil"></span></span></a>';
    row.insertCell(6).innerHTML = '<a onClick="CallDeleteFormf(this)" role="button" data-toggle="modal" class="btn btn-primary btn-lg">' +
        '<span class="network-name"><span class="glyphicon glyphicon-trash"></span> </span></a>';

}

function getFullFConf(st) {
    if (st == 'Firebase') {
        return 'FirebaseConf';
    } else if (st == 'TestF') {
        return 'TestFConf'
    }

    return null;
}
function fConfToTable(st) {
    if (st == 'FirebaseConf') {
        return 'Firebase';
    } else if (st == 'TestFConf') {
        return 'TestF'
    }
    return null;
}

