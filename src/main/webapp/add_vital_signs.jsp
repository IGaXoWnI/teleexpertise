<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="model.Employee" %>
<%@ page import="model.Patient" %>
<%@ page import="dao.PatientDAO" %>
<%@ page import="service.VitalSignsService" %>
<%
    Employee user = (Employee) session.getAttribute("user");
    if (user == null) {
        response.sendRedirect("login.jsp");
        return;
    }

    String patientIdStr = request.getParameter("patientId");
    Patient patient = null;
    model.VitalSigns latestVitalSigns = null;
    
    if (patientIdStr != null && !patientIdStr.isEmpty()) {
        try {
            Long patientId = Long.parseLong(patientIdStr);
            patient = new PatientDAO().findById(patientId);
            if (patient != null) {
                java.util.List<model.VitalSigns> vitalSignsList = new dao.VitalSignsDAO().findByPatientId(patientId);
                if (!vitalSignsList.isEmpty()) {
                    latestVitalSigns = vitalSignsList.get(0);
                }
            }
        } catch (Exception e) {
            request.setAttribute("error", "Patient introuvable");
        }
    }

    if ("POST".equalsIgnoreCase(request.getMethod())) {
        request.setCharacterEncoding("UTF-8");
        String tension = request.getParameter("tension");
        String frequenceCardiaque = request.getParameter("frequenceCardiaque");
        String temperatureStr = request.getParameter("temperature");
        String frequenceRespiratoireStr = request.getParameter("frequenceRespiratoire");
        String poidsStr = request.getParameter("poids");
        String tailleStr = request.getParameter("taille");
        String patientIdParam = request.getParameter("patientId");

        if (tension == null || tension.trim().isEmpty() || 
            frequenceCardiaque == null || frequenceCardiaque.trim().isEmpty() ||
            temperatureStr == null || temperatureStr.trim().isEmpty() ||
            frequenceRespiratoireStr == null || frequenceRespiratoireStr.trim().isEmpty() ||
            poidsStr == null || poidsStr.trim().isEmpty() ||
            tailleStr == null || tailleStr.trim().isEmpty() ||
            patientIdParam == null || patientIdParam.trim().isEmpty()) {
            request.setAttribute("error", "Veuillez remplir tous les champs requis");
        } else {
            try {
                Long patientId = Long.parseLong(patientIdParam);
                Integer temperature = Integer.parseInt(temperatureStr);
                Integer frequenceRespiratoire = Integer.parseInt(frequenceRespiratoireStr);
                Integer poids = Integer.parseInt(poidsStr);
                Integer taille = Integer.parseInt(tailleStr);

                new VitalSignsService().addVitalSigns(patientId, tension, frequenceCardiaque, 
                                                      temperature, frequenceRespiratoire, poids, taille);
                
                session.setAttribute("success", "Signes vitaux enregistrés avec succès");
                response.sendRedirect(request.getContextPath() + "/dashboard.jsp");
                return;
            } catch (NumberFormatException e) {
                request.setAttribute("error", "Valeurs numériques invalides");
            } catch (Exception e) {
                request.setAttribute("error", e.getMessage());
            }
        }
    }
