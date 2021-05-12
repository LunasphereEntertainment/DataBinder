package com.lunasphere.DataManager.models;

import com.fasterxml.jackson.annotation.JsonIgnore;

public abstract class SchemaObj {
    String name;
    boolean tracking;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isTracking() {
        return tracking;
    }

    public void setTracking(boolean tracking) {
        this.tracking = tracking;
    }

    @JsonIgnore
    public void toggleTracking() {
        this.tracking = !this.tracking;
    }
}
