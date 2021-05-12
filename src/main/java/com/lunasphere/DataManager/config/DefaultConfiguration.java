package com.lunasphere.DataManager.config;

import com.lunasphere.DataManager.models.Target;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class DefaultConfiguration implements ApplicationConfiguration {

//    List<Connection> connections;

    @Override
    public List<Target> getConnections() {
        return new ArrayList<>(Arrays.asList(
                new Target("jdbc:postgresql://localhost:5432/EXTENSIONS_DB", "postgres", "postgres")
        ));
    }
}
