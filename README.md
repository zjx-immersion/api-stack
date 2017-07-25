
# API Stack

[![Build Status](https://travis-ci.org/zjx-immersion/api-stack.svg?branch=master)](https://travis-ci.org/zjx-immersion/api-stack)

## Capabilities todo list in API Stack

### V1
 
 - [x] API Test support with alone api test source set
 - [X] API Test with Rest-assured
 - [x] Spring boot with actuator for simple health check and metrics
 - [X] Test coverage report and verification with Jacoco
 - [X] IDE feature support like git、java
 - [ ] Project folder with multi level structure
 - [X] Config support multi ENV with spring profile
 - [ ] Config content reading with unify mode  (eg:@ConfigurationProperties)
 - [X] Check Style support with customized config
 - [ ] ORM support: JAP with local env connect H2 
 - [ ] JAP with Dev env connect Mysql 7 and multi name rule persistence operation in JPA Respostory
 - [ ] DB managements like Flyway
 - [ ] Object mapping abstract with Orika
 - [X] JUnit Test Support - Include Spring Test Starter(Already Include Junit) and Mockito 
 - [ ] Junit with JUnitParams
 - [X] Log Aspect and Logback format for multi ENV
 - [ ] API request exception handling strategy and record them in log in public framework
 - [ ] API Error request unify response formation
 - [X] CORS simple Support
 - [ ] Request & Response gzip support
 - [ ] API quick access UI support: Swagger ui 
 - [ ] Security and Authentication support with Auth 2.0 and JWT and Spring Security
 - [X] API /info with production ready info (like: version, name, build number etc.)
 - [X] CI process like Jenkins pipeline
 - [X] Remote debug support
 - [ ] Cache support
 - [ ] Internationalization (i18n)
 - [ ] The unify way to call dependence service
 - [ ] API version support
 - [ ] Rest api playload standard building like JaonAPI
 - [ ] Build jar in container
 - [X] Containerized the env API server
 - [ ] Containerized the env DB 
 - [ ] File upload and download support like Image
 - [ ] Hot Reload when doing some updates
 
 ### V2
 - [ ] CORS Support including client validation
 - [ ] Customer Driven Contract Testing - (Tool advice: spring boot contract or Pact)
 - [ ] Ansible deploy process strategy
 - [ ] Containerized building and running with newest Docker multi build feature
 
 ## Contribution
 if you wang to pick any item upon, please click [Board](https://github.com/zjx-immersion/api-stack/projects/1), and choose the corresponding crad which exist in todo list.
 When you start a card, please convert this card to a issue and create a separated branch for implementation, then create a pull request for merging to master.