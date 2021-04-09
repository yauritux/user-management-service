## Prerequisites
1. Axon Server
2. PostgreSQL
3. Maven

## Run The Services

1. Start the Axon server and PostgreSQL database simply by calling docker-compose as shown below:
   ```
   docker-compose up
   ```
2. Create a new database inside our PostgreSQL container. Name it as `powerbiz`.  
   You can do this either by using your favourite Postgres db manager tools like `pgadmin`,
   or using this following docker command:
   ```
   docker container exec -it powerbiz_postgres createdb --host=localhost \
   --port=5432 --username=postgres --owner=postgres powerbiz
   ```
3. `cd` into `user-management-service` folder and build the project:
   ```
   mvn clean install
   ```
4. `cd` into `usercmdsvc` folder, and run the service:
   ```
   mvn spring-boot:run
   ```
5. `cd` into `userquerysvc` folder, and run the service:
   ```
   mvn spring-boot:run
   ```
   
## Shutdown the Services

1. Stop both `usercmdsvc` and `userquerysvc`
2. Shutdown the Axon-Server and PostgreSQL database:
   ```
   docker-compose down
   ```