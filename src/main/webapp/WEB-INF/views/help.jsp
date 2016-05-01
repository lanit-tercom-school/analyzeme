<%--
  Created by IntelliJ IDEA.
  User: Ольга
  Date: 01.05.2016
  Time: 17:14
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Help</title>
    <style>
        code {
            white-space:pre-wrap;
        }
    </style>
</head>
<body>
<h1>Советы по написанию R-scripts</h1>
<h2>Используемые файлы с данными</h2>
<p>0_10.json</p>
<code style=display:block;>{"Data":[{ "x": "0","y": "0" },{ "x": "1","y": "1" },
    {"x": "2","y": "2"},{ "x": "3","y": "3" },
    { "x": "4","y": "4" },{ "x": "5","y": "5" },
    { "x": "6","y": "6" },{ "x": "7","y": "7" },
    { "x": "8","y": "8" },{ "x": "9","y": "9" },
    { "x": "10","y": "10" }]}</code>
<p>lineal.json</p>
<code style=display:block;>{"Data":[{ "x": "1","y": "1"},{"x": "20","y": "20"},
    {"x": "40", "y": "40"},{"x": "60","y": "60"},
    {"x": "80","y": "80"},{"x": "100","y": "100"}]}</code>

<h2>Примеры скриптов</h2>

<p>Скрипт - текстовой файл с кодом R. Проверенные расширения - txt и R.
    Сейчас обязательная кодировка - ANSI, при необходимости можно реализовать другой вариант.
</p>
<p>script.txt (ANSI)</p>
<code style=display:block;>result <- x_from__repo__0_10.json__[5];
    m <- mean(y_from__repo__lineal.json__);
    result <- result + m;</code>
<p>scriptPoint.txt (ANSI)</p>

<code style=display:block;>a <- x_from__repo__0_10.json__[5];
    b <- mean(y_from__repo__0_10.json__);
    r <- c(a, b);</code>
<p>scriptPoints.txt (ANSI)</p>

<code style=display:block;>r <- matrix(c(x_from__repo__0_10.json__[5], mean(y_from__repo__0_10.json__),
    y_from__repo__0_10.json__[2], x_from__repo__0_10.json__[10]), nrow=2, ncol=2, byrow=TRUE);
    m <- c(x_from__repo__lineal.json__[3], mean(x_from__repo__lineal.json__));
    result <- rbind(r, m, deparse.level = 0);</code>

<h2>Представление данных</h2>

<h3>Данные из файлов</h3>

<p>Сейчас реализована поддержка файлов с данными в формате json, аналогичных
    приведённым выше <code>0_10.json и lineal.json.</code> Имя файла должно удовлетворять регулярному выражению
    <code>[A-Za-z0-9,_.]+.</code></p>

<p>Для обращения к данным в файлах, загруженных в репозиторий (позднее - к данным из других источников),
    пользователь в своём скрипте должен использовать переменные специального вида. Название переменной
    должно удовлетворять регулярному выражению <code>([A-Za-z]+)_from__([A-Za-z]+)__([A-Za-z0-9,_.]+)__</code> и
    состоять из:

<ul>
    <li>название "колонки" данных (для точек это x или y)</li>
    <li>специальное выражение _from__</li>
    <li>указание на источник данных (сейчас поддерживается только repo для загруженных в FileRepository файлов)</li>
    <li>название источника, окружённое символами __ - например, <code>__0_10.json__</code></li>
</ul>
</p>

<h3>Итого, примеры названий переменных:</h3>
<code style=display:block;>
    x_from__repo__0_10.json__
    y_from__repo__0_10.json__
    //in the future
    y_from__webRepo__webToken__
    //in the future
    time_from__repo__0_10.json__
</code>


<p>Реальная инициализация этих переменных будет происходить на стороне сервера перед запуском скрипта.
    Использовать же их в скрипте нужно как R-вектора.</p>

<h2>Результат работы скрипта</h2>

Сейчас пользователь может писать скрипты, результатом работы которых является:

<ul>
    <li>число (double)</li>
    <li>точка (объект analyzeme.analyze.Point - поля "x" и "y")</li>
    <li>набор точек (List объектов analyzeme.analyze.Point - поля "x" и "y")</li>
</ul>

<p>Для того, чтобы получить число, в последней строке скрипта необходимо прописать команду,
    результатом которой является число в R. Например:</p>

<code style=display:block;>result <- mean(x_from__repo__0_10.json__);</code>
<p>Для получения точки - вектор:</p>

<code style=display:block;>r <- c(x_from__repo__0_10.json__[5], mean(y_from__repo__0_10.json__));</code>
<p>Для набора точек - матрицу (одна точка - одна строка):</p>

<code style=display:block;>r <- matrix(c(x_from__repo__0_10.json__[5], mean(y_from__repo__0_10.json__),
    y_from__repo__0_10.json__[2], x_from__repo__0_10.json__[10]), nrow=2, ncol=2, byrow=TRUE);</code>
</body>
</html>
