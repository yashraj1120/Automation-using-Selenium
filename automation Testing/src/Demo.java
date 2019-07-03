
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;  
import org.openqa.selenium.WebDriver;  
import org.openqa.selenium.chrome.ChromeDriver;  
  
public class Demo {  
  
    public static void main(String[] args) {  
        
    // declaration and instantiation of objects/variables  
    String exePath = "C:\\Users\\ybaheti\\Downloads\\chromedriver_win32\\chromedriver.exe";
	System.setProperty("webdriver.chrome.driver", exePath); 
	
	WebDriver driver = new ChromeDriver();
	
	//setting implicit wait 
	driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
	
	// Launch website  
    driver.navigate().to("http://www.google.com/");  
          
    // Click on the search text box and send value  
    //driver.findElement(By.name("q")).sendKeys("javatpoint tutorials");  
    driver.findElement(By.xpath("//*[@id=\"tsf\"]/div[2]/div/div[1]/div/div[1]/input")).sendKeys("opentext");; 
   
    // Click on the search button  
    driver.findElement(By.name("btnK")).click();  
      
    }  
  
}  
