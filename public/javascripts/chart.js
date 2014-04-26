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

    function randomData() {
        var dataMap = { "key1" : [randomInt(), randomInt(), randomInt()],
                        "key2" : [randomInt(), randomInt(), randomInt()] }
        return mapToData(dataMap);
    }

    function generateChart() {
      var chart = makeLineChart("chart-metrics", randomData());
    }

}());