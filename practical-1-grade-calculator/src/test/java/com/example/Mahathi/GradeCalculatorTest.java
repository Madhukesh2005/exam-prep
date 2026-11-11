package com.example.Mahathi;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class GradeCalculatorTest {

    @Test
    void testGradeA() {
        assertEquals("A", GradeCalculator.calculateGrade(90));
    }

    @Test
    void testGradeB() {
        assertEquals("B", GradeCalculator.calculateGrade(80));
    }

    @Test
    void testGradeC() {
        assertEquals("C", GradeCalculator.calculateGrade(70));
    }
}
