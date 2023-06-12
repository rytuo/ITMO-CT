package com.sd.sd1.actors.search;

import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.sd.sd1.model.SourceId;

import static org.assertj.core.api.Assertions.assertThat;
import akka.actor.testkit.typed.javadsl.BehaviorTestKit;
import akka.actor.testkit.typed.javadsl.TestInbox;
import akka.actor.typed.Behavior;


public abstract class SearchActorTests {

    private final SearchApiStub searchApiStub = createSearchApiStub();

    @BeforeEach
    public void beforeEach() {
        searchApiStub.run();
    }

    @AfterEach
    public void afterEach() {
        searchApiStub.stop();
    }

    @Test
    public void makeRequestTest() throws JsonProcessingException {
        String query = "testQuery";

        searchApiStub.stub(0, query);

        BehaviorTestKit<SearchActor.MakeRequest> testKit = BehaviorTestKit.create(createSearchActor());
        TestInbox<MasterSearchActor.SearchEvent> testInbox = TestInbox.create();
        testKit.run(new SearchActor.MakeRequest(query, testInbox.getRef()));

        searchApiStub.verify(query);

        List<MasterSearchActor.SearchEvent> results = testInbox.getAllReceived();
        assertThat(results).hasSize(1);

        MasterSearchActor.SearchEvent result = results.get(0);
        assertThat(result).isInstanceOf(MasterSearchActor.SearchResultEvent.class);
        MasterSearchActor.SearchResultEvent resultTyped = (MasterSearchActor.SearchResultEvent) result;
        assertThat(resultTyped.sourceId()).isEqualTo(getSourceId());
        assertThat(resultTyped.documents()).isEqualTo(searchApiStub.getTestDocuments());

        assertThat(testKit.isAlive()).isFalse();
    }


    abstract SearchApiStub createSearchApiStub();

    abstract Behavior<SearchActor.MakeRequest> createSearchActor();

    abstract SourceId getSourceId();
}
