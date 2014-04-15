function updateValue(value) {
    $(".widget .value").html(value);
}

function metricsWidget(json) {
    $('.metrics .value').html(json.users.length);
}

function timeWidget(json) {
  $('.time .value').html(json.time);
}

function renderMetric(message) {

    var v = JSON.parse(message.value);

    if (message.key === "metrics") {
      metricsWidget(v);
    }
    if (message.key === "time") {
      timeWidget(v)
    }
}

function updateDashboard(message) {
    var message = JSON.parse(message.data);
    console.log(message);
    renderMetric(message);
}

$(document).ready(function() {
    var ws = new WebSocket("ws://localhost:9000/socket");
    ws.onopen = function() {
        ws.onmessage =  updateDashboard
        ws.onerror = function(err) { console.log(err) }
  }
})