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
        String pwd = employee.getPassword();
        if (pwd == null) {
            throw new IllegalArgumentException("Password must be provided");
        }
        // If the password already looks like a BCrypt hash, don't re-hash it.
        if (!(pwd.startsWith("$2a$") || pwd.startsWith("$2y$") || pwd.startsWith("$2b$"))) {
            employee.setPasswordHash(BCrypt.hashpw(pwd, BCrypt.gensalt()));
        }
        employeeDAO.save(employee);
    }

    public Employee login(String email, String password) {
        Employee employee = employeeDAO.findByEmail(email);
        if (employee == null) {
            return null;
        }
        boolean ok = false;
        try {
            ok = BCrypt.checkpw(password, employee.getPassword());
        } catch (Exception e) {
            // suppress printing to console; return null on error
            return null;
        }
        if (ok) {
            activeSessions.add(email);
            return employee;
        } else {
            return null;
        }
    }

    public void logout(String email) {
        activeSessions.remove(email);
    }

    public boolean isLoggedIn(String email) {
        return activeSessions.contains(email);
    }
}