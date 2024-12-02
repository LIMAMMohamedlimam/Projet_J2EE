<!DOCTYPE html>
<html>
<head>
    <title>Teacher Header</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/styles.css">
</head>
<body>
<header>
    <nav class="nav-bar">
        <ul>
            <li><a href="${pageContext.request.contextPath}Enseignant-dashboard.jsp">Dashboard</a></li>
            <li><a href="CoursServlet?action=myCourses">Mes Cours</a></li>
            <li><a href="NotesServlet?action=list">Saisir des Notes</a></li>
            <li><a href="ResultatServlet?action=resultsByCourse">Résultats des Cours</a></li>
            <li><a href="LogoutServlet">Déconnexion</a></li>
        </ul>
    </nav>
</header>
</body>
</html>
