# log-acess-api
WEBSERVICE API REST JAVA

# utilidade
- Retorna informações (métricas) de acesso à determinadas URLs.

/laaa/health - consulta vida da aplicação
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

/laa/metrics - retorna métricas
------------ 
[GET] - http://localhost:8080/log-acess-api/app/log-acess/laar/metrics/data=20-11-2002

Métrica 1 - Top 3 URL acessadas no mundo inteiro
Métrica 2 - Top 3 URL acessadas por região
Métrica 3 - URL com mais menos no mundo inteiro
Métrica 4 - Top 3 acessos por dia, mês e ano - recebe data por parâmetro (&data)
Métrica 5 - Minuto com mais acesso em todas URLs