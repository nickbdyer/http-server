package uk.nickbdyer.httpserver.controllers;

public class FormData {

    private String data;

    public FormData(String data) {
        this.data = data;
    }

    public String getData() {
        return data;
    }

    public FormData() {
        this.data = "";
    }

    public void setData(String data) {
        this.data = data;
    }
}
