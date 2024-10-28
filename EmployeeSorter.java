import java.util.List;


/**
 * Utility class providing sorting methods for a list of `Employee` objects.
 * Contains implementations for quicksort and selection sort algorithms, allowing
 * sorting by various criteria including employee ID, last name, and paycheck amount.
 */
public class EmployeeSorter {

    /**
     * Sorts the `employeeList` using the quicksort algorithm based on the specified criterion.
     *
     * @param employeeList The list of employees to be sorted.
     * @param low The starting index for the quicksort algorithm.
     * @param high The ending index for the quicksort algorithm.
     * @param sortByID If true, sorts the list by employee ID. If false, sorts by last name (first name as a tiebreaker).
     */
    public static void quicksort(List<Employee> employeeList, int low, int high, boolean sortByID) {
        if (low < high) {
            int pivotIndex = partition(employeeList, low, high, sortByID);
            quicksort(employeeList, low, pivotIndex - 1, sortByID);
            quicksort(employeeList, pivotIndex + 1, high, sortByID);
        }
    }

    /**
     * Helper method for `quicksort` to partition the list around a pivot element.
     * This method reorders elements in place and returns the index of the pivot.
     *
     * @param employeeList The list of employees to partition.
     * @param low The starting index for partitioning.
     * @param high The ending index for partitioning.
     * @param sortByID If true, partitions the list by employee ID. If false, partitions by last name.
     * @return The index of the pivot after partitioning.
     */
    private static int partition(List<Employee> employeeList, int low, int high, boolean sortByID) {
        Employee pivot = employeeList.get(high);
        int i = low - 1;

        for (int j = low; j < high; j++) {
            if (shouldSwap(employeeList.get(j), pivot, sortByID)) {
                i++;
                swap(employeeList, i, j);
            }
        }

        swap(employeeList, i + 1, high);
        return i + 1;
    }

    /**
     * Determines whether two employees should be swapped based on the sorting criterion.
     *
     * @param e1 The first employee for comparison.
     * @param pivot The pivot employee used for comparison.
     * @param sortByID If true, compares by employee ID. If false, compares by last name and first name.
     * @return True if `e1` should be swapped with the pivot, based on the sorting order.
     */
    private static boolean shouldSwap(Employee e1, Employee pivot, boolean sortByID) {
        if (sortByID) {
            // Sort by employee ID
            return e1.getEmployeeNumber() < pivot.getEmployeeNumber();
        } else {
            // Sort by last name (and first name as a tiebreaker)
            int lastNameComparison = e1.getLastName().compareTo(pivot.getLastName());
            if (lastNameComparison < 0) {
                return true;
            } else if (lastNameComparison == 0) {
                // If last names are the same, compare first names
                return e1.getFirstName().compareTo(pivot.getFirstName()) < 0;
            }
            return false;
        }
    }

    /**
     * Swaps two elements in the `employeeList`.
     *
     * @param employeeList The list containing the elements to swap.
     * @param i The index of the first element.
     * @param j The index of the second element.
     */
    private static void swap(List<Employee> employeeList, int i, int j) {
        Employee temp = employeeList.get(i);
        employeeList.set(i, employeeList.get(j));
        employeeList.set(j, temp);
    }

    /**
     * Sorts the `employeeList` by paycheck amount in descending order using the selection sort algorithm.
     * After sorting, it prints each employee's last name, first name, and paycheck amount in aligned columns.
     *
     * @param employeeList The list of employees to be sorted by paycheck.
     */
    public static void sortByPaycheck(List<Employee> employeeList) {
        int s = employeeList.size();

        // Selection sort algorithm
        for (int i = 0; i < s - 1; i++) {
            int maxIndex = i;

            // Find the employee with the highest paycheck in the remaining unsorted list
            for (int j = i + 1; j < s; j++) {
                if (employeeList.get(j).getPaycheck() > employeeList.get(maxIndex).getPaycheck()) {
                    maxIndex = j;
                }
            }

            // Swap the found maximum element with the first element
            Employee temp = employeeList.get(maxIndex);
            employeeList.set(maxIndex, employeeList.get(i));
            employeeList.set(i, temp);
        }

        // Print the employees in descending order of paycheck
        for (Employee employee : employeeList) {
            System.out.printf("%-20s %10s%n",
                    employee.getLastName() + ", " + employee.getFirstName(),
                    String.format("$%,.2f", employee.getPaycheck()));
        }
    }

}

