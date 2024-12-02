<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Course Form</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            margin: 0;
            padding: 0;
            background-color: #f4f4f9;
        }
        .container {
            max-width: 800px;
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
        label {
            display: block;
            font-weight: bold;
            margin-top: 15px;
        }
        input, select, button {
            width: 100%;
            padding: 10px;
            font-size: 1rem;
            border: 1px solid #ccc;
            border-radius: 5px;
            margin-top: 5px;
        }
        button {
            background-color: #2e86c1;
            color: white;
            cursor: pointer;
            margin-top: 20px;
        }
        button:hover {
            background-color: #1b4f72;
        }
    </style>
</head>
<body>
<div class="container">
    <div class="header">${cours == null ? "Add New Course" : "Edit Course"}</div>
    <form method="post" action="CoursServlet">
        <input type="hidden" name="id" value="${cours != null ? cours.idCours : ''}">

        <label for="matiere">Subject:</label>
        <input type="text" id="matiere" name="matiere" placeholder="Enter subject name" value="${cours != null ? cours.matiere.nom : ''}">

        <label for="date">Date:</label>
        <input type="date" id="date" name="date" value="${cours != null ? cours.date : ''}">

        <label for="jour">Day:</label>
        <input type="text" id="jour" name="jour" value="${cours != null ? cours.jour : ''}">

        <label for="horaire">Time:</label>
        <input type="text" id="horaire" name="horaire" value="${cours != null ? cours.horaire : ''}">

        <button type="submit">Save</button>
    </form>
</div>
</body>
</html>
