package com.sd.sd1.actors.search;

import java.util.List;

import org.glassfish.grizzly.http.Method;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sd.sd1.model.Document;
import com.xebialabs.restito.semantics.Action;
import com.xebialabs.restito.server.StubServer;

import static com.xebialabs.restito.builder.stub.StubHttp.whenHttp;
import static com.xebialabs.restito.builder.verify.VerifyHttp.verifyHttp;
import static com.xebialabs.restito.semantics.Action.ok;
import static com.xebialabs.restito.semantics.Action.stringContent;
import static com.xebialabs.restito.semantics.Condition.get;
import static com.xebialabs.restito.semantics.Condition.method;
import static com.xebialabs.restito.semantics.Condition.parameter;
import static com.xebialabs.restito.semantics.Condition.uri;

public class SearchApiStub {

    private final ObjectMapper objectMapper = new ObjectMapper();
    private final int port;

    private StubServer stubServer;

    public SearchApiStub(int port) {
        this.port = port;
    }

    public void run() {
        stubServer = new StubServer(port).run();
    }

    public void stop() {
        stubServer.stop();
    }

    public void stub(int delayMs, String query) throws JsonProcessingException {
        whenHttp(stubServer)
                .match(get("/search"), parameter("q", query))
                .then(Action.delay(delayMs), ok(), stringContent(
                        objectMapper.writeValueAsString(new SearchActor.ResponseModel(getTestDocuments()))
                ));
    }

    public void verify(String query) {
        verifyHttp(stubServer).once(
                method(Method.GET),
                uri("/search"),
                parameter("q", query)
        );
    }

    public List<Document> getTestDocuments() {
        return List.of(
                new Document("title1"),
                new Document("title2"),
                new Document("title3"),
                new Document("title4"),
                new Document("title5")
        );
    }
}
