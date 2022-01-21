# Application deployment

For this project Docker was used to build a container image. Due to the lack of knowledge and time constraints
the image was not deployed and ran in a cloud service like ECS. A docker compose was created and then used to run the
application.

## Jib
* The traditional way of using the `docker build` command was not considered here.
Instead, a Google plugin named [Jib](https://github.com/GoogleContainerTools/jib/tree/master/jib-maven-plugin) was used.
As stated before it is a plugin created by Google that creates docker images following
the most recent standards for docker image creation. <p>&nbsp; 
* Jib creates a layered docker image separating the application into multiple layers, splitting dependencies from classes.
That way we don’t have to wait for Docker to rebuild the entire image - just build the layers that were changed.
Another main advantage is that we don't actually need Docker installed in our 
system and we don't need the classic Dockerfile file in the project.

### Configuration

```xml
<!-- jib plugin -->
            <plugin>
                <groupId>com.google.cloud.tools</groupId>
                <artifactId>jib-maven-plugin</artifactId>
                <version>${jib.version}</version>
                <executions>
                    <execution>
                        <phase>compile</phase>
                        <goals>
                            <goal>dockerBuild</goal>
                        </goals>
                    </execution>
                </executions>
                <configuration>
                    <skip>true</skip>
                    <from>
                        <image>openjdk:11.0.4-jre-slim</image>
                    </from>
                    <to>
                        <image>triangle-backend</image>
                    </to>
                    <container>
                        <ports>
                            <port>8080</port>
                        </ports>
                    </container>
                    <skip>${jib.skip}</skip>
                </configuration>
            </plugin>
```

The main configuration here is done in the `<configuration>` tag where we can see that the syntax is similar to the syntax
that is normally provided in a Dockerfile. Optionally we could push the image to a cloud repository like ECR but that was
not necessary. Instead, the image was pushed to the docker daemon to be run locally. The Jib plugin is executed during the
`compile` maven lifecycle and can be skipped by setting the `<jib.skip>` to false or adding the `-Djib.skip` flag in all maven commands.

## Docker Compose
During initial tests the provided [docker compose](../../docker-compose.yml) had a local instance of a mysql database
running for development purposes but after the creation of an RDS database that service was removed. As we only have one
service it would not be needed to use a docker compose for it but, as there are many properties to be configured, it was 
decided to keep the docker compose instead of an extremely large `docker run` instruction. This way the service configuration
is more perceptible as it is written in a YAML format.

```yaml
version: '2'
services:
  triangle-backend:
    container_name: triangle-backend
    image: triangle-backend
    ports:
      - "8080:8080"
    environment:
      datasource.url: jdbc:mysql://triangle.ceku3opa8ckn.eu-west-1.rds.amazonaws.com:3306/triangle?createDatabaseIfNotExist=true
      datasource.username: root
      datasource.password: thepassword
    logging:
      driver: awslogs
      options:
        awslogs-region: eu-west-1
        awslogs-group: triangle-logs
        awslogs-stream: triangle-stream
```

### Environment Variables
In order to keep the [application.properties](../../src/main/resources/application.properties) environment agnostic,
environment variables were used. That way, whether we run the program locally or in a cloud environment, the application
does not need to change. The application.properties file is the following:
```properties
spring.jpa.database=mysql
spring.jpa.hibernate.ddl-auto=update
spring.datasource.url=${datasource.url:jdbc:mysql://localhost:3306/triangle?createDatabaseIfNotExist=true}
spring.datasource.username=${datasource.username:root}
spring.datasource.password=${datasource.password:ThePassword}
```
When the application is started Spring will try to assign values to each variable. The framework follows a specific
order for this. Please refer to https://docs.spring.io/spring-boot/docs/current/reference/html/features.html#features.external-config 
for more information on the complete order. If, at the end, a value is not found for the variable, the default value that is
represented by the string after the `:` will be used. At a certain time, Spring will look for the `datasource.url`, `datasource.username` and
`datasource.password` variables in the container's environment variables and it will find the values that are being described in the
`environment` section of the docker compose.

### Logging

As required the application logs must be sent to Amazon CloudWatch. The properties in the docker compose were created as
described in the [Docker official documentation](https://docs.docker.com/config/containers/logging/awslogs/). This, however,
does not create the CloudWatch resources. Please refer to the [CloudWatch section](../infrastructure/README.md#cloudwatch) for more information.