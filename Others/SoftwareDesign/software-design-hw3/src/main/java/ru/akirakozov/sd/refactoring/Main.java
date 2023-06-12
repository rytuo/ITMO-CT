package ru.akirakozov.sd.refactoring;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;

import ru.akirakozov.sd.refactoring.controller.Controller;
import ru.akirakozov.sd.refactoring.controller.sql.SQLController;
import ru.akirakozov.sd.refactoring.controller.sql.SQLExecutor;
import ru.akirakozov.sd.refactoring.controller.sql.SQLResultCollector;
import ru.akirakozov.sd.refactoring.servlet.AddProductServlet;
import ru.akirakozov.sd.refactoring.servlet.GetProductsServlet;
import ru.akirakozov.sd.refactoring.servlet.QueryServlet;
import ru.akirakozov.sd.refactoring.view.HtmlResponseBuilder;
import ru.akirakozov.sd.refactoring.view.ResponseBuilder;

/**
 * @author akirakozov
 */
public class Main {

    private static final String DATABASE_URL = "jdbc:sqlite:prod.db";

    private static final int SERVER_PORT = 8081;

    private static final String CONTEXT_PATH = "/";

    private static final String ADD_PRODUCT_PATH = "/add-product";
    private static final String GET_PRODUCTS_PATH = "/get-products";
    private static final String QUERY_PATH = "/query";

    public static void main(String[] args) throws Exception {
        Controller controller = new SQLController(
                new SQLExecutor(DATABASE_URL),
                new SQLResultCollector()
        );
        ResponseBuilder responseBuilder = new HtmlResponseBuilder();

        controller.init();

        Server server = new Server(SERVER_PORT);

        ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);
        context.setContextPath(CONTEXT_PATH);
        server.setHandler(context);

        context.addServlet(new ServletHolder(new AddProductServlet(controller, responseBuilder)), ADD_PRODUCT_PATH);
        context.addServlet(new ServletHolder(new GetProductsServlet(controller, responseBuilder)), GET_PRODUCTS_PATH);
        context.addServlet(new ServletHolder(new QueryServlet(controller, responseBuilder)), QUERY_PATH);

        server.start();
        server.join();
    }
}
