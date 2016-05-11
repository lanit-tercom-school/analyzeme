<%--
  Created by IntelliJ IDEA.
  User: Helen
  Date: 16.04.16
  Time: 12:01
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ page contentType="text/html;charset=utf-8" %>
 <!DOCTYPE html>
 <html lang="en">

 <head>

     <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
     <meta http-equiv="X-UA-Compatible" content="IE=edge">
     <meta name="viewport" content="width=device-width, initial-scale=1">
     <meta name="description" content="">
     <meta name="author" content="">

     <title>AnalyzeMe</title>

     <!-- Material-Design Lite -->
     <spring:url value="/resources/angular2app/" var="angularPath"/>
     <spring:url value="/resources/css/output.css" var="schemeCss"/>
     <link href="${schemeCss}" rel="stylesheet"/>
     <!--<link rel="stylesheet" href="${angularPath}lib/mdl/material.min.css">-->
     <script src="${angularPath}lib/mdl/material.min.js"></script>
     <link rel="stylesheet" href="https://fonts.googleapis.com/icon?family=Material+Icons">


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

 </head>

 <body>

 <div class="mdl-layout mdl-js-layout mdl-layout--fixed-header">
     <header class="mdl-layout__header">
         <div class="mdl-layout__header-row">
             <span class="mdl-layout-title"></span>
             <!-- Navigation -->
             <nav class="mdl-navigation" role="navigation">
                 <a href="/index" class="mdl-navigation__link">AnalyzeMe</a>
                 <a href="/demo" class="mdl-navigation__link">Try now</a>
                 <a href="/app" class="mdl-navigation__link">Projects</a>
                 <a href="/data/spb" class="mdl-navigation__link">Preview</a>
                 <a href="/config" class="mdl-navigation__link">R Configurations</a>
             </nav>
         </div>
     </header>
     <main class="mdl-layout__content" style="overflow-y: hidden;">
         <div class="page-content">
             <!-- Make a table -->
             <a name="about"></a>
             <div class="intro-header3">
                 <div class="container">

                     <div class="row clearfix">
                         <div class="col-md-12 column">

                             <table class="table table-bordered table-hover" id="myTable2" onclick="clickEvent(event)">
                                 <thead>
                                 <tr>
                                     <th class="text-center">

                                     </th>
                                     <th class="text-center">
                                         Тип объекта (obj_type)
                                     </th>
                                     <th class="text-center">
                                         Правовой режим (right)
                                     </th>
                                     <th class="text-center">
                                         Уникальный код (obj_id)
                                     </th>

                                     <th class="text-center">
                                         Доля Санкт-Петербурга в праве (obj_dolya_spb)
                                     </th>
                                     <th class="text-center">
                                         Район (region)
                                     </th>
                                     <th class="text-center">
                                         Кадастровый номер (obj_kd_nmb)
                                     </th>
                                     <th class="text-center">
                                         Кадастровая стоимость ЗУ (kad_cost)
                                     </th>
                                     <th class="text-center">
                                         Площадь (obj_sqr)
                                     </th>
                                     <th class="text-center">
                                         Остаточная стоимость (cost)
                                     </th>
                                     <th class="text-center">
                                         Использование (obj_use)
                                     </th>
                                     <th class="text-center">
                                         Дата оценки (cost_date)
                                     </th>
                                     <th class="text-center">
                                         Адрес (address)
                                     </th>
                                     <th class="text-center">
                                         Основание регистрации права СПб (right_doc)
                                     </th>
                                     <th class="text-center">
                                         Реестровый номер (obj_reestr_num)
                                     </th>

                                 </tr>
                                 </thead>
                                 <tbody>
                                 </tbody>
                             </table>
                         </div>
                     </div>

                     <a class="btn btn-default pull-left" onclick="Show(); return true;">Show</a>
                     <a class="btn btn-default pull-left" onclick="Go(); return true;">Go</a>

                 </div>

             </div>
         </div>
     </main>
 </div>



 <!-- Connection -->

 <script type="text/javascript">

     var data;

     function Show(){

         var xhr = new XMLHttpRequest();

         xhr.onreadystatechange = function() {
             if (xhr.readyState == 4 && xhr.status == 200) {
                 try {

                     data = JSON.parse(xhr.responseText);

                    alert (data[0].num_id);
                   //  alert (xhr.responseText);
                 }
              catch (err) {
                 alert(err.message + " in " + request.responseText);
                 return;
             }
                 }
                 }
         xhr.open("GET", "/preview/file/data",  true);
         xhr.send(null);
     }

     function Go() {
         for (i = 0; i < 500; i++){
             var tbody = document.getElementById("myTable2").getElementsByTagName("TBODY")[0];
             var row = document.createElement("TR")

             var td1 = document.createElement("TD")

             td1.appendChild(document.createTextNode(data[i].num_id))
             var td2 = document.createElement("TD")
             td2.appendChild(document.createTextNode(data[i].row.obj_type))
             var td3 = document.createElement("TD")
             td3.appendChild(document.createTextNode(data[i].row.right))
             var td4 = document.createElement("TD")
             td4.appendChild(document.createTextNode(data[i].row.obj_id))
             var td5 = document.createElement("TD")
             td5.appendChild(document.createTextNode(data[i].row.obj_dolya_spb))
             var td6 = document.createElement("TD")
             td6.appendChild(document.createTextNode(data[i].row.region))
             var td7 = document.createElement("TD")
             td7.appendChild(document.createTextNode(data[i].row.obj_kd_nmb))
             var td8 = document.createElement("TD")
             td8.appendChild(document.createTextNode(data[i].row.kad_cost))
             var td9 = document.createElement("TD")
             td9.appendChild(document.createTextNode(data[i].row.obj_sqr))
             var td10 = document.createElement("TD")
             td10.appendChild(document.createTextNode(data[i].row.cost))
             var td11 = document.createElement("TD")
             td11.appendChild(document.createTextNode(data[i].row.obj_use))
             var td12 = document.createElement("TD")
             td12.appendChild(document.createTextNode(data[i].row.cost_date))
             var td13 = document.createElement("TD")
             td13.appendChild(document.createTextNode(data[i].row.address))
             var td14 = document.createElement("TD")
             td14.appendChild(document.createTextNode(data[i].row.right_doc))
             var td15 = document.createElement("TD")
             td15.appendChild(document.createTextNode(data[i].row.obj_reestr_num))

             row.appendChild(td1);
             row.appendChild(td2);
             row.appendChild(td3);
             row.appendChild(td4);
             row.appendChild(td5);
             row.appendChild(td6);
             row.appendChild(td7);
             row.appendChild(td8);
             row.appendChild(td9);
             row.appendChild(td10);
             row.appendChild(td11);
             row.appendChild(td12);
             row.appendChild(td13);
             row.appendChild(td14);
             row.appendChild(td15);

             tbody.appendChild(row);

         }
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
