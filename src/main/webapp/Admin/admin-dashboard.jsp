<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ include file="header.jsp" %>
<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Admin Dashboard</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            margin: 0;
            padding: 0;
            background-color: #f9f9f9;
        }

        .dashboard-container {
            display: grid;
            grid-template-columns: repeat(3, 1fr);
            grid-column-gap: 5px;
            grid-row-gap: 15px;
            margin: 0 auto;
            width: 80%;
            padding: 20px 0;
        }

        .card {
            background-color: white;
            border: 1px solid #ddd;
            border-radius: 8px;
            box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1);
            width: 200px;
            height: 200px;
            text-align: center;
            padding: 15px;
            transition: transform 0.3s, box-shadow 0.3s;
        }

        .card img {
            max-width: 100px;
            margin-bottom: 20px;
        }

        .card h3 {
            font-size: 20px;
            margin: 15px 0;
        }

        .card:hover {
            transform: translateY(-5px);
            box-shadow: 0 8px 16px rgba(0, 0, 0, 0.2);
        }
    </style>
</head>
<body>

<div class="dashboard-container">
    <div class="card">
        <a href="EtudiantServlet?action=list">
            <img src="${pageContext.request.contextPath}/icons/student.jpg" alt="Gestion des étudiants">
            <h3>Gestion des étudiants</h3>
        </a>
    </div>
    <div class="card">
        <a href="EnseignantServlet?action=list">
            <img src="${pageContext.request.contextPath}/icons/teacher.jpeg" alt="Gestion des enseignants">
            <h3>Gestion des enseignants</h3>
        </a>
    </div>
    <div class="card">
        <a href="EmploiServlet?action=list">
            <img src="${pageContext.request.contextPath}/icons/schedule.png" alt="Gestion des emplois">
            <h3>Gestion des emplois du temps</h3>
        </a>
    </div>
    <div class="card">
        <a href="EtudiantServlet?action=filter">
            <img src="${pageContext.request.contextPath}/icons/filter.png" alt="Afficher la liste des étudiants">
            <h3>Liste des étudiants</h3>
        </a>
    </div>
    <div class="card">
        <a href="CoursServlet?action=list">
            <img src="${pageContext.request.contextPath}/icons/courses.jpeg" alt="Affichage des cours">
            <h3>Affichage des cours</h3>
        </a>
    </div>
    <div class="card">
        <a href="ResultatServlet?action=list">
            <img src="${pageContext.request.contextPath}/icons/results.png" alt="Gestion des résultats">
            <h3>Gestion des résultats</h3>
        </a>
    </div>
</div>
</body>
</html>
