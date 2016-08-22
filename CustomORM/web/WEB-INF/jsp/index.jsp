<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Custom ORM</title>
    </head>

    <body>
        <%! String entity;%>
        <form:form method="GET" action="">
            <input type="submit" value="Student" onclick="this.form.action = '/CustomORM/returnView/Student';">
            <input type="submit" value="Teacher" onclick="this.form.action = '/CustomORM/returnView/Teacher';">
            <input type="submit" value="Course" onclick="this.form.action = '/CustomORM/returnView/Course';">
        </form:form>
    </body>
</html>
