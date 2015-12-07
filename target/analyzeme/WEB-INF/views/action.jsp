<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<!DOCTYPE html>
<html lang="en">

<head>

    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="description" content="">
    <meta name="author" content="">

    <title>AnalyzeMe</title>

    <!-- Bootstrap Core CSS -->
    <spring:url value="/resources/css/bootstrap.min.css" var="mainCss" />
    <link href="${mainCss}" rel="stylesheet" />

    <!-- Custom CSS -->
    <spring:url value="/resources/css/landing-page.css" var="landingCss" />
    <link href="${landingCss}" rel="stylesheet" />
    <spring:url value="/resources/css/action-stylesheet.css" var="actionCss" />
    <link href="${actionCss}" rel="stylesheet" />

    <!-- Custom Fonts -->
    <spring:url value="/resources/font-awesome/css/font-awesome.min.css" var="fontAwesomeCss" />
    <link href="${fontAwesomeCss}" rel="stylesheet" type="text/css"/>
    <spring:url value="http://fonts.googleapis.com/css?family=Lato:300,400,700,300italic,400italic,700italic" var="fontCss" />
    <link href="${fontCss}" rel="stylesheet" type="text/css" />


    <!-- D3.js -->
    <spring:url value="/resources/js/d3.js" var="d3js" />
    <script type="text/javascript" src="${d3js}"></script>
    <spring:url value="/resources/js/graph.js" var="graphical" />

    <!-- HTML5 Shim and Respond.js IE8 support of HTML5 elements and media queries -->
    <!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
    <!--[if lt IE 9]>
        <script src="https://oss.maxcdn.com/libs/html5shiv/3.7.0/html5shiv.js"></script>
        <script src="https://oss.maxcdn.com/libs/respond.js/1.4.2/respond.min.js"></script>
    <![endif]-->
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

