<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <style>
        body {
            font-family: Arial, sans-serif;
            margin: 0;
            padding: 0;
            background-color: #f9f9f9;
        }

        .navbar {
            background-color: #007BFF;
            display: flex;
            align-items: center;
            justify-content: space-between;
            padding: 10px 20px;
            color: white;
        }

        .navbar .left {
            flex: 0 0 0;
        }

        .navbar .menu {
            display: flex;
            align-items: center;
            justify-content: center;
            flex: 1;
            gap: 50px;
        }

        .navbar a {
            color: white;
            text-decoration: none;
            padding: 10px 20px;
            border-radius: 5px;
            transition: background-color 0.3s;
        }

        .navbar .dropdown {
            position: relative;
        }

        .navbar .dropdown:hover .dropdown-menu {
            display: block;
        }

        .dropdown-menu {
            display: none;
            position: absolute;
            background-color: white;
            min-width: 200px;
            top: 100%;
            left: 0;
            box-shadow: 0 8px 16px rgba(0, 0, 0, 0.2);
            border-radius: 5px;
            z-index: 10;
            padding: 10px;
            gap: 5px;
            flex-direction: column;
            align-items: flex-start;
        }

        .dropdown:hover .dropdown-menu,
        .dropdown-menu:hover {
            display: flex; /* Ensure the menu remains visible */
        }


        .dropdown-menu a {
            color: black;
            padding: 10px;
            text-decoration: none;
            display: block;
        }

        .dropdown-menu a:hover {
            background-color: #f1f1f1;
        }
        .navbar .dropdown:hover .dropdown-menu,
        .navbar .dropdown .dropdown-menu:hover {
            display: block;
        }

        .navbar .logout {
            flex: 0 0 auto;
            padding: 10px 20px;
            background-color: #FF4136;
            border-radius: 5px;
            font-weight: bold;
            text-align: center;
        }

        .navbar .logout:hover {
            background-color: #e32f25;
        }

        .card img {
            max-width: 100px;
            margin-bottom: 10px;
        }

        .card h3 {
            font-size: 16px;
            margin: 10px 0;
        }

    </style>
</head>
<body>
<div class="navbar">
    <div class="left">
        <a href="${pageContext.request.contextPath}/AdminDashboardServlet">Dashboard</a>
    </div>
    <div class="menu">
        <div class="dropdown">
            <a href="#">Gestion des étudiants</a>
            <div class="dropdown-menu">
                <a href="etudiant-list.jsp">Ajouter / Modifier / Supprimer un étudiant</a>
                <a href="#">Inscrire un étudiant à un cours</a>
                <a href="#">Consultation des données d’un étudiant</a>
            </div>
        </div>
        <div class="dropdown">
            <a href="#">Gestion des emploi du temps</a>
            <div class="dropdown-menu">
                <a href="#">Création, modification, suppression de cours</a>
                <a href="#">Affichage de la liste des cours</a>
                <a href="#">Attribution des cours aux enseignants et aux étudiants</a>
            </div>
        </div>
        <div class="dropdown">
            <a href="#">Gestion des enseignants</a>
            <div class="dropdown-menu">
                <a href="#">Ajout / Modification / Suppression d’un enseignant</a>
                <a href="#">Consulter les données d’un prof</a>
                <a href="#">Affectation des enseignants au cours</a>
            </div>
        </div>
        <div class="dropdown">
            <a href="#">Gestion des résultats</a>
        </div>
    </div>
    <a href="#" class="logout">Déconnexion</a>
</div>
</body>
</html>
