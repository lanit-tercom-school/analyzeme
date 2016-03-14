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
 
     <!-- Bootstrap Core CSS -->
     <spring:url value="/resources/css/bootstrap.min.css" var="mainCss"/>
     <link href="${mainCss}" rel="stylesheet"/>
 
     <!-- Custom CSS -->
     <spring:url value="/resources/css/landing-page.css" var="landingCss"/>
     <link href="${landingCss}" rel="stylesheet"/>
 
     <!-- Custom Fonts -->
     <spring:url value="/resources/font-awesome/css/font-awesome.min.css" var="fontAwesomeCss"/>
     <link href="${fontAwesomeCss}" rel="stylesheet" type="text/css"/>
     <spring:url value="http://fonts.googleapis.com/css?family=Lato:300,400,700,300italic,400italic,700italic"
                 var="fontCss"/>
     <link href="${fontCss}" rel="stylesheet" type="text/css"/>
 
     <!-- HTML5 Shim and Respond.js IE8 support of HTML5 elements and media queries -->
     <!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
     <!--[if lt IE 9]>
     <script src="https://oss.maxcdn.com/libs/html5shiv/3.7.0/html5shiv.js"></script>
     <script src="https://oss.maxcdn.com/libs/respond.js/1.4.2/respond.min.js"></script>
     <![endif]-->

     <!-- Delete row -->
     <script type="text/javascript">
         function killRow(src) {
             var dRow = src.parentElement.parentElement;
             document.all("myTable").deleteRow(dRow.rowIndex);
         }
     </script>
 </head>

 <body>

 <!-- Navigation -->
<nav class="navbar navbar-default navbar-fixed-top topnav" role="navigation">
    <div class="container topnav">
        <!-- Brand and toggle get grouped for better mobile display -->
        <div class="navbar-header">
            <a href="index" type="button" class="btn btn-info btn-lg" href="index">AnalyzeMe</a>
                <a href="action" type="button" class="btn btn-info btn-lg">Try now</a>
                    <a href="projects" type="button" class="btn btn-success btn-lg">Projects</a>
        </div>

        <!-- /.navbar-collapse -->
    </div>
    <!-- /.container -->
</nav>

 <!-- Make a table -->
<a name="about"></a>
    <div class="intro-header2">
     <div class="container">
    <div class="row clearfix">
          <div class="col-md-12 column">

               <table class="table table-bordered table-hover" id="myTable">
                    <thead>
                         <tr>
                              <th class="text-center">
                                   
                              </th>
                              <th class="text-center">
                                   Title
                              </th>
                              <th class="text-center">
                                   Owner
                              </th>
                              <th class="text-center">
                                   Last modified
                              </th>
                         </tr>
                    </thead>
                    <tbody>
                       </tbody>
                  </table>
             </div>
        </div>

         <!-- New project button -->
            <a class="btn btn-default pull-left" onclick="addRow('myTable');return false;">New Project</a>
   </div>

    </div>

    <script type="text/javascript">
        function addRow(id){


            <!-- Show a window for Project name -->
            var projectName = prompt('New Project', "Project1");

            <!-- Make a new row -->
            var tbody = document.getElementById(id).getElementsByTagName("TBODY")[0];
            var row = document.createElement("TR")
            var td1 = document.createElement("TD")
            //td1.innerHTML = '<input class="form-control" type="text" name="title0"/>';
            td1.innerHTML = '<i class = "glyphicon glyphicon-remove" type="button" onclick="killRow(this);">';
            td1.appendChild(document.createTextNode(""))
            var td2 = document.createElement("TD")
            td2.appendChild (document.createTextNode(projectName))
            var td3 = document.createElement("TD")
            td3.appendChild (document.createTextNode("you"))
            var td4 = document.createElement("TD")
            td4.appendChild (document.createTextNode("today"))
            row.appendChild(td1);
            row.appendChild(td2);
            row.appendChild(td3);
            row.appendChild(td4);
            tbody.appendChild(row);
        }
    </script>

 <!-- jQuery -->
 <spring:url value="/resources/js/jquery.js" var="jqueryJs"/>
 <script src="${jqueryJs}"></script>
 
 <!-- Bootstrap Core JavaScript -->
 <spring:url value="/resources/js/bootstrap.min.js" var="mainJs"/>
 <script src="${mainJs}"></script>
 
 </body>
 
 </html>
