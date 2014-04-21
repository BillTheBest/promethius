var loggingEnabled = true;

function setValue(selector, value) {
  document.querySelector(selector).innerHTML = value;
}

function log(message) {
    if (loggingEnabled) {
        console.log(message);
    }
}

function metricsWidget(json) {
    setValue(".metrics .value", json.users.length);
}

function timeWidget(json) {
    setValue(".time .value", json.time);
}

