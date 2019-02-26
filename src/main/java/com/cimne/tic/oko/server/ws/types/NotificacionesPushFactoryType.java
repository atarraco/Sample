package com.cimne.tic.oko.server.ws.types;

public enum NotificacionesPushFactoryType {

    None(0),
    ANDROID_PUSH_FRAME(1),
    ANDROID_PUSH_APP(2),
    IPHONE_PUSH_APP(3);

    private final int code;

    NotificacionesPushFactoryType(int code) {
        this.code = code;
    }

    public int toInt() {
        return code;
    }
}
