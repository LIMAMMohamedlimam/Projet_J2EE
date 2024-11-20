<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <title>JSP - Hello World</title>
</head>
<body>
<script>
    // Function to save the token to local storage
    function saveTokenToLocalStorage(jwt) {
        document.cookie = "jwt" + jwt
        console.log("Token saved to local storage");
    }
</script>
<h1><%= "Hello World!" %>
</h1>

<h1>Request Data</h1>

<script>
    // Retrieve the token from the JSP request
    const token = {hello: 'world'};
    if (token) {
        saveTokenAsCookie(token);
        console.log("Saved!");
    } else {
        console.log("No token parameter found in the request.");
    }
</script>
<br/>
<a href="hello-servlet">Hello Servlet</a>
</body>
</html>