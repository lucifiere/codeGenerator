/**
 * Created by ao on 2017/10/31.
 */
function copyRow(button) {
    var table = button.parentNode.parentNode.parentNode;
    var buttonRow = button.parentNode.parentNode;
    addRow(buttonRow,table);
    /*var row = table.insertRow();
    for(var i = 0;i < buttonRow.cells.length;i++){
        var cell = row.insertCell(i);
        cell.class =  buttonRow.cells[i].class;
        cell.align = buttonRow.cells[i].align;
        cell.bgColor = buttonRow.cells[i].bgColor;
        cell.innerHTML = buttonRow.cells[i].innerHTML;
    }*/
}
function addRow(row,table){
    var newRow = table.insertRow();
    for(var i = 0;i < row.cells.length;i++){
        var cell = newRow.insertCell(i);
        cell.class =  row.cells[i].class;
        cell.align = row.cells[i].align;
        cell.bgColor = row.cells[i].bgColor;
        cell.innerHTML = row.cells[i].innerHTML;
        cell.childNodes[0].value = row.cells[i].childNodes[0].value;
    }
}
function add(){

}