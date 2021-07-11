package Server;


import Communication.Connection;

import java.io.IOException;

public class ServerMain {
    public static void main(String[] args) throws IOException {
        ServerConnection serverConnection = new ServerConnection(ServerConfigurations.getConfigurations().getServerPort());
        while (true) {
            try {
                Connection connection = serverConnection.acceptNewConnection();
                Thread newTask = new Thread(new RequestsHandler(connection));
                newTask.start();
            } catch (Exception e) {
                ServerLogger.log(e);
            }
        }
    }
}