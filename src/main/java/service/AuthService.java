package service;

import dao.EmployeeDAO;
import model.Employee;
import org.mindrot.jbcrypt.BCrypt;
import java.util.HashSet;
import java.util.Set;

public class AuthService {
    EmployeeDAO employeeDAO = new EmployeeDAO();
    private Set<String> activeSessions = new HashSet<>();

    public void register(Employee employee) {
        if (employee.getRole() == null) {
            throw new IllegalArgumentException("Role must be set before registration");
        }
        employee.setPasswordHash(BCrypt.hashpw(employee.getPassword(), BCrypt.gensalt()));
        employeeDAO.save(employee);
    }

    public Employee login(String email, String password) {
        Employee employee = employeeDAO.findByEmail(email);
        if (employee != null && BCrypt.checkpw(password, employee.getPassword())) {
            activeSessions.add(email);
            return employee;
        }
        return null;
    }

    public void logout(String email) {
        activeSessions.remove(email);
    }

    public boolean isLoggedIn(String email) {
        return activeSessions.contains(email);
    }
}