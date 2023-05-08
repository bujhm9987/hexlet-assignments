package exercise.connections;

import exercise.TcpConnection;

// BEGIN
public class Connected implements Connection{
    private TcpConnection connection;

    public Connected(TcpConnection connection) {
        this.connection = connection;
    }

    @Override
    public String getCurrentState() {
        return "connected";
    }

    @Override
    public void connect() {
        System.out.println("Error! Connection already connected");
    }

    @Override
    public void disconnect() {
        connection.setConnect(new Disconnected(connection));
    }

    @Override
    public void write(String buffer) {
        connection.buffers.add(buffer);
    }
}
// END
