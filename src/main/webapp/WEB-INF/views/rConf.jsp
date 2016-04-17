<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%--
  Created by IntelliJ IDEA.
  User: asus
  Date: 17.04.2016
  Time: 14:20
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>RConfiguration</title>

    <spring:url value="/resources/js/rConf.js" var="rConfJs"/>
    <script src="${rConfJs}"></script>
    <!-- jQuery -->
    <spring:url value="/resources/js/jquery.js" var="jqueryJs"/>
    <script src="${jqueryJs}"></script>
    <!-- Bootstrap Core JavaScript -->
    <spring:url value="/resources/js/bootstrap.min.js" var="mainJs"/>
    <script src="${mainJs}"></script>


</head>

<body onload="load()">
<div id="AddingForm">
    <table>
        <tr>
            <td>Name:</td>
            <td><input type="text" id="name"></td>
        </tr>
        <tr>
            <td>Select type:</td>
            <td>
                <select id="Rtype">
                    <option value="FakeR">FakeR</option>
                    <option value="RServ">RServ</option>
                    <option value="Rinjin">Rinjin</option>
                </select>
            </td>
        </tr>
        <tr>
            <td>Port:</td>
            <td><input type="text" id="port"></td>
        </tr>
        <tr>
            <td>Turned on:</td>
            <td><input type="checkbox" id="enabledField"></td>
        </tr>
        <tr>
            <td>&nbsp;</td>
            <td><input type="button" id="add" value="Add instance" onclick="Javascript:addRow()"></td>
        </tr>
        <tr>
            <td>&nbsp;</td>
        </tr>
    </table>
</div>


<div id="instancesData">
    <table id="listOfInstances" border="1">
        <tr>
            <td>&nbsp;</td>
            <td><b>Name</b></td>
            <td><b>Type</b></td>
            <td><b>Using Port</b></td>
            <td><b>Turned on</b></td>
        </tr>
    </table>
    &nbsp;<br/>
</div>

<!--
<div id="myDynamicTable">
<input type="button" id="create" value="Click here" onclick="Javascript:addTable()">
to create a Table and add some data using JavaScript
</div>-->
</body>

</html>