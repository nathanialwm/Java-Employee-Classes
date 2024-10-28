import java.util.*;
public class Payroll {
    // Create reference to user input through Scanner class
    static Scanner input = new Scanner(System.in);

    /**
     * Displays a menu with options to manage employee operations and captures user input to execute the chosen operation.
     * The menu includes options to create an employee, search by last name, display an employee by ID, run payroll, or quit.
     * Process:
     * 1. Prints a menu with numbered choices:
     *    - 1: Create an Employee
     *    - 2: Search employee by last name
     *    - 3: Display employee by ID
     *    - 4: Run Payroll
     *    - 5: Quit
     * 2. Prompts the user to enter a choice and handles input as an integer.
     *    - Input is parsed from a string to avoid `Scanner.nextInt` issues.
     *    - If input is invalid, displays an error and calls `menu()` again.
     * 3. Executes the chosen operation via a switch statement:
     *    - 1: Calls `addEmployee()` to create a new employee.
     *    - 2: Calls `searchLastName()` to find employees by last name.
     *    - 3: Calls `employeeByID()` to display an employee by ID.
     *    - 4: Calls `runPayroll()` to execute payroll operations.
     *    - 5: Exits the menu.
     * Error Handling:
     * - Catches non-integer input errors and prompts the user to enter a valid choice.
     * - Recursively calls `menu()` for invalid input, including any unlisted choice.
     * Dependencies:
     * - Methods `addEmployee()`, `searchLastName()`, `employeeByID()`, and `runPayroll()`.
     * - `input`: A `Scanner` instance for capturing user input.
     */
    public static void menu() {
        int choice = 0;
        System.out.print("""
                
                1. Create an Employee
                2. Search employee by last name
                3. Display employee by ID
                4. Run Payroll
                5. Quit
                """);
        //Change input into Integer. Handled this way because Scanners nextInt type error is more difficult to deal with
        try {
            choice = Integer.parseInt(input.nextLine());
        } catch (Exception e) {
            System.out.println("Invalid input type");
            menu();
        }
        switch(choice) {
            case 1:
                addEmployee();
                menu();
            case 2:
                searchLastName();
                menu();
            case 3:
                employeeByID();
                menu();
            case 4:
                runPayroll();
                menu();
            case 5:
                break;
            default:
                System.out.println("Invalid input");
                menu();
        }
    }

