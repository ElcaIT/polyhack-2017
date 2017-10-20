# (Micro-)Service Kickstarter

This kickstarter application demonstrates:

 * how to build a simple *REST* backend using **Spring Boot**
 * how to build a simple frontend using **React**
 * optional: how to bundle the backend and frontend together into an executable "fat" `.jar`
 * optional: how to setup a project environment that can be shared with colleagues (provided self-contained **Maven** and **Node** environments) 

## Prerequisites

### Development Environment Setup (optional)
> If you don't want to perform the steps below but still want to profit from the maven wrapper you can run it from the root project directory by issuing `./mvnw` or `./mvnw.cmd` respectively.

A set of utility methods is provided in the `etc/shell/additional_bash_profile.sh` file that conveniently allow you to work with the **Maven Wrapper**. If you wish to use those:
 
 1. Copy the `.bash_profile` to your *nix home directory (**Cygwin** home dir on *Windows*) - the last two lines are relevant
 1. Copy the `cygwin.bat` (*Windows*) or `darwin.sh` (*macOS*) file to a directory outside of the repository and customize it. This file is the entry point to the project-specific environment
 1. Execute the `.bat` or `.sh` script 

### Development tools
Any development tool - *IntelliJ*, *Eclipse*, *Visual Studio Code*... - can be used with the project. For *IntelliJ* the frontend development is strongly recommended. The following plugins should be installed to support the development:

* ReactJS
* NodeJS

Additionally, as we use [Lombok](https://projectlombok.org/)(an annotation processor generating boilerplate getters/setters/... for classes) please install the [Lombok plugin](https://plugins.jetbrains.com/plugin/6317-lombok-plugin) for IntelliJ or simply get rid ofall **Lombok** annotations and replace them with the equivalent cocde.

## Working with the project 

### Directory/Project layout
Currently the following folders are present:

* `etc` - containing environment specific configuration and support files
* `code` - contains the application code
* `code/backend` - contains the *Backend* code
* `code/frontend` - contains the *Frontend* code
* `release/spring-boot` - contains the *Spring Boot .jar* release (which is executable as well)

### Coding
### Swagger Specification
The *REST* interface provided by this application are documented using *Swagger 2.0* and are accessible by pointing your browser to [http://localhost:8080/swagger-ui.html](http://localhost:8080/swagger-ui.html) and to [http://localhost:8080/v2/api-docs](http://localhost:8080/v2/api-docs) for the formal specification after the backend application has been started.

### Database Access
The backend features an embedded [h2 Database](http://www.h2database.com/html/main.html). This database can be accessed at 
[http://localhost:8080/h2-console/](http://localhost:8080/h2-console/). The access parameters are the following

* Driver class: `'org.h2.Driver'`
* JDBC URL: `'jdbc:h2:mem:testdb'`
* Username: `'sa'`
* Password: `''`

## Building
### General
The entire application can be build by running the following command in the project root directory:

    mvn install

### Spring Boot
The *Spring Boot Application* can be build by invoking the following maven command in the `code/backend` directory:

    mvn install

The application is released using the standard *maven-release-plugin*.

### React
The *npm* build is completely integrated into the maven build. Thus, the *React* application can be build by invoking the following command in the `code/frontend` directory:

    mvn install
    
Note that maven installs and uses a local installation of *Node* and *npm* (`code/frontend/node`) and does not use any global installation.
    
## Execution
### General
The complete application (backend + frontend) can be executed in two modes:
1. Executing the *Spring Boot Application* and the *React* application separately. This is recommended during development.
2. Executing the generated `.jar` file, which contains the backend **and** the frontend. In this mode the *React* application will be served from the *Spring 
Boot* server as static content. This is how the complete application could be run in a cloud environment. 

### Spring Boot
#### IDE
Execute the single `main` method provided in the code within your IDE.

### Spring Boot and React
#### Maven
The service can be started using maven by executing the following command in the `release/spring-boot` directory:

    mvn spring-boot:run
    
This executes the generated `.jar` file in an embedded *Tomcat* sever.

#### Command Line
The service can be started on the command line by issuing the following command after the application has been built:

    java -jar release/spring-boot/target/*.jar

...alternatively the generated `.jar` file can be copied into a web container like *Tomcat*.

### React
#### General
For executing the *React* application the environment provides an (*webpack*) development server which is reachable under [http://localhost:3000/](http://localhost:3000/). The *React* application is based on the [Create React App Starter](https://github.com/facebookincubator/create-react-app). The server will watch the files under `code/frontend/src` and automatically recompile if something changes.

For development the connection to the backend server the development server contains a proxy, which is configured to redirect calls to `/api/*` to the *Spring Boot Application*. Note that this is configured in `package.json` which is automatically used when using `npm start`. More information can be found [here](https://github.com/facebookincubator/create-react-app/blob/master/packages/react-scripts/template/README.md#proxying-api-requests-in-development)

#### IDE / Command Line
The development server can be started using npm by executing the following command in the `code/frontend` directory:

    npm start

Note that this uses the globally installed Node and npm version. If the Node version differs too much from the locally installed Node version in the maven environment, Node will complain about it.

#### Maven
The development server can be started using maven (and thus Node and npm provided by the maven environment). For this the following command must be executed in the `code/frontend` directory:

    mvn frontend:npm
    
**IMPORTANT:** When terminating the execution with `CTRL + C`, the Node process is not terminated and must be killed manually (e.g. task manager). This is an known issue with the *frontend-maven-plugin*. According to [issue 41](https://github.com/eirslett/frontend-maven-plugin/issues/41) it should be fixed but this is not true for the current version (1.5) running in our environment.

## Using this kickstarter
### Frontend Only
If you'd like to start with a frontend only application you have two options which are quite similar:
 1. Copy out all the code from `code/frontend`
 1. Follow the instructions on[Create React App](https://github.com/facebookincubator/create-react-app)
 
In both cases you need to have *NodeJS* installed on your machine. A good way to install *NodeJS* on your machine is:

- Unix: [NVM](https://github.com/creationix/nvm)
- Windows:[NVM for Windows](https://github.com/coreybutler/nvm-windows)

Follow the instructions on these pages. *NVM* allows you to also manage different version of *NodeJS* on you machine.

### Backend and frontend
If you'd like you can clone and reuse this project and adapt it to your needs (throw away non-needed domain classes / modify the frontend / etc). Of course you can also only use this project as a reference or not at all.
