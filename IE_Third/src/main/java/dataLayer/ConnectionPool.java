package dataLayer;

import dataLayer.dbConnectionPool.BasicDBConnectionPool;
import dataLayer.dbConnectionPool.impl.SQLiteBasicDBConnectionPool;

public class ConnectionPool {

    private final static String dbURL = "jdbc:sqlite:taca7.db";
    private static BasicDBConnectionPool instance;

    public static BasicDBConnectionPool getInstance() {
        if (instance == null) {
            instance = new SQLiteBasicDBConnectionPool(10, 20 , dbURL);
        }
        return instance;
    }
}
