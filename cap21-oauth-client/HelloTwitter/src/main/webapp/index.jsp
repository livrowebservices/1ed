<pre>
Demo de OAuth Client com Twitter

Hello
<a href="<%=request.getContextPath()%>/rest/twitter">/rest/twitter</a>

Mostra a URL de autorização do Twitter
<a href="<%=request.getContextPath()%>/rest/twitter/auth">/rest/twitter/auth</a>

Valida o código verificador e imprime o Access Token (precisa do parâmetro (/verify/{verifier})
<a href="<%=request.getContextPath()%>/rest/twitter/verify/{verifier}">/rest/twitter/verify/{verifier}</a>
</pre>