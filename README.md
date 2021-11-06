# partydj-server
PartyDJ Java Backend Server

## Development Environment
> JDK 11
Apache Maven

## Running Appliocation
`mvn clean install`
`mvn spring-boot:run`

## Docker Runtime

If you would like, you can now run the docker container instead of installing \
dependencies and running this app on your local machine

### Docker build

If you have not created the docker network before, run the following command
`docker network create partydj_network`

Now you can build the docker image locally and create
`docker build -t gsugambit/partydj-server .`
`docker-compose up -d`

the application will run externally on `http://localhost:19191`
