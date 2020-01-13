[![Build Status](https://travis-ci.com/rednavis/vaadin-showcase.svg?branch=master)](https://travis-ci.com/rednavis/vaadin-showcase)
[![Codacy Badge](https://api.codacy.com/project/badge/Grade/21e248caf9cf4192ae618eada63469c6)](https://www.codacy.com/gh/rednavis/vaadin-showcase?utm_source=github.com&amp;utm_medium=referral&amp;utm_content=rednavis/vaadin-showcase&amp;utm_campaign=Badge_Grade)
[![License: GPL v3](https://img.shields.io/badge/License-GPLv3-blue.svg)](https://www.gnu.org/licenses/gpl-3.0)


# Showcase of Vaadin features

This application will demonstrate example of proper architecture of Vaadin application based on the following patterns and best practices:
- separation of concerns (server and client side of the application should be explicitly decoupled)
- DRY
- standard Java EE features like JSR-330 (Dependency Injection)
- ...

# Prerequisites
- Intellij IDEA 2019.3 (other IDE should be OK but where not tested)
- Oracle JDK 1.8.X
- Maven 3.6.3

# Setting up IDE
For Intellij IDEA, navigate to Preferences | Build, Execution, Deployment | Compiler | Annotation Processors and turn on `Enable Annotation Processing`.

# How to build 
`mvn install`

# How to launch locally
Type `mvn package tomee:run` from project root or `webapp` module and then navigate to `http://localhost:8080`.

# Continuous Integration
https://travis-ci.com/rednavis/vaadin-showcase

# Quality assurance
https://app.codacy.com/gh/rednavis/vaadin-showcase/dashboard
