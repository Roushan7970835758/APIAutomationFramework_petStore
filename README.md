## API Automation Framework – PetStore

Automation framework for the [Swagger PetStore Users API](https://petstore.swagger.io), built with Java, Maven, TestNG, and Rest Assured. It supports both conventional tests and data‑driven tests (via Excel), generates Extent HTML reports, and logs execution using Log4j2.

### Key Features
- **Tech stack**: Java 11+, Maven, TestNG, Rest Assured
- **Reporting**: ExtentReports HTML under `reports/`
- **Logging**: Log4j2 with rolling logs under `logs/`
- **Data‑driven testing**: Excel (`testData/Userdata.xlsx`) via Apache POI
- **Configurable endpoints**: Hardcoded (`Routes.java`) or externalized (`routes.properties`)

### Project Structure
```
APIAutomationFramework_petStore/
  src/test/java/api/
    endpoints/         # Route constants and endpoint wrappers
    payload/           # POJOs (e.g., User)
    test/              # TestNG test classes
    utilities/         # Data providers, reporting, Excel utils
  src/test/resources/  # log4j2 config, routes.properties
  testData/            # Userdata.xlsx for data-driven tests
  reports/             # Extent HTML reports
  logs/                # Execution logs
  pom.xml              # Maven build & dependencies
  testng.xml           # Test suite (with Extent listener)
```

### Prerequisites
- Java 11 or later (set `JAVA_HOME`)
- Maven 3.8+ (`mvn -v` to verify)

### Install Dependencies
```bash
mvn clean compile
```

### How to Run Tests
- **Recommended (uses TestNG suite and Extent listener):**
```bash
mvn -P Regression test
```

- **Run a specific test class (without suite):**
```bash
mvn -Dtest=api.test.UserTest test
```
Replace `api.test.UserTest` with `api.test.UserTest2`, `api.test.UserTest3`, or `api.test.DataDrivenTest` as needed.

### Data‑Driven Tests (Excel)
- File: `testData/Userdata.xlsx`
- Sheet name: `Sheet1`
- Expected columns (row 0 as headers, data from row 1):
  - `userID`, `userName`, `fname`, `lname`, `useremail`, `pwd`, `ph`
- Providers in `api.utilities.DataProviders`:
  - `@DataProvider(name="Data")` → full row data for create/update
  - `@DataProvider(name="UserNames")` → only the `userName` column

### Choosing Endpoint Configuration
- `api.endpoints.userEndPoints` uses static URLs from `api.endpoints.Routes`.
- `api.endpoints.userEndPoints2` reads URLs from `src/test/resources/routes.properties`.

`routes.properties` keys:
```properties
post_url=https://petstore.swagger.io/v2/user
get_url=https://petstore.swagger.io/v2/user/{username}
update_url=https://petstore.swagger.io/v2/user/{username}
delete_url=https://petstore.swagger.io/v2/user/{username}
```

### Reports & Logs
- **Extent report**: generated at `reports/PetStore_Report.html` (name may include timestamp depending on config)
- **Logs**: `logs/automation.log` (configured in `src/test/resources/log4j2.xml`)
- **TestNG outputs**: `test-output/` (HTML and XML)

### Notable Classes
- `api.test.UserTest` – CRUD flow with randomized data (Faker) using `userEndPoints`
- `api.test.UserTest2` – Data‑driven CRUD using `userEndPoints2` (properties‑based)
- `api.test.UserTest3` – CRUD flow using `userEndPoints2`
- `api.test.DataDrivenTest` – Create/Delete using Excel providers
- `api.utilities.ExtentReportManager` – TestNG listener wiring ExtentReports
- `api.utilities.XLUtility` – Excel helper via Apache POI

### Common Maven Commands
```bash
# Clean previous artifacts
mvn clean

# Run full suite with reporting
mvn -P Regression test

# Run a single class
mvn -Dtest=api.test.UserTest test
```

### Troubleshooting
- The public PetStore API can be rate‑limited or briefly unavailable; sporadic 4xx/5xx responses may occur.
- Ensure `testData/Userdata.xlsx` exists and matches expected columns and sheet name.
- If reports don’t generate, make sure you’re running via `testng.xml` (use the `Regression` Maven profile) so the Extent listener is active.

### CI/CD
Configure your CI (e.g., Jenkins) to run:
```bash
mvn clean -P Regression test
```
Archive `reports/*.html`, `test-output/**`, and `logs/automation.log` as build artifacts.

### License
This project is provided as‑is for learning and internal usage.