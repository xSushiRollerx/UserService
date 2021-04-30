# UserService

This repository contains solely the microservice pertaining to the users.

It consists of relating entities, daos, services and controller which will be explained below.

Entity: 

  User - general user object that contains personal info and account information
  
  Customer - Users with specific role will have additional information stored
  
  Driver - Users with specific role will have additional information stored
  
Dto:

  User DTO - username, password, first name, last name, email, phone (may add 'created at' and loyalty points later)
  
  Driver DTO - username, password, rating, total deliveries, first name, phone
  
Dao:

  User: Find by email, username, phone, verification_code is added
  
  Customer: Generic JPA
  
  Driver: Generic JPA
  
Service:

  User: Services User entities and contains business logic
  
Controller:

  Registration (/users):
  
    /verify - GET: sets account to active
    
    /verify - PUT: sends new verification code
    
    /user - passes fields to user service to register new user; redirects to /helloworld2 on success
    
  Driver (/users):
  
    /driver/{username} - GET: return driver DTO
    
    /driver/{username} - POST: reactivates driver account
    
    /driver/{username} - DELETE: deactivates driver account
    
  User (/users):
    
    /user/{username} - GET: return user DTO
    
    /user/{username} - PUT: update user info
    
    /user/{username} - DELETE: closes the user account
    
  Admin (/admins):
    
    /driver/{username} - PUT: updates driver from DTO in request
    
    /user/{username} - DELETE: closes the user account
    
    /user/{username}/role - PUT: updates user role
    
    /user/{username} - PUT: update user info
    
    /driver/{username} - DELETE: deactivate driver (May need to remove the reactivate account on driver side)
    
    
