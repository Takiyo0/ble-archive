package us.takiyo.extensions;

public class SocketData {
    private String op = "";
    private String data = "";
    private String sessionId = null;

    public SocketData(String op, String data, String sessionId) {
        this.op = op;
        this.data = data;
        this.sessionId = sessionId;
    }

    public String getOp() {
        return this.op;
    }

    public String getData() {
        return this.data;
    }

    public String getSessionId() {
        return this.sessionId;
    }

    public static SocketData parseData(String data) {
        try {
            String[] datas = data.split("#%");
            return new SocketData(datas[0], datas[1], datas.length > 2 ? datas[2] : null);
        } catch (Exception ignored) {
            return null;
        }
    }
}
