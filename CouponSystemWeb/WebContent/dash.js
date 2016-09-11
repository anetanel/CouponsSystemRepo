/**
 * 
 */


function whoAmI(getClientType) {
	var xhttp = new XMLHttpRequest();
	  xhttp.onreadystatechange = function() {
	    if (this.readyState == 4 && this.status == 200) {
	    	var result = JSON.parse(this.responseText);
	    	document.getElementById("info").innerHTML = result;
	    }
	  };
	  var clientType = getClientType;
	  xhttp.open("GET", "rest/" + clientType + "/whoami", true);
	  xhttp.send();
}

function getClientType() {
	var xhttp = new XMLHttpRequest();
	  xhttp.onreadystatechange = function() {
	    if (this.readyState == 4 && this.status == 200) {
	    	var clientType = JSON.parse(this.responseText);
//	    	alert(clientType.clientType.toLowerCase());
	    	return clientType.clientType.toLowerCase();
	    }
	  };
	  xhttp.open("GET", "rest/general/clienttype", true);
	  xhttp.send();
}