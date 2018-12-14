# Liferay Workspace starter
The workspace requires access to Liferay's private nexus, if you don't have access you can switch the bundle version to CE and the target platform to 7.1-GA1 in `gradle.properties`.
You also have to disable the private maven repo in `build.gradle`.

## Setup
Before being able to run the portal you need to configure your username and password by running the following commands:
```bash
./gradlew addCredentials --key liferayNexusUsername --value email
./gradlew addCredentials --key liferayNexusPassword --value password
```

Afterwards you need to start the database, you can either configure it yourself or run the provided docker-compose scripts in the database directory.
```bash
docker-compose -f database/postgresql-compose.yml up -d
```
PostgreSQL is the default configured database, if you prefer using MYSQL then select the `mysql-compose.yml` file. 
You will also have to change the database settings in the `configs/local/portal-ext.properties` file.

Now you are ready to run the `initBundle` script:
```bash
blade server init
```
This will create a complete Liferay distribution in the `bundles` directory.

To finally run your portal execute:
```bash
blade server start -d
```