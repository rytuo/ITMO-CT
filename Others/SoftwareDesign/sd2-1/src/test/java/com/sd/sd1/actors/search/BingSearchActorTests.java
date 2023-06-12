package com.sd.sd1.actors.search;

import com.sd.sd1.model.SourceId;

import akka.actor.typed.Behavior;


public class BingSearchActorTests extends SearchActorTests {

    @Override
    SearchApiStub createSearchApiStub() {
        return new SearchApiStub(8092);
    }

    @Override
    Behavior<SearchActor.MakeRequest> createSearchActor() {
        return BingSearchActor.create();
    }

    @Override
    SourceId getSourceId() {
        return SourceId.BING;
    }
}
