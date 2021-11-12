# partydj-server
PartyDJ Java Backend Server

## Development Environment
> JDK 11
Apache Maven

## Youtube Data Api
The application uses Google's Youtube Data API in order to retrieve data \
You will need to run with the youtube profile and provide a youtube key \
Environment Variable `YOUTUBE_APIKEY` should be provided when running app locally or in docker. \
https://www.youtube.com/watch?v=N18czV5tj5o&ab_channel=WebbyFan.com

## Running Application
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
