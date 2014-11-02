/* global c3, Websocket */

(function() {

    /**
     * Flatten a nested structure
     */
    function flatten(arr) {
        return Array.prototype.concat.apply([], arr);
    }

    var Promethius = function () {

    };

    Promethius.prototype.render = function ( ) {

    };

    /**
     * Render a standard metric response
     *
     * @param { } key
     * @param { } valuey
     */
    function renderStandardMetric(key, value) {
        var element = document.querySelector('[data-key="' + key + '"]');
        if (element)
            element.querySelector(".value").innerHTML = value;
        else
            console.log("ERROR: Metric with key \"" + key + "\" given but no matching widget found");
            console.log("Data is " + value);
    }

    /**
     * When a metric is sent, render it to the front end
     *
     * @param { } message
     */
    function updateDashboard(message) {
        var m = JSON.parse(message.data);
        renderStandardMetric(m.key, m.value);
    }

    /**
     * We need to dynamically build the URL for different hosts
     */
    function urlForWs() {
        return "ws://" + window.location.host + "/socket";
    }

    function webSocketConnect() {
        var socketUrl = urlForWs();
        var ws = new window.WebSocket(socketUrl);
        ws.onopen = function() {
            console.log("Web socket connected");
            ws.onmessage = updateDashboard;
        };
    }

    function randomInt() {
        return Math.floor(10 + Math.abs(Math.random() * 1500));
    }

    window.onload = webSocketConnect;

}());
