# UserService

This repository contains solely the microservice pertaining to the users.

It consists of relating entities, daos, services and controller which will be explained below.
Entity: 
  User - general user object that contains personal info and account information
  Customer - Users with specific role will have additional information stored
  Driver - Users with specific role will have additional information stored
Dao:
  User: Find by email, username, verification_code is added
  Customer: Generic JPA
  Driver: Generic JPA
Service:
  User: Services User entities and contains business logic
Controller:
  Registration (/users):
    /verify - sets account to active
    /register - passes fields to user service to register new user; redirects to /helloworld2 on success
    /helloworld2 - to be replaced in the future with mapping to node js service for sending out emails (send account verification link)

## Jenkins Pipeline

Currently configured at http://52.53.232.194:8080/
