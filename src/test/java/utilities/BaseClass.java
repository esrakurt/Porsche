package utilities;

import java.util.Random;
import com.github.javafaker.Faker;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.util.concurrent.TimeUnit;


public class BaseClass {


    public WebDriver driver;
    public Faker faker;
    public Random random;
    public Select select;
    public Actions action;


    //      Open browser
    public void openWebsite() {

        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        action = new Actions(driver);
        driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
        faker= new Faker();
        random= new Random();
        driver.get("https://www.porsche.com/usa/modelstart/");
    }
}

/*
package utilities;
import com.github.javafaker.Faker;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.asserts.SoftAssert;
import java.util.concurrent.TimeUnit;

public abstract class TestBase {
    protected WebDriver driver;
    protected Actions actions;
    protected SoftAssert softAssert;
    protected Faker faker;

    @BeforeClass
    public void setUpClass(){
        WebDriverManager.chromedriver().setup();
    }
    @BeforeMethod
    public void setUpMethod(){
        driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        actions = new Actions(driver);
        softAssert = new SoftAssert();
        faker = new Faker();
        driver.manage().window().maximize();
    }
    @AfterMethod
    public void tearDownMethod(){
         driver.quit();
        softAssert.assertAll();
    }
}

 */
