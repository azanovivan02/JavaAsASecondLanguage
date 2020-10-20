package io.github.javaasasecondlanguage.flitter.dto;

public class AppResult<T> {

    public static <E> AppResult<E> ok(E data) {
        var result = new AppResult<E>();
        result.setData(data);
        return result;
    }

    public static <E> AppResult<E> fail(String errorMessage) {
        var result = new AppResult<E>();
        result.setErrorMessage(errorMessage);
        return result;
    }

    private T data;
    private String errorMessage;

    public AppResult() {
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    @Override
    public String toString() {
        return "MadeResult{" +
                "data=" + data +
                ", errorMessage='" + errorMessage + '\'' +
                '}';
    }
}
