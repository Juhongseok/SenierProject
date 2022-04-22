# SenierProject

# E-R Diagram
![ERD](https://github.com/wnghdtjr129/SenierProject/blob/main/picture/chagedERD.png)

# Directory 구조
```bash
   `-- src
       |-- main
       |   |-- generated
       |   |-- java
       |   |   `-- com
       |   |       `-- jhs
       |   |           `-- seniorProject
       |   |               |-- SeniorProjectApplication.java
       |   |               |-- TestDataInit.java
       |   |               |-- configuration
       |   |               |   `-- WebConfig.java
       |   |               |-- controller
       |   |               |   |-- HomeController.java
       |   |               |   |-- SessionConst.java
       |   |               |   |-- UserController.java
       |   |               |   `-- form
       |   |               |       |-- LoginForm.java
       |   |               |       `-- SignUpForm.java
       |   |               |-- domain
       |   |               |   |-- Friend.java
       |   |               |   |-- Location.java
       |   |               |   |-- Map.java
       |   |               |   |-- SmallSubject.java
       |   |               |   |-- User.java
       |   |               |   |-- UserMap.java
       |   |               |   |-- baseentity
       |   |               |   |   |-- TimeAndPersonInfo.java
       |   |               |   |   `-- TimeInfo.java
       |   |               |   |-- compositid
       |   |               |   |   |-- SmallSubjectId.java
       |   |               |   |   |-- UserMapId.java
       |   |               |   |   `-- friendId.java
       |   |               |   |-- enumeration
       |   |               |   |   |-- BigSubject.java
       |   |               |   |   `-- Visibility.java
       |   |               |   `-- exception
       |   |               |       |-- DuplicatedUserException.java
       |   |               |       `-- NoSuchUserException.java
       |   |               |-- interceptor
       |   |               |   `-- LoginInterceptor.java
       |   |               |-- repository
       |   |               |   `-- UserRepository.java
       |   |               `-- service
       |   |                   `-- UserService.java
       |   `-- resources
       |       |-- application.yml
       |       |-- static
       |       `-- templates
       |           |-- index.html
       |           |-- loginIndex.html
       |           `-- users
       |               |-- loginForm.html
       |               `-- signUpForm.html
       `-- test
           `-- java
               `-- com
                   `-- jhs
                       `-- seniorProject
                           |-- SeniorProjectApplicationTests.java
                           |-- repository
                          |   `-- UserRepositoryTest.java
                           `-- service
                               `-- UserServiceTest.java
```
