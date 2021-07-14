package io.muic.ooc.webapp.security;

import com.zaxxer.hikari.HikariDataSource;
import io.muic.ooc.webapp.config.ConfigProperties;
import io.muic.ooc.webapp.config.ConfigurationLoader;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DatabaseConnectionService {

    private final HikariDataSource ds;

    /**
     * Database connection pool using hikari library
     * The secret and variables are loaded from disk
     * config.properties is not committed to git repository
     */

    public DatabaseConnectionService() {
        ds = new HikariDataSource();
        ds.setMaximumPoolSize(20);
        ConfigProperties configProperties = ConfigurationLoader.load();
        if(configProperties == null) {
            throw new RuntimeException("Unable to read the config.properties.");
        }
        ds.setDriverClassName(configProperties.getDatabaseDriverClassName());
        ds.setJdbcUrl(configProperties.getDatabaseConnectionUrl());
        ds.addDataSourceProperty("user", configProperties.getDatabaseUsername());
        ds.addDataSourceProperty("password", configProperties.getDatabasePassword());
        ds.setAutoCommit(false);
    }

    public Connection getConnection() throws SQLException{
        return ds.getConnection();
    }

//    public static void main(String[] args) {
//        final HikariDataSource ds = new HikariDataSource();
//        ds.setMaximumPoolSize(20);
//        ds.setDriverClassName("org.mariadb.jdbc.Driver");
//        ds.setJdbcUrl("jdbc:mariadb://localhost:3306/login_webapp");
//        ds.addDataSourceProperty("user", "mansahej");
//        ds.addDataSourceProperty("password", "mansahej20");
//        ds.setAutoCommit(false);
//
//
//        try {
//            Connection connection = ds.getConnection();
//            String sql = "INSERT INTO tbl_user(username, password, display_name) VALUES (?, ?, ?);";
//            PreparedStatement ps = connection.prepareStatement(sql);
//            // setting username column
//            ps.setString(1, "my_username");
//            // setting password column
//            ps.setString(2, "my_password");
//            // setting display name column
//            ps.setString(3, "my_display_name");
//            ps.executeUpdate();
//            //manually commit the change
//            connection.commit();
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//
//    }

}