<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="model.Employee" %>
<%@ page import="model.Patient" %>
<%@ page import="dao.PatientDAO" %>
<%@ page import="java.util.List" %>
<%@ page import="java.text.SimpleDateFormat" %>
<%
    Employee user = (Employee) session.getAttribute("user");
    if (user == null) {
        response.sendRedirect("login.jsp");
        return;
    }

    // Flash messages: read from session (set after PRG) and move to request so JSP displays them only once
    Object flashSuccess = session.getAttribute("success");
    if (flashSuccess != null) {
        request.setAttribute("success", flashSuccess);
        session.removeAttribute("success");
    }
    Object flashError = session.getAttribute("error");
    if (flashError != null) {
        request.setAttribute("error", flashError);
        session.removeAttribute("error");
    }

    // Fetch real patient data from database
    PatientDAO patientDAO = new PatientDAO();
    String searchQuery = request.getParameter("search");
    List<Patient> patients;
    
    if (searchQuery != null && !searchQuery.trim().isEmpty()) {
        patients = patientDAO.searchPatients(searchQuery.trim());
    } else {
        patients = patientDAO.findAll();
    }
    
    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm");
%>
<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Infirmier — Tableau de bord</title>
    <style>
        /* Notion-like minimal, black & white theme */
        :root{
            --bg: #ffffff;
            --muted: #666;
            --text: #111;
            --surface: #fafafa;
            --border: #e9e9e9;
            --radius: 8px;
            --gap: 16px;
            --maxw: 1100px;
        }
        *{box-sizing:border-box}
        body{font-family: Inter, ui-sans-serif, system-ui, -apple-system, 'Segoe UI', Roboto, 'Helvetica Neue', Arial; background:var(--bg); color:var(--text); margin:0}

        /* Top bar */
        .topbar{border-bottom:1px solid var(--border); padding:18px 24px; display:flex; align-items:center; justify-content:space-between}
        .brand{font-weight:600; font-size:1rem; letter-spacing:0.2px}
        .user-meta{color:var(--muted); font-size:0.9rem}
        .button-ghost{background:none;border:1px solid var(--border);padding:8px 12px;border-radius:8px;color:var(--text);font-size:0.9rem}

        /* Page layout */
        .page{max-width:var(--maxw); margin:28px auto; padding:0 20px; display:grid; grid-template-columns:300px 1fr; gap:24px}

        /* Sidebar */
        .sidebar{padding:18px; background:var(--surface); border-radius:var(--radius); border:1px solid var(--border)}
        .sidebar h4{margin:0 0 12px 0; font-size:0.95rem}
        .search{display:flex; gap:8px}
        .search input{flex:1;padding:10px;border:1px solid var(--border);border-radius:8px;background:#fff}
        .action-list{margin-top:16px; display:flex; flex-direction:column; gap:8px}
        .action-list button{background:#fff;border:1px solid var(--border);padding:10px;border-radius:8px;text-align:left}

        /* Content area */
        .content{display:flex; flex-direction:column; gap:16px}
        .card{background:#fff;border:1px solid var(--border);border-radius:10px;padding:16px}
        .card h3{margin:0;font-size:1rem}
        .muted{color:var(--muted);font-size:0.9rem}

        /* Patient card */
        .patient-card{display:flex; gap:12px; align-items:flex-start}
        .patient-avatar{width:56px;height:56px;border-radius:10px;background:var(--surface);display:flex;align-items:center;justify-content:center;font-weight:700;color:var(--muted)}
        .patient-info{flex:1}
        .vitals{display:flex;gap:8px;margin-top:8px}
        .vital-pill{background:var(--surface);border:1px solid var(--border);padding:8px 10px;border-radius:8px;font-size:0.9rem}

        /* Patients table minimal */
        table{width:100%;border-collapse:collapse;margin-top:8px}
        th,td{padding:10px;border-bottom:1px solid var(--border);text-align:left;font-size:0.95rem}
        th{color:var(--muted);font-weight:600;font-size:0.85rem}
        tr:hover{background:var(--surface)}

        /* Footer small */
        .note{color:var(--muted);font-size:0.88rem}

        @media (max-width:900px){.page{grid-template-columns:1fr;padding:16px}}
    </style>
</head>
<body>
    <div class="topbar">
        <div class="brand">MedExpert</div>
        <div style="display:flex;gap:12px;align-items:center">
            <div class="user-meta">Infirmier · <strong><%= user.getPrenom() %> <%= user.getNom() %></strong></div>
            <a href="logout" class="button-ghost">Déconnexion</a>
        </div>
    </div>

    <main class="page">
        <%-- Show success/error messages forwarded by servlets or present in session (flash) --%>
        <% if (request.getAttribute("success") != null) { %>
            <div style="grid-column:1/-1;padding:12px;border-radius:8px;background:#eef6ea;border:1px solid #cfe2d8;color:#154734;margin-bottom:12px"><%= request.getAttribute("success") %></div>
        <% } %>
        <% if (request.getAttribute("error") != null) { %>
            <div style="grid-column:1/-1;padding:12px;border-radius:8px;background:#fff0f0;border:1px solid #f3c6c6;color:#8b2b2b;margin-bottom:12px"><%= request.getAttribute("error") %></div>
        <% } %>

        <!-- Left: search / quick actions / queue -->
        <aside class="sidebar">
            <h4>Recherche Patient</h4>
            <form method="get" class="search">
                <input type="search" name="search" placeholder="Nom, N°SS, téléphone" aria-label="Rechercher patient" value="<%= searchQuery != null ? searchQuery : "" %>">
                <button type="submit" class="button-ghost">Go</button>
            </form>

            <div class="action-list">
                <a href="new_patient.jsp" class="button-ghost" style="display:inline-block;text-align:center;text-decoration:none">Nouveau patient</a>
                <a href="add_vital_signs.jsp" class="button-ghost" style="display:inline-block;text-align:center;text-decoration:none">Ajouter signes vitaux</a>
                <button>File d'attente (<%= patients.size() %>)</button>
            </div>

            <div style="margin-top:18px">
                <div class="muted">File d'attente - aujourd'hui</div>
                <div style="margin-top:8px;display:flex;flex-direction:column;gap:8px">
                    <%
                    int queueLimit = Math.min(patients.size(), 5);
                    for (int i = 0; i < queueLimit; i++) {
                        Patient p = patients.get(i);
                    %>
                    <div class="patient-mini" style="display:flex;justify-content:space-between;align-items:center;padding:8px;border-radius:8px;border:1px solid var(--border);background:#fff">
                        <div><strong><%= p.getFirstName() %> <%= p.getLastName() %></strong><div class="muted">N°SS: <%= p.getSocialSecurityNumber() %></div></div>
                        <div class="muted">#<%= i + 1 %></div>
                    </div>
                    <% } %>
                    <% if (patients.isEmpty()) { %>
                    <div class="muted" style="padding:8px;text-align:center">Aucun patient enregistré</div>
                    <% } %>
                </div>
            </div>

            <div style="margin-top:14px;color:var(--muted);font-size:0.85rem">Nombre total aujourd'hui: <strong><%= patients.size() %></strong></div>
        </aside>

        <!-- Right: main content -->
        <section class="content">
            <div class="card">
                <h3>Accueil du patient</h3>
                <p class="muted" style="margin-top:6px">Recherchez un patient par nom, numéro de sécurité sociale ou téléphone. Ajoutez un nouveau patient ou enregistrez les signes vitaux.</p>
            </div>

            <div class="card">
                <div style="display:flex;justify-content:space-between;align-items:center">
                    <h3>Liste des patients enregistrés aujourd'hui</h3>
                    <div class="muted">Tri: heure d'arrivée (ancien → récent)</div>
                </div>

                <table aria-label="patients-today">
                    <thead>
                        <tr>
                            <th>ID</th>
                            <th>Nom</th>
                            <th>Prénom</th>
                            <th>N°SS</th>
                            <th>Date naissance</th>
                            <th>Téléphone</th>
                        </tr>
                    </thead>
                    <tbody>
                        <% if (patients.isEmpty()) { %>
                        <tr>
                            <td colspan="6" style="text-align:center;color:var(--muted)">Aucun patient enregistré</td>
                        </tr>
                        <% } else {
                            for (Patient patient : patients) {
                        %>
                        <tr>
                            <td><%= patient.getId() %></td>
                            <td><%= patient.getLastName() %></td>
                            <td><%= patient.getFirstName() %></td>
                            <td><%= patient.getSocialSecurityNumber() %></td>
                            <td class="muted"><%= dateFormat.format(patient.getBirthDate()) %></td>
                            <td><%= patient.getPhoneNumber() %></td>
                        </tr>
                        <%
                            }
                        }
                        %>
                    </tbody>
                </table>

                <div style="margin-top:12px" class="note">Les données sont chargées depuis la base de données en temps réel.</div>
            </div>
        </section>
    </main>
</body>
</html>