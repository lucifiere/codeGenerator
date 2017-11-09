/**
 * Created by ao on 2017/10/31.
 */
function copyRow(button) {
    var table = button.parentNode.parentNode.parentNode;
    var buttonRow = button.parentNode.parentNode;
    var row = table.insertRow(buttonRow.rowIndex);
    for(var i = 0;i < buttonRow.cells.length;i++){
        var cell = row.insertCell(i);
        cell.class =  buttonRow.cells[i].class;
        cell.align = buttonRow.cells[i].align;
        cell.bgColor = buttonRow.cells[i].bgColor;
        cell.innerHTML = buttonRow.cells[i].innerHTML;
    }
    var objIndex = getObjIndex(button,button.name);
    document.getElementsByName("eleName")[objIndex+1].value = null;
    document.getElementsByName("dbTable")[objIndex+1].value = null;
    document.getElementsByName("dbColName")[objIndex+1].value = null;
    document.getElementsByName("dbComment")[objIndex+1].value = null;
    document.getElementsByName("beanName")[objIndex+1].value = null;
    document.getElementsByName("inputType")[objIndex+1].value = 1;
    document.getElementsByName("isNullable")[objIndex+1].value = 1;
    document.getElementsByName("lengthLimit")[objIndex+1].value = null;
    document.getElementsByName("typeLimit")[objIndex+1].value = 1;
    updateOrderNum(getObjIndex(table,"table"));
    return row;
}
function deleteRow(button){
    var index = getRowIndex(button);
    var table = getTable(button);
    if(table.rows.length == 1){
        return;
    }
    table.deleteRow(index-1);
    updateOrderNum(getObjIndex(table,"table"));
}
function deleteAllRow(table){
    for(var i = 0;i < table.rows.length;i++){
        table.deleteRow(0);
    }
}
function copyRowAndValue(button) {
    var newRow = copyRow(button);
    var objIndex = getObjIndex(button,button.name);
    document.getElementsByName("eleName")[objIndex+1].value = document.getElementsByName("eleName")[objIndex-1].value;
    document.getElementsByName("dbTable")[objIndex+1].value = document.getElementsByName("dbTable")[objIndex-1].value ;
    document.getElementsByName("dbColName")[objIndex+1].value = document.getElementsByName("dbColName")[objIndex-1].value;
    document.getElementsByName("dbComment")[objIndex+1].value = document.getElementsByName("dbComment")[objIndex-1].value;
    document.getElementsByName("beanName")[objIndex+1].value = document.getElementsByName("beanName")[objIndex-1].value;
    document.getElementsByName("inputType")[objIndex+1].value = document.getElementsByName("inputType")[objIndex-1].value;
    document.getElementsByName("isNullable")[objIndex+1].value = document.getElementsByName("isNullable")[objIndex-1].value;
    document.getElementsByName("lengthLimit")[objIndex+1].value = document.getElementsByName("lengthLimit")[objIndex-1].value;
    document.getElementsByName("typeLimit")[objIndex+1].value = document.getElementsByName("typeLimit")[objIndex-1].value;
}

function copyRowForTable(button,table){
    var buttonRow = button.parentNode.parentNode;
    var row = table.insertRow();
    for(var i = 0;i < buttonRow.cells.length;i++){
        var cell = row.insertCell(i);
        cell.class =  buttonRow.cells[i].class;
        cell.align = buttonRow.cells[i].align;
        cell.bgColor = buttonRow.cells[i].bgColor;
        cell.innerHTML = buttonRow.cells[i].innerHTML;
    }
    var buttonIndex = getObjIndex(button,button.name);
    var tableIndex = getObjIndex(table,"table");
    var objIndex = getRowCount(tableIndex)-1;
    document.getElementsByName("eleName")[objIndex].value = document.getElementsByName("eleName")[buttonIndex].value;
    document.getElementsByName("dbTable")[objIndex].value = document.getElementsByName("dbTable")[buttonIndex].value ;
    document.getElementsByName("dbColName")[objIndex].value = document.getElementsByName("dbColName")[buttonIndex].value;
    document.getElementsByName("dbComment")[objIndex].value = document.getElementsByName("dbComment")[buttonIndex].value;
    document.getElementsByName("beanName")[objIndex].value = document.getElementsByName("beanName")[buttonIndex].value;
    document.getElementsByName("inputType")[objIndex].value = document.getElementsByName("inputType")[buttonIndex].value;
    document.getElementsByName("isNullable")[objIndex].value = document.getElementsByName("isNullable")[buttonIndex].value;
    document.getElementsByName("lengthLimit")[objIndex].value = document.getElementsByName("lengthLimit")[buttonIndex].value;
    document.getElementsByName("typeLimit")[objIndex].value = document.getElementsByName("typeLimit")[buttonIndex].value;
    updateOrderNum(getObjIndex(table,"table"));
}
function getRowIndex(button){
    return button.parentNode.parentNode.rowIndex;
}
function getRowNum(button){
    return button.parentNode.parentNode.parentNode.rows.length;
}

function getTable(obj){
    return obj.parentNode.parentNode.parentNode;
}

function getObjIndex(table,name){
    var tableList = document.getElementsByName(name);
    for(var i = 0;i < tableList.length;i++){
        if(tableList[i] === table){
            return i;
        }
    }
}
function updateOrderNum(tableIndex){
    var orderNums = document.getElementsByName("ordinalNumber");
    var startIndex = getRowCount(tableIndex-1);
    var endIndex = getRowCount(tableIndex);
    for(var  i = startIndex;i < endIndex;i++){
        orderNums[i].value = i - startIndex+1;
    }
}
function getRowCount(tableIndex){
    var tableList = document.getElementsByName("table");
    var count = 0;
    for(var i = 0;i <= tableIndex;i++){
        count += tableList[i].rows.length;
    }
    return count;
}