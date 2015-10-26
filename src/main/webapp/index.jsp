<html>
<body>
<h1>Welcome!</h1>
<h2>Analyze Me right now!</h2>
<%! private int accessCount = 0; %>
Accesses to page since server startup: <%= ++accessCount %>
<FORM NAME="buttonAction" ACTION="action.html" METHOD="POST">
    <INPUT TYPE="SUBMIT" VALUE="Move to action page">
</FORM>
</body>
</html>
