package io.github.javaasasecondlanguage.flitter;

import io.github.javaasasecondlanguage.flitter.dto.AppResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RestController;

import static io.github.javaasasecondlanguage.flitter.Utils.ok;

@RestController
public class ClearController {

    @Autowired
    StateHolder holder;

    @DeleteMapping("/clear")
    public ResponseEntity<AppResult<Object>> clear() {
        holder.getNameToUserMap().clear();
        holder.getTokenToUserMap().clear();
        holder.getNameToFlitsMap().clear();
        holder.getPublisherNameToSubscribersMap().clear();
        holder.getSubscriberNameToPublishersMap().clear();
        return ok();
    }
}