    /**
     * Prompts the user to add a new employee by providing details interactively via console input.
     * Depending on the employee type (Salaried, Commissioned, Hourly), additional information such
     * as salary, hourly wage, or commission schedule is requested and validated.
     * Process:
     * 1. Requests the employee's name as "first last".
     * 2. Asks for the employee type: "salaried", "commissioned", or "hourly".
     * 3. Based on type, prompts for:
     *    - "hourly": Hourly wage input as a float.
     *    - "salaried": Annual salary input as a float.
     *    - "commissioned": Commission schedule and base salary.
     *       - For commission schedule, user specifies units sold and value per unit.
     *       - The schedule is organized in a 2D array and displayed after input.
     * 4. Finally, creates a new employee instance with provided details and adds it to the employee list.
     * Validations:
     * - Ensures proper name format or allows 'q' to quit.
     * - Ensures valid input for employee type and numeric fields (wage, salary, commission values).
     * Error Handling:
     * - Catches invalid formats and displays appropriate error messages to the console.
     * - Restarts commission schedule input if "q" is entered midway.
     * Dependencies:
     * - `employeeList`: A list to store created employee instances.
     * - `CommissionEmployee`: Represents commissioned employees.
     * - `input`: A Scanner instance for capturing user input.
     */
    public static void addEmployee() {

        //Initialize empty variables for while loops
        String firstN = "";
        String lastN = "";
        String empType = "";
        float salaryWage = 0;
        float hourlyWage = 0;
        float[][] schedule ={};

        while (firstN.isEmpty() && lastN.isEmpty()) {
            //Prompt user for new employee name
            System.out.println("Enter employee name: 'first last', type q to exit");
            String newName = input.nextLine();
            /*
            Check input against regex pattern
            patterns for quit: "q", "q word", "word q"
            patterns for name: "first last"
            This check will allow for single character first or last names, so take care to enter properly
             */
            if (newName.equals("q")) {
                menu();
            } else if (newName.matches("^[A-Za-z-]+ [A-Za-z-]+$")) {
                //Split strings by space and store them locally
                firstN = newName.split("\\s+")[0];
                lastN = newName.split("\\s+")[1];
            } else {
                System.out.println("Your input wasn't valid. The format should be 'first last' or 'q' to quit");
            }
        }

        while (empType.isEmpty()) {
            //Prompt user for employee type
            System.out.println("Enter employee pay type (Salaried, Commissioned, Hourly): ");
            String newType = input.nextLine().toLowerCase();
            /*
            Check input against regex pattern
            Pattern checks for salaried or commissioned or hourly
             */
            if (newType.matches("salaried|commissioned|hourly")) {
                empType = newType;
            } else {
                System.out.println("Your input wasn't valid.");
            }
        }
        // Get pay rate information based on pay type
        // Hourly
        if (empType.equals("hourly")) {
            while (hourlyWage == 0) {
                System.out.println("Enter the employee's hourly wage: ");
                String newWage = input.nextLine();
                try {
                    hourlyWage = Float.parseFloat(newWage);
                } catch (Exception e) {
                    System.out.println("Invalid input type");
                }
            }
        } //Salary
        if (empType.equals("salaried")) {
            while (salaryWage == 0) {
                System.out.println("Enter the employee's annual salary: ");
                String newWage = input.nextLine();
                try {
                    salaryWage = Float.parseFloat(newWage);
                } catch (Exception e) {
                    System.out.println("Invalid input type");
                }
            }
        } //commission
        if (empType.equals("commissioned")) {
            //initialize local variables for user input and counter
            String unitSold = "";
            String valPer = "";
            int counter = 0;
            System.out.println("Enter the commission schedule first. If you make a mistake enter 'q' and press enter until the loop restarts");
            while (salaryWage == 0) {
                // Set size of schedule
                System.out.println("How many rows will you be entering?");
                while (counter == 0) {
                    try {
                        counter = Integer.parseInt(input.nextLine());
                    } catch (Exception e) {
                        System.out.println("Invalid input type");
                    }
                }
                schedule = new float[2][counter];
                //Get commission schedule
                for (int i = 0; i < counter; i++) {
                    System.out.printf("Enter units sold for row %o. Enter 'q' to quit", i+1);
                    unitSold = input.nextLine();
                    if (unitSold.equals("q") || valPer.equals("q")) {
                        //Reset variable values and end loop
                        unitSold = "";
                        valPer = "";
                        counter = 0;
                        break;
                    } else {
                        System.out.printf("Enter value per units sold for row %o.", i+1);
                        valPer = input.nextLine();
                        try {
                            schedule[0][i] = Float.parseFloat(unitSold);
                            schedule[1][i] = Float.parseFloat(valPer);
                        } catch (Exception e) {
                            System.out.println("Invalid input type");
                        }
                    }
                }
                //Get salary
                System.out.println("Enter the employee's annual salary: ");
                String newWage = input.nextLine();
                try {
                    salaryWage = Float.parseFloat(newWage);
                } catch (Exception e) {
                    System.out.println("Invalid input type");
                }
                //System.out.println(Arrays.deepToString(schedule));
            }
            employeeList.add(new CommissionEmployee(firstN, lastN, salaryWage, schedule));
            //System.out.printf(String.valueOf(employeeList.getLast()));
        }
    }

