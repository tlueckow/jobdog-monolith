## Jobdog Monolith

The Jobdog Monolith component is the legacy application part in the Codekeepers showcase
"Supporting constant change in Legacy Software".

It has a simple UI to create, edit and delete job postings.
The application stores job postings in a memory database.
A Quartz timer runs every n seconds and publishes not already published job postings to an AWS lambda function.

### Starting the web application

Change directory to app module.

`cd app`

Start the Spring Boot application running on port 8080.

`mvn spring-boot:run`

You can start editing job postings at:

http://localhost:8080

### Preparing the simulator

1. Install Chrome browser on your local machine.
2. Get Chromedriver (version must fit your Chrome browser version) http://chromedriver.storage.googleapis.com/index.html
3. Install Chromedriver on your local machine in `$HOME/bin`

### Starting the simulator

Check the web application is running on port 8080.

In a second shell change directory to simulation module.

`cd simulation`

Build the simulator.

`mvn install`

Start the simulator for 10 posts (default is 5).

`mvn exec:java mvn exec:java -Dexec.args="10"`

## Dockerize web application 

```
cd app
mvn install
docker build .
```

## Run docker image locally
```
docker images
docker run -p 8080:8080 <IMAGE ID>
```

## Deploy to AWS fargate

1. Get fargate CLI tool from https://github.com/awslabs/fargatecli

### AWS deployment and running

```
export AWS_PROFILE=<YOUR_AWS_PROFILE> 
fargate task run jobdog --security-group-id sg-0707ec94eb1300cc6 --region eu-central-1
```

### Find out IP address and connect via browser

```
fargate task info jobdog --region eu-central-1
http://<IP>:8080
```

### Watch logfile

1. Get and install saw from https://github.com/TylerBrock/saw

```
saw watch /fargate/task/app
```

### Monitor logfile

1. Get and install kmon from https://github.com/rwirdemann/kmon

```
saw watch /fargate/task/app > /tmp/joblog.log 
kmon -logfile /tmp/jobdog.log
```

### Stop the container

```
fargate task stop jobdog --region eu-central-1 
```