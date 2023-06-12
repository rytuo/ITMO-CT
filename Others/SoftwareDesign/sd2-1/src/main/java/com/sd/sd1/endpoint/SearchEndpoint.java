package com.sd.sd1.endpoint;

import java.time.Duration;
import java.util.List;
import java.util.concurrent.CompletionStage;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.sd.sd1.actors.search.MasterSearchActor;
import com.sd.sd1.model.SearchResult;

import akka.actor.typed.ActorSystem;
import akka.actor.typed.javadsl.AskPattern;

@RestController
public class SearchEndpoint {

    @GetMapping("/search")
    public List<SearchResult> search(
            @RequestParam String query
    ) {
        var actorSystem = ActorSystem.create(MasterSearchActor.create(), "search");
        CompletionStage<MasterSearchActor.Return> stage = AskPattern.ask(
                actorSystem,
                replyTo -> new MasterSearchActor.SearchStartEvent(query, replyTo),
                Duration.ofSeconds(10),
                actorSystem.scheduler()
        );
        return stage.toCompletableFuture().join().results();
    }
}
