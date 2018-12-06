package com.coexplore.api.common.response;

/**
 * resultCode is based on HttpCode, and it has the following format:
 * HttpCode.xxx For example: resultCode = 200.001
 * @param <T>
 * @author duy.nh
 */

public class StandardResponse<T> {

    private String resultCode;
    private String message;
    private T value;

    public StandardResponse(ResponseStatus responseStatus, T value) {
        super();
        this.resultCode = responseStatus.getCode();
        this.message = responseStatus.getMessage();
        this.value = value;
    }

    public StandardResponse<T> successfulRespone(T value) {
        this.resultCode = ResponseStatus.SUCCESS.getCode();
        this.message = ResponseStatus.SUCCESS.getMessage();
        this.value = value;

        return this;
    }

    public String getResultCode() {
        return resultCode;
    }

    public void setResultCode(String resultCode) {
        this.resultCode = resultCode;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getValue() {
        return value;
    }

    public void setValue(T value) {
        this.value = value;
    }

    public StandardResponse() {
        super();
    }
}
