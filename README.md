# Project Title
	Expense Tracker

## Group Members
	- Espanto, Mitchie T.
	- Polancos, Philippe Andre L.
	- Dumasig, Jasper Reden B.
	- Villegas, Albert Matthew V.
	- Diaquino, Dan Emmanuel T.
    - Sumabal, Jimrich Lawrence G.

## Project Description
Expense Tracker is a desktop application developed using JavaFX that allows users to record, manage, and monitor their daily expenses. The system helps users organize their spending by categories and track their financial habits over time.

## Proposed Features
	- Add and delete Transaction records
    - Add, Edit, Delete Accounts and Categories
    - Bind Categories to an Account
    - Bind Transactions to an Account and a Category bound to that account
	- Categorize Transactions (e.g., Food, Transport, School)
	- View all expenses in a table format
	- Display total expenses and monthly summaries
    - Provide Useful Insights on Transaction Data
    - Export/Import Data for Exploratory Data Analysis
    - Filter Transactions based on their respective Categories and Accounts

## Planned Technologies
	- Java
	- JavaFX
	- JDBC
	- Database (SQLite/MySQL)

## Object-Oriented Programming (OOP)
The system is designed using object-oriented principles. The main classes include:
- User
- Account
- Transaction
- Category

## Graphical User Interface (GUI)
The application uses JavaFX with FXML to create a user-friendly graphical interface. It includes forms, tables, and controls for managing expenses and budgets.

## UML Diagrams
The project includes the following diagrams:
- Use Case Diagram to represent user interactions
- Class Diagram to show system structure, relationships, and inheritance

## Design Patterns
The project uses Singleton Pattern for managing a single database connection and Model-View-Controller for predictable code architecture

## Criteria Fulfillment

### Object-Oriented Programming Principles (15%)
- class BaseService extends from parent class Database which handles database connectivity and all other Services extend from BaseService. class BaseService's one singular purpose is to handle session between screens to make sure default operations such as instantiating a default category and a default account happens so we don't run into errors.

### Java Generics (10%)
- database queries such as "getAllTransactions", "getAllCategories", "getAllAccounts" use Lists to return data and expandableLists on the front-end side such as "incomeListView" and "expenseListView" accept a List of Hbox from pre-loaded data and also Pie charts use HashMap data from List of data classes "Category" and "Transaction".

### Multithreading and Concurrency (10%)
- function "exportCsv" from the OptionService class uses multi-threading to speed-up the process.

### Graphical User Interface (15%)
- search and filter operations use eventListeners to re-render the local list with desired results.

### Database Connectivity (15%)
- each table is a service (UserService,TransactionService,AccountService,CategoryService) with one unique Service for import/export (OptionService). most are able to do full CRUD operations and some are constraint to their functionalities

### Unified Modeling Language (10%)
- UML Class Diagram and Use Case Diagram
- Consistency between UML and code

### Design Patterns (10%)
- the architecture is Model-View-Presenter as it is recommended for javafx and the design pattern is mainly a Singleton for database operations

### Code Quality and Documentation (15%)
- Clean, modular, well-documented code
- Naming conventions and coding standards
- code is camel-case compliant as per Object Oriented Programming standards and functions / variables are constraint to 3 words maximum to state their purpose
