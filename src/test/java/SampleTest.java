import io.github.bonigarcia.wdm.WebDriverManager;
import org.apache.logging.log4j.LogManager;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Action;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

public class SampleTest {

    protected static WebDriver driver;
    private Logger logger = LogManager.getLogger(SampleTest.class);
    Actions action;

    @Before
    public void setUp(){
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        logger.info("Драйвер поднят");
        action = new Actions(driver);
        Duration duration = Duration.ofSeconds(10);
        driver.manage().timeouts().implicitlyWait(duration);

//wait for loading page
        driver.manage().timeouts().pageLoadTimeout(duration);

//wait for an asynchronous script to finish execution
        driver.manage().timeouts().setScriptTimeout(duration);
    }


    @Test
    public void openPage(){
        driver.get("https://otus.ru");
        logger.info("Открыта страница otus");
    }

    @Test
    public void draw(){
        driver.get("http://www.htmlcanvasstudio.com/");

        WebElement canvas = driver.findElement(By.cssSelector("#imageTemp"));

        Actions beforeBuild = action.clickAndHold(canvas).moveByOffset(100, 100).moveByOffset(-50, -10).release();
        Action afterBuild = beforeBuild.build();
        afterBuild.perform();
    }

    @Test
    public void audi() throws InterruptedException {
        driver.get("https://www.drive2.ru/cars/audi/?sort=selling");

        JavascriptExecutor js = ((JavascriptExecutor) driver);
        WebElement footer = driver.findElement(By.xpath("//footer[@class='l-footer']"));
        //js.executeScript("window.scrollTo(0, document.body.scrollHeight)");
        List<WebElement> cars = driver.findElements(By.xpath("//div[@class='pyOJNXBxPT6PZIslNZA__caption']"));

        while(true){
            int i = cars.size();
            js.executeScript("window.scrollTo(0, document.body.scrollHeight)");
            Thread.sleep(5000);
            cars = driver.findElements(By.xpath("//span[@class='c-car-title c-link c-link--text']"));
            if (i == cars.size())
                break;
        }

        for (int i = 0; i < cars.size(); i++) {
            System.out.println(cars.get(i).getText());
        }
    }

    @After
    public void setDown(){
        if(driver != null){
            driver.quit();
        }
    }
}
