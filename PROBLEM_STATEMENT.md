### Problem Statement 
For every required condition on an Entity, we have to expose a seperate API that filters DB records to return the ones that matched

#### Example:
1. /users `Returns all user records in the system`
2. /users/{userId} `returns a user record corresponding to the provided id (pkey)`
3. /users/email/{emailId} `returns a user record corresponding to the provided email-id`
4. /users/country/{countryId} `returns user records that belong to the country whose id is provided`

and so on...

--- 
### [Solution](https://github.com/hardikSinghBehl/RSQL-spring-boot)
An approach that allows for a single GET API to be exposed per Entity and the condition(s) are provided by the frontend as required.
