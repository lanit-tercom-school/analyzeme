'use strict';
(function (app) {
    //draw graph
    app.HighchartsUtils = {};
    app.HighchartsUtils.DrawGraph = function (Data) {
        var data2D=[];
       for (var i = 0; i < Data.length; i++) {
            data2D.push([parseFloat(Data[i].x),parseFloat(Data[i].y)]);
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
            //
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
                series: {
                    color: '#2e7d32'
                }
            },
            series: [{
                data: data2D
            }]
        });
    };
})(window.app || (window.app = {}));
