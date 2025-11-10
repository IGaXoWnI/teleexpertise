package service.servicesInterfaces;

import model.Employee;

public interface IAuthService {
    void register(Employee employee);
    Employee login(String email, String password);
    void logout(String email);
    boolean isLoggedIn(String email);
}
