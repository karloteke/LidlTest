import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.edge.EdgeDriver;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;
import static org.junit.jupiter.api.Assertions.assertEquals;


//D. Haz una copia de tu código a otro fichero e intenta introducir ahí el patrón Page Object.

public class PageObjectTest {
    public static class LidlPageObject {
        private WebDriver driver;
        private By cookiesAcceptButton = By.className("cookie-alert-extended-button");
        private By productTiles = By.className("product-grid-box-tile");
        private By searchBarInput = By.id("mainsearch-input");
        private By searchButton = By.cssSelector("#mainsearch-form > div.search-bar-container-button > button");
        private By cartBadge = By.xpath("//*[@id=\"minibasket\"]/a/span/b");
        private By addToCartButton = By.id("add-to-cart");

        public LidlPageObject(WebDriver driver) {
            this.driver = driver;
        }

        public void goToHomePage() {
            driver.get("https://www.lidl.es/");
        }

        public void goToPage() {
            driver.get("https://www.lidl.es/es/tefal-sarten-o-28-cm/p54267");
        }

        public void acceptCookies() {
            driver.findElement(cookiesAcceptButton).click();
        }

        public void searchFor(String query) {
            WebElement inputElement = driver.findElement(searchBarInput);
            inputElement.sendKeys(query);
            driver.findElement(searchButton).click();
        }

        public void acceptCookiesOnProductPage() {
            driver.findElement(cookiesAcceptButton).click();
        }

        public void addToCart() {
            driver.findElement(addToCartButton).click();
        }

        public String getCartBadgeText() {
            WebElement cartBadgeElement = driver.findElement(cartBadge);
            return cartBadgeElement.getText();
        }
    }

    @Test
    public void listProductsTest() {
        WebDriverManager.edgedriver().setup();
        EdgeDriver driver = new EdgeDriver();
        LidlPageObject page = new LidlPageObject(driver);
        page.goToHomePage();
        page.acceptCookies();
        int productCount = driver.findElements(page.productTiles).size();
        assertEquals(productCount, 0);
        driver.quit();
    }

    @Test
    public void ResultTitleTest() {
        WebDriverManager.edgedriver().setup();
        EdgeDriver driver = new EdgeDriver();
        LidlPageObject page = new LidlPageObject(driver);
        page.goToHomePage();
        page.acceptCookies();
        page.searchFor("galletas chocolate");
        String title = driver.getTitle();
        assertEquals("Resultado de búsqueda | Lidl", title);
        driver.quit();
    }

    @Test
    public void AddCartTest() {
        WebDriverManager.edgedriver().setup();
        EdgeDriver driver = new EdgeDriver();
        LidlPageObject page = new LidlPageObject(driver);
        page.goToPage();
        page.acceptCookies();
        page.addToCart();
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement cartBadgeElement = wait.until(ExpectedConditions.visibilityOfElementLocated(page.cartBadge));
        String badgeText = cartBadgeElement.getText();
        assertEquals("1", badgeText);
        driver.quit();
    }
}


