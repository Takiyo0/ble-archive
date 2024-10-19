package us.takiyo.extensions;

import us.takiyo.Main;

public class Page {
    private String id;

    public Page(String id) {
        this.id = id;
    }

    public String execute(Main main) {
        return "Not implemented";
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
