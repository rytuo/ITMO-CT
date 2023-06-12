package com.sd.sd1.actors.search;

import com.sd.sd1.model.SourceId;

import akka.actor.typed.Behavior;


public class GoogleSearchActorTests extends SearchActorTests {

    @Override
    SearchApiStub createSearchApiStub() {
        return new SearchApiStub(8091);
    }

    @Override
    Behavior<SearchActor.MakeRequest> createSearchActor() {
        return GoogleSearchActor.create();
    }

    @Override
    SourceId getSourceId() {
        return SourceId.GOOGLE;
    }
}
