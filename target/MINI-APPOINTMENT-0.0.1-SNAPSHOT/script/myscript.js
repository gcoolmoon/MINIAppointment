$(function() {
	// make ajax request
	getAppointments();
	// validate date input
	validateDateInput();
	// define onclick for newButton

	$('#searchButton').click(getAppointments);
	

});


//PRE VALIDATE DATE TIME PICKER TO START FROM TODAY
function validateDateInput() {
	
	
	var today = new Date();
	var day = today.getDate();
	var month = today.getMonth() + 1;
	var year = today.getFullYear();
	if (day < 10) {
		day = '0' + day
	}
	if (month < 10) {
		month = '0' + month
	}

	today = year + '-' + month + '-' + day;
	console.log("Date: "+today);
	document.getElementById("appointmentDate").setAttribute("min", today);
}

//DOM CONTROLLING FUNTION TO HIDE/VIEW 
function newAppointment() {
	var tobeHidden = document.getElementById("createNew");
	var hiddenForm = document.getElementById("newForm");
	if (hiddenForm.style.display === "none") {
		hiddenForm.style.display = "block";
	} else {
		hiddenForm.style.display = "none";
	}

	tobeHidden.style.display = "none";
}

//FUNCTION TO BE CALLED FOR VALIDATION
function validateForm() {
	if (checkDate())
		return true;
	else {
		alert("Please enter a valid date!");
		return false;
	}
}

//IMPLEMTATION OF VALIDATING DATE ON FORM SUBMISSION
function checkDate() {
	var dateString = document.getElementById('appointmentDate').value;
	var timeString = document.getElementById('appointmentTime').value;
   
	var myDate = new Date(dateString.replace(/-/g, '/'));

	var day = myDate.getDate();
	var month = myDate.getMonth() + 1;
	var year = myDate.getFullYear();
	var res = timeString.split(":");
	var hours = res[0];
	var minutes = res[1];
	
	
	
	myDate.setHours(hours);
	myDate.setMinutes(minutes);
	if (myDate <= today) {
		
		$('#error').text('You cannot enter a date in the past!');
		return false;
	}
	return true;
}

// GET LIST OF APPOINTMENTS FROM SERVLET
function getAppointments() {

	var appoint = new Object();
	appoint.appointmentDate = $('#appointmentDate').val();
	appoint.description = $('#appointmentDesc').val();
	

	$.ajax({
		url : "appointServlet",
		type : 'GET',
		dataType : 'json',
		data : {
			'searchText' : $("#searchText").val()
		},
		contentType : 'application/json',
		mimeType : 'application/json',

		success : function(data) {
			createTableFromData(data);

		},
		error : function(data, status, er) {
			alert("error: " + data + " status: " + status + " er:" + er);
		}
	});
}



function createTableFromData(data) {
	var myBooks = data;
	var header = [ "DATE", "TIME", "DESCRIPTION" ];
	console.log("creating tables .... ");
	var col = [];
	for (var i = 0; i < myBooks.length; i++) {
		for ( var key in myBooks[i]) {
			if (col.indexOf(key) === -1) {
				col.push(key);
			}
		}
	}

	//ADDING TABLE
	var table = document.createElement("table");
	table.className = "table";

	var tr = table.insertRow(-1); 

	for (var i = 1; i < col.length; i++) { 
		var th = document.createElement("th"); 
		th.innerHTML = header[i - 1];
		tr.appendChild(th);
	}

	// ADD DATA TO THE TABLE
	for (var i = 0; i < myBooks.length; i++) {

		tr = table.insertRow(-1);

		for (var j = 1; j < col.length; j++) {
			var tabCell = tr.insertCell(-1);
			tabCell.innerHTML = myBooks[i][col[j]];
		}
	}

	// ADD TABLE TO THE CONTAINER
	var divContainer = document.getElementById("showData");
	divContainer.innerHTML = "";
	divContainer.appendChild(table);

	console.log("table created and data added ... ");
}