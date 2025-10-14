<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Connexion - MedExpert</title>
    <style>
        * {
            margin: 0;
            padding: 0;
            box-sizing: border-box;
        }

        body {
            font-family: -apple-system, BlinkMacSystemFont, 'Segoe UI', Roboto, sans-serif;
            background: #fff;
            min-height: 100vh;
            display: flex;
            align-items: center;
            justify-content: center;
            padding: 20px;
        }

        .login-container {
            background: #fff;
            border: 1px solid #ddd;
            width: 100%;
            max-width: 400px;
            padding: 2rem;
        }

        .form-title {
            font-size: 1.5rem;
            font-weight: 600;
            color: #000;
            margin-bottom: 0.5rem;
        }

        .form-subtitle {
            color: #666;
            margin-bottom: 2rem;
        }

        .form-group {
            margin-bottom: 1rem;
        }

        .form-group label {
            display: block;
            margin-bottom: 0.5rem;
            color: #000;
            font-weight: 500;
        }

        .form-input {
            width: 100%;
            padding: 0.75rem;
            border: 1px solid #ddd;
            font-size: 1rem;
            transition: border-color 0.2s ease;
        }

        .form-input:focus {
            outline: none;
            border-color: #000;
        }



        .btn-login {
            background: #000;
            color: #fff;
            border: none;
            padding: 0.75rem;
            width: 100%;
            font-size: 1rem;
            font-weight: 500;
            cursor: pointer;
            transition: background-color 0.2s ease;
            margin-bottom: 1rem;
        }

        .btn-login:hover {
            background: #333;
        }

        .register-link {
            text-align: center;
            padding-top: 1rem;
            border-top: 1px solid #eee;
        }

        .register-link a {
            color: #000;
            text-decoration: none;
            font-weight: 500;
        }

        .register-link a:hover {
            text-decoration: underline;
        }

        .error {
            background: #f8f8f8;
            color: #d00;
            padding: 0.75rem;
            margin-bottom: 1rem;
            border-left: 3px solid #d00;
        }




    </style>
</head>
<body>


    <div class="login-container">
        <h1 class="form-title">Connexion</h1>
        <p class="form-subtitle">Accédez à votre espace professionnel</p>

        <% if (request.getAttribute("error") != null) { %>
            <div class="error">
                <%= request.getAttribute("error") %>
            </div>
        <% } %>

        <form action="auth" method="post">
            <input type="hidden" name="action" value="login">
            


            <div class="form-group">
                <label for="email">Email</label>
                <input type="email" id="email" name="email" class="form-input" required>
            </div>

            <div class="form-group">
                <label for="password">Mot de passe</label>
                <input type="password" id="password" name="password" class="form-input" required>
            </div>

            <button type="submit" class="btn-login">Se connecter</button>
        </form>

        <div class="register-link">
            <p>Pas de compte ? <a href="register.jsp">S'inscrire</a></p>
        </div>
    </div>


</body>
</html>