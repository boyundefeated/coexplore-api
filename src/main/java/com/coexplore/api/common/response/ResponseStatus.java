package com.coexplore.api.common.response;

/**
 * Enumeration of Response status codes Format of code is: HttpCode.xxx
 *
 * @author duy.nh
 *
 */
public enum ResponseStatus {

    /* ======= Enumeration of success response ======= */
    SUCCESS("200.000", "Success"),
    EMPTY_DATA("200.001", "Empty data"),

    CREATED("201.000", "Created successfully"),

    /* ======= error code of bad request ======= */
    BAD_REQUEST("400.000", "Bad request"),
    MISSING_PARAMS("400.001", "Missing params"),
    INVALID_PARAMS("400.002", "Invalid params"),
    INVALID_BODY("400.003", "Invalid body"),
    INVALID_TYPE_PARAM("400.004", "Invalid type of param"),
    
    
    
    
    // user
    USER_INVALID_PASSWORD("400.051", "Invalid password"),
    USER_NOT_FOUND("400.052", "User could not be found"),
    USER_ACTIVATION_NOT_FOUND("400.053", "No user was found for this activation key"),
    USER_CURRENT_LOGIN_NOT_FOUND("400.054", "Current user login not found"),
    USER_EMAIL_ALREADY_USED("400.055", "Email already in used"),
    USER_EMAIL_NOT_FOUND("400.056", "Email not found"),
    USER_RESET_KEY_NOT_FOUND("400.057", "No user was found for this reset key"),
    USER_LOGIN_ALREADY_USED("400.058", "Username already in used"),
    USER_NEW_BUT_HAVE_ID("400.059", "A new user cannot already have an ID"),
    USER_UPDATE_FAIL("400.060", "User update fail"),

    
    
    
    

    /* ======= error code of unauthorized request ======= */
    AUTHENTICATE_FAILED("401.000", "Authenticate failed"),
    WRONG_USERNAME_PASSWORD("401.001", "Wrong username or password"),
    INVALID_TOKEN("401.002", "Invalid Token"),
    EXPIRED_TOKEN("401.003", "Expired Token"),

    /* ======= error code of forbidden request ======= */
    PERMISSION_DENIED("403.000", "Forbidden to access"),

    /* ======= error code of resource not found ======= */
    RESOURCE_NOT_FOUND("404.000", "Resource not found"),
    IMAGE_NOT_FOUND("404.001", "Image not found"),

    /* ======= server error ======= */
    INTERNAL_SERVER_ERROR("500.000", "Internal Server Error");

    private String code;
    private String message;

    private ResponseStatus(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

}
