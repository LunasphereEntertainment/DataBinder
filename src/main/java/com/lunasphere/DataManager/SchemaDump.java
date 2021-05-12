package com.lunasphere.DataManager;

import com.lunasphere.DataManager.config.ApplicationConfiguration;
import com.lunasphere.DataManager.config.DatabaseConfig;
import com.lunasphere.DataManager.config.DefaultConfiguration;
import com.lunasphere.DataManager.models.Database;
import com.lunasphere.DataManager.models.Schema;
import com.lunasphere.DataManager.models.Target;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.Level;

import javax.xml.crypto.Data;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class SchemaDump {

    private static Logger logger = LogManager.getLogger(SchemaDump.class.getName());
    private static Scanner in = new Scanner(System.in);

    public static void promptSelection(List<Database> dbs) {
        final String FORMAT_STR = "%3s\t\t%20s\t\t%7s\n";

        while (true) {
            System.out.flush();

            int iter = 0;

            logger.log(Level.INFO, "Schema List: ");

            System.out.printf(FORMAT_STR, "#", "DB NAME", "STATUS");
            System.out.printf(FORMAT_STR, "---", "--------------------", "-------");

            for (Database schema : dbs) {
                System.out.printf(FORMAT_STR, ++iter, schema.getName(), schema.isTracking() ? "MONITOR" : "IGNORE");
//                logger.log(Level.INFO, "{}\t-\t{}\t\t{}", );
            }

            System.out.println("Enter schema to toggle selection. Enter 'done' to complete selection.");

            String input = in.next();

            if (input.equals("done")) {
                return;
            } else {
                try {
                    int selectedIdx = Integer.parseInt(input);
                    if (selectedIdx <= dbs.size() && selectedIdx > 0) {
                        Database selectedSchema = dbs.get(selectedIdx - 1);
                        selectedSchema.toggleTracking();
                        continue;
                    }
                } catch (NumberFormatException ex) {}
                logger.log(Level.ERROR, "Invalid integer entered, please try again.");
            }
        }
    }

    public static void main(String[] args) {

        ApplicationConfiguration config = new DefaultConfiguration();

        List<Target> targets = config.getConnections();

        logger.log(Level.DEBUG, "Preparing to access {}", DatabaseConfig.URL);

        for (Target target : targets) {
            try {
                logger.log(Level.DEBUG, "Attempting DB connection: {}", DatabaseConfig.URL);

                Connection conn = DriverManager.getConnection(DatabaseConfig.URL, DatabaseConfig.USER, DatabaseConfig.PASS);

                logger.log(Level.DEBUG, "Connection Success!");
                logger.log(Level.TRACE, "Retrieving list of databases...");

                DatabaseMetaData meta = conn.getMetaData();

                String driverName = meta.getDriverName();

                Database.DatabaseType type;

                PreparedStatement stmt;
                if (driverName.toLowerCase().contains("postgres")) {
                    type = Database.DatabaseType.Postgres;
                    // USE POSTGRESQL QUERY TO RETRIEVE DATABASES
                    stmt = conn.prepareStatement("SELECT datname FROM pg_database WHERE datistemplate = false;");
                } else if (driverName.toLowerCase().contains("mysql")) {
                    type = Database.DatabaseType.MySQL;
                    // USE MYSQL QUERY TO RETRIEVE DATABASES
                    stmt = conn.prepareStatement("SELECT DISTINCT(SCHEMA_NAME) FROM INFORMATION_SCHEMA.SCHEMATA");
                } else {
                    logger.log(Level.ERROR, "DataManager does not currently support driver {}", driverName);
                    return;
                }

                ResultSet dbListResults = stmt.executeQuery();

                int iter = 0;

                List<Database> databases = target.getDatabases();

                while (dbListResults.next()) {
                    String dbName = dbListResults.getString(1);

                    Database db = new Database(dbName, "", type);

                    databases.add(db);
                }

                promptSelection(databases);

            } catch (SQLException ex) {
                System.err.println("exception whilst connecting to db: " + ex.getMessage());
            }
        }

    }
}
