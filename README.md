[![Build Status](https://travis-ci.com/rednavis/vaadin-showcase.svg?branch=master)](https://travis-ci.com/rednavis/vaadin-showcase)
[![Codacy Badge](https://api.codacy.com/project/badge/Grade/21e248caf9cf4192ae618eada63469c6)](https://www.codacy.com/gh/rednavis/vaadin-showcase?utm_source=github.com&amp;utm_medium=referral&amp;utm_content=rednavis/vaadin-showcase&amp;utm_campaign=Badge_Grade)
[![Maintainability](https://api.codeclimate.com/v1/badges/eb18be5f089efbefe1c5/maintainability)](https://codeclimate.com/github/rednavis/vaadin-showcase/maintainability)
[![Test Coverage](https://api.codeclimate.com/v1/badges/eb18be5f089efbefe1c5/test_coverage)](https://codeclimate.com/github/rednavis/vaadin-showcase/test_coverage)
[![codecov](https://codecov.io/gh/rednavis/vaadin-showcase/branch/master/graph/badge.svg)](https://codecov.io/gh/rednavis/vaadin-showcase)
[![License: GPL v3](https://img.shields.io/badge/License-GPLv3-blue.svg)](https://www.gnu.org/licenses/gpl-3.0)


# Showcase of Vaadin features

This application will demonstrate example of proper architecture of Vaadin application based on the following patterns and best practices:
- separation of concerns (server and client side of the application should be explicitly decoupled)
- DRY
- standard Java EE features like JSR-330 (Dependency Injection)
and many others.

# Prerequisites
- Intellij IDEA or Eclipse EE
- Oracle JDK 1.8.X
- Maven 3.6.3

# How to contribute
Please go through [Contribution Guide](http://bit.ly/2sx6BKz)

# Setting up IDE
For Intellij IDEA:
- navigate to the Preferences | Build, Execution, Deployment | Compiler | Annotation Processors and turn on `Enable Annotation Processing`
- navigate to the Preferences | Plugins, open the Marketplace tab, install Lombok Plugin by Michail Plushnikov

# How to build 
`mvn install`

# How to launch locally
Type `mvn package tomee:run` from project root or `webapp` module and then navigate to `http://localhost:8080`.

# Unit testing 
We need to keep coverage of all logic by Unit tests between above 80%. We don't need to add Unit tests for Vaadin UI classes, such as Views and others.

# Integration testing 
By example of `PostgreSqlDbTest.class`, you can see how we can use `testcontainers` and `docker` for integration testing.

# Continuous Integration
https://travis-ci.com/rednavis/vaadin-showcase

# Quality assurance
[Codacy](https://app.codacy.com/gh/rednavis/vaadin-showcase/dashboard)
[DeepCode](https://www.deepcode.ai/app/gh/rednavis/vaadin-showcase/e3030bc66806e770256e0435586ec621733d39c0/_/dashboard/)
