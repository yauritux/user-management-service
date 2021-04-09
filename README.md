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
3. Setup Database
   * log into our postgres container
   ```
   docker container exec -it dev-postgres psql -h localhost -U postgres
   ```
   * create new database, give it a name `powerbiz`
   ```
   postgres# create database powerbiz;
   ```
