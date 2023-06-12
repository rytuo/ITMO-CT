package ru.akirakozov.sd.refactoring.servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ru.akirakozov.sd.refactoring.controller.Controller;
import ru.akirakozov.sd.refactoring.entity.Product;
import ru.akirakozov.sd.refactoring.view.ResponseBuilder;

/**
 * @author akirakozov
 */
public class GetProductsServlet extends HttpServlet {

    private final Controller controller;
    private final ResponseBuilder responseBuilder;

    public GetProductsServlet(Controller controller, ResponseBuilder responseBuilder) {
        this.controller = controller;
        this.responseBuilder = responseBuilder;
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        List<Product> products = controller.getAllProducts();
        response.setStatus(HttpServletResponse.SC_OK);
        response.setContentType(responseBuilder.getContentType());
        response.getWriter().println(responseBuilder.createGetTemplate(products));
    }
}
