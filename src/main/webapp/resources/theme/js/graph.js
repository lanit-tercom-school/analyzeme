
var data = [
    [new Date(2001, 0, 1), 1],
    [new Date(2002, 0, 1), 6],
    [new Date(2003, 0, 1), 2],
    [new Date(2004, 0, 1), 1],
    [new Date(2005, 0, 1), 4],
    [new Date(2006, 0, 1), 5]
];

var margin = {top: 20, right: 30, bottom: 30, left: 40},
    width = 940 - margin.left - margin.right,
    height = 500 - margin.top - margin.bottom;

var x = d3.time.scale()
    .domain([new Date(2001, 0, 1), new Date(2006, 0, 1)])
    .range([0, width]);

var y = d3.scale.linear()
    .domain([0, 6])
    .range([height, 0]);

var xAxis = d3.svg.axis()
    .scale(x)
    .orient("bottom");

var yAxis = d3.svg.axis()
    .scale(y)
    .orient("left");

var line = d3.svg.line()
    .interpolate("monotone")
    .x(function(d) { return x(d[0]); })
    .y(function(d) { return y(d[1]); });

var svg = d3.select("body").append("svg")
    .datum(data)
    .attr("width", width + margin.left + margin.right)
    .attr("height", height + margin.top + margin.bottom)
    .append("g")
    .attr("transform", "translate(" + margin.left + "," + margin.top + ")");

svg.append("g")
    .attr("class", "x axis")
    .attr("transform", "translate(0," + height + ")")
    .call(xAxis);

svg.append("g")
    .attr("class", "y axis")
    .call(yAxis);

svg.append("path")
    .attr("class", "line")
    .attr("d", line);

svg.selectAll(".dot")
    .data(data)
    .enter().append("circle")
    .attr("class", "dot")
    .attr("cx", line.x())
    .attr("cy", line.y())
    .attr("r", 3.5);