    /**
     * Prompts the user to search for employees by last name. The method sorts the employee list by last name,
     * then performs a sequential search to find all employees with a matching last name.
     * Process:
     * 1. Prompts the user to enter a last name to search for. The input is case-insensitive.
     * 2. Allows the user to type "q" to return to the main menu.
     * 3. Validates the input to ensure it contains only alphabetic characters and hyphens.
     * 4. If valid, the method:
     *    - Sorts the `employeeList` by last name.
     *    - Searches the list for all employees with the specified last name.
     * 5. If matches are found, prints each matching employee's first name, last name, and employee number.
     *    If no matches are found, displays a message indicating no results.
     * Dependencies:
     * - `EmployeeSorter.quicksort()`: Sorts the employee list by last name.
     * - `EmployeeSearch.sequentialSearchByLastName()`: Performs a search for employees by last name.
     * - `menu()`: Returns to the main menu if the user inputs "q".
     * Error Handling:
     * - Validates the input format to allow only alphabetic characters and hyphens.
     * throws IOException If an input/output error occurs while reading user input.
     */
    public static void searchLastName() {
        System.out.println("Enter last name to search for");
        String userIn = input.nextLine().toLowerCase();
        if (userIn.equals("q")) {
            menu();
        } else if (userIn.matches("^[A-Za-z-]+")) {
            EmployeeSorter.quicksort(employeeList, 0, employeeList.size() - 1, false);
            List<Employee> foundEmployees = EmployeeSearch.sequentialSearchByLastName(employeeList, userIn);

            if (!foundEmployees.isEmpty()) {
                System.out.println("Employees with last name " + userIn + ":");
                for (Employee employee : foundEmployees) {
                    System.out.println(employee.getFirstName() + " " + employee.getLastName() + " " + employee.getEmployeeNumber());
                }
            } else {
                System.out.println("No employees found with last name " + userIn);
            }
        } else {
            System.out.println("Invalid input");
            searchLastName();
        }
    }

    /**
     * Prompts the user to search for an employee by their ID. This method sorts the employee list by ID
     * and then performs a binary search to find an employee with the specified ID.
     * Process:
     * 1. Prompts the user to enter an employee ID. Input is expected as a numeric string.
     * 2. Allows the user to type "q" to return to the main menu.
     * 3. Validates the input format to ensure it contains only numeric characters.
     * 4. If valid:
     *    - Sorts the `employeeList` by employee ID.
     *    - Searches for the specified employee ID using binary search.
     * 5. If a match is found, displays the employee’s details. If no match is found, displays an error message.
     * Error Handling:
     * - Checks for non-numeric input and recursively prompts the user to enter valid input.
     * Dependencies:
     * - `EmployeeSorter.quicksort()`: Sorts the employee list by ID before the search.
     * - `EmployeeSearch.binarySearchByID()`: Performs the binary search for the specified employee ID.
     * - `menu()`: Returns to the main menu if the user inputs "q".
     */
    public static void employeeByID() {
        System.out.println("Enter employee number to search for");
        String userIn = input.nextLine();
        if (userIn.equals("q")) {
            menu();
        } else if (userIn.matches("[0-9]+")) {
            EmployeeSorter.quicksort(employeeList, 0, employeeList.size() - 1, true);
            Employee foundEmployee = EmployeeSearch.binarySearchByID(employeeList, Integer.parseInt(userIn));

            if (foundEmployee != null) {
                System.out.println(foundEmployee.toString());
            } else {
                System.out.println("No employee found with ID " + Integer.parseInt(userIn));
            }
        } else {
            System.out.println("Invalid input");
            employeeByID();
        }
    }

