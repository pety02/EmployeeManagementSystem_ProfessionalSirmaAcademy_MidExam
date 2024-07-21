# Employee Management System

## Description

This Employee Management System provides functionalities for adding, removing, terminating, updating employee data, and searching employees through a console-based user interface implemented in Java. Data is stored and read from CSV files. The project includes three primary classes: `Employee`, `Department`, and `Company`.

## Classes

### `Employee`

- **ID**: A unique identifier for the employee, which is automatically incremented.
- **First Name**: The first name of the employee.
- **Last Name**: The last name of the employee.
- **Start Date**: The date when the employee started working.
- **End Date**: The date when the employee was terminated (if applicable).
- **Department**: The department where the employee works.
- **Role**: The role of the employee within the company.
- **Salary**: The salary of the employee.
- **Termination Flag**: A boolean field indicating whether the employee has been terminated.

### `Department`

- **ID**: A unique identifier for the department.
- **Name**: The name of the department.
- **Employee List**: A list of employees working in the department.
- **Company**: The company to which the department belongs.

### `Company`

- **ID**: A unique identifier for the company.
- **Name**: The name of the company.
- **Department List**: A list of departments belonging to the company.

### Enum `Role`

- Defines roles and allows for easier extension of roles/positions within the company.

## Features

- **Add Employees**: Ability to add new employees to the system.
- **Remove Employees**: Ability to remove employees by marking them as `isFired` and setting an end date.
- **Terminate Employees**: Mark employees as terminated and set an end date.
- **Update Employee Data**: Ability to update existing employee data.
- **Search Employees**: Search by ID, name, or department name.
- **Search Departments and Companies**: The project will include features for adding, updating, and deleting companies, as well as searching among all available entities in the future.

## CSV Support

The project includes functionality for reading from and writing to CSV files:

- **Reader**: Reads data from a CSV file and converts it into the corresponding class (`Employee`, `Department`, `Company`).
- **Writer**: Writes data from the classes into a CSV file.

## Validation

The application validates user input and provides feedback on incorrect data. It offers a user-friendly console interface for interacting with the user and waits for valid input.

## Converters

The project provides converters that transform objects from the `Employee`, `Department`, and `Company` classes into CSV format and vice versa.

## Installation and Usage

1. Clone the repository:
   ```bash
   git clone <https://github.com/pety02/EmployeeManagementSystem_ProfessionalSirmaAcademy_MidExam/tree/master>
