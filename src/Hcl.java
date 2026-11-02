
import java.util.Scanner;

public class Hcl {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        LeaveManagementSystem system = new LeaveManagementSystem();

        boolean running = true;

        while (running) {

            System.out.println("\n===== EMPLOYEE LEAVE MANAGEMENT SYSTEM =====");
            System.out.println("1. Add Employee");
            System.out.println("2. Display All Employees");
            System.out.println("3. Search Employee");
            System.out.println("4. Apply Leave");
            System.out.println("5. Approve Leave");
            System.out.println("6. Reject Leave");
            System.out.println("7. Display All Leave Requests");
            System.out.println("8. Display Employee Leave Balance");
            System.out.println("9. Exit");
            System.out.print("Enter your choice: ");

            try {
                int choice = Integer.parseInt(sc.nextLine().trim());

                switch (choice) {

                    case 1:
                        System.out.print("Enter Employee ID: ");
                        String id = sc.nextLine().trim();

                        System.out.print("Enter Employee Name: ");
                        String name = sc.nextLine().trim();

                        System.out.print("Enter Department: ");
                        String department = sc.nextLine().trim();

                        System.out.print("Enter Email: ");
                        String email = sc.nextLine().trim();

                        System.out.print("Enter Leave Balance: ");
                        int balance = Integer.parseInt(
                            sc.nextLine().trim()
                        );

                        if (id.isEmpty() || name.isEmpty()
                                || department.isEmpty()
                                || email.isEmpty()) {
                            throw new IllegalArgumentException(
                                "Employee fields cannot be empty."
                            );
                        }

                        if (balance < 0) {
                            throw new IllegalArgumentException(
                                "Leave balance cannot be negative."
                            );
                        }

                        System.out.println("1. Permanent");
                        System.out.println("2. Contract");
                        System.out.print("Select employee type: ");

                        int type = Integer.parseInt(
                            sc.nextLine().trim()
                        );

                        Employee employee;

                        if (type == 1) {
                            employee = new PermanentEmployee(
                                id, name, department, email, balance
                            );
                        } else if (type == 2) {
                            employee = new ContractEmployee(
                                id, name, department, email, balance
                            );
                        } else {
                            throw new IllegalArgumentException(
                                "Invalid employee type."
                            );
                        }

                        system.addEmployee(employee);
                        break;

                    case 2:
                        system.displayAllEmployees();
                        break;

                    case 3:
                        System.out.print("Enter Employee ID: ");
                        String searchId = sc.nextLine().trim();

                        system.searchEmployee(searchId)
                              .displayEmployee(true);
                        break;

                    case 4:
                        System.out.print("Enter Employee ID: ");
                        String leaveEmployeeId =
                            sc.nextLine().trim();

                        System.out.println(
                            "Leave Types: CASUAL, SICK, EARNED"
                        );
                        System.out.print("Enter Leave Type: ");

                        LeaveRequest.LeaveType leaveType =
                            LeaveRequest.LeaveType.valueOf(
                                sc.nextLine().trim().toUpperCase()
                            );

                        System.out.print("Enter Number of Days: ");
                        int days = Integer.parseInt(
                            sc.nextLine().trim()
                        );

                        System.out.print("Enter Reason: ");
                        String reason = sc.nextLine().trim();

                        if (reason.isEmpty()) {
                            throw new IllegalArgumentException(
                                "Leave reason cannot be empty."
                            );
                        }

                        system.applyLeave(
                            leaveEmployeeId, leaveType, days, reason
                        );
                        break;

                    case 5:
                        System.out.print("Enter Request ID: ");
                        String approveId = sc.nextLine().trim();

                        system.approveLeave(approveId);
                        break;

                    case 6:
                        System.out.print("Enter Request ID: ");
                        String rejectId = sc.nextLine().trim();

                        system.rejectLeave(rejectId);
                        break;

                    case 7:
                        system.displayAllLeaveRequests();
                        break;

                    case 8:
                        System.out.print("Enter Employee ID: ");
                        String balanceId = sc.nextLine().trim();

                        system.displayLeaveBalance(balanceId);
                        break;

                    case 9:
                        running = false;
                        System.out.println("Thank you for using the system.");
                        break;

                    default:
                        System.out.println(
                            "Invalid choice. Select from 1 to 9."
                        );
                }

            } catch (EmployeeNotFoundException
                     | InsufficientLeaveException e) {

                System.out.println("Error: " + e.getMessage());

            } catch (IllegalArgumentException e) {

                System.out.println("Error: " + e.getMessage());

            } catch (Exception e) {

                System.out.println(
                    "Invalid input. Please enter the correct value."
                );
            }
        }

        sc.close();
    }
}
