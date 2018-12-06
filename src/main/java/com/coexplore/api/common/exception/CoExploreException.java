package com.coexplore.api.common.exception;

import com.coexplore.api.common.response.ResponseStatus;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

/**
 * resultCode is based on HttpCode, and it has the following format:
 * HttpCode.xxx For example: resultCode = 400.001 (bad request)
 *
 * @author duy.nh
 */

@JsonInclude(Include.NON_NULL)

public class CoExploreException extends RuntimeException {

    private static final long serialVersionUID = -3553959017640474140L;

    private String resultCode;
    private String message;
    
    @JsonIgnore
    private String value;

    public CoExploreException(ResponseStatus responseStatus) {
        this.resultCode = responseStatus.getCode();
        this.message = responseStatus.getMessage();
    }

    public CoExploreException(String code, String message) {
        this.resultCode = code;
        this.message = message;
    }
    
    public CoExploreException(ResponseStatus responseStatus, String cause) {
        this.resultCode = responseStatus.getCode();
        this.message = responseStatus.getMessage();
        this.value =  cause;
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

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
