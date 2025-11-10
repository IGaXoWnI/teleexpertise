package servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.Employee;
import model.Generaliste;
import service.ConsultationService;

import java.io.IOException;

@WebServlet("/generaliste")
public class GeneralisteServlet extends HttpServlet {

    private ConsultationService consultationService = new ConsultationService();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        
        Employee user = (Employee) req.getSession().getAttribute("user");
        if (user == null || !(user instanceof Generaliste)) {
            resp.sendRedirect("login.jsp");
            return;
        }

        String patientIdStr = req.getParameter("patientId");
        String motif = req.getParameter("motif");
        String observations = req.getParameter("observations");
        String diagnostic = req.getParameter("diagnostic");
        String traitement = req.getParameter("traitement");
        String coutStr = req.getParameter("cout");

        if (patientIdStr == null || motif == null || motif.trim().isEmpty() || 
            observations == null || observations.trim().isEmpty() || coutStr == null) {
            req.getSession().setAttribute("error", "Veuillez remplir tous les champs requis");
            resp.sendRedirect("add_consultation.jsp?patientId=" + patientIdStr);
            return;
        }

        try {
            Long patientId = Long.parseLong(patientIdStr);
            Double cout = Double.parseDouble(coutStr);
            
            consultationService.createConsultation(patientId, (Generaliste) user, motif, 
                    observations, diagnostic, traitement, cout);
            
            req.getSession().setAttribute("success", "Consultation enregistrée avec succès");
            resp.sendRedirect("generaliste_dashboard.jsp");
        } catch (NumberFormatException e) {
            req.getSession().setAttribute("error", "Valeurs numériques invalides");
            resp.sendRedirect("add_consultation.jsp?patientId=" + patientIdStr);
        } catch (Exception e) {
            req.getSession().setAttribute("error", e.getMessage());
            resp.sendRedirect("add_consultation.jsp?patientId=" + patientIdStr);
        }
    }
}
