/**
 * Created by ao on 2017/11/3.
 */
function changeOrdinalNumber(obj){
    var ordinalNumberList = document.getElementsByName("ordinalNumber");
    var newValue = obj.value;
    var oldValue = getRowIndex(obj);
    var table = obj.parentNode.parentNode.parentNode;
    ordinalNumberList = removeArrayElement(oldValue-1,ordinalNumberList);
    ordinalNumberList = addArrayElement(newValue-1,obj,ordinalNumberList);
    var colNum = ordinalNumberList.length;
    for(var i = 0;i < colNum;i++){
        ordinalNumberList[i].value = i + 1;
        var row = ordinalNumberList[i].parentNode.parentNode;
        addRow(row,table);
    }
    for(var i = 0;i < colNum;i++){
        table.deleteRow(0);
    }
}
function removeArrayElement(index,array){
    var newArray = new Array(array.length-1);
    var j = 0;
    for(var i = 0;i < array.length;i++){
        if(i != index) {
            newArray[j] = array[i];
            j++;
        }
    }
    return newArray;
}
function addArrayElement(index,ele,array) {
    var newArray = new Array(array.length+1);
    var j = 0;
    for(var i = 0;i < array.length;i++){
        if(i != index) {
            newArray[j] = array[i];
        }else{
            newArray[j] =  ele;
            j++;
            newArray[j] = array[i];
        }
        j++;
    }
    return newArray;
}

function changeTableTag(obj) {
    var isTable = obj.value;
    if(isTable == 0){
        return;
    }
    var index = getRowIndex(obj);
    if(index == 1){
        if(document.getElementsByName("tableName")[0] == null){
            return;
        }
    }
    var colNum = getRowNum(obj);
    var table = obj.parentNode.parentNode.parentNode;
    var tableIndex = getTableIndex(table,"table");
    var startIndex = getRowCount(tableIndex-1);
    var endIndex = getRowCount(tableIndex);
    createNewTable();
    var newTable = document.getElementsByName("table")[getTableIndex(table,"table")+1];
    for(var i = startIndex + index;i < endIndex;i++){
        addRow(table.rows[i],newTable);
    }
    for(var i = startIndex + index-1;i < endIndex;i++){
        table.deleteRow(index-1);
    }
}
function getTableIndex(table,name){
    var tableList = document.getElementsByName(name);
    for(var i = 0;i < tableList.length;i++){
        if(tableList[i] === table){
            return i;
        }
    }
}

function createNewTable(){
    var tableHtml = '<table width="100%"  class="table-7">'
        +'<thead>'
        +'<th>正确顺序</th>'
        +'<th>是否表格标题</th>'
        +'<th>页面字段名</th>'
        +'<th>来源表名</th>'
        +'<th>数据库字段名</th>'
        +'<th>数据库字段说明</th>'
        +'<th>来源bean名称</th>'
        +'<th>页面输入类型</th>'
        +'<th>值类型限制</th>'
        +'<th>必填</th>'
        +'<th>长度限制</th>'
        +'</thead>'
        +'<tbody name="table">'
        +'</tbody>'
        +'</table>';
    $("#pageConf").append(tableHtml);
}

function orderByTableName(obj){
    var tableIndex = getTableIndex(obj,obj.name);
    var table = document.getElementsByName("table")[tableIndex];
    var startIndex = getRowCount(tableIndex-1);
    var endIndex = getRowCount(tableIndex);

    var tableMapList = new Object();
    var dbTables = document.getElementsByName("dbTable");
    var tableNameSet =[];
    for(var i = startIndex;i < endIndex;i++){
        var tableName = ifBlankReturnNull(dbTables[i].value);
        if(tableName == null){
            tableName = getFirstNotNullTableName(i,startIndex,endIndex);
            if(tableName == null){
                return;
            }
        }

        addIfAbsent(tableNameSet,tableName);

        var tableList = tableMapList[tableName];
        if(tableList == null){
            var newList = [];
            tableMapList[tableName] = newList;
        }
        tableMapList[tableName].push(dbTables[i]);
    }
    dbTables = [];
    for(var i = 0;i < tableNameSet.length;i++){
        var tables = tableMapList[tableNameSet[i]];
        for(var j = 0;j <tables.length;j++) {
            dbTables.push(tables[j]);
        }
    }

    for(var i = 0;i < dbTables.length;i++){
        copyRowAndValue(dbTables[i]);
    }

    for(var i = 1;i < dbTables.length;i++){
        table.deleteRow(1);
    }
    var orderNum = document.getElementsByName("ordinalNumber");
    for(var i = 1;i < dbTables.length;i++){
        orderNum[i + startIndex].value = i+1;
    }
    return dbTables;
}

function addIfAbsent(array,ele){
    var contain = false;
    for(var i = 0;i < array.length;i++){
          if(array[i] == ele){
              contain = true;
              break;
          }
    }
    if(!contain) {
        array.push(ele);
    }
}

function getFirstNotNullTableName(start,startIndex,endIndex){
    var dbTables = document.getElementsByName("dbTable");
    for(var i = start;i < startIndex;i--){
        var tableName = ifBlankReturnNull(dbTables[i].value);
        if(tableName != null){
            return tableName;
        }
    }
    for(var i = start;i < endIndex;i++){
        var tableName = ifBlankReturnNull(dbTables[i].value);
        if(tableName != null){
            return tableName;
        }
    }
    return null;
}

function getRowCount(tableIndex){
    var tableList = document.getElementsByName("table");
    var count = 0;
    for(var i = 0;i <= tableIndex;i++){
        count += tableList[i].rows.length;
    }
    return count;
}



function  changDBColName(obj) {
    var index = getTableIndex(obj,obj.name);
    var colName = ifBlankReturnNull(obj.value);
    if(colName == null){
        return;
    }
    $.ajax({
        type: "post",
        url: basePath+"/conf/changeDBColName.do",
        data: {tableNames: document.getElementById("tableName").value,dbColName:colName},
        dataType: "json",
        async: false,
        error: function () {
            alert("查询失败");
        },
        success: function (data) {
            if(data.length == 0){
                return;
            }
            var element = data[0];
            document.getElementsByName("dbTable")[index].value = element.dbTable;
            document.getElementsByName("dbColName")[index].value = element.dbColName;
            document.getElementsByName("dbComment")[index].value = element.dbComment;
            document.getElementsByName("beanName")[index].value = element.beanName;
            document.getElementsByName("inputType")[index].value = element.inputType;
            document.getElementsByName("isNullable")[index].value = element.isNullable;
            document.getElementsByName("lengthLimit")[index].value = element.lengthLimit;
            document.getElementsByName("typeLimit")[index].value = element.typeLimit;
        }
    });
}

function getColList(obj) {
    var index = getTableIndex(obj,obj.name);
    var tableName = document.getElementsByName("dbTable")[index].value;
    $.ajax({
        type: "post",
        url: basePath+"/conf/allElementList.do",
        data: {tableNames: document.getElementById("tableName").value,tableName:tableName},
        dataType: "json",
        async: false,
        error: function () {
            alert("查询失败");
        },
        success: function (data) {
            if(data.length == 0){
                return;
            }
            var dataList = [];
            for(var i = 0; i < data.length;i++){
                var oneRow = data[i].dbColName + "    "+data[i].dbTable +"    "+
                    (data[i].dbComment == null ? "" : (data[i].dbComment.length > 5 ? data[i].dbComment.substring(0,5)+"..." : data[i].dbComment));
                dataList.push(oneRow);
            }
            return dataList;
        }
    });
}
function getTableList(){
    var tableName = document.getElementById("tableName");
    return tableName.split(",");
}