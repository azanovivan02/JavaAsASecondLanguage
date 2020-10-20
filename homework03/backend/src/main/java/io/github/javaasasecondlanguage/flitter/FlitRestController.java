package io.github.javaasasecondlanguage.flitter;

import io.github.javaasasecondlanguage.flitter.dto.AppResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import io.github.javaasasecondlanguage.flitter.dto.AddFlitForm;
import io.github.javaasasecondlanguage.flitter.dto.Flit;
import io.github.javaasasecondlanguage.flitter.dto.User;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static io.github.javaasasecondlanguage.flitter.Utils.fail;
import static io.github.javaasasecondlanguage.flitter.Utils.ok;
import static java.util.Collections.emptyList;
import static java.util.Collections.emptySet;

@RestController
@RequestMapping("flit")
public class FlitRestController {

    @Autowired
    StateHolder holder;

    @PostMapping("/add")
    public ResponseEntity<AppResult<Object>> add(@RequestBody AddFlitForm form) {
        System.out.println("Received request: "+form);

        String userToken = form.getUserToken();
        if (userToken == null || userToken.isEmpty()) {
            return fail("Token is empty");
        }
        if (!holder.getTokenToUserMap().containsKey(userToken)) {
            return fail("User token not found");
        }

        User user = holder.getTokenToUserMap().get(userToken);
        String userName = user.getUserName();
        String content = form.getContent();
        Flit newFlit = new Flit(userName, content);

        List<Flit> userFlits = holder.getNameToFlitsMap().computeIfAbsent(userName, e -> new ArrayList<>());
        userFlits.add(newFlit);

        return ok();
    }

    @GetMapping("discover")
    public ResponseEntity<AppResult<List<Flit>>> listAllFlits() {
        List<Flit> allFlits = holder.getNameToFlitsMap()
                .entrySet()
                .stream()
                .sorted(Comparator.comparing(Map.Entry::getKey))
                .map(Map.Entry::getValue)
                .flatMap(Collection::stream)
                .collect(Collectors.toList());

        return ok(allFlits);
    }

    @GetMapping("list/{userName}")
    public ResponseEntity<AppResult<List<Flit>>> listFlitsPublishedUser(@PathVariable String userName) {

        if (userName == null || userName.isEmpty()) {
            return fail("User name is empty");
        }
        if (!holder.getNameToUserMap().containsKey(userName)) {
            return fail("Username not found: "+userName);
        }

        List<Flit> userFlits = holder
                .getNameToFlitsMap()
                .getOrDefault(userName, emptyList());

        return ok(userFlits);
    }

    @GetMapping("list/feed/{userToken}")
    public ResponseEntity<AppResult<List<Flit>>> listFlitsConsumedByUser(@PathVariable String userToken) {
        if (userToken == null || userToken.isEmpty()) {
            return fail("Token is empty");
        }
        if (!holder.getTokenToUserMap().containsKey(userToken)) {
            return fail("User token not found");
        }

        var userName = holder.getTokenToUserMap()
                .get(userToken)
                .getUserName();
        var publishers = holder
                .getSubscriberNameToPublishersMap()
                .getOrDefault(userName, emptySet())
				.stream()
				.sorted()
				.collect(Collectors.toList());

		var consumedFlits = publishers
				.stream()
				.map(name -> holder.getNameToFlitsMap().getOrDefault(name, emptyList()))
				.flatMap(List::stream)
				.collect(Collectors.toList());

        return ok(consumedFlits);
    }
}
