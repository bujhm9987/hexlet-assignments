package exercise.servlet;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.util.List;
import java.util.Map;

import java.nio.file.Paths;
import java.nio.file.Path;
import java.nio.file.Files;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.apache.commons.lang3.ArrayUtils;

public class UsersServlet extends HttpServlet {

    @Override
    public void doGet(HttpServletRequest request,
                      HttpServletResponse response)
                throws IOException, ServletException {

        String pathInfo = request.getPathInfo();

        if (pathInfo == null) {
            showUsers(request, response);
            return;
        }

        String[] pathParts = pathInfo.split("/");
        String id = ArrayUtils.get(pathParts, 1, "");

        showUser(request, response, id);
    }

    private List getUsers() throws JsonProcessingException, IOException {
        // BEGIN
        File file = new File("src/main/resources/users.json");
        String users = Files.readString(file.toPath());
        ObjectMapper mapper = new ObjectMapper();
        List<Map<String, String>> listUser = mapper.readValue(users, new TypeReference<>() { });
        return listUser;
        // END
    }

    private void showUsers(HttpServletRequest request,
                          HttpServletResponse response)
                throws IOException {

        // BEGIN
        String pathInfo = request.getPathInfo();
        List<Map<String, String>> users = getUsers();
        if (pathInfo == null) {
            StringBuilder body = new StringBuilder();
            body.append("""
                    <!DOCTYPE html>
                    <html lang=\"ru\">
                        <head>
                            <meta charset=\"UTF-8\">
                            <title>Example application | Users</title>
                            <link rel=\"stylesheet\" href=\"mysite.css\">
                        </head>
                        <body>
                          <table>
                                
                    """);
            for (Map<String, String> user : users) {
                body.append("<tr><td>" + user.get("id") + "</td>" +
                        "<td><a href=/users/" + user.get("id") + ">" +
                        user.get("firstName") + " " + user.get("lastName") + "</a></td></tr>");
            }
            body.append("</table></body></html>");
            response.setContentType("text/html;charset=UTF-8");

            PrintWriter out = response.getWriter();
            out.println(body);
        }
        // END
    }

    private void showUser(HttpServletRequest request,
                         HttpServletResponse response,
                         String id)
                 throws IOException {

        // BEGIN
        List<Map<String, String>> users = getUsers();
        StringBuilder body = new StringBuilder();
        for (Map<String, String> user : users) {
            if (user.get("id").equals(id)) {
                body.append("""
                    <!DOCTYPE html>
                    <html lang=\"ru\">
                        <head>
                            <meta charset=\"UTF-8\">
                            <title>Example application | Users</title>
                            <link rel=\"stylesheet\" href=\"mysite.css\">
                        </head>
                        <body>
                          <table border="1">
                    """ + "<tr><td>id</td><td>" + user.get("id") + "</td><tr>" +
                        "<tr><td>firstName</td><td>" + user.get("firstName") + "</td></tr>" +
                        "<tr><td>lastName</td><td>" + user.get("lastName") + "</td></tr>" +
                        "<tr><td>email</td><td>" + user.get("email") + "</td></tr>" +
                        "</table></body></html>"
                );
            }
        }
        if (body.isEmpty()) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
        } else {
            PrintWriter out = response.getWriter();
            out.println(body);
        }
        // END
    }
}
