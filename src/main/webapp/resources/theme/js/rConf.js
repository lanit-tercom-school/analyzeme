/**
 * Created by asus on 17.04.2016.
 */
function addRow() {

    var name = document.getElementById("name");
    var RType = document.getElementById("Rtype");
    var port = document.getElementById("port");
    var enabled = document.getElementById("enabledField");

    var table = document.getElementById("listOfInstances");

    var rowCount = table.rows.length;
    var row = table.insertRow(rowCount);

    row.insertCell(0).innerHTML = '<input type="button" value = "Delete" onClick="Javacsript:deleteRow(this)">';
    row.insertCell(1).innerHTML = name.value;
    row.insertCell(2).innerHTML = RType.value;
    row.insertCell(3).innerHTML = port.value;
    row.insertCell(4).innerHTML = enabled.checked;

}

function deleteRow(obj) {

    var index = obj.parentNode.parentNode.rowIndex;
    var table = document.getElementById("listOfInstances");
    table.deleteRow(index);

}


function addTable() {

    var myTableDiv = document.getElementById("myDynamicTable");

    var table = document.createElement('TABLE');
    table.border = '1';

    var tableBody = document.createElement('TBODY');
    table.appendChild(tableBody);

    for (var i = 0; i < 3; i++) {
        var tr = document.createElement('TR');
        tableBody.appendChild(tr);

        for (var j = 0; j < 4; j++) {
            var td = document.createElement('TD');
            td.width = '75';
            td.appendChild(document.createTextNode("Cell " + i + "," + j));
            tr.appendChild(td);
        }
    }
    myTableDiv.appendChild(table);

}

function load() {

    console.log("Page load finished");

}