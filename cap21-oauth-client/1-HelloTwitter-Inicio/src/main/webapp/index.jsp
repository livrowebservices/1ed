<pre>
Demo de OAuth Client com Twitter

Hello
<a href="<%=request.getContextPath()%>/rest/twitter">/rest/twitter</a>

Mostra a URL de autorização do Twitter
<a href="<%=request.getContextPath()%>/rest/twitter/auth">/rest/twitter/auth</a>

Valida o código verificador e imprime o Access Token (precisa do parâmetro (/verify/{verifier})
<%=request.getContextPath()%>/rest/twitter/verify/{verifier}

Timeline, informe nos parâmetros o Access Token
<%=request.getContextPath()%>/rest/twitter/timeline?

<hr>

Versão 2, faz o fluxo de autorização automaticamente com um filtro

Mostra o JSON com a timeline do Twitter

<a href="<%=request.getContextPath()%>/rest/twitterv2/timeline">/rest/twitterv2/timeline</a>

<a href="<%=request.getContextPath()%>/rest/twitterv2/ultimoTweet">/rest/twitterv2/ultimoTweet</a>

</pre>