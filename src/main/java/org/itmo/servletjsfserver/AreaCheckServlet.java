package org.itmo.servletjsfserver;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.itmo.servletjsfserver.model.Attempt;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Date;

@WebServlet("pages/addAttempt")
public class AreaCheckServlet extends HttpServlet {

    private static boolean checkIfDotBelongsToFigure(double x, double y, double r) {
        // окружность
        if (x <= 0d && y <= 0d && (x * x + y * y <= (r/2) * (r/2))) return true;

        // треугольник
        if ((2*x + r) >= y && x <= 0d && y >= 0d) return true;

        // прямоугольник
        if (y <= 0d && x <= r && x >= 0d && y >= (float) -r) return true;

        return false;
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        long start = System.nanoTime();
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        double x = Double.parseDouble(request.getParameter("x"));
        double y = Double.parseDouble(request.getParameter("y"));
        double r = Double.parseDouble(request.getParameter("r"));
        boolean result = checkIfDotBelongsToFigure(x,y,r);
        long executionTime = System.nanoTime() - start;

        if (y < -5 || y > 3) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Failed to add attempt");
            return;
        }

        Attempt attempt = new Attempt(x, y, r);
        attempt.setResult(result);
        attempt.setCreatedAt(new Date());
        attempt.setExecutionTime(executionTime);

        if (DbHelper.addAttempt(attempt))  {
            PrintWriter out = response.getWriter();
            out.write("{\"result\": \"%s\"}".formatted(result ? "true" : "false"));
            out.flush();
            return;
        }
        response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Failed to add attempt");
    }
}
