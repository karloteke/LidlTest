import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class LidlTest {

    //A. Haz un test que liste los productos de una categoría y compruebe que se cargue el mismo número de productos que
    // se cargan en la web (es decir, cuéntalos a mano en tu navegador -por ej, categoría lavadoras-, y el selector
    //de tu test deberá ser uno del que puedas obtener cuántos elementos hay.

    @Test
    public void ListProductsTest() {
        WebDriverManager.edgedriver().setup();
        EdgeDriver driver = new EdgeDriver();
        driver.get("https://www.lidl.es/es/sillas-de-coche/c6316");

        //Aceptamos cookies
        WebElement cookies = driver.findElement(By.className("cookie-alert-extended-button"));
        cookies.click();

        //Listamos productos
        List<WebElement> products = driver.findElements(By.className("product-grid-box-tile"));
        assertEquals(products.size(), 0);
        driver.quit();
    }

    //B. Haz otro test que use la barra de búsqueda del e-commerce, y compruebe el título del resultado (el contenido del
    // elemento html <title> de la página web tras buscar).

    @Test
    public void ResultTitleTest() {
        WebDriverManager.edgedriver().setup();
        EdgeDriver driver = new EdgeDriver();
        driver.get("https://www.lidl.es/");

        //Aceptar cookies
        WebElement cookies = driver.findElement(By.className("cookie-alert-extended-button"));
        cookies.click();

        //Acceder a la barra de búsqueda
        WebElement inputElement = driver.findElement(By.id("mainsearch-input"));

        //Escribimos galletas chocolate en la barra de búsqueda
        inputElement.sendKeys("galletas chocolate");

        //Damos click al botón buscar
        WebElement searchButton = driver.findElement(By.cssSelector("#mainsearch-form > div.search-bar-container-button > button"));
        searchButton.click();
        String title = driver.getTitle();
        assertEquals("Resultado de búsqueda | Lidl", title);
        driver.quit();

    }

    //C. Haz un test que sea capaz de añadir un artículo al carrito. Razona qué testearías aquí y cómo
    // Otra forma sería, después de añadir el producto al carrito => navegamos a la página del carrito =>
    // y comprobamos que el producto se ha añadido a la lista.

    @Test
    public void AddCartTest() {
        WebDriverManager.edgedriver().setup();
        EdgeDriver driver = new EdgeDriver();
        driver.get("https://www.lidl.es/es/tefal-sarten-o-28-cm/p54267");
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        //Aceptar cookies
        WebElement cookies = driver.findElement(By.className("cookie-alert-extended-button"));
        cookies.click();

        //Añadimos producto al carrito
        WebElement inputElement = wait.until(ExpectedConditions.elementToBeClickable(By.id("add-to-cart")));
        inputElement.click();

        //Comprobamos que se nos muestre un popup con la notificación de que se ha añadido el producto
        WebElement badge = driver.findElement(By.className("status-area"));
        WebElement boldTag = badge.findElement(By.xpath("//*[@id=\"minibasket\"]/a/span/b"));
        String badgeText = boldTag.getText();
        assertEquals("1", badgeText);
        driver.quit();
    }
}