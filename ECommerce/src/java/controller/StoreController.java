/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import dal.ProductDAO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;
import model.Product;

/**
 *
 * @author Lenovo
 */
@WebServlet(name = "StoreController", urlPatterns = {"/store"})
public class StoreController extends HttpServlet {

    private static final int PAGE_SIZE = 9;

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
            out.println("<title>Servlet StoreController</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet StoreController at " + request.getContextPath() + "</h1>");
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
        ProductDAO dao = new ProductDAO();

        // Get query params
        String category = request.getParameter("category");
        String brand = request.getParameter("brand");
        String priceMinStr = request.getParameter("price-min");
        String priceMaxStr = request.getParameter("price-max");
        String search = request.getParameter("search");
        String sort = request.getParameter("sort");

        int priceMin = 0;
        int priceMax = 100000000;

        // Convert price parameters to Long if provided
        if (priceMinStr != null && !priceMinStr.isEmpty()) {
            priceMin = Integer.parseInt(priceMinStr);
        }
        if (priceMaxStr != null && !priceMaxStr.isEmpty()) {
            priceMax = Integer.parseInt(priceMaxStr);
        }
        if (sort == null) {
            sort = "default";
        }

        // Category product count
        List<Object[]> categoryList = dao.getCategoryProductCount();
        request.setAttribute("categoryList", categoryList);

        //Brands
        List<Object[]> brandList = new ArrayList<>();
        brandList.add(new Object[]{"SAMSUNG"});
        brandList.add(new Object[]{"APPLE"});
        brandList.add(new Object[]{"ASUS"});
        brandList.add(new Object[]{"DELL"});
        brandList.add(new Object[]{"HP"});
        brandList.add(new Object[]{"LENOVO"});
        brandList.add(new Object[]{"SONY"});
        brandList.add(new Object[]{"MICROSOFT"});
        brandList.add(new Object[]{"HUAWEI"});
        request.setAttribute("brandList", brandList);

        // Pagination
        String pageStr = request.getParameter("page");
        int currentPage = 1;
        if (pageStr != null) {
            currentPage = Integer.parseInt(pageStr);
        }

        int totalProducts = dao.getTotalProducts(category, brand, priceMin, priceMax, search);
        int totalPages = (int) Math.ceil((double) totalProducts / PAGE_SIZE);

        List<Object[]> productList = dao.getFilteredAndSortedProducts(currentPage, PAGE_SIZE, category, brand, priceMin, priceMax, search, sort);

        // Set attributes for the JSP
        request.setAttribute("productList", productList);
        request.setAttribute("currentPage", currentPage);
        request.setAttribute("totalPages", totalPages);

        //Get top bought products
        List<Object[]> topBoughtProducts = dao.getTopMostBoughtProducts(6);
        request.setAttribute("topBoughtProducts", topBoughtProducts);

        request.getRequestDispatcher("store.jsp").forward(request, response);
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
