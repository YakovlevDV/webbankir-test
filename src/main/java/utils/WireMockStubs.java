package utils;

import com.github.tomakehurst.wiremock.stubbing.StubMapping;
import io.restassured.http.ContentType;

import static com.github.tomakehurst.wiremock.client.WireMock.*;

public class WireMockStubs {

    public static StubMapping postUserStub() {
        return stubFor(post(urlEqualTo("/api/v1/users"))
                .inScenario("test")
                .whenScenarioStateIs("Started")
                .willSetStateTo("Created")
                .willReturn(
                        created()
                        .withBody("{\"id\": \"123\"}")));
    }

    public static StubMapping getUserStub() {
        return stubFor(get(urlEqualTo("/api/v1/users/123"))
                .inScenario("test")
                .whenScenarioStateIs("Created")
                .willReturn(ok().withHeader("Content-Type", "application/json").withBody("{\"id\": \"123\"}")));
    }

    public static StubMapping deleteUserStub() {
        return stubFor(delete(urlEqualTo("/api/v1/users/123"))
                .inScenario("test")
                .whenScenarioStateIs("Created")
                .willSetStateTo("Deleted")
                .willReturn(noContent()));
    }
}