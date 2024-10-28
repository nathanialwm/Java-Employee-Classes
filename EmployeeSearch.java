import java.util.ArrayList;
import java.util.List;


/**
 * Utility class providing search methods for a list of `Employee` objects.
 * Includes sequential and binary search algorithms for finding employees by last name or ID.
 */
public class EmployeeSearch {

    /**
     * Performs a sequential search to find all employees with the specified last name in a sorted list.
     * Stops searching once it surpasses the target last name, leveraging the sorted order.
     *
     * @param employeeList The list of employees, sorted by last name, to search through.
     * @param lastName The last name to search for, case-insensitive.
     * @return A list of employees with the specified last name, or an empty list if no matches are found.
     */
    public static List<Employee> sequentialSearchByLastName(List<Employee> employeeList, String lastName) {
        List<Employee> matchingEmployees = new ArrayList<>();

        for (Employee employee : employeeList) {
            int comparison = employee.getLastName().toLowerCase().compareTo(lastName);

            if (comparison == 0) {
                // Found a matching last name, add to the list
                matchingEmployees.add(employee);
            } else if (comparison > 0) {
                // Since the list is sorted, we can stop once we pass the target last name
                break;
            }
        }

        return matchingEmployees; // Return all employees with the specified last name
    }

    /**
     * Performs a binary search to find a single employee by their unique employee ID in a sorted list.
     * Assumes that `employeeList` is sorted in ascending order by employee ID.
     *
     * @param employeeList The list of employees, sorted by employee ID, to search through.
     * @param targetID The employee ID to search for.
     * @return The employee with the specified ID, or null if no employee with the ID is found.
     */
    public static Employee binarySearchByID(List<Employee> employeeList, int targetID) {
        int low = 0;
        int high = employeeList.size() - 1;

        while (low <= high) {
            int mid = low + (high - low) / 2;
            Employee midEmployee = employeeList.get(mid);

            if (midEmployee.getEmployeeNumber() == targetID) {
                return midEmployee; // Target ID found
            } else if (midEmployee.getEmployeeNumber() < targetID) {
                low = mid + 1; // Search in the right half
            } else {
                high = mid - 1; // Search in the left half
            }
        }

        return null; // Target ID not found
    }
}
