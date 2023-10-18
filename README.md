# cars-01-project

This multimodule project helps to manage cars. Cars contain information about model, price, price, color, mileage,
and components. You can simply add orders from .json, .txt files as well as from MySql database.
Due to implemented `abstract factory` design pattern there is an easy way to implement more sources from where you can
add your cars.
Project comes up with functionality of registering users to database, security and signing in.

# Set-up

- Java 20,
- Maven,
- Docker,
- Spark,
- Spring,
- Jjwt,
- Gson,
- Simplejavamail,
- Junit,
- Mockito,
- Lombok,
- Hamcrest,
- Jdbi.

# Design patterns
- Abstract factory
- Observer
- Builder
- Singleton
- Complex proxy

# How to install?

Use command below to install the package into your local repository.
```
mvn clean install
```
If you don't want to create local repository use:
```
mvn clean package -DskipTests
```

# How to run?

Run with docker:
```
docker-compose up -d --build mysql_main cars_01_web_app
```
Application image from dockerhub will be downloaded.

# Running unit tests

To run unit tests properly go to project destination in terminal and then execute below command:
```
docker-compose up -d --build mysql_test
```
This will download MySql image from dockerhub based on docker-compose.yml file, create test database and fill with
values that are required for unit tests. To run unit tests type:
```
mvn test
```
Inside `data` directory in `persistence` and `service` module, you will find data which is required for unit tests.

# Change data source

To change data source to .json, .txt or db go to [application.properties](api/src/main/resources/application.properties) file and choose source, that you want to use.

# Sending mails

To send mails properly go to [application.properties](api/src/main/resources/application.properties) and fill the values with proper data.
You can also modify activation email subject and email content inside this .properties file.

Due to implemented `observer` design pattern after sending mail to a person, the notification about sent mail 
is also being sent to chosen mail.

# Abstract factory

Abstract factory is based in [reader](persistence/src/main/java/com/app/persistence/data/reader).
To create objects using abstract factory you have to fill [application.properties](api/src/main/resources/application.properties) file config.
Choose what processor type you want to use: db, json or txt.

Serialization/deserialization data from .json files is made using `Gson` library. For reading purposes you can find
use of gson library in [Loader](persistence/src/main/java/com/app/persistence/data/reader/loader). For .txt serialization/deserialization stream methods are used and can be found
inside the same loader path as for .json format. Library responsible for working with database is Jdbi library.

`Data` directory in main project tree has necessary files to work with abstract factory.

# Docker-compose

Docker-compose file provides three containers:
- test database,
- main database,
- application.

# Api

Api has prepared following routings:
- ErrorRouting,
- CarsRouting,
- SecurityRouting,
- UserRouting.

Endpoints of each routing are described as comment inside [routing](api/src/main/java/com/app/web/routing).

Thanks to Spring framework there is `AppConfig` class which initializes all necessary objects for application
to work correctly.
`Main` class starts Spark developer server and gets necessary beans and runs them.

# Persistence

Persistence module is responsible for creating business models - in this case:
- Car,
- Component,
- User.

Module also contains:
- necessary mappers,
- models methods,
- repositories,
- dto,
- models for serialization and deserialization,
- abstract factory.

In this module there is a directory called [data](persistence/data). Inside it, you will find .json and .txt files, that can be used for
creating objects.

# Service

Service module is responsible for managing the set of cars, sending emails, managing registration of users 
and generating access tokens. All methods from cars service are described in [CarsServiceImpl.java](service/src/main/java/com/app/service/cars/CarsServiceImpl.java) file.
