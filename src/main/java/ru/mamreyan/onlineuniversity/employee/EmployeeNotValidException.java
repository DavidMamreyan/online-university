package ru.mamreyan.onlineuniversity.employee;

class EmployeeNotValidException extends RuntimeException {
    public EmployeeNotValidException(Employee employee) {
        super("Employee is not valid: " + employee.toString());
    }
}