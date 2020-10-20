package io.github.javaasasecondlanguage.flitter;

import org.springframework.stereotype.Component;
import io.github.javaasasecondlanguage.flitter.dto.Flit;
import io.github.javaasasecondlanguage.flitter.dto.User;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

@Component
public class StateHolder {

    private final ConcurrentMap<String, User> tokenToUserMap = new ConcurrentHashMap<>();
    private final ConcurrentMap<String, User> nameToUserMap = new ConcurrentHashMap<>();

    private final ConcurrentMap<String, List<Flit>> nameToFlitsMap = new ConcurrentHashMap<>();

    private final ConcurrentMap<String, Set<String>> subscriberNameToPublishersMap = new ConcurrentHashMap<>();
    private final ConcurrentMap<String, Set<String>> publisherNameToSubscribersMap = new ConcurrentHashMap<>();

    public ConcurrentMap<String, User> getTokenToUserMap() {
        return tokenToUserMap;
    }

    public ConcurrentMap<String, User> getNameToUserMap() {
        return nameToUserMap;
    }

    public ConcurrentMap<String, List<Flit>> getNameToFlitsMap() {
        return nameToFlitsMap;
    }

    public ConcurrentMap<String, Set<String>> getSubscriberNameToPublishersMap() {
        return subscriberNameToPublishersMap;
    }

    public ConcurrentMap<String, Set<String>> getPublisherNameToSubscribersMap() {
        return publisherNameToSubscribersMap;
    }


    {
        var macro = "Sed ut perspiciatis unde omnis iste natus error sit voluptatem accusantium doloremque laudantium, totam rem aperiam, eaque ipsa quae ab illo inventore veritatis et quasi architecto beatae vitae dicta sunt explicabo. Nemo enim ipsam voluptatem quia voluptas sit aspernatur aut odit aut fugit, sed quia consequuntur magni dolores eos qui ratione voluptatem sequi nesciunt. Neque porro quisquam est, qui dolorem ipsum quia dolor sit amet, consectetur, adipisci velit, sed quia non numquam eius modi tempora incidunt ut labore et dolore magnam aliquam quaerat voluptatem. Ut enim ad minima veniam, quis nostrum exercitationem ullam corporis suscipit laboriosam, nisi ut aliquid ex ea commodi consequatur? Quis autem vel eum iure reprehenderit qui in ea voluptate velit esse quam nihil molestiae consequatur, vel illum qui dolorem eum fugiat quo voluptas nulla pariatur?";

        List<User> sampleUsers = List.of(
                new User("Sasha", "1111"),
                new User("Shamil", "2222")
        );

        for (User user : sampleUsers) {
            tokenToUserMap.put(user.getUserToken(), user);
            nameToUserMap.put(user.getUserName(), user);

            List<Flit> flits = nameToFlitsMap.computeIfAbsent(user.getUserName(), e -> new ArrayList<>());

            for (int i = 0; i < 5; i++) {
                String flitContent = user.getUserName() + " flit " + i;
                Flit flit = new Flit(user.getUserName(), flitContent);
                flits.add(flit);
            }

            Flit flit = new Flit(user.getUserName(), macro);
            flits.add(flit);
        }
    }
}
