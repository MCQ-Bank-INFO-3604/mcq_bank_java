# MCQ Bank Java Application

This project is a Java-based desktop application designed to store multiple-choice questions (MCQs) and quickly create exams. It follows the Model-View-Controller (MVC) design pattern. The user interface was built with NetBeans, and SQLite was used as the database.

---

## Features

- **Question Management and Browsing**: Create, update, and delete questions. Browse and filter previously added questions via a tagging system.
- **Exam Management and Browsing**: Create and manage exams with associated questions. Export exams in different formats.
- **Course Management**: Manage courses, topics, and subtopics.
- **Database Integration**: Uses SQLite for persistent storage.
- **PDF Generation**: Generate PDF files for exams using iTextPDF.
- **Unit and Integration Tests**: Comprehensive tests for models and controllers using JUnit.

---

## Prerequisites

- **Java**: Ensure you have Java 17 or later installed.
- **Maven**: Used for dependency management and running tests.
- **SQLite**: The database used for storing application data.

---

## Setup Instructions

1. **Clone the Repository**:
   ```bash
   git clone <repository-url>
   cd mcq_bank_java/demo

2. **Build the Project:**
   Use Maven to build the project:
   ```mvn clean install

3. **Run the Application:**
   You can run the application by executing the App class:
   ```mvn exec:java -Dexec.mainClass="com.example.App"

4. **Run Tests:**
   Execute the unit and integration tests using Maven:
   ```mvn test



---

## Testing
The project includes unit and integration tests for the following components:

Questions: Tests for creating, deleting, and managing questions.
Exams: Tests for creating, deleting, and managing exams.
Courses: Tests for creating, deleting, and managing courses.
Test files are located in the src/test/java/com/example directory.

---

## Dependencies
The project uses the following dependencies:

JUnit: For unit and integration testing.
SQLite JDBC: For database connectivity.
FlatLaf: For UI styling.
iTextPDF: For generating PDF reports.
Dependencies are managed via Maven. See the pom.xml file for details.

---

## Database
The application uses an SQLite database (mcq_bank.db) with the following tables:

questions
exams
courses
topics
subtopics
Ensure the database file is accessible in the project directory.
