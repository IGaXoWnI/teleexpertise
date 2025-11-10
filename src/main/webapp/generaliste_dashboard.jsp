<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="model.Employee" %>
<%@ page import="model.Patient" %>
<%@ page import="model.VitalSigns" %>
<%@ page import="dao.PatientDAO" %>
<%@ page import="dao.VitalSignsDAO" %>
<%@ page import="java.util.List" %>
<%
    Employee user = (Employee) session.getAttribute("user");
    if (user == null || !user.getRole().name().equals("GENERALISTE")) {
        response.sendRedirect("login.jsp");
        return;
    }

    PatientDAO patientDAO = new PatientDAO();
    VitalSignsDAO vitalSignsDAO = new VitalSignsDAO();
    List<Patient> patients = patientDAO.findAll();
%>
<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="utf-8" />
    <meta name="viewport" content="width=device-width,initial-scale=1" />
    <title>Tableau de bord - Généraliste</title>
    <style>
        :root{--maxw:1200px;--muted:#666;--border:#e9e9e9}
        body{font-family:Inter,system-ui,Arial;background:#fafafa;color:#111;margin:0;padding:0}
        .header{background:#fff;border-bottom:1px solid var(--border);padding:16px 24px;display:flex;justify-content:space-between;align-items:center}
        .header h1{margin:0;font-size:1.2rem}
        .header .user-info{display:flex;gap:12px;align-items:center}
        .btn{padding:8px 14px;border-radius:8px;border:1px solid var(--border);background:#fff;text-decoration:none;color:#111;cursor:pointer}
        .btn-primary{background:#111;color:#fff;border:none}
        .container{max-width:var(--maxw);margin:24px auto;padding:0 24px}
        .card{background:#fff;border:1px solid var(--border);border-radius:10px;padding:20px;margin-bottom:20px}
        .card h2{margin:0 0 16px 0;font-size:1.1rem}
        table{width:100%;border-collapse:collapse}
        th,td{text-align:left;padding:12px;border-bottom:1px solid var(--border)}
        th{font-weight:600;color:var(--muted);font-size:0.9rem}
        .muted{color:var(--muted)}
        .message{padding:10px;border-radius:8px;margin-bottom:16px}
        .success{background:#eef6ea;border:1px solid #cfe2d8;color:#154734}
        .vital-btn{padding:6px 12px;font-size:0.85rem;border-radius:6px;background:#f5f5f5;border:1px solid var(--border);text-decoration:none;color:#111}
        .vital-btn:hover{background:#e9e9e9}
    </style>
</head>
<body>
    <div class="header">
        <h1>Tableau de bord - Généraliste</h1>
        <div class="user-info">
            <span class="muted">Dr. <%= user.getPrenom() %> <%= user.getNom() %></span>
            <a href="auth" class="btn">Déconnexion</a>
        </div>
    </div>

    <div class="container">
        <% if (session.getAttribute("success") != null) { %>
            <div class="message success"><%= session.getAttribute("success") %></div>
            <% session.removeAttribute("success"); %>
        <% } %>

        <div class="card">
            <h2>Liste des patients</h2>
            <table>
                <thead>
                    <tr>
                        <th>Nom</th>
                        <th>Prénom</th>
                        <th>Date de naissance</th>
                        <th>Téléphone</th>
                        <th>SSN</th>
                        <th>Signes vitaux</th>
                        <th>Action</th>
                    </tr>
                </thead>
                <tbody>
                    <% if (patients.isEmpty()) { %>
                        <tr><td colspan="7" class="muted">Aucun patient enregistré</td></tr>
                    <% } else {
                        for (Patient p : patients) {
                            List<VitalSigns> vitalSignsList = vitalSignsDAO.findByPatientId(p.getId());
                            VitalSigns latestVital = vitalSignsList.isEmpty() ? null : vitalSignsList.get(0);
                    %>
                        <tr>
                            <td><%= p.getLastName() %></td>
                            <td><%= p.getFirstName() %></td>
                            <td><%= p.getBirthDate() %></td>
                            <td><%= p.getPhoneNumber() %></td>
                            <td><%= p.getSocialSecurityNumber() %></td>
                            <td>
                                <% if (latestVital != null) { %>
                                    <a href="view_vital_signs.jsp?patientId=<%= p.getId() %>" class="vital-btn">Voir</a>
                                <% } else { %>
                                    <span class="muted">Aucun</span>
                                <% } %>
                            </td>
                            <td>
                                <a href="add_consultation.jsp?patientId=<%= p.getId() %>" class="vital-btn">Consulter</a>
                            </td>
                        </tr>
                    <% }} %>
                </tbody>
            </table>
        </div>
    </div>
</body>
</html>
