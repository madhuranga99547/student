# Student Management System

A simple, interactive Java console application for managing student records using a MySQL database.

## Features

- Add new students with validation (name, email, phone, major, GPA)
- View all students in a formatted table
- Search students by name
- View student details by ID
- Update student information
- Delete student records
- MySQL database integration with JDBC

## Prerequisites

- **Java JDK 8+**
- **MySQL Server**
- **MySQL JDBC Driver** (`mysql-connector-j-9.4.0.jar` included in `lib/`)

## Setup Instructions

### 1. Clone or Download the Project

Place all files in a folder.

### 2. Set Up the Database

1. Start your MySQL server.
2. Open a MySQL client (e.g. MySQL Workbench, command line).
3. Run the SQL script to create the database and table:
   ```sql
   SOURCE path/to/db.sql;
   ```
   Or copy-paste the contents of `db.sql` into your MySQL client.

### 3. Configure Database Connection

- Edit `src/DatabaseConnection.java` if needed to match your MySQL username, password, and connection URL.

### 4. Compile the Project

Open PowerShell in your project folder and run:

```powershell
javac -cp lib\mysql-connector-j-9.4.0.jar -d out src\*.java
```

### 5. Run the Application

```powershell
java -cp "out;lib\mysql-connector-j-9.4.0.jar" StudentManagementSystem
```

## Usage

- Follow the on-screen menu to add, view, update, search, or delete students.
- All inputs are validated for correctness.
- Data is stored in the MySQL database.

## Project Structure

```
Kosala/
│
├── db.sql                   # SQL script to set up database and tables
├── lib/
│   └── mysql-connector-j-9.4.0.jar  # MySQL JDBC driver
├── src/
│   ├── DatabaseConnection.java      # Handles DB connection
│   ├── InputValidator.java          # Validates user input
│   ├── Student.java                 # Student entity class
│   ├── StudentDAO.java              # Data access object for students
│   └── StudentManagementSystem.java # Main application (entry point)
└── out/                     # Compiled .class files
```

## Customization

- Change database credentials in `DatabaseConnection.java` as needed.
- Modify validation rules in `InputValidator.java` for custom requirements.

## License

This project is for educational purposes.

## Database Configuration

Before running the application, make sure to update your database credentials in `src/DatabaseConnection.java`:

```
private static final String URL = "jdbc:mysql://<DB_HOST>:<DB_PORT>/<DB_NAME>";
private static final String USERNAME = <DB_USER_NAME>;
private static final String PASSWORD = <DB_PASSWORD>;
```

Set `USERNAME` and `PASSWORD` to match your MySQL setup.

## What Does This App Do?

This Student Management System is a console-based Java application that allows you to:
- Add new students with validation for name, email, phone, major, and GPA
- View all students in a formatted table
- Search students by name
- View student details by ID
- Update student information
- Delete student records
- All data is stored and managed in a MySQL database using JDBC

The app provides a simple menu-driven interface for easy management of student records.
