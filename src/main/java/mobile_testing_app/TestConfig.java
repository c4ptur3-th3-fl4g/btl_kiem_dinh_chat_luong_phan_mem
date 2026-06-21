package mobile_testing_app;

public final class TestConfig {
    private TestConfig() {
    }

    public static String value(String name) {
        String systemValue = System.getProperty(name);
        if (systemValue != null && !systemValue.isBlank()) {
            return systemValue;
        }
        String environmentValue = System.getenv(name);
        return environmentValue == null ? "" : environmentValue.trim();
    }
}