    /**
     * Executes the payroll process by prompting the user to input hours worked for each hourly employee
     * and units sold for each commission-based employee. After collecting the required data, it sorts
     * the employees by paycheck amount in descending order.
     * Process:
     * 1. Displays instructions to the user about the input requirements for each employee type.
     * 2. Iterates through the `employeeList`, checking each employee's type:
     *    - For `HourlyEmployee`: Prompts the user to enter hours worked and updates the employee's hours.
     *    - For `CommissionEmployee`: Prompts the user to enter units sold and updates the employee's units sold.
     * 3. If an invalid input is entered (e.g., non-numeric), the method restarts from the beginning.
     * 4. After successful input for all employees, sorts `employeeList` by paycheck in descending order.
     * Error Handling:
     * - Catches invalid input formats for hours and units sold (e.g., non-numeric values).
     * - If an error occurs during input, the method restarts, requiring the user to re-enter all data.
     * Dependencies:
     * - `employeeList`: The list of all employees.
     * - `sortByPaycheck(employeeList)`: Sorts the list of employees by paycheck amount in descending order.
     * Warnings:
     * - Any error in input will restart the method, requiring all inputs to be re-entered from scratch.
     */
    public static void runPayroll() {
        System.out.print("""
                You will need to fill in hours for each hourly employee,
                and units sold for each commission employee.
                Take Care! An error will require restarting the payroll method
                
                """);
        for (Employee employee : employeeList) {
            if (employee instanceof HourlyEmployee hourlyEmployee) {
                System.out.printf("How many hours did %s %s work?", employee.getFirstName(), employee.getLastName());
                try {
                    hourlyEmployee.setHours(Float.parseFloat(input.nextLine()));
                } catch (Exception e) {
                    runPayroll();
                }
            }
            else if (employee instanceof CommissionEmployee commissionEmployee) {
                System.out.printf("How many units did %s %s sell?", employee.getFirstName(), employee.getLastName());
                try {
                    commissionEmployee.setUnitsSold(Integer.parseInt(input.nextLine()));
                } catch (Exception e) {
                    runPayroll();
                }
            }
        }
        EmployeeSorter.sortByPaycheck(employeeList);
        System.out.println("End of Payroll");
    }
    // Create a list of employees to use in sorting and display functions
    static List<Employee> employeeList = new ArrayList<>();
    static {
        employeeList.add(new CommissionEmployee("Eric", "Wilson", 65000, new float[][]{{10, 100, 200, 400}, {.5f, 1.2f, 2f, 3f}}));
        employeeList.add(new CommissionEmployee("Sarah", "Johnson", 67000, new float[][]{{20, 150, 300, 500}, {0.6f, 1.5f, 2.2f, 3.1f}}));
        employeeList.add(new CommissionEmployee("Liam", "Baker", 62000, new float[][]{{30, 120, 250, 450}, {0.8f, 1.3f, 2.0f, 3.2f}}));
        employeeList.add(new CommissionEmployee("Emma", "Hughes", 69000, new float[][]{{15, 130, 280, 420}, {0.7f, 1.4f, 2.1f, 3.0f}}));
        employeeList.add(new CommissionEmployee("Noah", "Davis", 66000, new float[][]{{25, 160, 290, 460}, {0.9f, 1.6f, 2.3f, 3.4f}}));
        employeeList.add(new CommissionEmployee("Olivia", "Martin", 64000, new float[][]{{10, 140, 260, 410}, {0.5f, 1.2f, 2.1f, 2.8f}}));
        employeeList.add(new CommissionEmployee("James", "Walker", 70000, new float[][]{{20, 100, 300, 600}, {0.6f, 1.0f, 2.5f, 3.5f}}));
        employeeList.add(new CommissionEmployee("Sophia", "Hall", 71000, new float[][]{{15, 110, 210, 310}, {0.7f, 1.1f, 1.8f, 2.6f}}));
        employeeList.add(new CommissionEmployee("Mason", "Young", 68000, new float[][]{{20, 90, 230, 370}, {0.9f, 1.2f, 2.0f, 2.9f}}));
        employeeList.add(new CommissionEmployee("Isabella", "Scott", 65000, new float[][]{{10, 100, 200, 400}, {0.5f, 1.3f, 1.9f, 2.7f}}));
        employeeList.add(new CommissionEmployee("Benjamin", "Green", 66000, new float[][]{{20, 120, 270, 450}, {0.6f, 1.4f, 2.2f, 3.3f}}));
        employeeList.add(new CommissionEmployee("Amelia", "Adams", 72000, new float[][]{{15, 110, 250, 480}, {0.8f, 1.5f, 2.4f, 3.1f}}));
        employeeList.add(new SalariedEmployee("Nathan", "Diamond", 122000));
        employeeList.add(new SalariedEmployee("Lily", "Turner", 87000));
        employeeList.add(new SalariedEmployee("Jack", "Peterson", 94000));
        employeeList.add(new SalariedEmployee("Sophia", "Ross", 102000));
        employeeList.add(new SalariedEmployee("Ethan", "Morris", 110000));
        employeeList.add(new SalariedEmployee("Ava", "Reed", 96000));
        employeeList.add(new SalariedEmployee("William", "Nguyen", 115000));
        employeeList.add(new SalariedEmployee("Chloe", "Kim", 98000));
        employeeList.add(new SalariedEmployee("Lucas", "Adams", 99000));
        employeeList.add(new SalariedEmployee("Ella", "Parker", 105000));
        employeeList.add(new SalariedEmployee("Daniel", "Long", 93000));
        employeeList.add(new SalariedEmployee("Emily", "Hernandez", 92000));
        employeeList.add(new SalariedEmployee("Mia", "Ramirez", 101000));
        employeeList.add(new SalariedEmployee("Matthew", "Carter", 108000));
        employeeList.add(new SalariedEmployee("Zoe", "Murphy", 97000));
        employeeList.add(new SalariedEmployee("James", "Bailey", 112000));
        employeeList.add(new SalariedEmployee("Grace", "Wright", 107000));
        employeeList.add(new SalariedEmployee("Samuel", "Bell", 95000));
        employeeList.add(new SalariedEmployee("Victoria", "Ross", 103000));
        employeeList.add(new SalariedEmployee("Henry", "Sanders", 100000));
        employeeList.add(new HourlyEmployee("Jessica", "Mason", 17.5f));
        employeeList.add(new HourlyEmployee("Oliver", "Brooks", 17.0f));
        employeeList.add(new HourlyEmployee("Isabella", "Lee", 23.5f));
        employeeList.add(new HourlyEmployee("Ethan", "Martinez", 16.5f));
        employeeList.add(new HourlyEmployee("Ava", "Gonzalez", 35.0f));
        employeeList.add(new HourlyEmployee("Mason", "Harris", 21.75f));
        employeeList.add(new HourlyEmployee("Sophia", "Clark", 18.25f));
        employeeList.add(new HourlyEmployee("Liam", "Walker", 40.0f));
        employeeList.add(new HourlyEmployee("Charlotte", "Hall", 15.5f));
        employeeList.add(new HourlyEmployee("Lucas", "Young", 27.0f));
        employeeList.add(new HourlyEmployee("Amelia", "King", 19.5f));
        employeeList.add(new HourlyEmployee("Logan", "Wright", 45.0f));
        employeeList.add(new HourlyEmployee("Mia", "Lopez", 30.0f));
        employeeList.add(new HourlyEmployee("Benjamin", "Hill", 22.5f));
        employeeList.add(new HourlyEmployee("Scarlett", "Scott", 33.5f));
        employeeList.add(new HourlyEmployee("Henry", "Green", 25.0f));
        employeeList.add(new HourlyEmployee("Emily", "Adams", 37.25f));
        employeeList.add(new HourlyEmployee("Michael", "Nelson", 28.75f));
        employeeList.add(new HourlyEmployee("Ella", "Baker", 15.25f));
        employeeList.add(new HourlyEmployee("Alexander", "Hall", 42.0f));
    }
    // main method
    public static void main (String[] args) {
        menu();
    }
}
