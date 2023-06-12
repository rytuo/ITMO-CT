package com.sd.sd1.actors.search;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.sd.sd1.model.Document;
import com.sd.sd1.model.SearchResult;
import com.sd.sd1.model.SourceId;

import static org.assertj.core.api.Assertions.assertThat;
import akka.actor.testkit.typed.javadsl.ActorTestKit;
import akka.actor.testkit.typed.javadsl.BehaviorTestKit;
import akka.actor.testkit.typed.javadsl.ManualTime;
import akka.actor.testkit.typed.javadsl.TestInbox;
import akka.actor.testkit.typed.javadsl.TestProbe;
import akka.actor.typed.ActorRef;


@TestMethodOrder(MethodOrderer.Random.class)
public class MasterSearchActorTests {

    private static final String QUERY = "testQuery";

    private static final ActorTestKit asyncTest = ActorTestKit.create(ManualTime.config());

    private final ManualTime manualTime = ManualTime.get(asyncTest.system());

    private final SearchApiStub yandexApiStub = new SearchApiStub(8090);
    private final SearchApiStub googleApiStub = new SearchApiStub(8091);
    private final SearchApiStub bingApiStub = new SearchApiStub(8092);

    @AfterAll
    public static void afterAll() {
        asyncTest.shutdownTestKit();
    }

    @BeforeEach
    public void beforeEach() {
        yandexApiStub.run();
        googleApiStub.run();
        bingApiStub.run();
    }

    @AfterEach
    public void afterEach() {
        yandexApiStub.stop();
        googleApiStub.stop();
        bingApiStub.stop();
    }

    @Test
    void instantResponseTest() throws JsonProcessingException {
        List<SearchResult> results = new ArrayList<>();
        results.addAll(makeResponse(SourceId.YANDEX, yandexApiStub.getTestDocuments()));
        results.addAll(makeResponse(SourceId.GOOGLE, googleApiStub.getTestDocuments()));
        results.addAll(makeResponse(SourceId.BING, bingApiStub.getTestDocuments()));

        yandexApiStub.stub(0, QUERY);
        googleApiStub.stub(0, QUERY);
        bingApiStub.stub(0, QUERY);

        TestProbe<MasterSearchActor.Return> probe = asyncTest.createTestProbe();
        ActorRef<MasterSearchActor.SearchEvent> masterSearchActor = asyncTest.spawn(MasterSearchActor.create());
        masterSearchActor.tell(new MasterSearchActor.SearchStartEvent(QUERY, probe.ref()));

        manualTime.timePasses(Duration.ofMillis(1));
        var message = probe.expectMessageClass(MasterSearchActor.Return.class);
        assertThat(message.results()).hasSameElementsAs(results);

        manualTime.expectNoMessageFor(Duration.ofSeconds(6), probe);
    }

    @Test
    void noResponseTest() throws JsonProcessingException {
        yandexApiStub.stub(15_500, QUERY);
        googleApiStub.stub(18_000, QUERY);
        bingApiStub.stub(16_000, QUERY);

        TestProbe<MasterSearchActor.Return> probe = asyncTest.createTestProbe();
        ActorRef<MasterSearchActor.SearchEvent> masterSearchActor = asyncTest.spawn(MasterSearchActor.create());
        masterSearchActor.tell(new MasterSearchActor.SearchStartEvent(QUERY, probe.ref()));

        manualTime.expectNoMessageFor(Duration.ofMillis(9999), probe);
        manualTime.timePasses(Duration.ofMillis(2));

        var message = probe.expectMessageClass(MasterSearchActor.Return.class);
        assertThat(message.results()).hasSameElementsAs(Collections.emptyList());

        manualTime.expectNoMessageFor(Duration.ofSeconds(6), probe);
    }

    @Test
    void naturalResponseTest() throws JsonProcessingException {
        List<SearchResult> results = new ArrayList<>();
        results.addAll(makeResponse(SourceId.YANDEX, yandexApiStub.getTestDocuments()));
        results.addAll(makeResponse(SourceId.GOOGLE, googleApiStub.getTestDocuments()));
        results.addAll(makeResponse(SourceId.BING, bingApiStub.getTestDocuments()));

        yandexApiStub.stub(200, QUERY);
        googleApiStub.stub(500, QUERY);
        bingApiStub.stub(700, QUERY);

        TestProbe<MasterSearchActor.Return> probe = asyncTest.createTestProbe();
        ActorRef<MasterSearchActor.SearchEvent> masterSearchActor = asyncTest.spawn(MasterSearchActor.create());
        masterSearchActor.tell(new MasterSearchActor.SearchStartEvent(QUERY, probe.ref()));

        manualTime.expectNoMessageFor(Duration.ofMillis(699), probe);
        manualTime.timePasses(Duration.ofMillis(2));
        var message = probe.expectMessageClass(MasterSearchActor.Return.class);
        assertThat(message.results()).hasSameElementsAs(results);

        manualTime.expectNoMessageFor(Duration.ofSeconds(6), probe);
    }

    @Test
    void terminateBeforeSearchTest() {
        BehaviorTestKit<MasterSearchActor.SearchEvent> syncTest = BehaviorTestKit.create(MasterSearchActor.create());
        syncTest.run(MasterSearchActor.TimeoutEvent.INSTANCE);
        assertThat(syncTest.isAlive()).isFalse();
    }

    @Test
    void beforehandTerminateTest() {
        TestInbox<MasterSearchActor.Return> testInbox = TestInbox.create();
        BehaviorTestKit<MasterSearchActor.SearchEvent> syncTest = BehaviorTestKit.create(MasterSearchActor.create());
        syncTest.run(new MasterSearchActor.SearchStartEvent(QUERY, testInbox.getRef()));
        syncTest.run(MasterSearchActor.TimeoutEvent.INSTANCE);
        testInbox.expectMessage(new MasterSearchActor.Return(Collections.emptyList()));
        assertThat(syncTest.isAlive()).isFalse();
    }

    private List<SearchResult> makeResponse(SourceId sourceId, List<Document> documents) {
        return documents.stream()
                .map(document -> new SearchResult(sourceId, document))
                .toList();
    }
}
