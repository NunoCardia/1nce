# Running the application with Docker

## Creating the docker image
To create the docker image just run:
> mvn clean install

and if you check your local images the `triangle-backend` image should be there. Keep in mind that Jib executes during the maven `compile` lifecycle so if you run this command **it will also
create the docker image**. To skip the docker image creation just add the following flag to all maven related commands:
`-Djib.skip`.

## Running the application

### Docker compose

To run a local instance version of this project execute the command:
>docker compose -f docker-compose.yml up -d

The file includes a mysql service based on the official MySQL image.