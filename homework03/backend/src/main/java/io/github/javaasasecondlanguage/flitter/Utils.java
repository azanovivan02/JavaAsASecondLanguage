package io.github.javaasasecondlanguage.flitter;

import io.github.javaasasecondlanguage.flitter.dto.AppResult;
import org.springframework.http.ResponseEntity;

import java.util.UUID;

public class Utils {

    public static String generateUserToken() {
        UUID uuid = UUID.randomUUID();
        return uuid.toString();
    }

    public static <T> ResponseEntity<AppResult<T>> ok() {
        var result = new AppResult<T>();
        return ResponseEntity.ok(result);
    }

    public static <T> ResponseEntity<AppResult<T>> ok(T data) {
        return ResponseEntity.ok(
                AppResult.ok(data)
        );
    }

    public static <T> ResponseEntity<AppResult<T>> fail(String errorMessage) {
        AppResult<T> appResult = AppResult.fail(errorMessage);
        return ResponseEntity
                .badRequest()
                .body(appResult);
    }
}
