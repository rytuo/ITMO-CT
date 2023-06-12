package com.sd.sd1.actors.search;

import java.io.IOException;
import java.util.List;

import com.sd.sd1.model.Document;

import akka.actor.typed.ActorRef;
import akka.actor.typed.Behavior;
import akka.actor.typed.javadsl.AbstractBehavior;
import akka.actor.typed.javadsl.ActorContext;
import akka.actor.typed.javadsl.Receive;

public abstract class SearchActor extends AbstractBehavior<SearchActor.MakeRequest> {

    public record ResponseModel(List<Document> documents) {
    }

    record MakeRequest(
            String query,
            ActorRef<MasterSearchActor.SearchEvent> responseTarget
    ) {
    }

    public SearchActor(ActorContext<MakeRequest> context) {
        super(context);
    }

    @Override
    public Receive<MakeRequest> createReceive() {
        return newReceiveBuilder()
                .onMessage(MakeRequest.class, this::onMakeRequest)
                .build();
    }

    protected abstract Behavior<MakeRequest> onMakeRequest(MakeRequest makeRequest) throws IOException, InterruptedException;
}
