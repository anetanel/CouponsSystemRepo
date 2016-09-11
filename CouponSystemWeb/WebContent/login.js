/**
 * 
 */

function login() {
	var username = document.getElementById("username").value;
	var password = document.getElementById("password").value;
	var radios = document.getElementsByName("clienttype");
	for (var i = 0, length = radios.length; i < length; i++) {
	    if (radios[i].checked) {
	        var client = radios[i].value;
	        break;
	    }
	}
	
	var xhttp = new XMLHttpRequest();
	  xhttp.onreadystatechange = function() {
	    if (this.readyState == 4 && this.status == 200) {
	    	var login = JSON.parse(this.responseText);
	    	if (login.login == "false") {
	    		document.getElementById("loginMessage").innerHTML = "login failed!";
	    	} else {
	    		document.getElementById("loginMessage").innerHTML = "login ok!";
	    		window.location = "dashboard.html";
	    	}
	    }
	  };
	  xhttp.open("GET", "login?username=" + username + "&password=" + password + "&clienttype=" + client, true);
	  xhttp.send();
}