<body>

    <!-- Navigation -->
    <nav class="navbar navbar-default navbar-fixed-top topnav" role="navigation">
        <div class="container topnav">
            <!-- Brand and toggle get grouped for better mobile display -->
            <div class="navbar-header">
                <button type="button" class="navbar-toggle" data-toggle="collapse" data-target="#bs-example-navbar-collapse-1">
                    <span class="sr-only">Toggle navigation</span>
                    <span class="icon-bar"></span>
                    <span class="icon-bar"></span>
                    <span class="icon-bar"></span>
                </button>
                <a class="navbar-brand topnav" href="index">AnalyzeMe</a>
		<%--<ul class="nav navbar-nav">
		  <li><button type="button" class="btn btn-primary navbar-btn"><i class="glyphicon glyphicon-log-in"></i>   Try now</button></li>
		</ul>--%>
            </div>
            <!-- Collect the nav links, forms, and other content for toggling -->

	<!--
            <div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">
                <ul class="nav navbar-nav navbar-right">
                    <li>
                        <a href="#about">About</a>
                    </li>
                    <li>
                        <a href="#services">Services</a>
                    </li>
                    <li>
                        <a href="#contact">Contact</a>
                    </li>
                </ul>
            </div>
	-->
            <!-- /.navbar-collapse -->
        </div>
        <!-- /.container -->
    </nav>


    <!-- Header -->
    <%--<a name="about"></a>
    <div class="intro-header">
        <div class="container">

            <div class="row">
                <div class="col-lg-12">
                    <div class="intro-message">
                        <h1>AnalyzeMe</h1>
                        <h3>A web service for data analysis</h3>
                        <hr class="intro-divider">
                        <ul class="list-inline intro-social-buttons">
                            <li>
                                <a href="#" class="btn btn-primary btn-lg"><i class="glyphicon glyphicon-log-in"></i> <span class="network-name">   Try now</span></a>
                            </li>
                            <li>
                                <a href="https://github.com/lanit-tercom-school/analyzeme" class="btn btn-default btn-lg"><i class="fa fa-github fa-fw"></i> <span class="network-name">Source code</span></a>
                            </li>
                        </ul>
                    </div>
                </div>
            </div>

        </div>
        <!-- /.container -->

    </div>--%>
    <!-- /.intro-header -->

    <!-- Page Content -->

    <div class="intro-header">
        <div class="text/javascript">
            <script type="text/javascript">
                var height = 500,
                        width = 500,
                        margin=30,
                        rawData = [
                            {x: 10, y: 67}, {x: 20, y: 74},{x: 30, y: 63},
                            {x: 40, y: 56}, {x: 50, y: 24}, {x: 60, y: 26},
                            {x: 70, y: 19}, {x: 80, y: 42}, {x: 90, y: 88}
                        ],
                        data=[];

                // создание объекта svg
                var svg = d3.select("body").append("svg")
                        .attr("class", "axis")
                        .attr("width", width)
                        .attr("height", height);

                // длина оси X= ширина контейнера svg - отступ слева и справа
                var xAxisLength = width - 2 * margin;

                // длина оси Y = высота контейнера svg - отступ сверху и снизу
                var yAxisLength = height - 2 * margin;

                // функция интерполяции значений на ось Х
                var scaleX = d3.scale.linear()
                        .domain([0, 100])
                        .range([0, xAxisLength]);

                // функция интерполяции значений на ось Y
                var scaleY = d3.scale.linear()
                        .domain([100, 0])
                        .range([0, yAxisLength]);
                // масштабирование реальных данных в данные для нашей координатной системы
                for(i=0; i<rawData.length; i++)
                    data.push({x: scaleX(rawData[i].x)+margin, y: scaleY(rawData[i].y) + margin});

                // создаем ось X
                var xAxis = d3.svg.axis()
                        .scale(scaleX)
                        .orient("bottom");
                // создаем ось Y
                var yAxis = d3.svg.axis()
                        .scale(scaleY)
                        .orient("left");

                // отрисовка оси Х
                svg.append("g")
                        .attr("class", "x-axis")
                        .attr("transform",  // сдвиг оси вниз и вправо
                                "translate(" + margin + "," + (height - margin) + ")")
                        .call(xAxis);

                // отрисовка оси Y
                svg.append("g")
                        .attr("class", "y-axis")
                        .attr("transform", // сдвиг оси вниз и вправо на margin
                                "translate(" + margin + "," + margin + ")")
                        .call(yAxis);

                // создаем набор вертикальных линий для сетки
                d3.selectAll("g.x-axis g.tick")
                        .append("line")
                        .classed("grid-line", true)
                        .attr("x1", 0)
                        .attr("y1", 0)
                        .attr("x2", 0)
                        .attr("y2", - (yAxisLength));

                // рисуем горизонтальные линии координатной сетки
                d3.selectAll("g.y-axis g.tick")
                        .append("line")
                        .classed("grid-line", true)
                        .attr("x1", 0)
                        .attr("y1", 0)
                        .attr("x2", xAxisLength)
                        .attr("y2", 0);

                // функция, создающая по массиву точек линии
                var line = d3.svg.line()
                        .x(function(d){return d.x;})
                        .y(function(d){return d.y;});
                // добавляем путь
                svg.append("g").append("path")
                        .attr("d", line(data))
                        .style("stroke", "steelblue")
                        .style("stroke-width", 2);
            </script>

        </div>
        <!-- /.container -->

    </div>
    <!--

    <div class="graph">
         <div class="container">
             <div class="row">
                 <div class="graphic col-lg-9">
                     <script >
                         d3.select("body").append("p").text("New paragraph!");
                     </script>
                     <script src="${graphical}" ></script>
                 </div>
             </div>
         </div>
     </div>
    -->






 <%--
     <a  name="services"></a>
     <div class="content-section-a">

         <div class="container">
             <div class="row">
                 <div class="col-lg-5 col-sm-6">
                     <hr class="section-heading-spacer">
                     <div class="clearfix"></div>
                     <h2 class="section-heading">Death to the Stock Photo:<br>Special Thanks</h2>
                     <p class="lead">A special thanks to <a target="_blank" href="http://join.deathtothestockphoto.com/">Death to the Stock Photo</a> for providing the photographs that you see in this template. Visit their website to become a member.</p>
                 </div>
                 <div class="col-lg-5 col-lg-offset-2 col-sm-6">
                     <img class="img-responsive" src="/resources/theme/img/ipad.png" alt="">
                 </div>
             </div>

         </div>
         <!-- /.container -->

     </div>
     <!-- /.content-section-a -->

     <div class="content-section-b">

         <div class="container">

             <div class="row">
                 <div class="col-lg-5 col-lg-offset-1 col-sm-push-6  col-sm-6">
                     <hr class="section-heading-spacer">
                     <div class="clearfix"></div>
                     <h2 class="section-heading">3D Device Mockups<br>by PSDCovers</h2>
                     <p class="lead">Turn your 2D designs into high quality, 3D product shots in seconds using free Photoshop actions by <a target="_blank" href="http://www.psdcovers.com/">PSDCovers</a>! Visit their website to download some of their awesome, free photoshop actions!</p>
                 </div>
                 <div class="col-lg-5 col-sm-pull-6  col-sm-6">
                     <img class="img-responsive" src="../img/dog.png" alt="">
                 </div>
             </div>

         </div>
         <!-- /.container -->

     </div>
     <!-- /.content-section-b -->

     <div class="content-section-a">

         <div class="container">

             <div class="row">
                 <div class="col-lg-5 col-sm-6">
                     <hr class="section-heading-spacer">
                     <div class="clearfix"></div>
                     <h2 class="section-heading">Google Web Fonts and<br>Font Awesome Icons</h2>
                     <p class="lead">This template features the 'Lato' font, part of the <a target="_blank" href="http://www.google.com/fonts">Google Web Font library</a>, as well as <a target="_blank" href="http://fontawesome.io">icons from Font Awesome</a>.</p>
                 </div>
                 <div class="col-lg-5 col-lg-offset-2 col-sm-6">
                     <img class="img-responsive" src="../img/phones.png" alt="">
                 </div>
             </div>

         </div>
         <!-- /.container -->

     </div>
     <!-- /.content-section-a -->

     <a  name="contact"></a>
     <div class="banner">

         <div class="container">

             <div class="row">
                 <div class="col-lg-6">
                     <h2>Connect to Start Bootstrap:</h2>
                 </div>
                 <div class="col-lg-6">
                     <ul class="list-inline banner-social-buttons">
                         <li>
                             <a href="https://twitter.com/SBootstrap" class="btn btn-default btn-lg"><i class="fa fa-twitter fa-fw"></i> <span class="network-name">Twitter</span></a>
                         </li>
                         <li>
                             <a href="https://github.com/IronSummitMedia/startbootstrap" class="btn btn-default btn-lg"><i class="fa fa-github fa-fw"></i> <span class="network-name">Github</span></a>
                         </li>
                         <li>
                             <a href="#" class="btn btn-default btn-lg"><i class="fa fa-linkedin fa-fw"></i> <span class="network-name">Linkedin</span></a>
                         </li>
                     </ul>
                 </div>
             </div>

         </div>
         <!-- /.container -->

     </div>
     <!-- /.banner -->--%>

    <!-- Footer -->
    <footer>
        <div class="container">
            <div class="row">
                <div class="col-lg-12">
                    <%--<ul class="list-inline">
                        <li>
                            <a href="#">Home</a>
                        </li>
                        <li class="footer-menu-divider">&sdot;</li>
                        <li>
                            <a href="#about">About</a>
                        </li>
                        <li class="footer-menu-divider">&sdot;</li>
                        <li>
                            <a href="#services">Services</a>
                        </li>
                        <li class="footer-menu-divider">&sdot;</li>
                        <li>
                            <a href="#contact">Contact</a>
                        </li>
                    </ul>--%>
                    <p class="copyright text-muted small">Copyright &copy; lanit-tercom.school 2015. All Rights Reserved</p>
                </div>
            </div>
        </div>
    </footer>
    <!-- <script>
         d3.select("body")
                 .append("p")
                 .text("New par");
     </script>
     -->
     <!-- jQuery -->
    <spring:url value="/resources/js/jquery.js" var="jqueryJs" />
    <script src="${jqueryJs}"></script>
    <script src="//d3js.org/d3.v3.min.js"></script>

    <!-- Bootstrap Core JavaScript -->
    <spring:url value="/resources/js/bootstrap.min.js" var="mainJs" />
    <script src="${mainJs}"></script>

</body>

</html>
