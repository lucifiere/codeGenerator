/**
 * Created by ao on 2017/11/10.
 */
function  changDBColName(obj) {
    var index = getTableIndex(obj,obj.name);
    var colName = ifBlankReturnNull(obj.value);
    if(colName == null || colName.trim().length==0){
        return ;
    }
    var eleName = document.getElementsByName("eleName")[index].value;
    if(eleName == null || eleName.trim().length==0){
        return ;
    }
    $.ajax({
        type: "post",
        url: basePath+"/conf/changeDBColName.do",
        data: {title:"",tableNames: document.getElementById("tableName").value,dbColName:colName,"eleName":eleName},
        dataType: "json",
        async: false,
        error: function () {
            alert("查询失败");
        },
        success: function (data) {
            display(obj,data,"dbColNameDiv");
            display(obj,data,"dbCommentDiv");
            display(obj,data,"dbTableDiv");
        }
    });
}

function display(obj,dataList,divName){
    var html = "<ul>";
    for(var i = 0;i < dataList.length;i++){
        html += createOneRow(divName,obj,eleNameList,dataList[i]);
    }
    html +="</ul>";
    var index = getObjIndex(obj,obj.name);
    document.getElementsByName(divName)[index].innerHTML="";
    document.getElementsByName(divName)[index].innerHTML=html;
    document.getElementsByName(divName)[index].style.display="";
}
function createOneRow(divName,obj,data){
    return '<li width="'+obj.width+'" '
        + 'name="'+obj.name+'li"'
        + 'onclick="setValue('+obj+','+obj+','+divName+')" '
        + ' onMouseOver="selected('+obj+','+data+','+this+')" '
        + ' onMouseOut="noSelect('+obj+','+data+','+this+')"'
        + '>'
        + obj[eleName]
        + '</li>';
}
function setValue(obj,data,divName){
    var index = getObjIndex(obj);
    document.getElementsByName("dbTable")[index].value = data.dbTable;
    document.getElementsByName("dbColName")[index].value = data.dbColName;
    document.getElementsByName("dbComment")[index].value = data.dbComment;
    document.getElementsByName("beanName")[index].value = data.beanName;
    document.getElementsByName("inputType")[index].value = data.inputType;
    document.getElementsByName("isNullable")[index].value = data.isNullable;
    document.getElementsByName("lengthLimit")[index].value = data.lengthLimit;
    document.getElementsByName("typeLimit")[index].value = data.typeLimit;
    document.getElementsByName("divName")[index].style.display="none";
}
function selected(obj,li,data){
    var index = getObjIndex(li,li.name);
    var liCount = document.getElementsByName("dbTableLi").length;
    for(var i = 0;i < liCount;i++){
        document.getElementsByName("dbTableLi")[index].style.color = "white";
        document.getElementsByName("dbColNameLi")[index].style.color = "white";
        document.getElementsByName("dbCommentLi")[index].style.color = "white";
    }
    document.getElementsByName("dbTableLi")[index].style.color = "gray";
    document.getElementsByName("dbColNameLi")[index].style.color = "gray";
    document.getElementsByName("dbCommentLi")[index].style.color = "gray";
}
function selected(obj,li,data){
    selected(obj,li,data);
}
function noSelect(obj,li,data){

}
function moveInput(obj){
    var index = getObjIndex(obj);
    document.getElementsByName("divName")[index].style.display="none";
}