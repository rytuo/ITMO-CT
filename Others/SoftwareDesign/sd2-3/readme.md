# Software design homework №3

Two services made via spring boot and data jpa with h2
- client - manages users and their actives
- stock - manages companies and their stocks

Client sends requests to server to know the actual price and buy/sell stocks

Unit tests for endpoints, services and docker containers tests for StockClient

For docker tests:
- install docker
- build stock image via `./gradlew bootJar && docker build -t stock stock`
- run StockClientTest