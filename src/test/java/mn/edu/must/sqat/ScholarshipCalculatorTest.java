package mn.edu.must.sqat;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ScholarshipCalculatorTest {
    private final ScholarshipCalculator calculator = new ScholarshipCalculator();

    @Test
    void letterGrade_highScore_returnsA() {
        assertEquals("A", calculator.letterGrade(95));
    }

    @Test
    void gpa_twoPassingScores_returnsAveragePoints() {
        assertEquals(3.5, calculator.gpa(new double[]{95, 85}), 0.000001);
    }

    @Test
    void scholarship_highGpaAndEnoughCredits_returns100() {
        assertEquals(100, calculator.scholarship(3.9, 16, false));
    }

    @Test
    void scholarship_midGpaAndEnoughCredits_returns70() {
        assertEquals(70, calculator.scholarship(3.5, 12, false));
    }

    @Test
    void scholarship_eligibleGpa_returns50() {
        assertEquals(50, calculator.scholarship(3.1, 10, false));
    }
}
