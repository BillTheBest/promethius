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
        return 10 + Math.abs(Math.random() * 1500);
    }

    function randomData() {
        return [['Production', randomInt(), 200, randomInt(), randomInt(), 250],
                ['Staging', randomInt(), randomInt(), randomInt(), 120, randomInt()],
                ['Live AWS', randomInt(), randomInt(), randomInt(), 50, randomInt()]]
    }

    function generateChart() {
      var chart = c3.generate({
        data: {
          columns: randomData()
        },
        tooltip: {
          show: false
        }
      });
      setInterval(function () {
        chart.load({
          columns: randomData()
        });
      }, 2000);
    }

    window.onload = function() { generateChart(); webSocketConnect(); }

}());