<%-- @elvariable id="etudiants" type="java.util.List<com.test.MyHyber.Entity.Etudiant>" --%>
<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ include file="header.jsp" %>
<!DOCTYPE html>
<html>
<head>
    <title>Liste des Étudiants</title>
</head>
<body>
<h1>Liste des Étudiants</h1>
<a href="EtudiantServlet?action=new">Ajouter un étudiant</a>
<table border="1">
    <thead>
    <tr>
        <th>ID</th>
        <th>Nom</th>
        <th>Prénom</th>
        <th>Date de naissance</th>
        <th>Email</th>
        <th>Contact</th>
        <th>Mot de Passe</th>
        <th>Role</th>
        <th>Actions</th>
    </tr>
    </thead>
    <tbody>
    <c:choose>
        <c:when test="${not empty etudiants}">
            <c:forEach var="etudiant" items="${etudiants}">
                <tr>
                    <td>${etudiant.utilisateur.id}</td>
                    <td>${etudiant.utilisateur.nom}</td>
                    <td>${etudiant.utilisateur.prenom}</td>
                    <td>${etudiant.utilisateur.dateDeNaissance}</td>
                    <td>${etudiant.utilisateur.email}</td>
                    <td>${etudiant.utilisateur.contact}</td>
                    <td>${etudiant.utilisateur.motDePasse}</td>
                    <td>${etudiant.utilisateur.role}</td>
                    <td>
                        <a href="EtudiantServlet?action=edit&id=${etudiant.id}">Modifier</a>
                        <a href="EtudiantServlet?action=delete&id=${etudiant.id}" onclick="return confirm('Êtes-vous sûr ?');">Supprimer</a>
                    </td>
                </tr>
            </c:forEach>
        </c:when>
        <c:otherwise>
            <tr>
                <td colspan="9">Aucun étudiant trouvé. Vérifiez la logique du servlet.</td>
            </tr>
        </c:otherwise>
    </c:choose>
    </tbody>
</table>
</body>
</html>
