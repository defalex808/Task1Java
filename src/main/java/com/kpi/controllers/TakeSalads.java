package com.kpi.controllers;

import com.kpi.dao.DaoFactory;
import com.kpi.dao.IngredientDAO;
import com.kpi.dao.SaladDAO;
import com.kpi.models.Ingredient;
import com.kpi.models.Salad;
import com.kpi.models.TypeIngredient;
import com.kpi.service.IngredientService;
import com.kpi.service.SaladService;
import com.kpi.service.TypeIngredientService;
import com.kpi.util.HibernateUtil;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.Type;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@WebServlet(name = "TakeSalad", urlPatterns = "/salads")
public class TakeSalads extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        DaoFactory daoFactory = new DaoFactory();
        SaladDAO saladService = daoFactory.getSaladDAO();
        IngredientDAO ingredientService = daoFactory.getIngredientDAO();

        try {
            List<Salad> salads = saladService.getAll();
            List<Ingredient> ingredients = ingredientService.getAll();

            request.setAttribute("salads", salads);
            request.setAttribute("ingredients", ingredients);
            request.getRequestDispatcher("Views/salads.jsp").forward(request, response);

        } catch (SQLException e) {
            e.printStackTrace();
        }


    }
}
