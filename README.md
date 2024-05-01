# SpareSti API

## Description
The API backend of sparesti.app. SpareSti is designed to make saving fun. The app is integrated with your online bank, therefore it has an overview of what your money is being spent on and can provide you with personalized saving tips based on this information. The app is suitable for all saving goals and offers motivation and tips tailored to your desires. Since we know that saving money can be difficult, SpareSti automatically deposits money into your savings account when you complete challenges. Based on your saved funds, the feed will give you personalized tips on how your money can be invested, and you will be able to set up a budget that provides you with the overview you need to make informed choices.

## Links

- **Frontend**: [https://gitlab.stud.idi.ntnu.no/idatt2106-2024-07/frontend](https://gitlab.stud.idi.ntnu.no/idatt2106-2024-07/frontend)
- **Jacoco Test Coverage**: [https://backend-idatt2106-v24-7-120575f97d1f5ac63c49fe399f685f116b780a1.pages.stud.idi.ntnu.no/jacoco/index.html](https://backend-idatt2106-v24-7-120575f97d1f5ac63c49fe399f685f116b780a1.pages.stud.idi.ntnu.no/jacoco/index.html)
- **Site Reports**: [https://backend-idatt2106-v24-7-120575f97d1f5ac63c49fe399f685f116b780a1.pages.stud.idi.ntnu.no/project-info.html](https://backend-idatt2106-v24-7-120575f97d1f5ac63c49fe399f685f116b780a1.pages.stud.idi.ntnu.no/project-info.html)

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

## Contributors
The individuals who contributed to the project:
- Anders Høvik
- Andreas Kluge Svendsrud 
- Henrik Dybdal 
- Henrik Teksle Sandok 
- Jens Christian Aanestad 
- Victor Kaste 
- Viktor Grevskott