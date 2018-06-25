package whiteboard_client;

public class ServerConfiguration {
    private String host;
    private int port;

    ServerConfiguration(String host, int port) {
        this.host = host;
        this.port = port;
    }

    ServerConfiguration() {
        this.host = "";
        this.port = -1;
    }

    public boolean isValid() {
        if (host.equals("") || port < 0) {
            return false;
        }

        return true;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public void setPort(String port) {
        try {
            this.port = Integer.parseInt(port);
        } catch (NumberFormatException e) {
            this.port = -1;
        }
    }
}
