### Representational State Transfer + Structured Query Language: RSQL
#### Demo application using [RSQL parser](https://github.com/jirutka/rsql-parser) to filter records based on provided condition(s)

A Single `GET API` is all that is needed to be exposed per [Entity](https://github.com/hardikSinghBehl/RSQL-spring-boot/tree/main/src/main/java/com/behl/dolores/entity) class.

### Additional Datatypes added/supported
* UUID
* LocalDate
* LocalDateTime
* Enum
* Boolean String (true, false) and Bit (1,0)
* Nested Objects

### Additional Operations added/supported
* equalIgnoreCase (=eic=)
* notEqualIgnoreCase (=neic=)

### Sample Recording showing examples

https://user-images.githubusercontent.com/69693621/138670878-0e6f66ae-9b64-4581-b144-2eb7c6fd02a5.mov

### Pagination and Sorting Support
* Pagination
  * Query parameters to include
    * `count` : maximum elements to be returned in a page 
    * `page` : starts with 1, if value provided is <=0, first page is returned and if value>totalPages, the last page is returned
  * Sample path
  ```
  http:localhost:8080/wizards?query=gender==female&count=5&page=1
  ```
  * Sample Response
  ```
    {
    "currentPage": 1,
    "totalPages": 2,
    "count": 5,
    "result": [
        {
            "id": 2,
            "fullName": "Hermione Granger",
            "species": "HUMAN",
            "gender": "FEMALE",
            "house": {
                "id": 1,
                "name": "Gryffindor"
            },
            "dateOfBirth": "1979-09-19",
            "eyeColor": "brown",
            "hairColor": "brown",
            "wand": {
                "id": 2,
                "wood": "vine",
                "core": "dragon heartstring",
                "length": null
            },
            "patronus": null,
            "isProfessor": false,
            "alive": true,
            "imageUrl": "http://hp-api.herokuapp.com/images/hermione.jpeg"
        } ... and 4 more
    ]
  }
  ```
 
* Sorting
  * Query parameter to be included
    * `sort`
  * Operators
    * Ascending: `@`
    * Descending: `$`

  * Examples:
    * to sort with fullName descending
    ```
    &sort="$descending"
    ```
    * to sort with dateOfBirth ascending
    ```
    &sort="@dateOfBirth"
    ```
    * to sort with ascending dateOfBirth and fullname descending
    ```
    &sort="@dateOfBirth$fullname"
    ```

### SQL Injection Attack Prevention Support
* HttpStatus.PRECONDITION_FAILED (412) thrown when SQL Injection attack is attempted
* [Library Used](https://github.com/rkpunjal/sql-injection-safe) in [RsqlSpecification.java](https://github.com/hardikSinghBehl/RSQL-spring-boot/blob/main/src/main/java/com/behl/dolores/rsql/RSQLSpecification.java)

https://user-images.githubusercontent.com/69693621/138661528-190f8b78-0f01-42a4-92d2-c1396024c9e4.mov

---

### Important Classes and packages
* [RSQLParserBean.java](https://github.com/hardikSinghBehl/RSQL-spring-boot/blob/main/src/main/java/com/behl/dolores/rsql/bean/RsqlParserBean.java)
* [RSQLSpecification.java](https://github.com/hardikSinghBehl/RSQL-spring-boot/blob/main/src/main/java/com/behl/dolores/rsql/RSQLSpecification.java)
* [Rsql-Operators](https://github.com/hardikSinghBehl/RSQL-spring-boot/tree/main/src/main/java/com/behl/dolores/rsql/constant)
* [SortingUtil.java](https://github.com/hardikSinghBehl/RSQL-spring-boot/blob/main/src/main/java/com/behl/dolores/utility/SortingUtil.java)
* [WizardService.java](https://github.com/hardikSinghBehl/RSQL-spring-boot/blob/main/src/main/java/com/behl/dolores/service/WizardService.java)
* [Tests](https://github.com/hardikSinghBehl/RSQL-spring-boot/tree/main/src/test/java/dolores/test)

### References
* [Baeldung Article](https://www.baeldung.com/rest-api-search-language-rsql-fiql)
* [Demo application](https://github.com/JonLally/rsql-demo)

---
⭐️ Star the repository to show support

🤝 PRs welcome 
