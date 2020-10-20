package io.github.javaasasecondlanguage.flitter.dto;

public class PositiveAppResult<T> {

    private T data;
    private String unknownField = "Hello";

    public PositiveAppResult() {
    }

    public PositiveAppResult(T data) {
        this.data = data;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "PositiveAppResult{" +
                "data=" + data +
                '}';
    }
}
