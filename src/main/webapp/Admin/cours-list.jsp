<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Course List</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            margin: 0;
            padding: 0;
            background-color: #f4f4f9;
        }
        .container {
            max-width: 1200px;
            margin: 20px auto;
            padding: 20px;
            background: white;
            border-radius: 10px;
            box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
        }
        .header {
            text-align: center;
            margin-bottom: 20px;
            font-size: 2rem;
            font-weight: bold;
            color: #333;
        }
        form {
            margin-bottom: 20px;
            display: flex;
            justify-content: space-between;
            align-items: center;
        }
        input[type="text"] {
            flex: 1;
            padding: 10px;
            font-size: 1rem;
            border: 1px solid #ccc;
            border-radius: 5px;
            margin-right: 10px;
        }
        button {
            padding: 10px 20px;
            font-size: 1rem;
            border: none;
            border-radius: 5px;
            background-color: #2e86c1;
            color: white;
            cursor: pointer;
        }
        button:hover {
            background-color: #1b4f72;
        }
        table {
            width: 100%;
            border-collapse: collapse;
            margin-top: 20px;
        }
        th, td {
            padding: 10px;
            text-align: left;
            border: 1px solid #ddd;
        }
        th {
            background-color: #2e86c1;
            color: white;
        }
        tr:nth-child(even) {
            background-color: #f2f2f2;
        }
        tr:hover {
            background-color: #ddd;
        }
        a {
            color: #2e86c1;
            text-decoration: none;
            font-weight: bold;
        }
        a:hover {
            text-decoration: underline;
        }
    </style>
</head>
<body>
<div class="container">
    <div class="header">Course List</div>
    <a href="CoursServlet?action=new" style="display: inline-block; margin-bottom: 20px; font-weight: bold;">Add New Course</a>
    <form method="get" action="CoursServlet">
        <input type="hidden" name="action" value="search">
        <input type="text" name="keyword" placeholder="Search by subject...">
        <button type="submit">Search</button>
    </form>
    <table>
        <thead>
        <tr>
            <th>ID</th>
            <th>Subject</th>
            <th>Teacher</th>
            <th>Day</th>
            <th>Time</th>
            <th>Actions</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach var="cours" items="${coursList}">
            <tr>
                <td>${cours.idCours}</td>
                <td>${cours.matiere.nom}</td>
                <td>${cours.enseignant.nom}</td> <!-- Utilisation de la relation ManyToOne -->
                <td>${cours.jour}</td>
                <td>${cours.horaire}</td>
                <td>
                    <a href="CoursServlet?action=edit&id=${cours.idCours}">Edit</a> |
                    <a href="CoursServlet?action=delete&id=${cours.idCours}" onclick="return confirm('Are you sure?');">Delete</a> |
                    <a href="CoursServlet?action=assign&idCours=${cours.idCours}">Assign</a>
                </td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
</div>
</body>
</html>
