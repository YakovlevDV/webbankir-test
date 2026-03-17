package tests;

import com.codeborne.selenide.Selenide;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.restassured.response.Response;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import utils.WireMockService;
import utils.WireMockStubs;

import static com.codeborne.selenide.Condition.text;
import static org.hamcrest.Matchers.equalTo;

public class DeleteUserTest extends BaseTest {

    @BeforeEach
    public void beforeTest() {
        WireMockService.startServer();
        WireMockStubs.postUserStub();
        WireMockStubs.getUserStub();
        WireMockStubs.deleteUserStub();
    }

    @AfterEach
    public void afterTest() {
        WireMockService.stopServer();
    }

    @Test()
    public void deleteUserTest() {
        String userId = "123";
        Response response;

        // Создаем нового пользователя через API
        JsonNode body = new ObjectMapper().createObjectNode()
                .put("name", "Dima");

        response = apiClient.createUser(body);
        response.then()
                .log().ifValidationFails()
                .statusCode(201);

        // Проверям, что пользователь создался через API
        response = apiClient.getUserById(userId);
        response.then()
                .statusCode(200)
                .body("id", equalTo("123"));

        // Проверяем, что он появился на странице /admin/users
        Selenide.open("/api/v1/users/" + userId);
        Selenide.$("body").shouldHave(text("{\"id\": \"123\"}"));

        // Удаляем пользователя через апи
        response = apiClient.deleteUser(userId);
        response.then()
                .log().ifValidationFails()
                .statusCode(204);

        // Обновляем страницу и проверям что пользователь удалился
        Selenide.refresh();
        Selenide.$("body").shouldNotHave(text("{\"id\": \"123\"}"));
    }
}