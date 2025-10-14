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
import model.enums.CreneauStatus;
import model.Creneau;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.LocalDate;
import java.util.List;
import java.util.ArrayList;
import service.AuthService;

import java.io.IOException;

@WebServlet("/auth")
public class AuthServlet extends HttpServlet {
    private AuthService authService = new AuthService();

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
                session.setAttribute("employee", employee);
                session.setAttribute("role", employee.getRole());
                
                // Redirect based on role
                switch (employee.getRole()) {
                    case GENERALISTE:
                        response.sendRedirect("generaliste/dashboard.jsp");
                        break;
                    case SPECIALISTE:
                        response.sendRedirect("specialiste/dashboard.jsp");
                        break;
                    case INFIRMIER:
                        response.sendRedirect("infirmier/dashboard.jsp");
                        break;
                }
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
            
            // Create specific employee type based on role
            switch (role) {
                case GENERALISTE:
                    employee = new Generaliste();
                    break;
                case SPECIALISTE:
                    String specialiteStr = request.getParameter("specialite");
                    String tarifStr = request.getParameter("tarif");
                    
                    if (specialiteStr == null || specialiteStr.isEmpty()) {
                        throw new IllegalArgumentException("Spécialité requise pour un spécialiste");
                    }
                    if (tarifStr == null || tarifStr.isEmpty()) {
                        throw new IllegalArgumentException("Tarif requis pour un spécialiste");
                    }
                    
                    Specialite specialite = Specialite.valueOf(specialiteStr);
                    Double tarif = Double.parseDouble(tarifStr);
                    String[] creneauxValues = request.getParameterValues("creneaux");
                    
                    employee = new Specialiste();
                    ((Specialiste) employee).setSpecialite(specialite);
                    ((Specialiste) employee).setTarif(tarif);
                    
                    // Create creneaux if selected
                    if (creneauxValues != null) {
                        List<Creneau> creneaux = new ArrayList<>();
                        LocalDate today = LocalDate.now();
                        
                        for (String creneauValue : creneauxValues) {
                            String[] times = creneauValue.split("-");
                            LocalTime startTime = LocalTime.parse(times[0]);
                            LocalTime endTime = LocalTime.parse(times[1]);
                            
                            Creneau creneau = new Creneau();
                            creneau.setDateHeureDebut(LocalDateTime.of(today, startTime));
                            creneau.setDateHeureFin(LocalDateTime.of(today, endTime));
                            creneau.setCreneauStatus(CreneauStatus.DISPONIBLE);

                            
                            creneaux.add(creneau);
                        }
                        ((Specialiste) employee).setCreneaux(creneaux);
                    }
                    break;
                case INFIRMIER:
                    employee = new Infirmier();
                    break;
                default:
                    throw new IllegalArgumentException("Invalid role");
            }
            
            employee.setNom(nom);
            employee.setPrenom(prenom);
            employee.setEmail(email);
            employee.setPasswordHash(password);
            employee.setRole(role);

            authService.register(employee);
            
            request.setAttribute("success", "Compte créé avec succès. Vous pouvez vous connecter.");
            request.getRequestDispatcher("login.jsp").forward(request, response);
            
        } catch (Exception e) {
            request.setAttribute("error", "Erreur lors de la création du compte: " + e.getMessage());
            request.getRequestDispatcher("register.jsp").forward(request, response);
        }
    }
}