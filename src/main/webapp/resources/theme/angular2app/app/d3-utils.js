'use strict';
(function(app) {
    //draw graph
    app.d3Utils = {};
    app.d3Utils.DrawGraph = function(Data) {
        //alert(Data);
        var vis = d3.select("#svgVisualize");
        //clear Graph
        vis.selectAll("*").remove();

        var xRange = d3.scale.linear().range([40, 400]).domain([d3.min(Data, function(d) {
                return (d.x);
            }),
            d3.max(Data, function(d) {
                return d.x;
            })
        ]);
        var yRange = d3.scale.linear().range([400, 40]).domain([d3.min(Data, function(d) {
                return d.y;
            }),
            d3.max(Data, function(d) {
                return d.y;
            })
        ]);
        var xAxis = d3.svg.axis().scale(xRange);
        var yAxis = d3.svg.axis().scale(yRange).orient("left");
        vis.append("svg:g").call(xAxis).attr("transform", "translate(0,400)");
        vis.append("svg:g").call(yAxis).attr("transform", "translate(40,0)");

        var circles = vis.selectAll("circle").data(Data);
        circles
            .enter()
            .insert("circle")
            .attr("cx", function(d) {
                return xRange(d.x);
            })
            .attr("cy", function(d) {
                return yRange(d.y);
            })
            .attr("r", 10)
            .style("fill", "red");
    };
})(window.app || (window.app = {}));
