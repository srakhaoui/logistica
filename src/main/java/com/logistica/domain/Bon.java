package com.logistica.domain;

public class Bon {

    private byte[] content;
    private String contentType;

    public Bon() {
        content = new byte[]{};
    }

    public Bon(byte[] content, String contentType) {
        this.content = content;
        this.contentType = contentType;
    }

    public byte[] getContent() {
        return content;
    }

    public void setContent(byte[] content) {
        this.content = content;
    }

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }
}
