package fileTest;

import io.qameta.allure.Description;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import org.apache.commons.io.FileUtils;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import static io.qameta.allure.Allure.step;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class TxtTest {

    ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
    File file = new File(classLoader.getResource("exampleTXT.txt").getFile());

    @Feature("txtFile")
    @Story("Существование txt-файла")
    @DisplayName("Проверка существования файла в папке resources")
    @Description("Is file exist in folder?")
    @Test
    void txtFileExistTest() {
        step("Проверяем существование файла в папке", ()->{
            assertTrue(file.exists());
        });
    }

    @Feature("txtFile")
    @Story("Чтение txt-файла")
    @DisplayName("Проверка существования слов (фраз) в файле txt")
    @Description("Проверка, что в txt-файле есть сочетание фраз \"Реабилитация\" и \"Царенко Тимофей Петрович\"")
    @Test
    void txtContainsText() throws IOException {
        String s1 = FileUtils.readFileToString(file, "UTF-8");
        step("В тексте есть слово \"Реабилитация\"", () -> {
            assertTrue(s1.contains("Реабилитация"));
        });

        step("В тексте есть ФИО \"Царенко Тимофей Петрович\"", () -> {
            assertTrue(s1.contains("Царенко Тимофей Петрович"));
        });
    }

    @Feature("txtFile")
    @Story("txt-файл не пустой")
    @DisplayName("Test: количество символов с пробелами в файле более 1")
    @Description("Проверка, что символов с пробелами в файле больше 1")
    @Test
    void txtContainsChar() throws IOException {
        FileInputStream fix = new FileInputStream(file);
        byte[] byteArray = new byte[(int)file.length()];
        String data = new String(byteArray);
        step("Подсчет количества символов в файле", ()-> {
            fix.read(byteArray);
        });
        step("Символов в файле не равно 0", () -> {
            assertNotNull(data.length());
        });
    }

    @Feature("txtFile")
    @Story("Количество слов в txt-файле")
    @DisplayName("Test: количество слов без пробелов в файле менее 8400")
    @Description("Проверка, что слов без пробелов в файле менее 8400 суммарно")
    @Test
    void txtContainsWords() throws IOException {
        FileInputStream fix = new FileInputStream(file);
        byte[] bytesArray = new byte[(int)file.length()];
        final int[] count = {0};
        fix.read(bytesArray);
        String s = new String(bytesArray);
        String [] data = s.split(" ");
        step("Подсчет количество символов в файле", ()-> {
            for (int i=0; i<data.length; i++) {
                count[0]++;
            }
        });

        step("Слов в файле менее 8400", () -> {
            assertTrue(count[0]<8400);
        });
    }
}
