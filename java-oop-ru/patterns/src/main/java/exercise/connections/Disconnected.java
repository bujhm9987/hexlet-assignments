package exercise.connections;

import exercise.TcpConnection;

// BEGIN
public class Disconnected implements Connection{
    private TcpConnection connection;

    public Disconnected(TcpConnection connection) {
        this.connection = connection;
    }

    @Override
    public String getCurrentState() {
        return "disconnected";
    }

    @Override
    public void connect() {
        connection.setConnect(new Connected(connection));
    }

    @Override
    public void disconnect() {
        System.out.println("Error! Connection already disconnected");
    }

    @Override
    public void write(String buffer) {
        System.out.println("Error! Connection already disconnected. Recording is not possible");
    }
}
// END
