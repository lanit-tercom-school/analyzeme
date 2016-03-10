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

     <script src="http://d3js.org/d3.v3.min.js" charset="utf-8"></script>
     <script src="http://code.jquery.com/jquery-2.1.4.min.js"></script>
 </head>
 
 <body>
 
 <!-- Navigation -->
<nav class="navbar navbar-default navbar-fixed-top topnav" role="navigation">
    <div class="container topnav">
        <!-- Brand and toggle get grouped for better mobile display -->
        <div class="navbar-header">
            <!--<span class="sr-only">Toggle navigation</span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>-->
            <a href="index" type="button" class="btn btn-info btn-lg" href="index">AnalyzeMe</a>
                <a href="action" type="button" class="btn btn-info btn-lg">Try now</a>
                    <a href="projects" type="button" class="btn btn-success btn-lg">Projects</a>
            <!--       <a href="https://github.com/lanit-tercom-school/analyzeme" class="btn btn-info btn-lg"><i>class="fa fa-github fa-fw"></i> Source code</a>-->


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
                         <tr id='addr0'>
                             <!--   <td>
                                     <input type="checkbox" id="shest001">
                                </td>
                                <td>
                               <input type="text" name='title0'  placeholder='Title' class="form-control"/>
                              </td>
                              <td>
                                  <input type="text" name='owner0' placeholder='Owner' class="form-control"/>
                                 </td>
                                 <td>
                                 <input type="text" name='last_modified0' placeholder='Last modified' class="form-control"/>
                                 </td>
                            </tr>-->
                       <tr id='addr1'></tr>
                       </tbody>
                  </table>
             </div>
        </div>
            <a class="btn btn-default pull-left" onclick="addRow('myTable');return false;">New Project</a>
        <!-- <div class="popup" id="popup">

         </div>-->
        <a id='delete_row' class="pull-right btn btn-default" onclick="myDeleteFunction('1');return false;">Delete Project</a>
   </div>

    </div>

    <script type="text/javascript">
        function addRow(id){



            var projectName = prompt('New Project', "Project1");

            var tbody = document.getElementById(id).getElementsByTagName("TBODY")[0];
            var row = document.createElement("TR")
            var td1 = document.createElement("TD")
            //td1.innerHTML = '<input class="form-control" type="text" name="title0"/>';
            td1.innerHTML = '<input type="checkbox" id="shest001">';
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

        function myDeleteFunction(r) {
            document.getElementById("myTable").deleteRow(r);
        }

        /*$('.myTable tbody tr').each(function(i) {
            var number = i + 1;
            $(this).find('td:first').text(number+".");
        });

        $(document).ready(function(){

            $('tr').click(function(){

                alert(this.rowIndex+1);

            });

        });*/

        function PopUpShow() {
            $("#popup").show();
        }
        //Function hides PopUp
        function PopUpHide() {
            $("#popup").hide();
        }
    </script>





   <!-- <div class="row row-spaced">
        <div class="col-xs-12">
             <div class="card card-thin project-list-card">
                  <ul select-all-list ng-if="projects.length &gt; 0" max-height="projectListHeight - 25" ng-cloak class="list-unstyled project-list structured-list">
   <li class="container-fluid">
        <div class="row">
             <div class="col-xs-6">
                  <input select-all type="checkbox" class="select-all">
                  <span ng-click="changePredicate('name')" class="header clickable">Title
                       <i ng-class="getSortIconClass('name')" class="tablesort fa">
                       </i>
                  </span>
             </div>
             <div class="col-xs-2">
                  <span ng-click="changePredicate('accessLevel')" class="header clickable">Owner
                       <i ng-class="getSortIconClass('accessLevel')" class="tablesort fa">
                       </i>
                  </span>
             </div>
             <div class="col-xs-4">
                  <span ng-click="changePredicate('lastUpdated')" class="header clickable">Last modified
                       <i ng-class="getSortIconClass('lastUpdated')" class="tablesort fa">
                       </i>
                  </span>
             </div>
        </div>
   </li>
   <li ng-repeat="project in visibleProjects | orderBy:predicate:reverse" ng-controller="ProjectListItemController" class="project_entry container-fluid">
        <div class="row">
             <div class="col-xs-6">
                  <input select-individual type="checkbox" ng-model="project.selected" class="select-item">
                  <span>
                       <a href="/project/{{project.id}}" class="projectName">{{project.name}}</a>
                       <span ng-controller="TagListController">
                            <a href ng-repeat="tag in project.tags" ng-click="selectTag(tag)" class="label label-default tag-label">{{tag.name}}
                            </a>
                       </span>
                  </span>
             </div>
             <div class="col-xs-2"><span class="owner">{{ownerName()}}</span></div><div class="col-xs-4">
             <span class="last-modified">{{project.lastUpdated | formatDate}}
             </span>
        </div>
   </div>
   </li>
     </div>
      </div>
       </div>-->






 <!-- jQuery -->
 <spring:url value="/resources/js/jquery.js" var="jqueryJs"/>
 <script src="${jqueryJs}"></script>
 
 <!-- Bootstrap Core JavaScript -->
 <spring:url value="/resources/js/bootstrap.min.js" var="mainJs"/>
 <script src="${mainJs}"></script>
 
 </body>
 
 </html>
