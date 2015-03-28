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
     * @param { } value
     */
    function renderStandardMetric(key, value) {
        var element = document.querySelector('[data-key="' + key + '"]');
        if (element) {
            element.querySelector(".value").innerHTML = value;
        }
    }

    function renderJsonMetric(key, value) {
      var element = document.querySelector('[data-key="' + key + '"]');
      var json = JSON.parse(value);
      for (k in json) {
        if (json.hasOwnProperty(k)) {
          element.dataset[k] = json[k];
          // TODO scrappy as shit
          var e = document.querySelector('.' + k);
          console.log('.' + k);
          if (e) { {
            if (e.nodeName == "IMG") {
              e.setAttribute('src', json[k]);
            } else {
              e.innerHTML = json[k];
              }
            }
          }
        }
      }
    }

    /**
     * When a metric is sent, render it to the front end
     *
     * @param { } message
     */
    function updateDashboard(message) {
        console.log("Message: " + message);
        var m = JSON.parse(message.data);

        if (m.key.indexOf('json-') === 0) {
          renderJsonMetric(m.key, m.value);
        } else {
          renderStandardMetric(m.key, m.value);
        }
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

    window.onload = webSocketConnect;

}());
