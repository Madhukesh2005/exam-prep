package com.example.Mahathi;

public class GradeCalculator {

    public static String calculateGrade(int marks) {
        if (marks >= 90) {
            return "A";
        } else if (marks >= 80) {
            return "B";
        } else if (marks >= 70) {
            return "C";
        } else {
            return "F";
        }
    }

    public static void main(String[] args) {
        int marks = 95;
        String grade = calculateGrade(marks);

        System.out.println("Marks : " + marks);
        System.out.println("Grade : " + grade);
    }
}
