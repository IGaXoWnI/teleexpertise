package servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.Employee;
import model.enums.Role;

import java.io.IOException;

@WebServlet("/page/*")
public class RouteServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String pathInfo = req.getPathInfo();
        
        if (pathInfo == null || pathInfo.equals("/")) {
            resp.sendRedirect(req.getContextPath() + "/page/login");
            return;
        }
        
        String page = pathInfo.substring(1);
        Employee user = (Employee) req.getSession().getAttribute("user");
        
        switch (page) {
            case "login":
                req.getRequestDispatcher("login.jsp").forward(req, resp);
                break;
            case "register":
                req.getRequestDispatcher("/register.jsp").forward(req, resp);
                break;
            case "dashboard":
                if (user == null) {
                    resp.sendRedirect(req.getContextPath() + "/page/login");
                    return;
                }
                if (user.getRole() == Role.GENERALISTE) {
                    req.getRequestDispatcher("/generaliste_dashboard.jsp").forward(req, resp);
                } else {
                    req.getRequestDispatcher("/dashboard.jsp").forward(req, resp);
                }
                break;
            case "new-patient":
                if (user == null || !(user instanceof model.Infirmier)) {
                    resp.sendRedirect(req.getContextPath() + "/page/login");
                    return;
                }
                req.getRequestDispatcher("/new_patient.jsp").forward(req, resp);
                break;
            case "add-vital-signs":
                if (user == null || !(user instanceof model.Infirmier)) {
                    resp.sendRedirect(req.getContextPath() + "/page/login");
                    return;
                }
                req.getRequestDispatcher("/add_vital_signs.jsp").forward(req, resp);
                break;
            case "add-consultation":
                if (user == null || !(user instanceof model.Generaliste)) {
                    resp.sendRedirect(req.getContextPath() + "/page/login");
                    return;
                }
                String patientId = req.getParameter("patientId");
                req.getRequestDispatcher("/add_consultation.jsp?patientId=" + patientId).forward(req, resp);
                break;
            case "view-vital-signs":
                if (user == null || !(user instanceof model.Generaliste)) {
                    resp.sendRedirect(req.getContextPath() + "/page/login");
                    return;
                }
                String pid = req.getParameter("patientId");
                req.getRequestDispatcher("/view_vital_signs.jsp?patientId=" + pid).forward(req, resp);
                break;
            default:
                resp.sendError(HttpServletResponse.SC_NOT_FOUND);
        }
    }
}
