<pre>
<form enctype="application/x-www-form-urlencoded" action="<%=request.getContextPath()%>/rest/carros/postFotoBase64"
	method="POST">
	FileName:
	<input name="fileName" type="text" />
	Base64:
	<textarea name="base64" type="textarea" cols="60" rows="10" ></textarea>
	<!-- Botão de submit -->
	<input type="submit" value="Enviar arquivo" />
</form>
</pre>