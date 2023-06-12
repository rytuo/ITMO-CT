package com.sd.sd2.endpoint;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.sd.sd2.model.command.CreateSubscriptionCommand;
import com.sd.sd2.model.command.UpdateSubscriptionCommand;
import com.sd.sd2.model.query.GetSubscriptionInfoQuery;
import com.sd.sd2.service.command.SubscriptionCommandHandler;
import com.sd.sd2.service.query.SubscriptionsQueryHandler;

@RestController
@RequestMapping("/subscription")
public class SubscriptionEndpoint {

    private final SubscriptionCommandHandler subscriptionCommandHandler;
    private final SubscriptionsQueryHandler subscriptionsQueryHandler;

    @Autowired
    public SubscriptionEndpoint(
            SubscriptionCommandHandler subscriptionCommandHandler,
            SubscriptionsQueryHandler subscriptionsQueryHandler
    ) {
        this.subscriptionCommandHandler = subscriptionCommandHandler;
        this.subscriptionsQueryHandler = subscriptionsQueryHandler;
    }

    @GetMapping("/info")
    public String getSubscriptionInfo(
            @RequestParam long id
    ) {
        return subscriptionsQueryHandler.getSubscriptionInfo(
                new GetSubscriptionInfoQuery(id)
        );
    }

    @PostMapping("/create")
    public long createSubscription() {
        return subscriptionCommandHandler.createSubscription(
                new CreateSubscriptionCommand()
        );
    }

    @PostMapping("/update")
    public void updateSubscription(
            @RequestParam long id
    ) {
        subscriptionCommandHandler.updateSubscription(
                new UpdateSubscriptionCommand(id)
        );
    }
}
