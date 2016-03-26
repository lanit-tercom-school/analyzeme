<%--
  Created by IntelliJ IDEA.
  User: asus
  Date: 24.03.2016
  Time: 14:38
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <title>Ace Editor Demo</title>
    <style type="text/css">
        #editor {
            position: absolute;
            top: 0;
            right: 0;
            bottom: 0;
            left: 0;
        }
    </style>
</head>
<body>
<!-- https://habrahabr.ru/post/174987/ -->
<div id="editor"></div>
<script src="http://d1n0x3qji82z53.cloudfront.net/src-min-noconflict/ace.js" type="text/javascript" charset="utf-8"></script>
<script>
    var editor = ace.edit("editor"); // теперь обращаться к редактору будем через editor
    // Далее весь экшон будет проходить тут!
    editor.setTheme("ace/theme/monokai");
    editor.getSession().setMode("ace/mode/javascript");

    editor.setValue("<source code>"); // задаем
    editor.getValue(); // -> String (получаем)

    var selectionRange = editor.getSelectionRange(); // Range - название говорит само за себя
    var selectionText = editor.getSession().getTextRange(selectionRange); // -> String (текст выделения)

    var pos = editor.getCursorPosition(); // Object {row: N, column: M}
    editor.insert("I know how to insert text"); // вставляем в точку, где находится курсор
    editor.gotoLine(lineNumber); // переходим на линию #lineNumber (нумерация с нуля)


    var lines = editor.session.getLength(); // количество строчек в документе
    editor.getSession().setUseSoftTabs(true); // использования "мягкого" выравнивания Tab-ами.
    editor.getSession().setUseWrapMode(true); // включаем text wrapping
    editor.setShowPrintMargin(false); // такая полоска-граница (40, 80 или свободно число символов, считая слева)
    editor.setReadOnly(true); // нельзя редактировать, false - можно

</script>
</body>
</html>