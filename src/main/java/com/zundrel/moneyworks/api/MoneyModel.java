package com.zundrel.moneyworks.api;

public class MoneyModel {

    private String parent;
    private Location textures;

    public MoneyModel(String parent, Location textures) {
        this.parent = parent;
        this.textures = textures;
    }

    public String getParent() {
        return parent;
    }

    public Location getTextures() {
        return textures;
    }

    public void setParent(String parent) {
        this.parent = parent;
    }

    public void setTextures(Location textures) {
        this.textures = textures;
    }
}
