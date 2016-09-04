'use strict';
(function (app) {
    //draw graph
    app.HighchartsUtils = {};
    app.HighchartsUtils.DrawGraph = function (data) {
        var series = [];
        if (data.length!= 0) {
            var xAxis;
            if (data[0].hasOwnProperty("x"))
                xAxis = "x";
            else
                xAxis = Object.keys(data[0])[0];// First property
            console.log(xAxis);
            Object.keys(data[0]).forEach(function (key) {
                if (key != xAxis) {
                    var data2D = [];
                    for (var i = 0; i < data.length; i++) {
                        data2D.push([parseFloat(data[i][xAxis]), parseFloat(data[i][key])]);
                    }
                    series.push({data: data2D, name: key});
                }
            });
        }
        $('#svgVisualize').highcharts({
            chart: {
                zoomType: 'x'
            },
            title: {
                text: 'Your line chart',
                x: -20 //center
            },
            subtitle: {
                text: 'Click and drag in the plot area to zoom in'
            },
            xAxis: {
                minPadding: 0.05,
                maxPadding: 0.05

            },
            yAxis: {
                title: {
                    text: null
                },

                plotLines: [{
                    value: 0,
                    width: 1,
                    color: '#2e7d32'

                }]
            },

            //legend: {
            //    layout: 'vertical',
            //    align: 'right',
            //    verticalAlign: 'middle',
            //    borderWidth: 0
            //},

            plotOptions: {
                line: {
                    allowPointSelect: true
                },

            },


            series: series

        });
    };
})(window.app || (window.app = {}));
