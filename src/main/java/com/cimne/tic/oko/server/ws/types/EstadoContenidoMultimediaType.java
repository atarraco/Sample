package com.cimne.tic.oko.server.ws.types;

public enum EstadoContenidoMultimediaType {

    None(0),
    Upload(1),
    Request_Launch_Push(2),
    ErrorUpload(3),
    ErrorRequest_Launch_Push(4),
    Push_Sent_To_Device(5),
    ErrorPush_Launched(6),
    Ack_From_Google(7),
    Nack_From_Google(8),
    Receipt_FromGoogle(9);

    private final int code;

    EstadoContenidoMultimediaType(int code) {
        this.code = code;
    }

    public int toInt() {
        return code;
    }
}
