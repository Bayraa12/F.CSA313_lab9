package mn.edu.must.sqat;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

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

    @ParameterizedTest(name = "score={0}, grade={1}")
    @CsvSource({"85, B", "75, C", "65, D", "55, F"})
    void letterGrade_otherValidScores_returnsExpectedGrade(double score, String expected) {
        assertEquals(expected, calculator.letterGrade(score));
    }

    @Test
    void letterGrade_negativeScore_throws() {
        assertThrows(IllegalArgumentException.class, () -> calculator.letterGrade(-0.01));
    }

    @Test
    void letterGrade_scoreAbove100_throws() {
        assertThrows(IllegalArgumentException.class, () -> calculator.letterGrade(100.01));
    }

    @Test
    void gpa_lowerGrades_returnsAveragePoints() {
        assertEquals(1.0, calculator.gpa(new double[]{75, 65, 55}), 0.000001);
    }

    @Test
    void gpa_nullScores_throws() {
        assertThrows(IllegalArgumentException.class, () -> calculator.gpa(null));
    }

    @Test
    void gpa_emptyScores_throws() {
        assertThrows(IllegalArgumentException.class, () -> calculator.gpa(new double[]{}));
    }

    @Test
    void scholarship_negativeGpa_throws() {
        assertThrows(IllegalArgumentException.class,
                () -> calculator.scholarship(-0.1, 16, false));
    }

    @Test
    void scholarship_gpaAbove4_throws() {
        assertThrows(IllegalArgumentException.class,
                () -> calculator.scholarship(4.1, 16, false));
    }

    @Test
    void scholarship_negativeCredits_throws() {
        assertThrows(IllegalArgumentException.class,
                () -> calculator.scholarship(3.9, -1, false));
    }

    @Test
    void scholarship_violation_returns0() {
        assertEquals(0, calculator.scholarship(3.9, 16, true));
    }

    @Test
    void scholarship_highGpaLowCredits_returns70() {
        assertEquals(70, calculator.scholarship(3.9, 14, false));
    }

    @Test
    void scholarship_midGpaLowCredits_returns50() {
        assertEquals(50, calculator.scholarship(3.5, 11, false));
    }

    @Test
    void scholarship_lowGpa_returns0() {
        assertEquals(0, calculator.scholarship(2.9, 10, false));
    }

    @Test
    void gpa_zeroElements_throwsForEmptyAndNullScores() {
        assertAll(
                () -> assertThrows(IllegalArgumentException.class,
                        () -> calculator.gpa(new double[]{})),
                () -> assertThrows(IllegalArgumentException.class,
                        () -> calculator.gpa(null)));
    }

    @Test
    void gpa_oneElement_returnsItsGradePoints() {
        assertEquals(4.0, calculator.gpa(new double[]{95}), 0.000001);
    }

    @Test
    void gpa_multipleElements_returnsAverageAcrossAllScores() {
        assertEquals(2.5, calculator.gpa(new double[]{95, 85, 75, 65}), 0.000001);
    }

    @ParameterizedTest(name = "score={0}, grade={1}")
    @CsvSource({
            "60, D",
            "59.99, F",
            "69.99, D",
            "0, F",
            "100, A",
            "89.99, B",
            "90, A"
    })
    void letterGrade_boundaryScores_matchesSpecification(double score, String expected) {
        assertEquals(expected, calculator.letterGrade(score));
    }
}
