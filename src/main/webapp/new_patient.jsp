<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="model.Employee" %>
<%@ page import="model.Patient" %>
<%@ page import="service.InfirmierService" %>
<%@ page import="java.text.SimpleDateFormat" %>
<%@ page import="java.util.Date" %>
<%
    // Require login
    Employee user = (Employee) session.getAttribute("user");
    if (user == null) {
        response.sendRedirect("login.jsp");
        return;
    }

    // If this page received a POST, handle create patient here as a fallback
    if ("POST".equalsIgnoreCase(request.getMethod())) {
        request.setCharacterEncoding("UTF-8");
        String firstName = request.getParameter("firstName");
        String lastName = request.getParameter("lastName");
        String birthDateStr = request.getParameter("birthDate");
        String phoneNumber = request.getParameter("phoneNumber");
        String address = request.getParameter("address");
        String ssn = request.getParameter("ssn");

        if (firstName == null || firstName.trim().isEmpty() || lastName == null || lastName.trim().isEmpty() || birthDateStr == null || birthDateStr.trim().isEmpty() || phoneNumber == null || phoneNumber.trim().isEmpty() || ssn == null || ssn.trim().isEmpty()) {
            request.setAttribute("error", "Veuillez remplir les champs requis (nom, prénom, date de naissance, téléphone, numéro de sécurité sociale).");
        } else {
            Date birthDate = null;
            try {
                birthDate = new SimpleDateFormat("yyyy-MM-dd").parse(birthDateStr);
                Patient p = new Patient();
                p.setFirstName(firstName.trim());
                p.setLastName(lastName.trim());
                p.setBirthDate(birthDate);
                p.setPhoneNumber(phoneNumber.trim());
                p.setAdress(address == null ? null : address.trim());
                p.setSocialSecurityNumber(ssn.trim());

                try {
                    new InfirmierService().addPatient(p);
                    // PRG: set flash in session and redirect to dashboard
                    session.setAttribute("success", "Patient enregistré avec succès.");
                    response.sendRedirect(request.getContextPath() + "/dashboard.jsp");
                    return;
                } catch (Exception e) {
                    // Do not print stack trace here; forward a user-friendly message
                    request.setAttribute("error", e.getMessage());
                }
            } catch (Exception e) {
                request.setAttribute("error", "Format de date invalide. Utilisez AAAA-MM-JJ.");
            }
        }
    }
%>
<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="utf-8" />
    <meta name="viewport" content="width=device-width,initial-scale=1" />
    <title>Nouveau patient</title>
    <style>
        :root{--maxw:700px;--muted:#666;--border:#e9e9e9}
        body{font-family:Inter,system-ui,Arial;background:#fff;color:#111;margin:0;padding:24px;display:flex;justify-content:center}
        .sheet{max-width:var(--maxw);width:100%;border:1px solid var(--border);border-radius:10px;padding:20px;background:#fff}
        h2{margin:0 0 12px 0}
        label{display:block;font-size:0.9rem;margin-top:12px;color:var(--muted)}
        input,textarea{width:100%;padding:10px;border:1px solid var(--border);border-radius:8px;margin-top:6px}
        .row{display:grid;grid-template-columns:1fr 1fr;gap:12px}
        .actions{display:flex;gap:8px;justify-content:flex-end;margin-top:14px}
        .btn{padding:10px 14px;border-radius:8px;border:1px solid var(--border);background:#fff}
        .btn-primary{background:#111;color:#fff;border:none}
        .muted{color:var(--muted)}
        .message{padding:10px;border-radius:8px;margin-top:12px}
        .success{background:#eef6ea;border:1px solid #cfe2d8;color:#154734}
        .error{background:#fff0f0;border:1px solid #f3c6c6;color:#8b2b2b}
        @media (max-width:600px){.row{grid-template-columns:1fr}}
    </style>
</head>
<body>
    <div class="sheet">
        <h2>Nouveau patient</h2>
        <p class="muted">Remplissez les informations du patient. Les champs marqués sont requis.</p>

        <% if (request.getAttribute("success") != null) { %>
            <div class="message success"><%= request.getAttribute("success") %></div>
        <% } %>
        <% if (request.getAttribute("error") != null) { %>
            <div class="message error"><%= request.getAttribute("error") %></div>
        <% } %>

        <form action="<%= request.getContextPath() %>/new_patient.jsp" method="post">
            <div class="row">
                <div>
                    <label for="firstName">Prénom *</label>
                    <input id="firstName" name="firstName" required />
                </div>
                <div>
                    <label for="lastName">Nom *</label>
                    <input id="lastName" name="lastName" required />
                </div>
            </div>

            <label for="birthDate">Date de naissance * (AAAA-MM-JJ)</label>
            <input id="birthDate" name="birthDate" placeholder="1988-03-25" required />

            <label for="phoneNumber">Numéro de téléphone *</label>
            <input id="phoneNumber" name="phoneNumber" placeholder="+212612345678" required />

            <label for="ssn">Numéro de sécurité sociale *</label>
            <input id="ssn" name="ssn" placeholder="123456789" required />

            <label for="address">Adresse (optionnel)</label>
            <textarea id="address" name="address" rows="3"></textarea>

            <div class="actions">
                <a href="dashboard.jsp" class="btn">Annuler</a>
                <button type="submit" class="btn btn-primary">Créer le patient</button>
            </div>
        </form>
    </div>
</body>
</html>
