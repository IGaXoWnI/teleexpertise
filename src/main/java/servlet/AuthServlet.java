package servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.Employee;
import model.Generaliste;
import model.Specialiste;
import model.Infirmier;
import model.enums.Role;
import model.enums.Specialite;
import service.AuthService;

import java.io.IOException;

@WebServlet("/auth")
public class AuthServlet extends HttpServlet {
    
    private AuthService authService = new AuthService();
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        // Handle logout
        HttpSession session = request.getSession(false);
        if (session != null) {
            Employee user = (Employee) session.getAttribute("user");
            if (user != null) {
                authService.logout(user.getEmail());
            }
            session.invalidate();
        }
        response.sendRedirect("login.jsp");
    }
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        String action = request.getParameter("action");
        
        if ("login".equals(action)) {
            handleLogin(request, response);
        } else if ("register".equals(action)) {
            handleRegister(request, response);
        }
    }
    
    private void handleLogin(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        
        try {
            Employee employee = authService.login(email, password);
            
            if (employee != null) {
                HttpSession session = request.getSession();
                session.setAttribute("user", employee);
                session.setAttribute("userRole", employee.getRole());
                
                response.sendRedirect("dashboard.jsp");
            } else {
                request.setAttribute("error", "Email ou mot de passe incorrect");
                request.getRequestDispatcher("login.jsp").forward(request, response);
            }
        } catch (Exception e) {
            request.setAttribute("error", "Erreur de connexion");
            request.getRequestDispatcher("login.jsp").forward(request, response);
        }
    }
    
    private void handleRegister(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        String nom = request.getParameter("nom");
        String prenom = request.getParameter("prenom");
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        String roleStr = request.getParameter("role");
        
        try {
            Role role = Role.valueOf(roleStr);
            Employee employee;
            
            switch (role) {
                case GENERALISTE:
                    employee = new Generaliste();
                    break;
                case SPECIALISTE:
                    employee = new Specialiste();
                    String specialiteStr = request.getParameter("specialite");
                    String tarifStr = request.getParameter("tarif");
                    
                    if (specialiteStr != null && !specialiteStr.isEmpty()) {
                        ((Specialiste) employee).setSpecialite(Specialite.valueOf(specialiteStr));
                    }
                    if (tarifStr != null && !tarifStr.isEmpty()) {
                        ((Specialiste) employee).setTarif(Double.parseDouble(tarifStr));
                    }
                    break;
                case INFIRMIER:
                    employee = new Infirmier();
                    break;
                default:
                    throw new IllegalArgumentException("Rôle invalide");
            }
            
            employee.setNom(nom);
            employee.setPrenom(prenom);
            employee.setEmail(email);
            employee.setPasswordHash(password);
            employee.setRole(role);
            
            authService.register(employee);
            
            request.setAttribute("success", "Inscription réussie! Vous pouvez maintenant vous connecter.");
            request.getRequestDispatcher("login.jsp").forward(request, response);
            
        } catch (Exception e) {
            request.setAttribute("error", "Erreur lors de l'inscription: " + e.getMessage());
            request.getRequestDispatcher("register.jsp").forward(request, response);
        }
    }
}