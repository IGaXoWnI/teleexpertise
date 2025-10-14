<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Inscription - MedExpert</title>
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

        .register-container {
            background: #fff;
            border: 1px solid #ddd;
            width: 100%;
            max-width: 500px;
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

        .form-row {
            display: grid;
            grid-template-columns: 1fr 1fr;
            gap: 1rem;
            margin-bottom: 1rem;
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

        .role-selector {
            display: grid;
            grid-template-columns: repeat(3, 1fr);
            gap: 0.5rem;
        }

        .role-option {
            padding: 0.75rem;
            border: 1px solid #ddd;
            text-align: center;
            cursor: pointer;
            transition: all 0.2s ease;
            background: #fff;
        }

        .role-option:hover {
            border-color: #000;
        }

        .role-option.active {
            border-color: #000;
            background: #000;
            color: #fff;
        }

        .role-option input {
            display: none;
        }

        .btn-register {
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

        .btn-register:hover {
            background: #333;
        }

        .login-link {
            text-align: center;
            padding-top: 1rem;
            border-top: 1px solid #eee;
        }

        .login-link a {
            color: #000;
            text-decoration: none;
            font-weight: 500;
        }

        .login-link a:hover {
            text-decoration: underline;
        }

        .error {
            background: #f8f8f8;
            color: #d00;
            padding: 0.75rem;
            margin-bottom: 1rem;
            border-left: 3px solid #d00;
        }

        .success {
            background: #f8f8f8;
            color: #0a0;
            padding: 0.75rem;
            margin-bottom: 1rem;
            border-left: 3px solid #0a0;
        }

        .specialiste-fields {
            border: 1px solid #ddd;
            padding: 1rem;
            margin-bottom: 1rem;
            background: #f9f9f9;
        }

        select.form-input {
            width: 100%;
            padding: 0.75rem;
            border: 1px solid #ddd;
            font-size: 1rem;
            transition: border-color 0.2s ease;
            background: #fff;
        }

        .creneaux-grid {
            display: grid;
            grid-template-columns: repeat(2, 1fr);
            gap: 0.5rem;
        }

        .creneau-option {
            display: flex;
            align-items: center;
            padding: 0.5rem;
            border: 1px solid #ddd;
            cursor: pointer;
            transition: all 0.2s ease;
            font-size: 0.9rem;
        }

        .creneau-option:hover {
            border-color: #000;
            background: #f5f5f5;
        }

        .creneau-option input {
            margin-right: 0.5rem;
        }



        @media (max-width: 768px) {
            .form-row {
                grid-template-columns: 1fr;
            }
            
            .role-selector {
                grid-template-columns: 1fr;
            }
        }
    </style>
</head>
<body>


    <div class="register-container">
        <h1 class="form-title">Inscription</h1>
        <p class="form-subtitle">Créez votre compte professionnel</p>

        <% if (request.getAttribute("error") != null) { %>
            <div class="error">
                <%= request.getAttribute("error") %>
            </div>
        <% } %>

        <% if (request.getAttribute("success") != null) { %>
            <div class="success">
                <%= request.getAttribute("success") %>
            </div>
        <% } %>

        <form action="auth" method="post">
            <input type="hidden" name="action" value="register">
            
            <div class="form-row">
                <div class="form-group">
                    <label for="nom">Nom</label>
                    <input type="text" id="nom" name="nom" class="form-input" required>
                </div>

                <div class="form-group">
                    <label for="prenom">Prénom</label>
                    <input type="text" id="prenom" name="prenom" class="form-input" required>
                </div>
            </div>

            <div class="form-group">
                <label for="email">Email</label>
                <input type="email" id="email" name="email" class="form-input" required>
            </div>

            <div class="form-group">
                <label for="password">Mot de passe</label>
                <input type="password" id="password" name="password" class="form-input" required>
            </div>

            <div class="form-group">
                <label>Rôle</label>
                <div class="role-selector">
                    <label class="role-option">
                        <input type="radio" name="role" value="GENERALISTE" required>
                        Généraliste
                    </label>
                    <label class="role-option">
                        <input type="radio" name="role" value="SPECIALISTE" required>
                        Spécialiste
                    </label>
                    <label class="role-option">
                        <input type="radio" name="role" value="INFIRMIER" required>
                        Infirmier
                    </label>
                </div>
            </div>

            <div id="specialiste-fields" class="specialiste-fields" style="display: none;">
                <div class="form-group">
                    <label for="specialite">Spécialité</label>
                    <select id="specialite" name="specialite" class="form-input">
                        <option value="">Sélectionner une spécialité</option>
                        <option value="CARDIOLOGUE">Cardiologue</option>
                        <option value="PNEUMOLOGUE">Pneumologue</option>
                        <option value="DERMATOLOGUE">Dermatologue</option>
                        <option value="NEUROLOGUE">Neurologue</option>
                        <option value="ENDOCRINOLOGUE">Endocrinologue</option>
                        <option value="RADIOLOGUE">Radiologue</option>
                    </select>
                </div>

                <div class="form-group">
                    <label for="tarif">Tarif de consultation (DH)</label>
                    <input type="number" id="tarif" name="tarif" class="form-input" min="0" step="0.01" placeholder="300.00">
                </div>

                <div class="form-group">
                    <label>Créneaux de disponibilité</label>
                    <div class="creneaux-grid">
                        <label class="creneau-option">
                            <input type="checkbox" name="creneaux" value="09:00-09:30">
                            09h00 - 09h30
                        </label>
                        <label class="creneau-option">
                            <input type="checkbox" name="creneaux" value="09:30-10:00">
                            09h30 - 10h00
                        </label>
                        <label class="creneau-option">
                            <input type="checkbox" name="creneaux" value="10:00-10:30">
                            10h00 - 10h30
                        </label>
                        <label class="creneau-option">
                            <input type="checkbox" name="creneaux" value="10:30-11:00">
                            10h30 - 11h00
                        </label>
                        <label class="creneau-option">
                            <input type="checkbox" name="creneaux" value="11:00-11:30">
                            11h00 - 11h30
                        </label>
                        <label class="creneau-option">
                            <input type="checkbox" name="creneaux" value="11:30-12:00">
                            11h30 - 12h00
                        </label>
                        <label class="creneau-option">
                            <input type="checkbox" name="creneaux" value="14:00-14:30">
                            14h00 - 14h30
                        </label>
                        <label class="creneau-option">
                            <input type="checkbox" name="creneaux" value="14:30-15:00">
                            14h30 - 15h00
                        </label>
                        <label class="creneau-option">
                            <input type="checkbox" name="creneaux" value="15:00-15:30">
                            15h00 - 15h30
                        </label>
                        <label class="creneau-option">
                            <input type="checkbox" name="creneaux" value="15:30-16:00">
                            15h30 - 16h00
                        </label>
                    </div>
                </div>
            </div>

            <button type="submit" class="btn-register">Créer mon compte</button>
        </form>

        <div class="login-link">
            <p>Déjà inscrit ? <a href="login.jsp">Se connecter</a></p>
        </div>
    </div>

    <script>
        const specialisteFields = document.getElementById('specialiste-fields');
        const specialiteSelect = document.getElementById('specialite');
        const tarifInput = document.getElementById('tarif');
        
        document.querySelectorAll('.role-option').forEach(option => {
            option.addEventListener('click', function() {
                document.querySelectorAll('.role-option').forEach(opt => opt.classList.remove('active'));
                this.classList.add('active');
                this.querySelector('input').checked = true;
                
                // Show/hide specialiste fields
                const roleValue = this.querySelector('input').value;
                if (roleValue === 'SPECIALISTE') {
                    specialisteFields.style.display = 'block';
                    specialiteSelect.required = true;
                    tarifInput.required = true;
                } else {
                    specialisteFields.style.display = 'none';
                    specialiteSelect.required = false;
                    tarifInput.required = false;
                }
            });
        });
    </script>
</body>
</html>