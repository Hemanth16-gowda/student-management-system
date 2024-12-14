# Student Management System

This is a Student Management System built using Java and JDBC for performing CRUD operations (Create, Read, Update, Delete) on student records.

## Features

- **Insert Record**: Allows the addition of new student records into the database.
- **Display Record**: Displays a student's details based on their ID.
- **Update Record**: Enables updating a student's details (Name, Class, Marks, Gender).
- **Delete Record**: Allows deleting a student record by their ID.
- **Customized Display**: Lets you display selected details (ID, Name, Class, Marks, Gender) of a student based on their ID.

## Database

The project uses a MySQL database named `KU_db` with a `Studentdetails` table having the following columns:

| Column Name | Data Type       | Description |
|-------------|-----------------|-------------|
| `ID`        | `INT`           | Unique identifier for each student. |
| `Name`      | `VARCHAR(100)`  | Full name of the student. |
| `Class`     | `VARCHAR(10)`   | The class of the student (e.g., A1, A2, etc.). |
| `Marks`     | `INT`           | Marks obtained by the student (0-100). |
| `Gender`    | `VARCHAR(10)`   | Gender of the student (e.g., Male, Female, Other). |

## Technologies Used

- **Java**
- **JDBC (Java Database Connectivity)**
- **MySQL**

## How to Run

1. Clone the repository to your local machine:
   ```bash
   git clone https://github.com/Hemanth16-gowda/student-management-system.git
