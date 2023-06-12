package com.sd.sd1.actors.search;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sd.sd1.model.SourceId;

import akka.actor.typed.Behavior;
import akka.actor.typed.javadsl.ActorContext;
import akka.actor.typed.javadsl.Behaviors;

public class YandexSearchActor extends SearchActor {

    public static Behavior<MakeRequest> create() {
        return Behaviors.setup(YandexSearchActor::new);
    }

    public YandexSearchActor(ActorContext<MakeRequest> context) {
        super(context);
    }

    @Override
    protected Behavior<MakeRequest> onMakeRequest(MakeRequest makeRequest) throws IOException, InterruptedException {
        var client = HttpClient.newHttpClient();
        var request = HttpRequest
                .newBuilder(URI.create("http://localhost:8090/search?q=" + makeRequest.query()))
                .version(HttpClient.Version.HTTP_1_1)
                .GET()
                .build();

        ObjectMapper objectMapper = new ObjectMapper();
        var response = client.send(request, HttpResponse.BodyHandlers.ofString());
        if (response.statusCode() != 200) {
            throw new RuntimeException("Failed request " + response.statusCode());
        }
        ResponseModel responseModel = objectMapper.readValue(response.body(), ResponseModel.class);

        makeRequest.responseTarget().tell(new MasterSearchActor.SearchResultEvent(
                SourceId.YANDEX,
                responseModel.documents()
        ));
        return Behaviors.stopped();
    }
}
