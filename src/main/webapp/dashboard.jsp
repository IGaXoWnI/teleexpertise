<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="model.Employee" %>
<%
    Employee user = (Employee) session.getAttribute("user");
    if (user == null) {
        response.sendRedirect("login.jsp");
        return;
    }
%>
<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Dashboard - MedExpert</title>
    <style>
        * {
            margin: 0;
            padding: 0;
            box-sizing: border-box;
        }
        body {
            font-family: -apple-system, BlinkMacSystemFont, 'Segoe UI', Roboto, sans-serif;
            background: #f5f5f5;
        }
        .header {
            background: #fff;
            padding: 1rem 2rem;
            border-bottom: 1px solid #ddd;
            display: flex;
            justify-content: space-between;
            align-items: center;
        }
        .welcome {
            font-size: 1.2rem;
            font-weight: 600;
        }
        .logout {
            background: #000;
            color: #fff;
            border: none;
            padding: 0.5rem 1rem;
            cursor: pointer;
            text-decoration: none;
        }
        .container {
            max-width: 1200px;
            margin: 2rem auto;
            padding: 0 2rem;
        }
        .card {
            background: #fff;
            border: 1px solid #ddd;
            padding: 2rem;
            margin-bottom: 2rem;
        }
    </style>
</head>
<body>
    <div class="header">
        <div class="welcome">
            Bienvenue, <%= user.getPrenom() %> <%= user.getNom() %> (<%= user.getRole() %>)
        </div>
        <a href="logout" class="logout">Déconnexion</a>
    </div>
    
    <div class="container">
        <div class="card">
            <h2>Tableau de bord</h2>
            <p>Votre espace professionnel MedExpert</p>
        </div>
    </div>
</body>
</html>