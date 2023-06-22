package exercise.servlet;

import exercise.Data;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.util.List;
import java.util.stream.Collectors;
import static exercise.Data.getCompanies;

public class CompaniesServlet extends HttpServlet {

    @Override
    public void doGet(HttpServletRequest request,
                      HttpServletResponse response)
                throws IOException, ServletException {

        // BEGIN
        List<String> result;

        if (request.getQueryString() == null || request.getParameter("search").equals("")) {
            result = getCompanies().stream()
                    .toList();
        } else {
            result = getCompanies().stream()
                    .filter(v -> v.contains(request.getParameter("search")))
                    .toList();
        }
        PrintWriter out = response.getWriter();
        if (result.isEmpty()) {
            out.println("Companies not found");
        } else {
            result.forEach(out::println);
        }
        // END
    }
}
