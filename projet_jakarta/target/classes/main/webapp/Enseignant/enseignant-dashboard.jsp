<%@ page contentType="text/html; charset=UTF-8" language="java" %>
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
<div class="dashboard">
    <h1>Bienvenue au Dashboard Enseignant</h1>
    <div class="cards">
        <div class="card">
            <form action="${pageContext.request.contextPath}/CoursServlet" method="get">
                <input type="hidden" name="action" value="myCourses">
                <button type="submit">
                    <img src="${pageContext.request.contextPath}/icons/courses.jpeg" alt="Courses">
                    <p>Voir mes cours</p>
                </button>
            </form>
        </div>
        <div class="card">
            <form action="${pageContext.request.contextPath}/NotesServlet" method="get">
                <input type="hidden" name="action" value="list">
                <button type="submit">
                    <img src="${pageContext.request.contextPath}/icons/note.jpeg" alt="Notes">
                    <p>Saisir des notes</p>
                </button>
            </form>
        </div>
        <div class="card">
            <form action="${pageContext.request.contextPath}/ResultatServlet" method="get">
                <input type="hidden" name="action" value="resultsByCourse">
                <button type="submit">
                    <img src="${pageContext.request.contextPath}/icons/note.jpeg" alt="Results">
                    <p>Voir les résultats des cours</p>
                </button>
            </form>
        </div>
    </div>
</div>


</body>
</html>
