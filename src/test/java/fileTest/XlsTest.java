package fileTest;

import com.codeborne.xlstest.XLS;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.InputStream;

import static io.qameta.allure.Allure.step;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class XlsTest {

    @Feature("xlsFile")
    @Story("Тестирование файла в папке resources")
    @Test
    void xlsFileTest() throws Exception {

        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        InputStream stream = classLoader.getResourceAsStream("exampleXLS.xls");

        step("Проверяем существование файла в папке", () -> {
            assertTrue(stream != null);
        });

        XLS xlsFile = new XLS(stream);

        step("Test: количество листов в файле не менее 1", () -> {
            int sheets = xlsFile.excel.getNumberOfSheets();
            Assertions.assertTrue(sheets >= 1);
        });

        step("Test: Количество строк на листе 1 не менее 25, где value != null", () -> {
            int rowSheet1 = xlsFile.excel.getSheetAt(0).getPhysicalNumberOfRows();
            Assertions.assertTrue(rowSheet1 >= 25);
        });

        step("Test: Количество строк на листе 2 более 1, где value != null", () -> {
            int rowSheet2 = xlsFile.excel.getSheetAt(1).getPhysicalNumberOfRows();
            Assertions.assertTrue(rowSheet2 > 1);
        });

        step("В отчете \"ИСПОЛНИТЕЛЬ Рудольф Багмет\"", () -> {
            Assertions.assertEquals("ИСПОЛНИТЕЛЬ Рудольф Багмет",
                    xlsFile.excel.getSheetAt(0).getRow(8).getCell(0).getStringCellValue());
        });

    }
}
