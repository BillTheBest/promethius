function updateValue(value, label) {
    $(".widget .value").html(value + "<span> " + label + "</span>");
}

function updateDashboard(message) {
    console.log(message.data);
    var msgData = message.data;
    var json = JSON.parse(msgData);
    //updateValue(json.time);
    var numberOfUsers = json.users.length;
    updateValue(numberOfUsers, "users");
}

$(document).ready(function() {
    var ws = new WebSocket("ws://localhost:9000/socket");
    ws.onopen = function() {
        ws.onmessage =  updateDashboard
        ws.onerror = function(err) { console.log(err) }
  }
})