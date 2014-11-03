/*
 * © Copyright IBM Corp. 2014
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); 
 * you may not use this file except in compliance with the License. 
 * You may obtain a copy of the License at:
 * 
 * http://www.apache.org/licenses/LICENSE-2.0 
 * 
 * Unless required by applicable law or agreed to in writing, software 
 * distributed under the License is distributed on an "AS IS" BASIS, 
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or 
 * implied. See the License for the specific language governing 
 * permissions and limitations under the License.
 */
function submitOnClick(contentForm) {
    
	if (contentForm.username.value == "" || contentForm.password.value == "") {
        document.getElementById("wrongCredsMessage").style.display = "block";
        return;
    }
	var loginUi = "mainWindow";
    var argsMap = getArgsMap();
    var actionURL = decodeURIComponent(argsMap.actionURL);
        var redirectURL = argsMap.redirectURL;
        contentForm.action = "service/basicAuth/connections/JSApp"
                + "?loginUi=mainWindow&redirectURLToLogin="
                + encodeURIComponent(document.URL) + "&redirectURL="
                + encodeURIComponent(redirectURL);

    contentForm.submit();
}

function cancelOnClick() {
    var argsMap = getArgsMap();
    var redirectURL = decodeURIComponent(argsMap.redirectURL);
    var loginUi = decodeURIComponent(argsMap.loginUi);
    if (loginUi == "popup") {
        if(window.cancel){
            window.cancel();
            delete window.cancel;
        }
        window.close();
    } else {
        window.location.href = redirectURL;
    }
}

function onLoginPageLoad() {
    var argsMap = getArgsMap();
    var showWrongCredsMessage = argsMap.showWrongCredsMessage;
    if (showWrongCredsMessage == "true") {
        document.getElementById("wrongCredsMessage").style.display = "block";
    }
}

function getArgsMap() {
    try {
        var qString = location.search.substring(1);
        var qStringParams = qString.split("&");
                                               
        var argsMap = {};
        var i;
        for (i = 0; i < qStringParams.length; i++) {
            var argArray = qStringParams[i].split("=");
            argsMap[argArray[0]] = argArray[1];
        }
        return argsMap;
    } catch (err) {
        console.log("Error making agrs map in login.js " + err);
    }
}