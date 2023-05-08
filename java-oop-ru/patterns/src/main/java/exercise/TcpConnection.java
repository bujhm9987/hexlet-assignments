package exercise;
import exercise.connections.Connected;
import exercise.connections.Connection;
import exercise.connections.Disconnected;

import java.util.List;
import java.util.ArrayList;

// BEGIN
public class TcpConnection {
    private String ip;
    private int port;
    private Connection state;

    public List<String> buffers = new ArrayList<>();

    public TcpConnection(String ip, int port) {
        this.ip = ip;
        this.port = port;
        this.state = new Disconnected(this);
    }

    public void connect() {
        state.connect();
    }

    public void disconnect() {
        state.disconnect();
    }

    public String getCurrentState() {
        return state.getCurrentState();
    }

    public void write(String buffer) {
        state.write(buffer);
    }

    public void setConnect(Connection connect) {
        state = connect;
    }


}
// END
