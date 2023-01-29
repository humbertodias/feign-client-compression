# Feign compression

Testing feign client with gzip compression enabled


```mermaid
sequenceDiagram
actor User
User ->> DemoController : getRandomPerson
activate DemoController
DemoController ->> PersonService : getAll
activate PersonService
PersonService ->> PersonClient : getAll
activate PersonClient
PersonClient -->> PersonService : #32;
deactivate PersonClient
PersonService -->> DemoController : #32;
deactivate PersonService
deactivate DemoController
```

# Build
```
./gradlew build
```

# Run
```
./gradlew :server:bootRun
./gradlew :client:bootRun
```

# Client
Request
```
curl -I --request GET \
--url 'http://localhost:9090/demo/person?amount=999'
```
application/json
```
HTTP/1.1 200
Content-Type: application/json
Transfer-Encoding: chunked
Date: Sun, 29 Jan 2023 01:31:28 GMT
```
So, checkout the log have Gzip enabled on Feign Client
```
2023-01-28 22:26:02.559 DEBUG 44481 --- [nio-9090-exec-8] com.example.demo.client.PersonClient     : [PersonClient#getAll] <--- END HTTP (985130-byte body)
2023-01-28 22:26:05.489 DEBUG 44481 --- [nio-9090-exec-9] com.example.demo.client.PersonClient     : [PersonClient#getAll] ---> GET http://localhost:9191/api/faker?amount=999 HTTP/1.1
2023-01-28 22:26:05.490 DEBUG 44481 --- [nio-9090-exec-9] com.example.demo.client.PersonClient     : [PersonClient#getAll] Accept-Encoding: gzip
2023-01-28 22:26:05.490 DEBUG 44481 --- [nio-9090-exec-9] com.example.demo.client.PersonClient     : [PersonClient#getAll] Accept-Encoding: deflate
2023-01-28 22:26:05.490 DEBUG 44481 --- [nio-9090-exec-9] com.example.demo.client.PersonClient     : [PersonClient#getAll] ---> END HTTP (0-byte body)
2023-01-28 22:26:05.769 DEBUG 44481 --- [nio-9090-exec-9] com.example.demo.client.PersonClient     : [PersonClient#getAll] <--- HTTP/1.1 200  (279ms)
2023-01-28 22:26:05.770 DEBUG 44481 --- [nio-9090-exec-9] com.example.demo.client.PersonClient     : [PersonClient#getAll] connection: keep-alive
2023-01-28 22:26:05.770 DEBUG 44481 --- [nio-9090-exec-9] com.example.demo.client.PersonClient     : [PersonClient#getAll] content-type: application/json
2023-01-28 22:26:05.770 DEBUG 44481 --- [nio-9090-exec-9] com.example.demo.client.PersonClient     : [PersonClient#getAll] date: Sun, 29 Jan 2023 01:26:05 GMT
2023-01-28 22:26:05.770 DEBUG 44481 --- [nio-9090-exec-9] com.example.demo.client.PersonClient     : [PersonClient#getAll] keep-alive: timeout=60
2023-01-28 22:26:05.770 DEBUG 44481 --- [nio-9090-exec-9] com.example.demo.client.PersonClient     : [PersonClient#getAll] transfer-encoding: chunked
2023-01-28 22:26:05.771 DEBUG 44481 --- [nio-9090-exec-9] com.example.demo.client.PersonClient     : [PersonClient#getAll] vary: accept-encoding
2023-01-28 22:26:05.772 DEBUG 44481 --- [nio-9090-exec-9] com.example.demo.client.PersonClient     : [PersonClient#getAll] <--- END HTTP (98538-byte body)
```

# Server
Simulating feign client
```
curl -I -H "Accept-Encoding: gzip" --request GET \
--url 'http://localhost:9191/api/faker?amount=999'
```
Gzip enabled
```
HTTP/1.1 200
vary: accept-encoding
Content-Encoding: gzip
Content-Type: application/json
Transfer-Encoding: chunked
Date: Sun, 29 Jan 2023 01:29:08 GMT
```

# Ref
* [feign_request_response_compression](https://cloud.spring.io/spring-cloud-netflix/multi/multi_spring-cloud-feign.html#_feign_request_response_compression)