# SpareSti API

## Description
The API backend of sparesti.app. SpareSti is designed to make saving fun. The app is integrated with your online bank, therefore it has an overview of what your money is being spent on and can provide you with personalized saving tips based on this information. The app is suitable for all saving goals and offers motivation and tips tailored to your desires. Since we know that saving money can be difficult, SpareSti automatically deposits money into your savings account when you complete challenges. Based on your saved funds, the feed will give you personalized tips on how your money can be invested, and you will be able to set up a budget that provides you with the overview you need to make informed choices.

## Links
- **Website**: [https://sparesti.org/](https://sparesti.org/login)
- **Frontend**: [https://gitlab.stud.idi.ntnu.no/idatt2106-2024-07/frontend](https://gitlab.stud.idi.ntnu.no/idatt2106-2024-07/frontend)
- **Jacoco Test Coverage**: [https://backend-idatt2106-v24-7-120575f97d1f5ac63c49fe399f685f116b780a1.pages.stud.idi.ntnu.no](https://backend-idatt2106-v24-7-120575f97d1f5ac63c49fe399f685f116b780a1.pages.stud.idi.ntnu.no)

## Getting Started

### 1. Clone SpareSti:

#### Clone with SSH:
```bash
git@gitlab.stud.idi.ntnu.no:idatt2106-2024-07/backend.git
```
#### Clone with HTTPS:
```bash
https://gitlab.stud.idi.ntnu.no/idatt2106-2024-07/backend.git
```

### 2. Install dependencies
```bash
mvn clean install
```

### 3. Run the application
```bash
mvn spring-boot:run 
```

## Docker
How to run the application with docker. **NOTE:** You need to have docker pre-installed: [Click here](https://docs.docker.com/get-docker/)

### 1. Build the Docker image
```bash
docker build -t your-image-name .
```

### 2. Verify the image was created
```bash
docker images
```

### 3. Run the Docker container:
```bash
docker run -d --name your-container-name -p 8080:8080 your-image-name
```

## Tests
### 1. Run tests
```bash
mvn clean test
```

### 2. Site Reports
Reports giving general information about the application
```bash
mvn site
```

### 3. Jacoco Coverage Report
Gets generated when running mvn clean test and are located in:
```bash
/target/site/jacoco/index.html
```
## Database
To configure the database go to **src/main/resources/application.yml**

Here is the current configuration:
```yaml
spring:
  datasource:
    url: jdbc:mysql://129.241.98.39:3306/sparesti
    username: user
    password: Password1.
```
To configure the test database go to **src/test/resources/application.yml**

Here is the current configuration:
```yaml
spring:
  datasource:
    url: jdbc:mysql://129.241.98.39:3306/sparesti_test
    username: user
    password: Password1.
```
## Contributors
The individuals who contributed to the project:
- Anders Høvik
- Andreas Kluge Svendsrud 
- Henrik Dybdahl Berg
- Henrik Teksle Sandok 
- Jens Christian Aanestad 
- Victor Kaste 
- Viktor Grevskott