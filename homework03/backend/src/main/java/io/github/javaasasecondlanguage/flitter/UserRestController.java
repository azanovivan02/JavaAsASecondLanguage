package io.github.javaasasecondlanguage.flitter;

import io.github.javaasasecondlanguage.flitter.dto.AddUserForm;
import io.github.javaasasecondlanguage.flitter.dto.AppResult;
import io.github.javaasasecondlanguage.flitter.dto.PositiveAppResult;
import io.github.javaasasecondlanguage.flitter.dto.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

import static io.github.javaasasecondlanguage.flitter.Utils.fail;
import static io.github.javaasasecondlanguage.flitter.Utils.ok;

@RestController
@RequestMapping("user")
public class UserRestController {

    @Autowired
    StateHolder holder;

    @PostMapping("/register")
    public ResponseEntity<AppResult<User>> add(@RequestBody AddUserForm form) {
        String userName = form.getUserName();
        if (userName == null || userName.isEmpty()) {
            return fail("User name is empty");
        }
        if (holder.getNameToUserMap().containsKey(userName)) {
//            return ResponseEntity.ok(AppResult.fail("Error occurred"));
            return fail("User under such name is already registered: " + userName);
        }

        String userToken = Utils.generateUserToken();
        User newUser = new User(userName, userToken);

        holder.getTokenToUserMap().put(userToken, newUser);
        holder.getNameToUserMap().put(userName, newUser);

        return ok(newUser);
    }

    @GetMapping("/list")
    public ResponseEntity<PositiveAppResult<List<String>>> list() {
        List<String> userNames = holder
                .getNameToUserMap()
                .values()
                .stream()
                .map(User::getUserName)
                .collect(Collectors.toList());

        var positiveAppResult = new PositiveAppResult<>(userNames);
        return ResponseEntity.ok(positiveAppResult);
    }
}
