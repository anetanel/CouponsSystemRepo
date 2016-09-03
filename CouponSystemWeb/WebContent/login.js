/**
 * 
 */

function login() {
	var username = document.getElementById("username").value;
	var password = document.getElementById("password").value;
	var client = document.getElementById("client").value;
	
	var xhttp = new XMLHttpRequest();
	  xhttp.onreadystatechange = function() {
	    if (this.readyState == 4 && this.status == 200) {
	      document.getElementById("demo").innerHTML = this.responseText;
	    }
	  };
	  xhttp.open("GET", "rest/test/login?username=" + username + "&password=" + password + "&clientType=" + client, true);
	  xhttp.send();
}