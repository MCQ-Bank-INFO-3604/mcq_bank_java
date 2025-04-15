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

---

## Testing
The project includes unit and integration tests for the following components:

Questions: Tests for creating, deleting, and managing questions.
Exams: Tests for creating, deleting, and managing exams.
Courses: Tests for creating, deleting, and managing courses.
Test files are located in the src/test/java/com/example directory.
