<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!-- BEGIN -->
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <title>Company</title>
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.0/dist/css/bootstrap.min.css"
            rel="stylesheet"
            integrity="sha384-KyZXEAg3QhqLMpG8r+8fhAXLRk2vvoC2f3B09zVXn8CA5QIVfZOJ3BCsw2P0p/We"
            crossorigin="anonymous">
        <style type="text/css">
           BODY {
            background: white;
           }
           TABLE {
            width: 250px;
            border-collapse: collapse;
            border: 2px solid black;
           }
           TD, TH {
            padding: 3px;
            border: 1px solid black;
            text-align: left;
           }
        </style>
    </head>
    <body>
    <h4>Delete user ${user.get("firstName")} ${user.get("lastName")} ?</h4>
    <form action="/users/delete?id=${user.get("id")}" method="post">
        <button type="submit" class="btn btn-danger">Удалить</button>
    </form>
    </body>
</html>
<!-- END -->
