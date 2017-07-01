package com.theah64.movie_db.utils;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by theapache64 on 11/9/16.
 */
public class Response {

    private static final int ERROR_CODE_NO_ERROR = 0;
    private static final String KEY_ERROR = "error";
    private static final String KEY_ERROR_CODE = "error_code";
    private static final String KEY_MESSAGE = "message";
    private static final String KEY_DATA = "data";
    private final JSONObject joResp = new JSONObject();

    private final boolean hasError;
    private final int errorCode;
    private final String message;
    private final JSONObject joData;


    private Response(final boolean hasError, final int errorCode, final String message, final JSONObject joData) {
        this.hasError = hasError;
        this.errorCode = errorCode;
        this.message = message;
        this.joData = joData;
    }

    public Response(final String message, final JSONObject joData) {
        this(false, ERROR_CODE_NO_ERROR, message, joData);
    }

    public Response(final String message, final String key, final String value) throws JSONException {
        this(false, ERROR_CODE_NO_ERROR, message, new JSONObject().put(key, value));
    }

    public Response(final int errorCode, final String errorMessage) {
        this(true, errorCode, errorMessage, null);
    }

    public Response(final String errorMessage) {
        this(true, 1, errorMessage, null);
    }

    @Override
    public String toString() {
        return getResponse();
    }

    public String getResponse() {

        try {
            joResp.put(KEY_ERROR, hasError);
            joResp.put(KEY_ERROR_CODE, errorCode);
            joResp.put(KEY_MESSAGE, message);

            if (joData != null) {
                joResp.put(KEY_DATA, joData);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return joResp.toString();
    }


}
