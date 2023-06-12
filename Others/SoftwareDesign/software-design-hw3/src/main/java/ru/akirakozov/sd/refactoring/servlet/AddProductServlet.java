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
public class AddProductServlet extends HttpServlet {

    private static final String PRODUCT_NAME_PARAMETER = "name";
    private static final String PRODUCT_PRICE_PARAMETER = "price";

    private final Controller controller;
    private final ResponseBuilder responseBuilder;

    public AddProductServlet(Controller controller, ResponseBuilder responseBuilder) {
        this.controller = controller;
        this.responseBuilder = responseBuilder;
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Product product = parseRequest(request);
        controller.addProducts(List.of(product));
        response.setStatus(HttpServletResponse.SC_OK);
        response.setContentType(responseBuilder.getContentType());
        response.getWriter().println(responseBuilder.createOKTemplate());
    }

    private Product parseRequest(HttpServletRequest request) {
        String name = request.getParameter(PRODUCT_NAME_PARAMETER);
        long price = Long.parseLong(request.getParameter(PRODUCT_PRICE_PARAMETER));
        return new Product(name, price);
    }
}
