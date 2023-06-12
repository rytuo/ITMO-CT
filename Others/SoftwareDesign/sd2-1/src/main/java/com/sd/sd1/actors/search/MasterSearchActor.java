package com.sd.sd1.actors.search;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

import com.sd.sd1.model.Document;
import com.sd.sd1.model.SearchResult;
import com.sd.sd1.model.SourceId;

import akka.actor.typed.ActorRef;
import akka.actor.typed.Behavior;
import akka.actor.typed.javadsl.AbstractBehavior;
import akka.actor.typed.javadsl.ActorContext;
import akka.actor.typed.javadsl.Behaviors;
import akka.actor.typed.javadsl.Receive;
import akka.actor.typed.javadsl.TimerScheduler;


public class MasterSearchActor extends AbstractBehavior<MasterSearchActor.SearchEvent> {

    public record Return(List<SearchResult> results) { }

    public interface SearchEvent { }

    public record SearchStartEvent (String query, ActorRef<Return> responseTarget) implements SearchEvent { }

    public record SearchResultEvent (SourceId sourceId, List<Document> documents) implements SearchEvent { }

    public enum TimeoutEvent implements SearchEvent { INSTANCE }

    public static Behavior<SearchEvent> create() {
        return Behaviors.withTimers(timers ->
                Behaviors.setup(context ->
                        new MasterSearchActor(context, timers)
                )
        );
    }

    private static final Duration DELAY = Duration.ofSeconds(10);

    private final TimerScheduler<SearchEvent> timers;
    private final List<SearchResult> result = new ArrayList<>();
    private ActorRef<Return> responseTarget;
    private int left = 3;

    public MasterSearchActor(
            ActorContext<SearchEvent> context,
            TimerScheduler<SearchEvent> timers
    ) {
        super(context);
        this.timers = timers;
    }

    @Override
    public Receive<SearchEvent> createReceive() {
        return newReceiveBuilder()
                .onMessage(SearchStartEvent.class, this::onSearchStartEvent)
                .onMessage(SearchResultEvent.class, this::onSearchResultEvent)
                .onMessage(TimeoutEvent.class, this::onTerminateEvent)
                .build();
    }

    private Behavior<SearchEvent> onSearchStartEvent(SearchStartEvent searchStartEvent) {
        getContext().getLog().info("Processing query: \"{}\"", searchStartEvent.query);

        this.responseTarget = searchStartEvent.responseTarget();
        var yandexSearchActor = getContext().spawnAnonymous(YandexSearchActor.create());
        var googleSearchActor = getContext().spawnAnonymous(GoogleSearchActor.create());
        var bingSearchActor = getContext().spawnAnonymous(BingSearchActor.create());


        yandexSearchActor.tell(new SearchActor.MakeRequest(searchStartEvent.query, getContext().getSelf()));
        googleSearchActor.tell(new SearchActor.MakeRequest(searchStartEvent.query, getContext().getSelf()));
        bingSearchActor.tell(new SearchActor.MakeRequest(searchStartEvent.query, getContext().getSelf()));

        timers.startSingleTimer(TimeoutEvent.INSTANCE, DELAY);
        return Behaviors.same();
    }

    private Behavior<SearchEvent> onSearchResultEvent(SearchResultEvent searchResultEvent) {
        getContext().getLog().info("Got {} results from {}", searchResultEvent.documents.size(), searchResultEvent.sourceId);
        searchResultEvent.documents.forEach(document ->
                this.result.add(new SearchResult(searchResultEvent.sourceId, document))
        );
        left--;
        if (left == 0) {
            return stop();
        } else {
            return Behaviors.same();
        }
    }

    private Behavior<SearchEvent> onTerminateEvent(TimeoutEvent timeoutEvent) {
        return stop();
    }

    private Behavior<SearchEvent> stop() {
        if (responseTarget != null) {
            responseTarget.tell(new Return(this.result));
        }
        return Behaviors.stopped();
    }
}
