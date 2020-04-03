package ru.neoflex.tools;

public class StopServer {
    public void stopServer() {
        StartServer.getServer().stop();
    }
}
