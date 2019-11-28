//hanlde updating quantity
function increaseValue(elementID) {
	console.log(document.getElementById(elementID));
  var value = parseInt(document.getElementById(elementID).value, 10);
  console.log(elementID);
  value = isNaN(value) ? 0 : value;
  value++;
  document.getElementById(elementID).value = value;
}

function decreaseValue(elementID) {
  var value = parseInt(document.getElementById(elementID).value, 10);
  value = isNaN(value) ? 0 : value;
  value < 1 ? value = 1 : '';
  value--;
  document.getElementById(elementID).value = value;
}

//update the cart table dynamically
function UpdateTable(rowInfo){
	var table = document.getElementById("cartTable");
	var lastRowIndex = table.rows.length -1;
	console.log(lastRowIndex);
	var row = table.insertRow(lastRowIndex+1);

	var cell1 = row.insertCell(0);
	var cell2 = row.insertCell(1);
	var cell3 = row.insertCell(2);
	var cell4 = row.insertCell(3);

	cell1.innerHTML = rowInfo[0];
	cell2.innerHTML = rowInfo[1];
	cell3.innerHTML = "<form class='center' >"+
`<div class='value-button' id='decrease' onclick='decreaseValue("row${lastRowIndex+1}")' value='Decrease Value'>-</div>`+
`<input type='number' class="number" id='row${lastRowIndex+1}' value='1' />`+
`<div class='value-button' id='increase' onclick='increaseValue("row${lastRowIndex+1}")' value='Increase Value'>+</div>`+
"</form>"
	console.log(cell3.innerHTML);
	cell4.innerHTML = "Temp";

	}
