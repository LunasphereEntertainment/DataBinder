package com.lunasphere.DataManager.config;

import com.lunasphere.DataManager.models.Target;

import java.util.List;

public interface ApplicationConfiguration {
    List<Target> getConnections();
}
