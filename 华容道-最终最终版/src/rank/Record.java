package rank;

public class Record {
    private String username;
    private int timeUsed; // 用时（秒）

    public Record(String username, int timeUsed) {
        this.username = username;
        this.timeUsed = timeUsed;
    }

    // Getters
    public String getUsername() { return username; }
    public int getTimeUsed() { return timeUsed; }

    @Override
    public String toString() {
        return username + "," + timeUsed;
    }
}
