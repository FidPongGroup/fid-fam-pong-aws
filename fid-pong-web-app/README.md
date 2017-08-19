# fid-pong-web-app

- Yes I know the name needs work

Here is the actual code for our Web App.  We're going to start small with all of our code consolidated into a Spring Boot Application.

Therefore all Server-side java code, and client-side HTML/CSS/Javascript will be here

This app is designed to be relatively simple at first, while we ge the infracturcture and CI/CD sorted out, we can then add more functionality

### The Tech stack

#### Spring Boot
 - This application will be written in Java 8 using the Spring Boot Framework
 - Spring Boot is the modern generation of Spring, it has stopped using XML in favor on annotation based configuration
 - A Spring Boot Application compiles in a Jar file that is then fully executable.  no Tomcat Server installation

#### Hibernate
 - We'll be Using an Object-Relational-Mapper (ORM) to read and write to our MySQL DB
 - This will allow us to easily link our Java POJOs to a Relational Table without with minimal code
 - Hibernate has great Spring support, and integrates with Multiple Backends, so the DB type can be changed later

#### Thymeleaf
 - A great way to template HTML in Spring

#### Getting Started
 - Configure your applicaiton.properties file with the endpoint and credentials for the DB, then run the main method
 - Go to localhost:8080/games or localhost:8080/player and see what comes back.

### Current goals

- Display list of ping pong scores, and relevant statistical data
    - We'll start with reading through the app, then writing
- Do this securely via HTTPS

### Future goals
 - To be implemented after CI/CD and infrastructure are solid, this an AWS learning activity after all

- separate Services from Presentation

- Look into a completely serverless model (microservices via AWS Lambda)

- Be hipster as fuck and write in Ruby and Node.js

[Click here to learn more about Spring Boot](https://projects.spring.io/spring-boot/)

[Click here to learn more about AWS Lambda](https://aws.amazon.com/lambda/?nc2=h_m1)

[Click here to learn about being hipster as fuck](http://i.imgur.com/gyU0me4.png)
