<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="model.Employee" %>
<%@ page import="model.Patient" %>
<%@ page import="model.VitalSigns" %>
<%@ page import="dao.PatientDAO" %>
<%@ page import="dao.VitalSignsDAO" %>
<%@ page import="java.util.List" %>
<%@ page import="java.time.format.DateTimeFormatter" %>
<%
    Employee user = (Employee) session.getAttribute("user");
    if (user == null || !user.getRole().name().equals("GENERALISTE")) {
        response.sendRedirect("login.jsp");
        return;
    }

    String patientIdStr = request.getParameter("patientId");
    if (patientIdStr == null || patientIdStr.isEmpty()) {
        response.sendRedirect("generaliste_dashboard.jsp");
        return;
    }

    Long patientId = Long.parseLong(patientIdStr);
    PatientDAO patientDAO = new PatientDAO();
    VitalSignsDAO vitalSignsDAO = new VitalSignsDAO();
    
    Patient patient = patientDAO.findById(patientId);
    if (patient == null) {
        response.sendRedirect("generaliste_dashboard.jsp");
        return;
    }
    
    List<VitalSigns> vitalSignsList = vitalSignsDAO.findByPatientId(patientId);
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
%>
<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="utf-8" />
    <meta name="viewport" content="width=device-width,initial-scale=1" />
    <title>Signes vitaux - <%= patient.getFirstName() %> <%= patient.getLastName() %></title>
    <style>
        :root{--maxw:900px;--muted:#666;--border:#e9e9e9}
        body{font-family:Inter,system-ui,Arial;background:#fafafa;color:#111;margin:0;padding:0}
        .header{background:#fff;border-bottom:1px solid var(--border);padding:16px 24px;display:flex;justify-content:space-between;align-items:center}
        .header h1{margin:0;font-size:1.2rem}
        .btn{padding:8px 14px;border-radius:8px;border:1px solid var(--border);background:#fff;text-decoration:none;color:#111}
        .container{max-width:var(--maxw);margin:24px auto;padding:0 24px}
        .card{background:#fff;border:1px solid var(--border);border-radius:10px;padding:20px;margin-bottom:20px}
        .card h2{margin:0 0 8px 0;font-size:1.1rem}
        .patient-info{display:grid;grid-template-columns:repeat(2,1fr);gap:12px;margin-bottom:20px;padding:16px;background:#f9f9f9;border-radius:8px}
        .info-item{font-size:0.9rem}
        .info-label{color:var(--muted);font-size:0.85rem}
        .vital-record{border:1px solid var(--border);border-radius:8px;padding:16px;margin-bottom:12px}
        .vital-date{font-weight:600;margin-bottom:12px;color:#111}
        .vital-grid{display:grid;grid-template-columns:repeat(3,1fr);gap:12px}
        .vital-item{font-size:0.9rem}
        .vital-label{color:var(--muted);font-size:0.85rem}
        .muted{color:var(--muted)}
        @media (max-width:600px){.vital-grid{grid-template-columns:1fr}}
    </style>
</head>
<body>
    <div class="header">
        <h1>Signes vitaux - <%= patient.getFirstName() %> <%= patient.getLastName() %></h1>
        <a href="generaliste_dashboard.jsp" class="btn">Retour</a>
    </div>

    <div class="container">
        <div class="card">
            <h2>Informations patient</h2>
            <div class="patient-info">
                <div class="info-item">
                    <div class="info-label">Nom complet</div>
                    <div><%= patient.getFirstName() %> <%= patient.getLastName() %></div>
                </div>
                <div class="info-item">
                    <div class="info-label">Date de naissance</div>
                    <div><%= patient.getBirthDate() %></div>
                </div>
                <div class="info-item">
                    <div class="info-label">Téléphone</div>
                    <div><%= patient.getPhoneNumber() %></div>
                </div>
                <div class="info-item">
                    <div class="info-label">SSN</div>
                    <div><%= patient.getSocialSecurityNumber() %></div>
                </div>
            </div>
        </div>

        <div class="card">
            <h2>Historique des signes vitaux</h2>
            <% if (vitalSignsList.isEmpty()) { %>
                <p class="muted">Aucun signe vital enregistré pour ce patient</p>
            <% } else {
                for (VitalSigns vital : vitalSignsList) {
            %>
                <div class="vital-record">
                    <div class="vital-date"><%= vital.getDateMesure().format(formatter) %></div>
                    <div class="vital-grid">
                        <div class="vital-item">
                            <div class="vital-label">Tension artérielle</div>
                            <div><%= vital.getTension() %></div>
                        </div>
                        <div class="vital-item">
                            <div class="vital-label">Fréquence cardiaque</div>
                            <div><%= vital.getFrequenceCardiaque() %></div>
                        </div>
                        <div class="vital-item">
                            <div class="vital-label">Température</div>
                            <div><%= vital.getTemperature() %>°C</div>
                        </div>
                        <div class="vital-item">
                            <div class="vital-label">Fréquence respiratoire</div>
                            <div><%= vital.getFrequenceRespiratoire() %>/min</div>
                        </div>
                        <div class="vital-item">
                            <div class="vital-label">Poids</div>
                            <div><%= vital.getPoids() %> kg</div>
                        </div>
                        <div class="vital-item">
                            <div class="vital-label">Taille</div>
                            <div><%= vital.getTaille() %> cm</div>
                        </div>
                    </div>
                </div>
            <% }} %>
        </div>
    </div>
</body>
</html>
