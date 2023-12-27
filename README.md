# http-server

This repository contains a simple multi-threaded HTTP server that supports following operations:
1. `GET /echo/<random-string>` returns the random-string as response
2. `GET /files/filename` returns the contents of the file as octet-stream
3. `POST /files/filename` creates the file sent as text in body of the request

This server is written as part of CodeCrafters [challenge](https://app.codecrafters.io/courses/http-server/overview?_gl=1*5yzazs*_ga*MTE0NDk1ODg4Mi4xNzAzNjYwOTQw*_ga_N8D6K4M2HE*MTcwMzY2MDkzOS4xLjEuMTcwMzY2MDk3NC4wLjAuMA..)

## Running the server

`java Server [PORT]`

The default port is 4221.
