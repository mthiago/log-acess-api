# log-acess-api
WEBSERVICE API REST JAVA
<p>Importe o projeto para sua IDE, após isso importe os .jars que estão em /WebContent/WEB-INF/lib</p>
<p>O arquivo utils/database/database.db que se encontra na raíz do projeto, deverá ser importado para sua máquina, após isso, apontar o caminho escolhido no arquivo src/DatabaseConfig.properties</p>
<p>Para rodar a aplicação, pode ser utilizado Tomcat ou qualquer server de sua preferência.</p>

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

<p>Métrica 1 - Top 3 URL acessadas no mundo inteiro</p>
<p>Métrica 2 - Top 3 URL acessadas por região</p>
<p>Métrica 3 - URL com mais menos no mundo inteiro</p>
<p>Métrica 4 - Top 3 acessos por dia, mês e ano - recebe data por parâmetro (&data)</p>
<p>Métrica 5 - Minuto com mais acesso em todas URLs</p>