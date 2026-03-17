package tests;

import apis.UserApiClient;
import com.codeborne.selenide.Configuration;
import org.junit.jupiter.api.BeforeEach;

public class BaseTest {

    public final String BASE_URL = "http://localhost:8080";
    protected UserApiClient apiClient;

    @BeforeEach
    public void setUp() {
        Configuration.browser = System.getProperty("browser", "chrome");
        Configuration.headless = Boolean.parseBoolean(System.getProperty("headless"));
        Configuration.timeout = 5000;
        Configuration.screenshots = true;
        Configuration.baseUrl = BASE_URL;

        apiClient = UserApiClient.getInstance(BASE_URL);
    }
}