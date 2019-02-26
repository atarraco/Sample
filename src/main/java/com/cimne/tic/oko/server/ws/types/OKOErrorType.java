package com.cimne.tic.oko.server.ws.types;

/**
 * Created by lorddarks on 22/2/17.
 * Enum to define error codes and messages
 */
public enum OKOErrorType {

    GENERIC_ERROR(-1, "ERROR"),
    USER_ALREADY_EXISTS(-2, "USER ALREADY EXSISTS"),
    NOTIFICATION_NOT_FOUND(-3, "NOTIFICATION NOT FOUND"),
    NOT_LINKED(-4, "FRAME NOT LINKED"),
    NOT_OWNER(-5, "NOT THE OWNER"),
    NOT_ALLOWED(-6, "NOT ALLOWED"),
    ERROR_UPLOADING(-7, "ERROR UPLOADING"),
    CONTENT_NOT_FOUND(-8, "CONTENT NOT FOUND"),
    USER_NOT_VALIDATED(-9, "EMAIL O TELEFONO NO VALIDADOS"),
    NO_TOKEN(-55, "NO TOKEN"),
    INVALID_PASSWORD_FORMAT(-300, "INVALID PASSWORD FORMAT"),
    INVALID_MAIL_FORMAT(-401, "INVALID MAIL FORMAT"),
    LOGIN_ERROR(-402, "ERROR LOGGING IN"),
    FORBIDDEN(403, "Forbidden"),
    NOT_FOUND(-404, "NOT FOUND"),
    USER_NOT_FOUND(-410, "INVALID USER"),
    INVALID_NAME(-500, "EMPTY NAME"),
    PASSWORD_TOO_SHORT(-600, "PASSWORD TOO SHORT"),
    ERROR_VALIDATING_PHONE(-995, "ERROR VALIDATING PHONE NUMBER"),
    INVALID_SERIAL(-996, ""),
    ERROR_DELETING_FILE(-997, "ERROR DELETING FILE"),
    ERROR_DELETING_UNECRYPTED_FILES(-998, "ERROR DELETING UNENCRYPTED FILES"),
    INVALID_DEVICE(-999, "INVALID DEVICE");


    private final int code;
    private final String description;

    OKOErrorType(int code, String description) {
        this.code = code;
        this.description = description;
    }

    public String getMessage() {
        return description;
    }

    public int getCode() {
        return code;
    }

    @Override
    public String toString() {
        return code + ": " + description;
    }
}
