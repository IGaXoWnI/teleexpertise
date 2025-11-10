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

    String patientIdStr = request.getParameter("patientId");
    if (patientIdStr == null || patientIdStr.isEmpty()) {
        response.sendRedirect("generaliste_dashboard.jsp");
        return;
    }

    Long patientId = Long.parseLong(patientIdStr);
    PatientDAO patientDAO = new PatientDAO();
    Patient patient = patientDAO.findById(patientId);
    
    if (patient == null) {
        response.sendRedirect("generaliste_dashboard.jsp");
        return;
    }
    
    VitalSignsDAO vitalSignsDAO = new VitalSignsDAO();
    List<VitalSigns> vitalSignsList = vitalSignsDAO.findByPatientId(patientId);
    VitalSigns latestVital = vitalSignsList.isEmpty() ? null : vitalSignsList.get(0);
%>
<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="utf-8" />
    <meta name="viewport" content="width=device-width,initial-scale=1" />
    <title>Nouvelle consultation</title>
    <style>
        :root{--maxw:800px;--muted:#666;--border:#e9e9e9}
        body{font-family:Inter,system-ui,Arial;background:#fafafa;color:#111;margin:0;padding:0}
        .header{background:#fff;border-bottom:1px solid var(--border);padding:16px 24px;display:flex;justify-content:space-between;align-items:center}
        .header h1{margin:0;font-size:1.2rem}
        .btn{padding:8px 14px;border-radius:8px;border:1px solid var(--border);background:#fff;text-decoration:none;color:#111}
        .btn-primary{background:#111;color:#fff;border:none;cursor:pointer}
        .container{max-width:var(--maxw);margin:24px auto;padding:0 24px}
        .card{background:#fff;border:1px solid var(--border);border-radius:10px;padding:20px;margin-bottom:20px}
        .card h2{margin:0 0 16px 0;font-size:1.1rem}
        .patient-info{display:grid;grid-template-columns:repeat(2,1fr);gap:12px;padding:16px;background:#f9f9f9;border-radius:8px;margin-bottom:20px}
        .info-item{font-size:0.9rem}
        .info-label{color:var(--muted);font-size:0.85rem}
        label{display:block;font-size:0.9rem;margin-top:12px;color:var(--muted)}
        input,textarea{width:100%;padding:10px;border:1px solid var(--border);border-radius:8px;margin-top:6px;font-family:inherit}
        textarea{min-height:100px;resize:vertical}
        .actions{display:flex;gap:8px;justify-content:flex-end;margin-top:14px}
        .message{padding:10px;border-radius:8px;margin-bottom:16px}
        .error{background:#fff0f0;border:1px solid #f3c6c6;color:#8b2b2b}
        .muted{color:var(--muted)}
    </style>
</head>
<body>
    <div class="header">
        <h1>Nouvelle consultation</h1>
        <a href="generaliste_dashboard.jsp" class="btn">Retour</a>
    </div>

    <div class="container">
        <% if (session.getAttribute("error") != null) { %>
            <div class="message error"><%= session.getAttribute("error") %></div>
            <% session.removeAttribute("error"); %>
        <% } %>

        <div class="card">
            <h2>Patient</h2>
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

            <% if (latestVital != null) { %>
                <h2>Derniers signes vitaux</h2>
                <div class="patient-info">
                    <div class="info-item">
                        <div class="info-label">Tension</div>
                        <div><%= latestVital.getTension() %></div>
                    </div>
                    <div class="info-item">
                        <div class="info-label">Fréquence cardiaque</div>
                        <div><%= latestVital.getFrequenceCardiaque() %></div>
                    </div>
                    <div class="info-item">
                        <div class="info-label">Température</div>
                        <div><%= latestVital.getTemperature() %>°C</div>
                    </div>
                    <div class="info-item">
                        <div class="info-label">Poids</div>
                        <div><%= latestVital.getPoids() %> kg</div>
                    </div>
                </div>
            <% } %>
        </div>

        <div class="card">
            <h2>Consultation</h2>
            <form method="post" action="<%= request.getContextPath() %>/generaliste">
                <input type="hidden" name="patientId" value="<%= patientId %>" />

                <label for="motif">Motif de consultation *</label>
                <input id="motif" name="motif" required />

                <label for="observations">Observations *</label>
                <textarea id="observations" name="observations" required></textarea>

                <label for="diagnostic">Diagnostic</label>
                <textarea id="diagnostic" name="diagnostic"></textarea>

                <label for="traitement">Traitement prescrit</label>
                <textarea id="traitement" name="traitement"></textarea>

                <label for="cout">Coût (€) *</label>
                <input type="number" step="0.01" id="cout" name="cout" required />

                <div class="actions">
                    <a href="generaliste_dashboard.jsp" class="btn">Annuler</a>
                    <button type="submit" class="btn btn-primary">Enregistrer</button>
                </div>
            </form>
        </div>
    </div>
</body>
</html>
