import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.AfterClass;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class DataProviderDemo {
    private WebDriver d;

    @BeforeMethod
    public void inIt(){
        WebDriverManager.chromedriver().setup();
        d=new ChromeDriver();
    }

    @Test(dataProvider = "loginData")
    public void testBrowserLaunch(String userName, String password, String isValidData) throws InterruptedException {
//        System.out.println("UserName : " +   userName +"PassWord :  "  +password +"isValidData :" +   isValidData);
        d.get("https://opensource-demo.orangehrmlive.com/");
        WebElement email = d.findElement(By.xpath("//*[@id=\"txtUsername\"]"));
        email.clear();
        email.sendKeys(userName);
        WebElement ePassword = d.findElement(By.xpath("//*[@id=\"txtPassword\"]"));
        ePassword.clear();
        ePassword.sendKeys("Password");

        WebElement loginBtn = d.findElement(By.xpath("//*[@id=\"btnLogin\"]"));
        loginBtn.click();

        String expectedUrl = "https://opensource-demo.orangehrmlive.com/index.php/dashboard";
        String actualUrl =d.getCurrentUrl();
        if (isValidData.equals("Valid")){
            Assert.assertTrue(expectedUrl.equals(actualUrl));
            d.findElement(By.xpath("//*[@id=\"welcome\"]")).click();
            Thread.sleep(3000);
            d.findElement(By.xpath("//*[@id=\"welcome-menu\"]/ul/li[3]/a")).click();

        }
        else if(isValidData.equals("Invalid")){
            Assert.assertTrue(!expectedUrl.equals(actualUrl));
        }

    }
    @DataProvider(name= "loginData")
    public Object[][] getData(){
        String loginData[][]= {
                {"Admin", "admin123", "valid"},
                {"Admin", "admin", "invalid"},
                {"Admin", "adm", "Invalid"},
                {"Adm", "admin123", "Invalid"}
        };
        return loginData;
    }
    @AfterClass
    public void tearDown(){
        if(d!=null)
            d.quit();
   }

}
