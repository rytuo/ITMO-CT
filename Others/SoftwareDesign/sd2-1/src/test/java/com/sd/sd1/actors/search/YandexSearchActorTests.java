package com.sd.sd1.actors.search;

import com.sd.sd1.model.SourceId;

import akka.actor.typed.Behavior;


public class YandexSearchActorTests extends SearchActorTests {

    @Override
    SearchApiStub createSearchApiStub() {
        return new SearchApiStub(8090);
    }

    @Override
    Behavior<SearchActor.MakeRequest> createSearchActor() {
        return YandexSearchActor.create();
    }

    @Override
    SourceId getSourceId() {
        return SourceId.YANDEX;
    }
}
