<!DOCTYPE html>
<html>
<head>
    <title>Enseignant Dashboard</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/styles.css">
</head>
<body>
<div class="dashboard">
    <h1>Bienvenue au Dashboard Enseignant</h1>
    <div class="cards">
        <div class="card">
            <a href="CoursServlet?action=myCourses">
                <img src="${pageContext.request.contextPath}courses.jpeg" alt="Courses">
                <p>Voir mes cours</p>
            </a>
        </div>
        <div class="card">
            <a href="NotesServlet?action=list">
                <img src="${pageContext.request.contextPath}results.png" alt="Notes">
                <p>Saisir des notes</p>
            </a>
        </div>
        <div class="card">
            <a href="ResultatServlet?action=resultsByCourse">
                <img src="${pageContext.request.contextPath}results.png" alt="Results">
                <p>Voir les résultats des cours</p>
            </a>
        </div>
    </div>
</div>
</body>
</html>
