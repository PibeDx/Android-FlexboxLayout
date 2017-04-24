package com.josecuentas.android_flexboxlayout;

/**
 * Created by jcuentas on 24/04/17.
 */

public class User implements Item {

    private String id;
    private String name;

    public User(String id, String name) {
        this.id = id;
        this.name = name;
    }


    @Override public String getValue() {
        return name;
    }

    @Override public String getId() {
        return id;
    }

}
