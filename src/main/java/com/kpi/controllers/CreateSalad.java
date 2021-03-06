package com.kpi.controllers;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.ListMultimap;
import com.kpi.dao.DaoFactory;
import com.kpi.dao.IngredientDAO;
import com.kpi.dao.SaladDAO;
import com.kpi.models.Ingredient;
import com.kpi.models.Salad;
import com.kpi.service.IngredientService;
import com.kpi.service.SaladService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.*;
import java.util.stream.Collectors;

@WebServlet(name = "CreateSalad", urlPatterns = "/createSalad")
public class CreateSalad extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        DaoFactory daoFactory = new DaoFactory();
        IngredientDAO ingredientService = daoFactory.getIngredientDAO();

        String newTitle = request.getParameter("title");

        if (newTitle.isEmpty())
            request.getRequestDispatcher("/salads").forward(request, response);

        List<Long> idList = Arrays.asList(request.getParameterValues("ingredients")).stream().map(Long::parseLong).collect(Collectors.toList());

        Salad salad = new Salad();
        salad.setTitle(newTitle);

        try {

            for (Long currentId : idList
                    ) {
                Ingredient ingredient = ingredientService.getById(currentId);
                if (ingredient != null) {
                    ingredient.addSalad(salad);
                    ingredientService.update(ingredient);

                    salad.addIngredient(ingredient);
                }
            }

            response.setContentType("text/html");
            PrintWriter out = response.getWriter();
            out.println("<h1>Success</h1>");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        DaoFactory daoFactory = new DaoFactory();
        IngredientDAO ingredientService = daoFactory.getIngredientDAO();

        try {
            List<Ingredient> ingredientList = ingredientService.getAll();

            ListMultimap<String, Ingredient> resultsMap = ArrayListMultimap.create();

            for (Ingredient i : ingredientList
                    ) {
                resultsMap.put(i.getTypeIngredient().toString(), i);
            }

            request.setAttribute("ingrList", resultsMap);

            request.getRequestDispatcher("/Views/createsalad.jsp").forward(request, response);

        } catch (SQLException e) {
            e.printStackTrace();
        }


    }
}
