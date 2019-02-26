package com.cimne.tic.oko.server.ws.BLL;

import com.cimne.tic.oko.server.ws.types.OKOErrorType;

public class OKOBusinessException extends Exception {

    /**
     *
     */
    private static final long serialVersionUID = 1L;
    private int _errorNumber;

    public OKOBusinessException() {

    }

    public OKOBusinessException(String message) {

        super(message);
    }

    public OKOBusinessException(OKOErrorType error) {
        super(error.getMessage());
        this._errorNumber = error.getCode();
    }

    public OKOBusinessException(String message, int ErrorNumber) {
        super(message);
        this._errorNumber = ErrorNumber;

    }

    public int getErrorNumber() {
        return _errorNumber;
    }

    public void setErrorNumber(int value) {
        _errorNumber = value;
    }
}
