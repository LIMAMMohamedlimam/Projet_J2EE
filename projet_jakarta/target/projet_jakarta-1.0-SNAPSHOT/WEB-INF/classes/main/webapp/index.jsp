<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <title>JSP - Hello World</title>
</head>
<body>
<script>
    // Function to save the token to local storage
    function saveTokenToLocalStorage(token) {
        localStorage.setItem("token", token);
        console.log("Token saved to local storage");
    }
</script>
<h1><%= "Hello World!" %>
</h1>

<h1>Request Data</h1>
<ul>
    <%-- Display request parameters --%>
    <%
        java.util.Enumeration<String> attributeNames = request.getAttributeNames();
        while (attributeNames.hasMoreElements()) {
            String attributeName = attributeNames.nextElement() ;
            String attributeValue = request.getAttribute(attributeName).toString();
    %>
    <li><strong><%= attributeName %>:</strong> <%= attributeValue %></li>
    <% } %>
</ul>
<script>
    // Retrieve the token from the JSP request
    const token = "<%= request.getAttribute("token") != null ? request.getAttribute("token") : "" %>";
    if (token) {
        saveTokenToLocalStorage(token);
        console.log("Saved!");
    } else {
        console.log("No token parameter found in the request.");
    }
</script>
<br/>
<a href="hello-servlet">Hello Servlet</a>
</body>
</html>