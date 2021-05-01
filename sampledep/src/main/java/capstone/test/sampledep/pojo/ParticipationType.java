package capstone.test.sampledep.pojo;

public enum ParticipationType {
    HOST("host", 0),
    ATTENDEE("attendee", 1);

    private final String key;
    private final Integer value;

    ParticipationType(String key, Integer value) {
        this.key = key;
        this.value = value;
    }

    public String getKey() {
        return key;
    }
    public Integer getValue() {
        return value;
    }
}
