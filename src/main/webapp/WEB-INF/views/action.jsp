<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<!DOCTYPE html>
<html lang="en">
<head>

    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="description" content="">
    <meta name="author" content="">

    <title>AnalyzeMe</title>


    <script src="http://d3js.org/d3.v3.min.js" charset="utf-8"></script>


    <style>
        .axis path, .axis line {
            fill: none;
            stroke: #333;
        }

        .axis .grid-line {
            stroke: #000;
            stroke-opacity: 0.2;
        }

        .axis text {
            font: 10px Verdana;
        }
    </style>
</head>
<body onload="load()">

<!-- Header -->
<a id="about"></a>

<div class="intro-header2">
    <div class="container">
        <div class="row">
            <!-- Div for display Graph -->
            <div>
                <div id="button-list">Interpolation:
                    <button onclick="InitLineChart(selectedData)"> lineal</button>
                    <button onclick="InitBasicChart(selectedData)"> basic</button>
                    <button onclick="InitPointsChart(selectedData)"> points</button>
                </div>
                <svg id="visualisation" width="600" height="600" style="border:1px solid red;"></svg>

            </div>

        </div>
    </div>
    <!-- /.container -->

</div>
<!-- /.intro-header -->
</body>
<!-- script for display graph  -->
<script>

    var st = "{\"Data\":[{ \"x\": 1.0,\"y\": 50.0 }," +
            "{\"x\": 5.0,\"y\": 56.0}," +
            "{\"x\": 20.0,\"y\": 130.0}," +
            "{\"x\": 30.0,\"y\": 56.0}," +
            "{\"x\": 40.0,\"y\": 56.0}," +
            "{\"x\": 50.0,\"y\": 10.0}," +
            "{\"x\": 75.0,\"y\": 100.0}," +
            "{\"x\": 85.0,\"y\": 256.0}," +
            "{\"x\": 90.0,\"y\": 156.0}," +
            "{\"x\": 100.0,\"y\": 120.0}," +
            "{\"x\": 120.0,\"y\": 100.0}," +
            "{\"x\": 140.0,\"y\": 23.0}," +
            "{\"x\": 160.0,\"y\": 136.0}," +
            "{\"x\": 170.0,\"y\": 6.0}," +
            "{\"x\": 180.0,\"y\": 36.0}," +
            "{\"x\": 190.0,\"y\": 56.0}," +
            "{\"x\": 200.0,\"y\": 56.0}," +
            "{\"x\": 210.0,\"y\": 23.0}]}";









    var Data = JSON.parse(st).Data;
    var lineData = [{
        "x": 1.0,
        "y": 50.0
    }, {
        "x": 20,
        "y": 150
    }, {
        'x': 40.0,
        'y': 10.0
    }, {
        'x': 60,
        'y': 100
    }, {
        'x': 80,
        'y': 5
    }, {
        'x': 80,
        'y': 60
    }];
    var selectedData = Data;
    function load() {
        InitChart(selectedData);
    }
    var HEIGHT = 600,
            WIDTH = 600
            , MARGINS = {
                top: 50,
                right: 50,
                bottom: 50,
                left: 50

            };

    function InitChart(Data) {
        var vis = d3.select("#visualisation"),
                WIDTH = 600,
                HEIGHT = 600,
                MARGINS = {
                    top: 20,
                    right: 20,
                    bottom: 20,
                    left: 50
                },
                xRange = d3.scale.linear().range([MARGINS.left, WIDTH - MARGINS.right]).domain([d3.min(Data, function (d) {
                    return d.x;
                }),
                    d3.max(Data, function (d) {
                        return d.x;
                    })
                ]),

                yRange = d3.scale.linear().range([HEIGHT - MARGINS.top, MARGINS.bottom]).domain([d3.min(Data, function (d) {
                    return d.y;
                }),
                    d3.max(Data, function (d) {
                        return d.y;
                    })
                ]),

                xAxis = d3.svg.axis()
                        .scale(xRange)
                        .tickSize(5)
                        .tickSubdivide(true),

                yAxis = d3.svg.axis()
                        .scale(yRange)
                        .tickSize(5)
                        .orient("left")
                        .tickSubdivide(true);

        vis.append("svg:g")
                .attr("class", "x axis")
                .attr("transform", "translate(0," + (HEIGHT - MARGINS.bottom) + ")")
                .call(xAxis);

        vis.append("svg:g")
                .attr("class", "y axis")
                .attr("transform", "translate(" + (MARGINS.left) + ",0)")
                .call(yAxis);
        var xAxisLength = WIDTH - MARGINS.left - MARGINS.right;
        var yAxisLength = HEIGHT - MARGINS.bottom - MARGINS.top;
        //создаем набор вертикальных линий для сетки
        d3.selectAll("g.x-axis, g.tick")
                .append("line")
                .classed("grid-line", true) // добавляем класс
                .attr("x1", 0)
                .attr("y1", 0)
                .attr("x2", 0)
                .attr("y2", -(yAxisLength));


        // рисуем горизонтальные линии
        d3.selectAll("g.y-axis, g.tick")
                .append("line")
                .classed("grid-line", true) // добавляем класс
                .attr("x1", 0)
                .attr("y1", 0)
                .attr("x2", xAxisLength)
                .attr("y2", 0);

    }

    function InitLineChart(Data) {
        clearChart();
        var vis = d3.select("#visualisation"),
                xRange = d3.scale.linear().range([MARGINS.left, WIDTH - MARGINS.right]).domain([d3.min(Data, function (d) {
                    return d.x;
                }),
                    d3.max(Data, function (d) {
                        return d.x;
                    })
                ]),

                yRange = d3.scale.linear().range([HEIGHT - MARGINS.top, MARGINS.bottom]).domain([d3.min(Data, function (d) {
                    return d.y;
                }),
                    d3.max(Data, function (d) {
                        return d.y;
                    })
                ]);
        InitChart(Data);
        var lineFunc = d3.svg.line()
                .x(function (d) {
                    return xRange(d.x);
                })
                .y(function (d) {
                    return yRange(d.y);
                })
                .interpolate('linear');

        vis.append("svg:path")
                .attr("d", lineFunc(Data))
                .attr("stroke", "blue")
                .attr("stroke-width", 2)
                .attr("fill", "none");

    }

    function InitBasicChart(Data) {
        clearChart();
        var vis = d3.select("#visualisation"),
                xRange = d3.scale.linear().range([MARGINS.left, WIDTH - MARGINS.right]).domain([d3.min(Data, function (d) {
                    return d.x;
                }),
                    d3.max(Data, function (d) {
                        return d.x;
                    })
                ]),

                yRange = d3.scale.linear().range([HEIGHT - MARGINS.top, MARGINS.bottom]).domain([d3.min(Data, function (d) {
                    return d.y;
                }),
                    d3.max(Data, function (d) {
                        return d.y;
                    })
                ]);
        InitChart(Data);

        var lineFunc = d3.svg.line()
                .x(function (d) {
                    return xRange(d.x);
                })
                .y(function (d) {
                    return yRange(d.y);
                })
                .interpolate('basis');

        vis.append("svg:path")
                .attr("d", lineFunc(Data))
                .attr("stroke", "blue")
                .attr("stroke-width", 2)
                .attr("fill", "none");

    }

    function InitPointsChart(Data) {
        clearChart();
        var vis = d3.select("#visualisation"),
                xRange = d3.scale.linear().range([MARGINS.left, WIDTH - MARGINS.right]).domain([d3.min(Data, function (d) {
                    return d.x;
                }),
                    d3.max(Data, function (d) {
                        return d.x;
                    })
                ]),

                yRange = d3.scale.linear().range([HEIGHT - MARGINS.top, MARGINS.bottom]).domain([d3.min(Data, function (d) {
                    return d.y;
                }),
                    d3.max(Data, function (d) {
                        return d.y;
                    })
                ]);
        InitChart(Data);

        var circles = vis.selectAll("circle").data(Data);
        circles
                .enter()
                .insert("circle")
                .attr("cx", function (d) {
                    return xRange(d.x);
                })
                .attr("cy", function (d) {
                    return yRange(d.y);
                })
                .attr("r", 5)
                .style("fill", "blue");

    }
    function clearChart() {
        var vis = d3.select("#visualisation");
        vis.selectAll("*").remove();


    }
</script>

</html>
