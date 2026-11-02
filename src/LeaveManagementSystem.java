
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class LeaveManagementSystem {

    private List<Employee> employees = new ArrayList<>();
    private Set<String> employeeEmails = new HashSet<>();
    private Map<String, Employee> employeeMap = new HashMap<>();
    private List<LeaveRequest> leaveRequests = new ArrayList<>();

    private int nextRequestNumber = 1001;

    // Add a new employee
    public void addEmployee(Employee employee) {

    	
        if (employee.getAvailableLeaveBalance() < 0) {
            throw new IllegalArgumentException(
                "Leave balance cannot be negative."
            );
        }

        if (employeeMap.containsKey(employee.getEmployeeId())) {
            throw new IllegalArgumentException(
                "Duplicate employee ID."
            );
        }

        if (employeeEmails.contains(employee.getEmail())) {
            throw new IllegalArgumentException(
                "Duplicate employee email."
            );
        }

        employees.add(employee);
        employeeMap.put(employee.getEmployeeId(), employee);
        employeeEmails.add(employee.getEmail());

        System.out.println("Employee added successfully.");
    }

    // Display all employees using Iterator
    public void displayAllEmployees() {

        if (employees.isEmpty()) {
            System.out.println("No employees found.");
            return;
        }

        Iterator<Employee> iterator = employees.iterator();

        while (iterator.hasNext()) {
            Employee employee = iterator.next();
            employee.displayEmployee(true);
            System.out.println("----------------------------");
        }
    }

    // Search for an employee
    public Employee searchEmployee(String employeeId)
            throws EmployeeNotFoundException {

        Employee employee = employeeMap.get(employeeId);

        if (employee == null) {
            throw new EmployeeNotFoundException(
                "Employee " + employeeId + " does not exist."
            );
        }

        return employee;
    }

    // Apply for leave
    public LeaveRequest applyLeave(String employeeId,
                                   LeaveRequest.LeaveType leaveType,
                                   int numberOfDays,
                                   String reason)
            throws EmployeeNotFoundException,
                   InsufficientLeaveException {

        if (numberOfDays <= 0) {
            throw new IllegalArgumentException(
                "Leave days must be greater than 0."
            );
        }

        Employee employee = searchEmployee(employeeId);

        if (numberOfDays >
                employee.getAvailableLeaveBalance()) {

            throw new InsufficientLeaveException(
                "Insufficient leave balance. Available Leave: "
                + employee.getAvailableLeaveBalance()
                + ", Requested Leave: " + numberOfDays
            );
        }

        String requestId = "LR" + nextRequestNumber++;

        LeaveRequest request = new LeaveRequest(
            requestId, employeeId, leaveType,
            numberOfDays, reason
        );

        leaveRequests.add(request);

        System.out.println("Leave request created successfully.");
        System.out.println("Request ID: " + requestId);
        System.out.println("Status: " + request.getStatus());

        return request;
    }

    // Find a leave request by its ID
    private LeaveRequest findLeaveRequest(String requestId) {

        for (LeaveRequest request : leaveRequests) {
            if (request.getRequestId().equals(requestId)) {
                return request;
            }
        }

        throw new IllegalArgumentException(
            "Leave request " + requestId + " does not exist."
        );
    }

    // Approve a leave request
    public void approveLeave(String requestId)
            throws EmployeeNotFoundException {

        LeaveRequest request = findLeaveRequest(requestId);

        if (request.getStatus() != LeaveRequest.Status.PENDING) {
            throw new IllegalArgumentException(
                "This request has already been processed."
            );
        }

        Employee employee = searchEmployee(request.getEmployeeId());

        int previousBalance = employee.getAvailableLeaveBalance();
        int days = request.getNumberOfDays();

        employee.setAvailableLeaveBalance(previousBalance - days);
        request.setStatus(LeaveRequest.Status.APPROVED);

        System.out.println("Leave Request Approved.");
        System.out.println("Employee ID : " + employee.getEmployeeId());
        System.out.println("Previous Balance : " + previousBalance);
        System.out.println("Leave Used : " + days);
        System.out.println("Remaining Leave : "
                           + employee.getAvailableLeaveBalance());
    }

    // Reject a leave request
    public void rejectLeave(String requestId) {

        LeaveRequest request = findLeaveRequest(requestId);

        if (request.getStatus() != LeaveRequest.Status.PENDING) {
            throw new IllegalArgumentException(
                "This request has already been processed."
            );
        }

        request.setStatus(LeaveRequest.Status.REJECTED);

        System.out.println("Leave request rejected successfully.");
        System.out.println("Employee leave balance remains unchanged.");
    }

    // Display all leave requests
    public void displayAllLeaveRequests() {

        if (leaveRequests.isEmpty()) {
            System.out.println("No leave requests found.");
            return;
        }

        for (LeaveRequest request : leaveRequests) {
            request.displayLeaveRequest();
        }
    }

    // Display the leave balance of an employee
    public void displayLeaveBalance(String employeeId)
            throws EmployeeNotFoundException {

        Employee employee = searchEmployee(employeeId);

        System.out.println("Employee ID : " + employee.getEmployeeId());
        System.out.println("Employee Name : " + employee.getEmployeeName());
        System.out.println("Available Leave Balance : "
                           + employee.getAvailableLeaveBalance());
    }
}
