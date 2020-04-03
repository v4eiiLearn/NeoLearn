package ru.neoflex.tools;

import org.h2.tools.Server;

import java.sql.SQLException;

public class StartServer {
    private static Server server;
    public void init() {
        try {
            server = Server.createTcpServer("-tcpPort", "9092", "-tcpAllowOthers").start();
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static Server getServer() {
        return server;
    }
}
