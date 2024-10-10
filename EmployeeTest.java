import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * A class that contains unit tests for various types of employees:
 * SalariedEmployee, HourlyEmployee, and CommissionEmployee. Each test verifies
 * the correct calculation of paychecks and the string representation (toString)
 * of the employee details.
 */
class EmployeeTest {

    /**
     * Tests the getPaycheck method of the SalariedEmployee class.
     * Ensures that the paycheck is calculated as salary divided by 26.
     */
    @Test
    void testSalariedEmployeeGetPaycheck() {
        SalariedEmployee emp = new SalariedEmployee("John", "Doe", 52000);
        assertEquals(52000 / 26, emp.getPaycheck());
    }

    /**
     * Tests the toString method of the SalariedEmployee class with one case.
     * Ensures that the string is formatted correctly for a salaried employee.
     */
    @Test
    void testSalariedEmployeeToStringCase1() {
        SalariedEmployee emp = new SalariedEmployee("John", "Doe", 52000);
        String expected = String.format("Salaried, Base: $52,000; Id:%o - John, Doe", emp.getEmployeeNumber());
        assertEquals(expected, emp.toString());
    }

    /**
     * Tests the toString method of the SalariedEmployee class with a second case.
     * Ensures that the string is formatted correctly for a different salaried employee.
     */
    @Test
    void testSalariedEmployeeToStringCase2() {
        SalariedEmployee emp = new SalariedEmployee("Alice", "Brown", 60000);
        String expected = String.format("Salaried, Base: $60,000; Id:%o - Alice, Brown", emp.getEmployeeNumber());
        assertEquals(expected, emp.toString());
    }

    /**
     * Tests the getPaycheck method of the HourlyEmployee class.
     * Ensures that the paycheck is calculated as hours worked multiplied by the hourly rate.
     */
    @Test
    void testHourlyEmployeeGetPaycheck() {
        HourlyEmployee emp = new HourlyEmployee("Jane", "Smith", 25);
        emp.setHours(40);
        assertEquals(25 * 40, emp.getPaycheck());
    }

    /**
     * Tests the toString method of the HourlyEmployee class with one case.
     * Ensures that the string is formatted correctly for an hourly employee.
     */
    @Test
    void testHourlyEmployeeToStringCase1() {
        HourlyEmployee emp = new HourlyEmployee("Jane", "Smith", 25);
        emp.setHours(40);
        String expected = String.format("Hourly: $25.00; Id:%o - Jane, Smith", emp.getEmployeeNumber());
        assertEquals(expected, emp.toString());
    }

    /**
     * Tests the toString method of the HourlyEmployee class with a second case.
     * Ensures that the string is formatted correctly for a different hourly employee.
     */
    @Test
    void testHourlyEmployeeToStringCase2() {
        HourlyEmployee emp = new HourlyEmployee("Tom", "Johnson", 30);
        emp.setHours(35);
        String expected = String.format("Hourly: $30.00; Id:%o - Tom, Johnson", emp.getEmployeeNumber());
        assertEquals(expected, emp.toString());
    }

    /**
     * Tests the getPaycheck method of the CommissionEmployee class with a custom commission schedule.
     * Ensures that the paycheck includes both the base salary and commission based on units sold.
     */
    @Test
    void testCommissionEmployeeGetPaycheck() {
        float[][] commissionSchedule = {{0, 12, 25, 47, 70, 100}, {1, 3.3f, 4.1f, 4.7f, 5.5f, 7}};
        CommissionEmployee emp = new CommissionEmployee("Chris", "Evans", 48000, commissionSchedule);
        emp.setUnitsSold(30);  // Should earn $4.1 * 30 units
        assertEquals((48000f / 26) + (4.1f * 30), emp.getPaycheck());
    }

    /**
     * Tests the toString method of the CommissionEmployee class with a custom commission schedule.
     * Ensures that the string includes both the base salary and commission based on units sold.
     */
    @Test
    void testCommissionEmployeeToString1() {
        float[][] commissionSchedule = {{0, 12, 25, 47, 70, 100}, {1, 3.3f, 4.1f, 4.7f, 5.5f, 7}};
        CommissionEmployee emp = new CommissionEmployee("Chris", "Evans", 48000, commissionSchedule);
        emp.setUnitsSold(30);  // Should use 4.1 as the commission multiplier
        String expected = String.format("Commission: $%.2f Base: $48,000; Id:%o - Chris, Evans",
                (emp.getPaycheck() - 48000f / 26), emp.getEmployeeNumber());
        assertEquals(expected, emp.toString());
    }

    /**
     * Tests the toString method of the CommissionEmployee class with a different commission schedule.
     * Ensures that the string includes both the base salary and commission based on units sold.
     */
    @Test
    void testCommissionEmployeeToString2() {
        float[][] commissionSchedule = {{0, 30, 60, 100, 200, 1000}, {.33f, .55f, .75f, .91f, 1.03f, 1.25f}};
        CommissionEmployee emp = new CommissionEmployee("Taylor", "Swift", 65000, commissionSchedule);
        emp.setUnitsSold(150);  // Should use 1.03 as the commission multiplier
        String expected = String.format("Commission: $%.2f Base: $65,000; Id:%o - Taylor, Swift",
                (emp.getPaycheck() - 65000f / 26), emp.getEmployeeNumber());
        assertEquals(expected, emp.toString());
    }
}

