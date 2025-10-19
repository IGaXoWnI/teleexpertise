package servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.Patient;
import service.InfirmierService;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@WebServlet("/infirmier")
public class InfirmierServlet extends HttpServlet {

    private final InfirmierService infirmierService = new InfirmierService();
    private final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // Show form to create new patient
        req.getRequestDispatcher("/new_patient.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // Handle create patient
        req.setCharacterEncoding("UTF-8");
        String firstName = req.getParameter("firstName");
        String lastName = req.getParameter("lastName");
        String birthDateStr = req.getParameter("birthDate");
        String phoneNumber = req.getParameter("phoneNumber");
        String address = req.getParameter("address");

        if (firstName == null || firstName.trim().isEmpty() || lastName == null || lastName.trim().isEmpty() || birthDateStr == null || birthDateStr.trim().isEmpty() || phoneNumber == null || phoneNumber.trim().isEmpty()) {
            req.setAttribute("error", "Veuillez remplir les champs requis (nom, prénom, date de naissance, téléphone).");
            req.getRequestDispatcher("/new_patient.jsp").forward(req, resp);
            return;
        }

        Date birthDate;
        try {
            birthDate = sdf.parse(birthDateStr);
        } catch (ParseException e) {
            req.setAttribute("error", "Format de date invalide. Utilisez AAAA-MM-JJ.");
            req.getRequestDispatcher("/new_patient.jsp").forward(req, resp);
            return;
        }

        Patient p = new Patient();
        p.setFirstName(firstName.trim());
        p.setLastName(lastName.trim());
        p.setBirthDate(birthDate);
        p.setPhoneNumber(phoneNumber.trim());
        // the Patient model uses setAdress (typo) — use it
        p.setAdress(address == null ? null : address.trim());

        try {
            infirmierService.addPatient(p);
            // Use PRG pattern: redirect to dashboard with a flash-like message using session
            req.getSession().setAttribute("success", "Patient enregistré avec succès.");
            resp.sendRedirect(req.getContextPath() + "/dashboard.jsp");
        } catch (Exception e) {
            req.setAttribute("error", e.getMessage());
            req.getRequestDispatcher("/new_patient.jsp").forward(req, resp);
        }
    }
}
