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
    <spring:url value="/resources/css/drag-and-drop.css" var="dragAndDropCss"/>
    <link href="${dragAndDropCss}" rel="stylesheet"/>
    <!-- style for editor window and read-only code segment-->
    <spring:url value="/resources/css/r-editor-page.css" var="rEditorPageCss"/>
    <link href="${rEditorPageCss}" rel="stylesheet"/>
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

<!-- Navigation -->
<nav class="navbar navbar-default navbar-fixed-top topnav" role="navigation">
    <div class="container topnav">
        <!-- Brand and toggle get grouped for better mobile display -->
        <div class="navbar-header">
            <!--<span class="sr-only">Toggle navigation</span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>-->
            <a href="index" type="button" class="btn btn-info btn-lg">AnalyzeMe</a>
            <a href="action" type="button" class="btn btn-success btn-lg">Try now</a>
            <a href="projects" type="button" class="btn btn-info btn-lg">Projects</a>

        </div>

        <!-- /.navbar-collapse -->
    </div>
    <!-- /.container -->
</nav>

<div class="intro-RPageHeader">

            <div id="editor"></div>

</div>
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
<!-- scripts for editor aria -->
<script src="https://cdn.jsdelivr.net/ace/1.2.3/min/ace.js" type="text/javascript" charset="utf-8"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/ace/1.1.3/ext-language_tools.js" type="text/javascript" charset="utf-8"></script>
<script>

    ace.require("ace/ext/language_tools");

    //connect editor with div "editor"
    var editor = ace.edit("editor");
    //set theme for visualization
    editor.setTheme("ace/theme/twilight");
    //set mode for highlight syntax
    editor.session.setMode("ace/mode/r");
    //set initial data into window of editor
    editor.insert("#some r code for example: \n a <- c(4,3,3,3,3,4,4,3,5,4,5,5) \n \n \n");
    //select some part of editor window and set it as read-only segment
    var session  = editor.getSession()
            //now me can use class "Range"
            , Range    = ace.require("ace/range").Range
            //initialize  variable of class "Range"
            //(startRow,startColumn,endRow,endColumn)
            , range    = new Range(0, 0, 2,0)
            // set css style for this range
            , markerId = session.addMarker(range, "readonly-highlight");



    editor.keyBinding.addKeyboardHandler({
        handleKeyboard : function(data, hash, keyString, keyCode, event) {
            if (hash === -1 || (keyCode <= 40 && keyCode >= 37)) return false;

            if (intersects(range)) {

                return {command:"null", passEvent:false};
            }
        }
    });

    function intersects(range) {
        return editor.getSelectionRange().intersects(range);
    }


</script>
</body>
</html>