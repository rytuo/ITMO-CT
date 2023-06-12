package ru.akirakozov.sd.refactoring.servlet;

import java.net.http.HttpClient;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import ru.akirakozov.sd.refactoring.controller.Controller;
import ru.akirakozov.sd.refactoring.entity.Product;
import ru.akirakozov.sd.refactoring.view.HtmlResponseBuilder;
import ru.akirakozov.sd.refactoring.view.ResponseBuilder;

@ExtendWith(MockitoExtension.class)
public abstract class ServletTest {

    private static final String SERVER_URL = "http://localhost";
    private static final int SERVER_PORT = 8081;
    private static final String SERVER_CONTEXT_PATH = "/";

    @Mock
    Controller controller;

    ResponseBuilder responseBuilder = new HtmlResponseBuilder();

    Server server;
    HttpClient client;

    abstract void addServlet(ServletContextHandler contextHandler);

    @BeforeEach
    void beforeEach() throws Exception {
        this.server = new Server(SERVER_PORT);
        ServletContextHandler contextHandler = new ServletContextHandler(ServletContextHandler.SESSIONS);
        contextHandler.setContextPath(SERVER_CONTEXT_PATH);
        addServlet(contextHandler);
        server.setHandler(contextHandler);
        server.start();

        client = HttpClient.newHttpClient();
    }

    @AfterEach
    void afterEach() throws Exception {
        server.stop();
    }

    String getBaseUrl() {
        return SERVER_URL + ":" + SERVER_PORT;
    }

    Product createUniqueProduct() {
        long timestamp = System.currentTimeMillis();
        return new Product("test" + timestamp, timestamp);
    }
}
