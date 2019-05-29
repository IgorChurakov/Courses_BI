# Custom API

This API was written to demonstrate author's skills in building API's using Spring.
Although code uses various "REST" tools, technically, this is not a RESTful service.
It was build to meet it's specification which fits in terms of basic API rather than a RESTful service.

## Getting Started

These instructions will get you a copy of the project up and running on your local machine for development and testing purposes.

### Prerequisites

What things you need to install the software and how to install them

```
-Any valid Java IDE that supports development with Spring framework and Maven
```

### Installing

A step by step series of examples that tell you how to get a development env running

##### Step 1: Get sources from repository

Instructions on how to get sources using Git can be found [here](https://git-scm.com/book/en/v2/Git-Basics-Working-with-Remotes)

##### Step 2: Getting dependencies

To get all required dependencies for this application simply run
```
mvn install
```
or
```
mvn package
```
in the command line while you're in the application's root directory

##### Step 3: Running the app

To get this application running you need to invoke
```
public static void main(String[] args)
```
method in the "Application" class. 
###### API is ready for using! Default path to access it's controllers is http://localhost:8888/api/

##### Step 4: Interacting with the app

Since we have some preloaded data for demonstration "out-of-the-box", we can try to interact with it!

Let's get a user from our database:
```
http://localhost:8888/api/user/4
```
And the response should be something like:
```
{
    "id": 4,
    "firstName": "John",
    "secondName": "Cena",
    "middleName": "J",
    "position": "Director",
    "phone": "+79876541236",
    "docName": "Паспорт иностранного гражданина",
    "docNumber": "8514369458",
    "docDate": "2014-01-25",
    "citizenshipName": "Sweden",
    "citizenshipCode": 752,
    "isIdentified": true
}
```
## Running the tests

To run the tests simply set the environmental variable:
```
MVC_ACTIVE_PROFILE = test
```
And run the "ApiTestSuite" class

### What tests test...

Every test class is assigned to test it's correspondent controller layer and everything "under" it.  

```
What each method checks:

-Listing entities:
    simpleListTest()     - with minimal filter parameters
    fullListTest()       - with all filter parameters
    list*EntityName*Test - "catalogue"-entities without parameters

-Saving an entity:    
    simpleSaveTest()     - with minimal parameters
    fullSaveTest()       - with all parameters
    
-Updating an entity:
    simpleUpdateTest()   - with minimal parameters
    fullUpdateTest()     - with all parameters
        
-Getting an entity:
    getTest()            - by ID of entity
```

## Built With

* [Spring Framework](https://spring.io/docs) - Using Core, Boot, BootTest, JPA, JDBC, Web;
* [H2 Database](https://www.h2database.com/html/main.html) - Lightweight in-memory database
* [Maven](https://maven.apache.org/) - Dependency Management

## Authors

* **Igor Churakov** - *Initial work* - [IgorChurakov](https://github.com/IgorChurakov/)

## License

This project is licensed under the MIT License - see the [LICENSE.md](LICENSE.md) file for details
