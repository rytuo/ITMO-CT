package com.sd.sd1;

import com.sd.sd1.actors.search.MasterSearchActor;

import akka.actor.typed.ActorSystem;
import akka.actor.typed.Behavior;
import akka.actor.typed.javadsl.Behaviors;


public class Main {

    private static Behavior<MasterSearchActor.Return> create(String query) {
        return Behaviors.setup(context -> {
            var searchActor = context.spawn(
                    MasterSearchActor.create(),
                    "MasterSearchActor"
            );
            searchActor.tell(new MasterSearchActor.SearchStartEvent(query, context.getSelf()));

            return Behaviors.receive(MasterSearchActor.Return.class)
                    .onMessage(MasterSearchActor.Return.class, aReturn -> {
                        System.out.printf("Got %d results", aReturn.results().size());
                        for (int i = 0; i < aReturn.results().size(); i++) {
                            var searchResult = aReturn.results().get(i);
                            System.out.printf("%d. (%s) %s", i, searchResult.sourceId(), searchResult.document().title());
                        }
                        return Behaviors.stopped();
                    })
                    .build();
        });
    }

    public static void main(String... args) {
        if (args.length != 1) {
            System.out.println("Should have one argument");
        }
        String query = args[0];
        ActorSystem.create(Main.create(query), "SearchActor");
    }
}
