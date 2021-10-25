
## Local Setup
* Install Java 17 (recommended to use [SdkMan](https://sdkman.io))

`sdk install java 17-open`
* Install Maven (recommended to use [SdkMan](https://sdkman.io))

`sdk install maven`

* Clone the repo and configure appropriate datasource values in the application.properties file, run the below command

`mvn clean install`

* To start the application

`mvn spring-boot:run &`

* Access the swagger-UI on the below path (Recommended to use POSTMAN to hit APIs)
```
http://localhost:8080/swagger-ui.html
```
