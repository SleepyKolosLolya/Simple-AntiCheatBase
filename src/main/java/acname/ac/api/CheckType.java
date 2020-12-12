package acname.ac.api;

public enum CheckType {
    UTIL("Util", "UTIL");

    private final String checkName;
    private final String configLocation;

    CheckType(String checkName, String configLocation) {
        this.checkName = checkName;
        this.configLocation = configLocation;
    }

    public String getCheckName() {
        return checkName;
    }

    public String getConfigLocation() {
        return configLocation;
    }

}
