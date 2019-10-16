# log-acess-api
WEBSERVICE API REST JAVA

# utilidade
- Retorna informa��es (m�tricas) de acesso � determinadas URLs.

/laaa/health - consulta vida da aplica��o
------------ 
[GET] - http://localhost:8080/log-acess-api/app/log-acess/laar/health
<p>200 - ON</p>
<p>500 - OFF</p>

/laaa/ingest - recebe logs de acesso
------------ 
[POST] - http://localhost:8080/log-acess-api/app/log-acess/laar/ingest
```
[
	{
		"url": "/pets/exotic/cats/10",
		"timestamp": 1037825323957,
		"userId": "5b019db5-b3d0-46d2-9963-437860af707f",
		"region": 1
	}
]
```

/laa/metrics - retorna m�tricas
------------ 
[GET] - http://localhost:8080/log-acess-api/app/log-acess/laar/metrics/data=20-11-2002

M�trica 1 - Top 3 URL acessadas no mundo inteiro
M�trica 2 - Top 3 URL acessadas por regi�o
M�trica 3 - URL com mais menos no mundo inteiro
M�trica 4 - Top 3 acessos por dia, m�s e ano - recebe data por par�metro (&data)
M�trica 5 - Minuto com mais acesso em todas URLs