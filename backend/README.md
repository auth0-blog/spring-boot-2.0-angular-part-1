## Collaborative Diagrams

Loading a MongoDB instance with Docker:

```bash
docker run --name collaborative-diagrams \
    -p 27017:27017 \
    -d mongo
```

Also, you can run the `reset-database` script:

```bash
./reset-database
```

Issuing requests to the `diagrams` endpoint:

```bash
curl localhost:8080/diagrams

curl -X POST -H 'Content-Type: application/json' -d '{
    "id": "abc123",
    "title": "testing it, now!"
}' localhost:8080/diagrams
```