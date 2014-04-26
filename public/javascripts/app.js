// Poly fill

if (!('contains' in String.prototype)) {
    String.prototype.contains = function(str, startIndex) {
        return ''.indexOf.call(this, str, startIndex) !== -1;
    };
}

(function() {

    /**
     * Flatten a nested structure
     *
     */
    function flatten(arr) {
        return Array.prototype.concat.apply([], arr);
    }

    /*
     * Helper function that converts a map to a matrix like
     * format suitable for the D3.js chart library
     *
     */
    function mapToData(obj) {
        var result = [];
        for (k in obj) {
            result.push(flatten([k, obj[k]]));
        }
        return result;
    }

    function makeLineChart(key, data)  {
        return c3.generate({
            data: {
                columns: data
            },
            tooltip: {
                show: false
            }
        });
    }

    function isChartMetric(key) {
        return key.contains("chart")
    }

    function renderStandardMetric(key, value) {

        var element = document.querySelector('[data-key="' + key + '"]');
        if(element) {
            element.querySelector(".value").innerHTML = value;
        } else {
            console.log("ERROR: Metric with key \"" + key + "\" given but no matching widget found");
            console.log("Data is " + value);
        }
    }

    function updateMetric(key, value) {
        if (isChartMetric(key))
            console.log("Chart metric found");
        else
            renderStandardMetric(key, value);
    }

    function updateDashboard(message) {
        var m = JSON.parse(message.data);
        updateMetric(m.key, m.value);
    }
    
    // We need to dynamically build the URL for different hosts
    function urlForWs() {        
        return "ws://" + window.location.host + "/socket"
    }

    function webSocketConnect() {
        var socketUrl = urlForWs();
        var ws = new WebSocket(socketUrl);
        ws.onopen = function() {
            console.log("Web socket connected");
            ws.onmessage = updateDashboard;
        };
    }

    function randomInt() {
        return Math.floor(10 + Math.abs(Math.random() * 1500));
    }

    function randomData() {
        var dataMap = { "key1" : [randomInt(), randomInt(), randomInt(), randomInt()],
                        "key2" : [randomInt(), randomInt(), randomInt()] }
        return mapToData(dataMap);
    }

    function generateChart() {
      var chart = makeLineChart("chart-metrics", randomData());
    }

    window.onload = function() { generateChart(); webSocketConnect(); }

}());