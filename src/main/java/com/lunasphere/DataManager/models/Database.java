package com.lunasphere.DataManager.models;

import java.util.List;

public class Database extends SchemaObj {
    String version;
    DatabaseType Type;
    List<Schema> schemas;

    public enum DatabaseType {
        MySQL(0),
        Postgres(1);

        private int type;

        DatabaseType(int type) {
            this.type = type;
        }

        public String getType() {
            switch(this.type) {
                case 0:
                    return "MySQL";
                case 1:
                    return "Postgres";
                default:
                    return "Unknown";
            }
        }
    }

    public Database() {
    }

    public Database(String name, String version, DatabaseType type) {
        this.setName(name);
        this.version = version;
        Type = type;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public DatabaseType getType() {
        return Type;
    }

    public void setType(DatabaseType type) {
        Type = type;
    }

    public List<Schema> getSchemas() {
        return schemas;
    }

    public void setSchemas(List<Schema> schemas) {
        this.schemas = schemas;
    }
}
