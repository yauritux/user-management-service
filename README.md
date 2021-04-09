Prerequisites
1. Axon Server
   ```
   docker container run -d --name dev-axon-server \
   -p 8024:8024 -p 8124:8124 axoniq/axonserver
   ```
2. PostgreSQL
   ```
   docker container run -d --name dev-postgres -p 5432:5432 \
   -e POSTGRES_PASSWORD=test!@# -v local_pgdata:/var/lib/postgresql/data \
   postgres:11
   ```