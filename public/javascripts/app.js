(function() {

    function updateMetric(key, value) {
        var element = document.querySelector('[data-key="' + key + '"]');
        if(element) {
            element.querySelector(".value").innerHTML = value;
        } else {
            console.log("ERROR: Metric with key \"" + key + "\" given but no matching widget found");
        }
    }

    function updateDashboard(message) {
        var m = JSON.parse(message.data);
        updateMetric(m.key, m.value);
    }

    function webSocketConnect() {
        var ws = new WebSocket("ws://localhost:9000/socket");
        ws.onopen = function() {
            console.log("Web socket connected");
            ws.onmessage = updateDashboard;
        };
    }

    window.onload = webSocketConnect();

}());