%>
<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="utf-8" />
    <meta name="viewport" content="width=device-width,initial-scale=1" />
    <title>Ajouter signes vitaux</title>
    <style>
        :root{--maxw:700px;--muted:#666;--border:#e9e9e9}
        body{font-family:Inter,system-ui,Arial;background:#fff;color:#111;margin:0;padding:24px;display:flex;justify-content:center}
        .sheet{max-width:var(--maxw);width:100%;border:1px solid var(--border);border-radius:10px;padding:20px;background:#fff}
        h2{margin:0 0 12px 0}
        label{display:block;font-size:0.9rem;margin-top:12px;color:var(--muted)}
        input,select{width:100%;padding:10px;border:1px solid var(--border);border-radius:8px;margin-top:6px}
        .row{display:grid;grid-template-columns:1fr 1fr;gap:12px}
        .actions{display:flex;gap:8px;justify-content:flex-end;margin-top:14px}
        .btn{padding:10px 14px;border-radius:8px;border:1px solid var(--border);background:#fff;text-decoration:none;color:#111}
        .btn-primary{background:#111;color:#fff;border:none}
        .muted{color:var(--muted)}
        .message{padding:10px;border-radius:8px;margin-top:12px}
        .success{background:#eef6ea;border:1px solid #cfe2d8;color:#154734}
        .error{background:#fff0f0;border:1px solid #f3c6c6;color:#8b2b2b}
        @media (max-width:600px){.row{grid-template-columns:1fr}}
    </style>
    <script>
        function loadPatientVitalSigns() {
            const patientId = document.getElementById('patientId').value;
            if (patientId) {
                window.location.href = 'add_vital_signs.jsp?patientId=' + patientId;
            }
        }
    </script>
</head>
<body>
    <div class="sheet">
        <h2>Ajouter signes vitaux</h2>
        <% if (patient != null) { %>
            <p class="muted">Patient: <%= patient.getFirstName() %> <%= patient.getLastName() %></p>
        <% } else { %>
            <p class="muted">Sélectionnez un patient et remplissez les signes vitaux</p>
        <% } %>

        <% if (request.getAttribute("error") != null) { %>
            <div class="message error"><%= request.getAttribute("error") %></div>
        <% } %>

        <form method="post">
            <label for="patientId">Patient *</label>
            <select id="patientId" name="patientId" required onchange="loadPatientVitalSigns()">
                <option value="">Sélectionner un patient</option>
                <% 
                    for (Patient p : new PatientDAO().findAll()) {
                        String selected = (patient != null && p.getId().equals(patient.getId())) ? "selected" : "";
                %>
                    <option value="<%= p.getId() %>" <%= selected %>><%= p.getFirstName() %> <%= p.getLastName() %></option>
                <% } %>
            </select>

            <div class="row">
                <div>
                    <label for="tension">Tension artérielle *</label>
                    <input id="tension" name="tension" value="<%= latestVitalSigns != null ? latestVitalSigns.getTension() : "" %>" placeholder="120/80" required />
                </div>
                <div>
                    <label for="frequenceCardiaque">Fréquence cardiaque *</label>
                    <input id="frequenceCardiaque" name="frequenceCardiaque" value="<%= latestVitalSigns != null ? latestVitalSigns.getFrequenceCardiaque() : "" %>" placeholder="75 bpm" required />
                </div>
            </div>

            <div class="row">
                <div>
                    <label for="temperature">Température (°C) *</label>
                    <input type="number" id="temperature" name="temperature" value="<%= latestVitalSigns != null ? latestVitalSigns.getTemperature() : "" %>" placeholder="37" required />
                </div>
                <div>
                    <label for="frequenceRespiratoire">Fréquence respiratoire *</label>
                    <input type="number" id="frequenceRespiratoire" name="frequenceRespiratoire" value="<%= latestVitalSigns != null ? latestVitalSigns.getFrequenceRespiratoire() : "" %>" placeholder="16" required />
                </div>
            </div>

            <div class="row">
                <div>
                    <label for="poids">Poids (kg) *</label>
                    <input type="number" id="poids" name="poids" value="<%= latestVitalSigns != null ? latestVitalSigns.getPoids() : "" %>" placeholder="70" required />
                </div>
                <div>
                    <label for="taille">Taille (cm) *</label>
                    <input type="number" id="taille" name="taille" value="<%= latestVitalSigns != null ? latestVitalSigns.getTaille() : "" %>" placeholder="175" required />
                </div>
            </div>

            <div class="actions">
                <a href="dashboard.jsp" class="btn">Annuler</a>
                <button type="submit" class="btn btn-primary">Enregistrer</button>
            </div>
        </form>
    </div>
</body>
</html>