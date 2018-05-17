package com.tsarova.salon.content;

public class CommandContent {
    private ResponseType responseType;
    private String nextPage;

    public CommandContent() {
    }

    public CommandContent(ResponseType responseType, String nextPage) {
        this.responseType = responseType;
        this.nextPage = nextPage;
    }

    public ResponseType getResponseType() {
        return responseType;
    }

    public void setResponseType(ResponseType responseType) {
        this.responseType = responseType;
    }

    public String getNextPage() {
        return nextPage;
    }

    public void setNextPage(String nextPage) {
        this.nextPage = nextPage;
    }

    public enum ResponseType {
        FORWARD,
        REDIRECT,
        INCLUDE
    }
}
