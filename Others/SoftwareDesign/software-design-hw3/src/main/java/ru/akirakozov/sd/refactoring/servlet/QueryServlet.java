package ru.akirakozov.sd.refactoring.servlet;

import java.io.IOException;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ru.akirakozov.sd.refactoring.controller.Controller;
import ru.akirakozov.sd.refactoring.view.ResponseBuilder;

/**
 * @author akirakozov
 */
public class QueryServlet extends HttpServlet {

    private static final String COMMAND_PARAMETER = "command";

    private static final String MAX_COMMAND = "max";
    private static final String MIN_COMMAND = "min";
    private static final String SUM_COMMAND = "sum";
    private static final String COUNT_COMMAND = "count";

    private final Controller controller;
    private final ResponseBuilder responseBuilder;

    public QueryServlet(Controller controller, ResponseBuilder responseBuilder) {
        this.controller = controller;
        this.responseBuilder = responseBuilder;
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String command = request.getParameter(COMMAND_PARAMETER);
        String responseContent = getResponseContent(command);
        response.setStatus(HttpServletResponse.SC_OK);
        response.setContentType(responseBuilder.getContentType());
        response.getWriter().println(responseContent);
    }

    private String getResponseContent(String command) {
        return switch(command) {
            case MAX_COMMAND -> responseBuilder.createMaxTemplate(controller.getMaxPriceProduct().get(0));
            case MIN_COMMAND -> responseBuilder.createMinTemplate(controller.getMinPriceProduct().get(0));
            case SUM_COMMAND -> responseBuilder.createSumTemplate(controller.getPriceSum());
            case COUNT_COMMAND -> responseBuilder.createCountTemplate(controller.getProductsCount());
            default -> responseBuilder.createUnknownCommandTemplate(command);
        };
    }
}
