<html>
<head><title>Authorize: <%=request.getParameter("oauth_token")%></title></head>
<body>
<pre>
<form action="<%=request.getContextPath()%>/rest/authorize" method="post">
	<input type="hidden" name="oauth_token" value="<%=request.getParameter("oauth_token")%>" />
	Nome:
	<input type="text" name="nome" /><br>
	Login:
	<input type="text" name="login" /><br>
	Senha:
	<input type="password" name="senha" />
	<input type="submit" value="Autorizar" />
</form>
</pre>
</body>
</html>
