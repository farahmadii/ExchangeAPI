## A Rudimentary Exchange API

This repository contains a simple RESTful API for fetching exchange data. Currency data can be queried based on different criteria.
### How to run the project
Run the following commands from project root directory.
### Build project and docker
```bash
./mvnw package
mkdir -p target/dependency && (cd target/dependency; jar -xf ../*.jar)
docker build -t ecol_task .
```

## Run
```bash
docker-compose up
```

## Endpoints

API endpoints (except healthcheck) are secured with Oauth2 and JWT, so the bearer tocken should be obtained form authorization server and put in request header.

To get the brear token run the following:
```bash
curl -X "POST" "https://auth.sandbox.api-ecolytiq.com/oauth/token" \
     -H 'Content-Type: application/x-www-form-urlencoded; charset=utf-8' \
     -u 'test-es-sandbox-client-OEKXJR:oaOvDLohEXXpMUPcemdJndexjomZAshjhogQLAMTUDFSkUEWeujpUxUCgkebDhIk' \
     --data-urlencode "grant_type=client_credentials" \
     --data-urlencode "scope=all"
```

Input data should be in the format of compressed csv, see the example [here](test_files)

The following endpoints can be invoked using postman or issuing a curl command:

* http://localhost:8888/healthcheck: healthcheck endpoint
* http://localhost:8888/exchange/fetch: to upload compressed csv and fetch exchange data
* http://localhost:8888/exchange/find?currency=USD&date=09%20June%202021: example of search using date and currency
* http://localhost:8888/exchange/find?currency=USD: example of search using currency
* http://localhost:8888/exchange/find?date=09%20June%202021: example of search using date
* http://localhost:8888/exchange/findall: to get all exchange data

---
