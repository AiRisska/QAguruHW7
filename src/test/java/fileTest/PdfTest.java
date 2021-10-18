package fileTest;

import com.codeborne.pdftest.PDF;
import com.codeborne.pdftest.assertj.Assertions;
import com.codeborne.selenide.logevents.SelenideLogger;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import io.qameta.allure.selenide.AllureSelenide;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;

import static com.codeborne.pdftest.PDF.containsText;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;
import static io.qameta.allure.Allure.step;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class PdfTest {

    @Feature("pdfFile")
    @Story("Тестирование скачанного файла")
    @Test
    void pdfGuideTest() throws IOException {
        SelenideLogger.addListener("allure", new AllureSelenide());
        open("https://junit.org/junit5/docs/current/user-guide/");
        File pdfDownloadedFile = $(byText("PDF download")).download();
        PDF parsedPdf = new PDF(pdfDownloadedFile);

        step ("Test: наличие текста в документе", () -> {
            assertThat(parsedPdf, containsText("JUnit 5 User Guide"));
        });

        step("Test: один из авторов документа - Sam Brannen", () -> {
            String authorPdf = parsedPdf.author;
            Assertions.assertThat(authorPdf).contains("Sam Brannen");
        });

        step("Test: количество страниц в документе = 164", () -> {
            int numberPages = parsedPdf.numberOfPages;
            Assertions.assertThat(numberPages).isEqualTo(164);
        });

        step("Test: наименование файла в свойствах файла", () -> {
            String titlePdf = parsedPdf.title;
            Assertions.assertThat(titlePdf).isEqualTo("JUnit 5 User Guide");
        });
    }
    @Feature("pdfFile")
    @Story("Тестирование файла из папки resources")
    @Test
    void examplePdfTest () throws IOException {
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        File file = new File(classLoader.getResource("examplePDF.pdf").getFile());
        PDF parsedPdf = new PDF(file);
        step("Проверяем существование файла в папке", ()->{
            assertTrue(file.exists());
        });

        step ("Test: наличие текста в документе", () -> {
            assertThat(parsedPdf, containsText("Царенко Тимофей Петрович"));
        });

        step("Test: автор документа - irina", () -> {
            String authorPdf = parsedPdf.author;
            Assertions.assertThat(authorPdf).contains("irina");
        });

        step("Test: количество страниц в документе = 20", () -> {
            int numberPages = parsedPdf.numberOfPages;
            Assertions.assertThat(numberPages).isEqualTo(20);
        });
    }
}
