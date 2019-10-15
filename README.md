# log-acess-api
WEBSERVICE API REST JAVA

# Utilidade
- Retorna informa��es (m�tricas) de acesso � determinadas URLs.

/laaa/health - Consulta vida da aplica��o
------------ 
[GET] - http://localhost:8080/laaa/health
200 - ON
500 - OFF

/laaa/ingest - Recebe logs de acesso
------------ 
[POST] - http://localhost:8080/laaa/ingest

{
	"url": "/pets/exotic/cats/10",
	"timestamp": 1037825323957,
	"userId": "5b019db5-b3d0-46d2-9963-437860af707f",
	"region": 1
}

/laa/metrics - Retorna m�tricas
------------ 
[GET] - http://localhost:8080/laa/metrics&data=21-11-2015

M�trica 1 - Top 3 URL acessadas no mundo inteiro
M�trica 2 - Top 3 URL acessadas por regi�o
M�trica 3 - URL com mais menos no mundo inteiro
M�trica 4 - Top 3 acessos por dia, m�s e ano - recebe data por par�metro (&data)
M�trica 5 - Minuto com mais acesso em todas URLs