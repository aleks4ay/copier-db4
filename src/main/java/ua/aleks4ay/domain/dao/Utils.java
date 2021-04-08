package ua.aleks4ay.domain.dao;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
import java.util.Properties;

import static ua.aleks4ay.log.ClassNameUtil.getPreviousClassName;

public final class Utils {
    private static final Logger log = LoggerFactory.getLogger(Utils.class);
    private final static String dbfReaderPath;
    private final static String url;
    private final static String urlTest;
    private final static String username;
    private final static String password;

    static  {
        Properties props = new Properties();
        String filePropertiesName = "database.properties";
        try (InputStream in = Utils.class.getClassLoader().getResourceAsStream(filePropertiesName)) {
            props.load(in);
        }
        catch (IOException e) {
            log.error("IOException during read {} from {}.", new File(filePropertiesName).getAbsolutePath(), Utils.class, e.toString());
            e.printStackTrace();
        }
        dbfReaderPath = props.getProperty("dbReader.path");
        url = props.getProperty("database.url");
        urlTest = props.getProperty("database.test");
        username = props.getProperty("database.username");
        password = props.getProperty("database.password");
    }

    public static String getDbfReaderPath() {
        return dbfReaderPath;
    }

    public static Connection getConnection() {
        Connection connection = null;
        try {
            connection = DriverManager.getConnection(url, username, password);
        } catch (SQLException e) {
            log.debug("SQLException during get connection to url = {} from {}.", url, Utils.class, e);
            e.printStackTrace();
        }
        return connection;
    }

    public static Connection getConnectionTest() {
        Connection connection = null;
        try {
            connection = DriverManager.getConnection(urlTest, username, password);
        } catch (SQLException e) {
            log.debug("SQLException during get connection to url = {} from {}.", urlTest, Utils.class, e);
            e.printStackTrace();
        }
        return connection;
    }

    public static void closeConnection(Connection conn) {
        if (conn != null) {
            try {
                conn.close();
                log.debug("Close connection from {}.", getPreviousClassName());
            } catch (SQLException e) {
                log.debug("SQLException during close connection from {}.", Utils.class, e);
                try {
                    throw new SQLException(e);
                } catch (SQLException e1) {
                    e1.printStackTrace();
                }
            }
        }
    }

    static void closeStatement(Statement statement) {
        if (statement != null) {
            try {
                statement.close();
            } catch (SQLException e) {
                log.debug("SQLException during close Statement from {}.", Utils.class, e);
                try {
                    throw new SQLException(e);
                } catch (SQLException e1) {
                    e1.printStackTrace();
                }
            }
        }
    }

    static void closeResultSet(ResultSet rs) {
        if (rs != null) {
            try {
                rs.close();
            } catch (SQLException e) {
                log.debug("SQLException during close ResultSet from {}.", Utils.class, e);
                try {
                    throw new SQLException(e);
                } catch (SQLException e1) {
                    e1.printStackTrace();
                }
            }
        }
    }
}
