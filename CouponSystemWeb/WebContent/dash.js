/**
 * 
 */

function whoAmI() {

	var xhttp = new XMLHttpRequest();
	xhttp.onreadystatechange = function() {
		if (this.readyState == 4 && this.status == 200) {
			//var result = JSON.parse(this.responseText);
			document.getElementById("info").innerHTML = this.responseText;
		}
	};
	xhttp.open("GET", "rest/" + getClientType() + "/whoami", true);
	xhttp.send();
}

function getAllCompanies() {

	var xhttp = new XMLHttpRequest();
	xhttp.onreadystatechange = function() {
		//if (this.readyState == 4 && this.status == 200) {
			//var result = JSON.parse(this.responseText);
			document.getElementById("info").innerHTML = this.responseText;
		//}
	};
	xhttp.open("GET", "rest/" + getClientType() + "/getallcompanies", true);
	xhttp.send();
}

function getClientType() {
	var xhttp = new XMLHttpRequest();
	xhttp.open("GET", "rest/general/clienttype", false);
	xhttp.send();
	return xhttp.responseText;
}
