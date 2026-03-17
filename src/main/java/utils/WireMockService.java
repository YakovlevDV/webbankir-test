package utils;

import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.core.WireMockConfiguration;

public class WireMockService {

    private static WireMockServer server;

    private WireMockService() {
    }

    public static void startServer() {
        if (server == null) {
            server = new WireMockServer(WireMockConfiguration
                    .options().port(8080));
            server.start();
        }
    }

    public static void stopServer() {
        if (server.isRunning()) {
            server.stop();
        }
    }
}