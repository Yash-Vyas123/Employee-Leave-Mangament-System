// It stores the complete data like Employee id, name, department, email, leave balance//
public class Employee {

    private String employeeId;
    private String employeeName;
    private String department;
    private String email;
    private int availableLeaveBalance;

    public Employee(String employeeId, String employeeName,
                    String department, String email,
                    int availableLeaveBalance) {

        this.employeeId = employeeId;
        this.employeeName = employeeName;
        this.department = department;
        this.email = email;
        this.availableLeaveBalance = availableLeaveBalance;
    }

    public String getEmployeeId() {
        return employeeId;
    }

    public String getEmployeeName() {
        return employeeName;
    }

    public String getDepartment() {
        return department;
    }

    public String getEmail() {
        return email;
    }

    public int getAvailableLeaveBalance() {
        return availableLeaveBalance;
    }

    public void setAvailableLeaveBalance(int balance) {
        if (balance < 0) {
            throw new IllegalArgumentException(
                "Leave balance cannot be negative."
            );
        }

        this.availableLeaveBalance = balance;
    }

    protected String getEmployeeType() {
        return "Employee";
    }

    public void displayEmployee() {
        displayEmployee(false);
    }

    // Method overloading
    public void displayEmployee(boolean showType) {
        System.out.println("Employee ID : " + employeeId);
        System.out.println("Employee Name : " + employeeName);
        System.out.println("Department : " + department);
        System.out.println("Email : " + email);
        System.out.println("Leave Balance : "
                           + availableLeaveBalance);

        if (showType) {
            System.out.println("Employee Type : " + getEmployeeType());
        }
    }
}
