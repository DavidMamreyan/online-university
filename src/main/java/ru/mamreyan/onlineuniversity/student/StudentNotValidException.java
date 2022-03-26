package ru.mamreyan.onlineuniversity.student;

class StudentNotValidException extends RuntimeException {
    public StudentNotValidException(Student student) {
        super("Student is not valid: " + student.toString());
    }
}