/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package prj301demo.servlet;

import prj301demo.utils.DBUtils;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author DUNGHUYNH
 */
public class StudentListServlet extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet StudentListServlet</title>");            
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Student List </h1>");
            String sortCol = request.getParameter("sortCol");
            String input = request.getParameter("input");
            if (input == null) input = "";
            out.println("<form>");
            out.println("<input name='input' type='text' accesskey='q' value="+input+">");
            out.println("<input value='Search' type='submit' style='border-radius: 6px;'>");
            out.println("</form>");
            out.println("<table border='1px' style='border-collapse: collapse; margin-top: 1rem;'");
            out.println("<tr><th>ID</th>");
            out.println("<th><a href='?sortCol=firstName&input="+input+"'>First Name</a></th>");
            out.println("<th><a href='?sortCol=lastName&input="+input+"'>Last Name</a></th>");
            out.println("<th>Age</th></tr>");
            try {
                Connection con = DBUtils.getConnection();
                String query = "SELECT id, firstName, lastName, age FROM student";
                if (!input.isEmpty()) query += " WHERE firstName LIKE ? OR lastName LIKE ? ";
                if (sortCol != null) query += " ORDER BY " + sortCol;
                PreparedStatement st = con.prepareStatement(query);
                if (!input.isEmpty()) {
                    st.setString(1,"%" + input + "%");
                    st.setString(2,"%" + input + "%");
                }
                ResultSet rs = st.executeQuery();
                if (rs != null) {
                    while (rs.next()) {
                        int id = rs.getInt("id");
                        String firstName = rs.getString("firstName");
                        String lastName = rs.getString("lastName");
                        int age = rs.getInt("age");
                        out.println("<tr><td>" + id        + "</td>");
                        out.println("<td>"     + firstName + "</td>");
                        out.println("<td>"     + lastName  + "</td>");
                        out.println("<td>"     + age       + "</td></tr>");
                    }
                }
            } catch (SQLException ex) {
                System.out.println("SQL Exception in StudentListServlet" + ex.getMessage());
                ex.printStackTrace();
            } catch (Exception ex) {
                System.out.println("Exception in StudentListServlet" + ex.getMessage());
                ex.printStackTrace();
            }
            out.println("</table>");
            out.println("</body>");
            out.println("</html>");
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
