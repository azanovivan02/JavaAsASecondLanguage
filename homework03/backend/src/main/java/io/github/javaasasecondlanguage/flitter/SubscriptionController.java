package io.github.javaasasecondlanguage.flitter;

import io.github.javaasasecondlanguage.flitter.dto.AppResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import io.github.javaasasecondlanguage.flitter.dto.SubscribeForm;

import java.util.LinkedHashSet;
import java.util.Set;

import static io.github.javaasasecondlanguage.flitter.Utils.fail;
import static io.github.javaasasecondlanguage.flitter.Utils.ok;
import static java.util.Collections.emptySet;

@RestController
public class SubscriptionController {

    @Autowired
    StateHolder holder;

    @PostMapping("/subscribe")
    public ResponseEntity<AppResult<Boolean>> add(@RequestBody SubscribeForm form) {
        var subscriberToken = form.getSubscriberToken();
        if (subscriberToken == null || subscriberToken.isEmpty()) {
            return fail("Token is empty");
        }
        if (!holder.getTokenToUserMap().containsKey(subscriberToken)) {
            return fail("Subscriber token not found");
        }

        var subscriberName = holder
                .getTokenToUserMap()
                .get(subscriberToken)
                .getUserName();
        var publisherName = form.getPublisherName();

        var subscribers = holder
                .getPublisherNameToSubscribersMap()
                .computeIfAbsent(publisherName, e -> new LinkedHashSet<>());
        subscribers.add(subscriberName);

        var publishers = holder
                .getSubscriberNameToPublishersMap()
                .computeIfAbsent(subscriberName, e -> new LinkedHashSet<>());
        publishers.add(publisherName);

        return ok();
    }

    @PostMapping("/unsubscribe")
    public ResponseEntity<AppResult<Boolean>> delete(@RequestBody SubscribeForm form) {
        var subscriberToken = form.getSubscriberToken();
        if (subscriberToken == null || subscriberToken.isEmpty()) {
            return fail("Token is empty");
        }
        if (!holder.getTokenToUserMap().containsKey(subscriberToken)) {
            return fail("Subscriber token not found");
        }

        var subscriberName = holder
                .getTokenToUserMap()
                .get(subscriberToken)
                .getUserName();
        var publisherName = form.getPublisherName();

        var subscribers = holder
                .getPublisherNameToSubscribersMap()
                .computeIfAbsent(publisherName, e -> new LinkedHashSet<>());
        subscribers.remove(subscriberName);

        var publishers = holder
                .getSubscriberNameToPublishersMap()
                .computeIfAbsent(subscriberName, e -> new LinkedHashSet<>());
        publishers.remove(publisherName);

        return ok();
    }

    @GetMapping("/subscribers/list/{userToken}")
    public ResponseEntity<AppResult<Set<String>>> getSubscribers(@PathVariable String userToken) {
        if (userToken == null || userToken.isEmpty()) {
            return fail("Token is empty");
        }
        if (!holder.getTokenToUserMap().containsKey(userToken)) {
            return fail("User token not found");
        }

        var userName = holder
                .getTokenToUserMap()
                .get(userToken)
                .getUserName();

        var subscribers = holder
                .getPublisherNameToSubscribersMap()
                .getOrDefault(userName, emptySet());

        return ok(subscribers);
    }

    @GetMapping("/publishers/list/{userToken}")
    public ResponseEntity<AppResult<Set<String>>> getPublishers(@PathVariable String userToken) {
        if (userToken == null || userToken.isEmpty()) {
            return fail("Token is empty");
        }
        if (!holder.getTokenToUserMap().containsKey(userToken)) {
            return fail("User token not found");
        }

        var userName = holder
                .getTokenToUserMap()
                .get(userToken)
                .getUserName();

        var publishers = holder
                .getSubscriberNameToPublishersMap()
                .getOrDefault(userName, emptySet());

        return ok(publishers);
    }
}
