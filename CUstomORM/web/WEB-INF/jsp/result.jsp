<%@page import="customorm.model.Student"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<html>
<head>
    <title>Spring MVC Form Handling</title>
</head>
<body>

    <% Object obj = request.getAttribute("model"); %>

<h2> ${model['class'].simpleName} Information</h2>
   <table>
    <tr>
        <td>Name</td>
        <td>${model.name}</td>
    </tr>
     <%    
    if (obj instanceof Student ) {
%>
    <tr>
        <td>Address</td>
        <td>${model.address}</td>
    </tr>
    <% } %>
    <tr>
        <td>ID</td>
        <td>${model.id}</td>
    </tr>
</table>
   
    
</body>
</html>