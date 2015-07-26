<form enctype="multipart/form-data" action="<%=request.getContextPath()%>/rest/carros"
	method="POST">
	<!-- O tipo "file" cria o botão Browse para escolher o arquivo -->
	<input name="userfile" type="file" />
	<!-- Botão de submit -->
	<input type="submit" value="Enviar arquivo" />
</form>
