<pre>
<a href="<%=request.getContextPath()%>/rest/carros">/carros</a>

<a href="<%=request.getContextPath()%>/rest/carros/1">/carros/1</a>
</pre>

<hr>

<form method="post" action="<%=request.getContextPath()%>/hello">
	Nome: <input type="text" name="nome" />
	<br /><br />
	Sobrenome: <input type="text" name="sobrenome" />
	<br />
	<input type="submit" name="Enviar" />
</form>
