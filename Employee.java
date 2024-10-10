public abstract class Employee {

    private static int nextEmployeeNumber = 0;
    private final int employeeNumber;
    private String firstName;
    private String lastName;

    /**
     * Constructs an Employee with a first name and last name.
     *
     * @param firstName The first name of the employee.
     * @param lastName The last name of the employee.
     */
    public Employee(String firstName, String lastName) {
        this.employeeNumber = nextEmployeeNumber++;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public int getEmployeeNumber() {
        return employeeNumber;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    /**
     * Returns a string representation of the employee in the format:
     * "Id:{employeeNumber} - {firstName}, {lastName}".
     *
     * @return A string representing the employee.
     */
    @Override
    public String toString() {
        return "Id:" + employeeNumber + " - " + firstName + ", " + lastName;
    }

    /**
     * Abstract method to calculate and return the paycheck amount.
     * This must be implemented by subclasses.
     *
     * @return The paycheck amount.
     */
    public abstract float getPaycheck();
}

/**
 * The SalariedEmployee class represents an employee who is paid a fixed salary.
 * It extends the Employee class and calculates the paycheck as the salary divided by 26.
 */
class SalariedEmployee extends Employee {

    protected float salary;

    /**
     * Constructs a SalariedEmployee with the given first name, last name, and salary.
     *
     * @param firstName The first name of the employee.
     * @param lastName The last name of the employee.
     * @param salary The salary of the employee.
     */
    public SalariedEmployee(String firstName, String lastName, float salary) {
        super(firstName, lastName);
        this.salary = salary;
    }

    public float getSalary() {
        return salary;
    }

    public void setSalary(float salary) {
        this.salary = salary;
    }

    /**
     * Calculates and returns the paycheck amount, which is the salary divided by 26.
     *
     * @return The paycheck amount.
     */
    @Override
    public float getPaycheck() {
        return salary / 26;
    }

    /**
     * Returns a string representation of the salaried employee in the format:
     * "Salaried, Base: ${salary}; Id:{employeeNumber} - {firstName}, {lastName}".
     *
     * @return A string representing the salaried employee.
     */
    @Override
    public String toString() {
        return String.format("Salaried, Base: $%,.0f; Id:%o - %s, %s",
                salary, getEmployeeNumber(), getFirstName(), getLastName());
    }
}

/**
 * The HourlyEmployee class represents an employee who is paid based on the number of hours worked.
 * It extends the Employee class and calculates the paycheck as hours worked multiplied by the hourly rate.
 */
class HourlyEmployee extends Employee {

    private float hours;
    private float rate;

    /**
     * Constructs an HourlyEmployee with the given first name, last name, and hourly rate.
     *
     * @param firstName The first name of the employee.
     * @param lastName The last name of the employee.
     * @param rate The hourly rate of the employee.
     */
    public HourlyEmployee(String firstName, String lastName, float rate) {
        super(firstName, lastName);
        this.rate = rate;
    }

    public float getRate() {
        return rate;
    }

    public void setRate(float rate) {
        this.rate = rate;
    }

    public float getHours() {
        return hours;
    }

    public void setHours(float hours) {
        this.hours = hours;
    }

    /**
     * Calculates and returns the paycheck amount, which is the hours worked multiplied by the hourly rate.
     *
     * @return The paycheck amount.
     */
    @Override
    public float getPaycheck() {
        return hours * rate;
    }

    /**
     * Returns a string representation of the hourly employee in the format:
     * "Hourly: ${rate}; Id:{employeeNumber} - {firstName}, {lastName}".
     *
     * @return A string representing the hourly employee.
     */
    @Override
    public String toString() {
        return String.format("Hourly: $%.2f; Id:%o - %s, %s",
                rate, getEmployeeNumber(), getFirstName(), getLastName());
    }
}

/**
 * The CommissionEmployee class represents an employee who is paid a base salary plus commissions based on units sold.
 * It extends the SalariedEmployee class and calculates the paycheck as base salary divided by 26 plus the commission.
 */
class CommissionEmployee extends SalariedEmployee {

    private int unitsSold;
    private float[][] commissionSchedule;

    /**
     * Constructs a CommissionEmployee with the given first name, last name, salary, and commission schedule.
     *
     * @param firstName The first name of the employee.
     * @param lastName The last name of the employee.
     * @param salary The base salary of the employee.
     * @param schedule The commission schedule of the employee. This is stored as a 2d array
     */
    public CommissionEmployee(String firstName, String lastName, float salary, float[][] schedule) {
        super(firstName, lastName, salary);
        this.commissionSchedule = schedule;
    }

    public int getUnitsSold() {
        return unitsSold;
    }

    public void setUnitsSold(int unitsSold) {
        this.unitsSold = unitsSold;
    }

    public float[][] getCommissionSchedule() {
        return commissionSchedule;
    }

    public void setCommissionSchedule(float[][] commissionSchedule) {
        this.commissionSchedule = commissionSchedule;
    }

    /**
     * Calculates and returns the paycheck amount, which is the base salary divided by 26 plus the commission
     * based on units sold.
     *
     * @return The paycheck amount.
     */
    @Override
    public float getPaycheck() {
        float weekly = salary / 26;
        float value = 0;
        for (int i = 0; i < commissionSchedule[0].length; i++) {
            if (commissionSchedule[0][i] <= unitsSold) {
                value = commissionSchedule[1][i];
            } else {
                break;
            }
        }
        return weekly + (value * unitsSold);
    }

    /**
     * Returns a string representation of the commission employee in the format:
     * "Commission: ${commission} Base: ${salary}; Id:{employeeNumber} - {firstName}, {lastName}".
     *
     * @return A string representing the commission employee.
     */
    @Override
    public String toString() {
        return String.format("Commission: $%,.2f Base: $%,.0f; Id:%o - %s, %s",
                getPaycheck() - salary / 26, salary, getEmployeeNumber(), getFirstName(), getLastName());
    }
}
