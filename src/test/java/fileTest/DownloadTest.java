package fileTest;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.logevents.SelenideLogger;
import io.qameta.allure.*;
import io.qameta.allure.selenide.AllureSelenide;
import org.junit.jupiter.api.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;
import static io.qameta.allure.Allure.step;
import static org.assertj.core.api.Assertions.assertThat;

public class DownloadTest {
    @BeforeAll
    static void beforeAll() {
        Configuration.startMaximized = true;
    }

    @Feature("Download")
    @Story("Загрузка файла по URL")
    @DisplayName("Загрузка файла с сайта и проверка его содержимого")
    @Test
    void downloadTxtTest() throws Exception {
        SelenideLogger.addListener("allure", new AllureSelenide());
        open("https://github.com/selenide/selenide/blob/master/README.md");
        File download = $("#raw-url").download();

        step("File should contains text", () -> {
        String result;
        try (InputStream is = new FileInputStream(download)) {
            result = new String(is.readAllBytes(), "UTF-8");
            assertThat(result).contains("Selenide = UI Testing Framework powered by Selenium WebDriver");
            }
        });
    }
    @Feature("Upload")
    @Story("Загрузка файла на сайт через кнопку")
    @DisplayName("Загрузка файла на сайт и проверка по названию")
    @Test
    void uploadFileTest() {
        SelenideLogger.addListener("allure", new AllureSelenide());
        open("https://convert.io/ru/document-converter");
        $(".picker-dropdown__input").uploadFromClasspath("exampleTXT.txt");
//        Configuration.pageLoadTimeout = 300000;
        $(".file-name").shouldHave(text("exampleTXT.txt"));

    }

}
