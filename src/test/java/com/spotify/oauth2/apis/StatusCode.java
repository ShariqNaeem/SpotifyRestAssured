package com.spotify.oauth2.apis;

public enum StatusCode {
    CODE_200(200, ""),

    CODE_201(201, ""),

    CODE_400(400, "Missing required field: name"),

    CODE_401(401, "Invalid access token"),

    CODE_500(500, "");

    private final int code;
    private final String text;

    StatusCode(int code, String text) {
        this.code = code;
        this.text = text;
    }

    public int getCode() {
        return code;
    }

    public String getText() {
        return text;
    }

}
