<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<html>
<head>
    <title>Teacher</title>
</head>
<body>

    <form:form method="POST" action="">
        <table>
            <tr>
                <td><form:label path="name">Name</form:label></td>
                <td><form:input path="name" /></td>
            </tr>
            
            <tr>
                <td><form:label path="id">id</form:label></td>
                <td><form:input path="id" /></td>
            </tr>
            <tr>
                <td colspan="2">
                    <input type="submit" value="Add" onclick="this.form.action='/CustomORM/add';">
                    <input type="submit" value="update" onclick="this.form.action='/CustomORM/update';">
                    <input type="submit" value="delete" onclick="this.form.action='/CustomORM/delete';">
                    <input type="submit" value="view" onclick="this.form.action='/CustomORM/view';">
                </td>
            </tr>
        </table>  
    </form:form>
    
</body>
</html>