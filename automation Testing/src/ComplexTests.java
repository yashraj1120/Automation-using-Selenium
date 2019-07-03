/**
 * These are the complex Test Cases from the selenium exercises automation xlsx file
 * Contains tests from c1 to c11 
 * 
 * @author ybaheti
 * @date 28-June-2019
 */

import java.util.List;
import java.util.Scanner;
import java.util.Random;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.datatransfer.StringSelection;
import java.awt.event.KeyEvent;

public class ComplexTests {

	static String exePath = "C:\\Users\\ybaheti\\Downloads\\chromedriver_win32\\chromedriver.exe";
	Scanner sc;

	//Constructor 
	public ComplexTests() 
	{
		System.setProperty("webdriver.chrome.driver", exePath); 
		this.sc=new Scanner(System.in);
	}

	//	
	//HELPER FUNCTIONS
	//google search
	public String googleSearch(WebDriver driver)
	{
		driver.get("https://www.google.com");
		driver.manage().window().maximize();
		
		WebDriverWait wait = new WebDriverWait(driver, 10);
		
		//Take input from user and search
		System.out.println("Enter Search Item");
		String searchItem = sc.nextLine();
		
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("q"))).sendKeys(searchItem);
		wait.until(ExpectedConditions.elementToBeClickable(By.name("btnK"))).click();
		
		return searchItem;
	}

	//Gets the current page number 
	public int getPageNo(WebDriver driver)
	{
		//Scroll to top of the page
		JavascriptExecutor js = ((JavascriptExecutor) driver);
		js.executeScript("window.scrollTo(0, 0)");

		//Parse the result stats and gets the age number
		String resultStats = driver.findElement(By.id("resultStats")).getText();
		if(resultStats.startsWith("Page"))
		{
			return Integer.parseInt(resultStats.split(" ")[1]);
		}
		return 1;
	}

	//random number generator in range
	public static int getRandomNumberInRange(int min, int max) {

		if (min >= max) {
			throw new IllegalArgumentException("max must be greater than min");
		}

		Random r = new Random();
		return r.nextInt((max - min) + 1) + min;
	}

	//checks pageNumber with prev page number 
	public static Boolean checkPage(int currPage, int nextPage)
	{

		if(nextPage - 1 != currPage)
		{
			System.out.println("Wrong navigation");
			return true;
		}
		return false;
	}

	//System.out.println Wrapper
	public void sop(Object s)
	{
		System.out.println(s);
	}
	
	//GOOGLE SIGN-IN
	public void googleSignIn(WebDriver driver)
	{
		WebDriverWait wait = new WebDriverWait(driver, 10);
		
		try
		{
			wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id=\"gb_70\"]"))).click();
			driver.findElement(By.xpath("//*[@id=\"identifierId\"]")).sendKeys("zonekill77");
			wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id=\"identifierNext\"]/span/span"))).click();
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id=\"password\"]/div[1]/div/div[1]/input"))).sendKeys("zonekillpassword");
			wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id=\"passwordNext\"]/span/span"))).click();
			Thread.sleep(2000);
			driver.navigate().to("https://www.google.com/");
		} 
		catch (InterruptedException e) 
		{
			e.printStackTrace();
		}
	}
	

	//Complex Tests
	public void c1()
	{
		WebDriver driver = new ChromeDriver();

		String searchItem = googleSearch(driver);

		List<WebElement> searches = driver.findElements(By.className("r"));
		List<WebElement> searchLinks = driver.findElements(By.className("iUh30"));
		for(int link_no = 0 ;link_no<searchLinks.size(); link_no++)
		{
			WebElement link = searches.get(link_no);

			//TODO CHANGES TO CODE 
			String[] links = searches.get(link_no).getText().split("\n");
			
			try
			{
				//System.out.println(link.getText()+"\n");
				if(!links[0].split(" ")[0].toLowerCase().equals(searchItem.toLowerCase()))
				{
					String linkUrl = links[1];
					sop(linkUrl);
					try 
					{
						//((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", link);
						Thread.sleep(1000); 
						link.findElement(By.className("LC20lb")).click();
						if(linkUrl.equals(driver.getCurrentUrl()))
						{
							System.out.println("Url matches");
						}
						else
						{
							System.out.println("Url does not match");
						}
						break;
					} 
					catch (InterruptedException e)
					{
						e.printStackTrace();
					}
				}
			}
			catch (Exception e) 
			{
				// TODO Auto-generated catch block
			}
		}
		System.out.println("Successful Test c1");
	}

	public void c2()
	{

		WebDriver driver = new ChromeDriver();

		googleSearch(driver);

		WebDriverWait wait=new WebDriverWait(driver, 5);

		try
		{
			wait.until(ExpectedConditions.elementToBeClickable(By.id("hdtb-tls"))).click();
			wait.until(ExpectedConditions.elementToBeClickable(wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("hdtb-td-o"))).findElement(By.xpath("//*[@id=\"hdtbMenus\"]/div/div[3]/div")))).click();
			wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id=\"li_1\"]/a"))).click();
		} 
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	public void c3()
	{
		WebDriver driver = new ChromeDriver();

		driver.get("https://www.google.com/");

		WebDriverWait wait=new WebDriverWait(driver, 10);	

		try
		{
			wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id=\"fsettl\"]"))).click();
			wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id=\"fsett\"]/a[2]"))).click();
			String exactPhrase = sc.nextLine();
			String nonePhrase = sc.nextLine();
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id=\"CwYCWc\"]"))).sendKeys(exactPhrase);
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id=\"t2dX1c\"]"))).sendKeys(nonePhrase);
			wait.until(ExpectedConditions.elementToBeClickable(By.xpath("/html/body/div[1]/div[4]/form/div[5]/div[9]/div[2]/input"))).click();
		}
		catch (Exception e) 
		{
			e.printStackTrace();
		}
	}

	public void c4()
	{
		WebDriver driver = new ChromeDriver();

		googleSearch(driver);

		try
		{
			List<WebElement> searches = driver.findElements(By.className("LC20lb"));
			for(WebElement searchLink: searches)
			{
				if(searchLink.getText().contains("Wikipedia"))
				{
					searchLink.click();
				}
			}
		}
		catch (Exception e) 
		{
			e.printStackTrace();
		}
	}

	public void c5()
	{
		WebDriver driver = new ChromeDriver();

		driver.get("https://www.google.com/");

		WebDriverWait wait=new WebDriverWait(driver, 5);

		String searchItem = sc.nextLine();
		try
		{
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id=\"tsf\"]/div[2]/div/div[1]/div/div[1]/input"))).sendKeys(searchItem);
			wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id=\"tsf\"]/div[2]/div/div[2]/div[2]/div[2]/center/input[2]"))).click();
		}
		catch (Exception e) 
		{
			e.printStackTrace();
		}
	}


	//google calender
	public void c6()
	{
		WebDriver driver = new ChromeDriver();

		driver.get("https://www.google.com/");
		
		googleSignIn(driver);
		
		driver.manage().window().maximize();
		WebDriverWait wait=new WebDriverWait(driver, 10);

		try
		{
			wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id=\"gbwa\"]/div[1]/a"))).click();
			wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id=\"gb24\"]/span[1]"))).click();
			List<WebElement> dates = wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.xpath("//*[@id=\"drawerMiniMonthNavigator\"]/div/div[2]/div[2]/div/span")));
			int countToRemove=0;
			for(WebElement date:dates)
			{
				countToRemove++;
				if(date.getText().split(",").length>1)
				{
					break;
				}	
			}
			int randomDate = getRandomNumberInRange(countToRemove, 41);
			dates.get(randomDate).click();
			wait.until(ExpectedConditions.elementToBeClickable(By.xpath("/html/body/div[2]/div[1]/div[1]/div[1]/button/span/div[2]"))).click();
			String eventTitle = sc.nextLine();
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id=\"yDmH0d\"]/div/div/div[2]/span/div/div[1]/div[1]/div[3]/div/div[1]/div/div[1]/input"))).sendKeys(eventTitle);
			wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id=\"yDmH0d\"]/div/div/div[2]/span/div/div[1]/div[2]/div[3]/span/span"))).click();
			
		}
		catch (Exception e) 
		{
			System.out.print(e);
		}
	}

	
	//google drive upload
	public void c7()
	{
		WebDriver driver = new ChromeDriver();

		driver.get("https://www.google.com/");
		driver.manage().window().maximize();
		WebDriverWait wait=new WebDriverWait(driver, 10);

		try
		{
			wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id=\"gbwa\"]/div[1]/a"))).click();
			wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id=\"gb24\"]/span[2]"))).click();
			
		}
		catch (Exception e) 
		{
			System.out.print(e);
		}
	}


	//-----------------------------------------
	//change profile pic of google account
	public void setClipboardData(String string) 
	{
		//StringSelection is a class that can be used for copy and paste operations.
		StringSelection stringSelection = new StringSelection(string);
		Toolkit.getDefaultToolkit().getSystemClipboard().setContents(stringSelection, null);
	}

	public void uploadFile(String fileLocation) {
		try 
		{
			//Setting clipboard with file location
			setClipboardData(fileLocation);
			//native key strokes for CTRL, V and ENTER keys
			Robot robot = new Robot();
			sop("!!!");
			Thread.sleep(1000);
			
			robot.keyPress(KeyEvent.VK_CONTROL);
			robot.keyPress(KeyEvent.VK_V);
			
			robot.keyRelease(KeyEvent.VK_V);
			robot.keyRelease(KeyEvent.VK_CONTROL);
			Thread.sleep(1000);
			
			robot.keyPress(KeyEvent.VK_ENTER);
			robot.keyRelease(KeyEvent.VK_ENTER);
		} 
		catch (Exception exp)
		{
			exp.printStackTrace();
		}
	}
	
	public void c8()
	{
		WebDriver driver = new ChromeDriver();

		driver.get("https://www.google.com/");
		
		googleSignIn(driver);
		
		driver.manage().window().maximize();
		WebDriverWait wait=new WebDriverWait(driver, 20);

		try
		{
			wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id=\"gbw\"]/div/div/div[2]/div[2]/div[1]/a/span"))).click();
			wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id=\"gbw\"]/div/div/div[2]/div[2]/div[2]/div[1]/a"))).click();
			
			WebElement div = wait.until(ExpectedConditions.visibilityOfElementLocated((By.xpath("//*[@id=\"gsr\"]/div[3]"))));
			driver.switchTo().frame(div.findElement(By.tagName("iframe")));
			
			wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id=\"doclist\"]/div/div[4]/div[2]/div/div[2]/div/div/div[1]/div/div[2]/div[1]/div/div/div[1]"))).click();
			
			uploadFile("C:\\Users\\ybaheti\\Pictures\\y (2).png");
			
			wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id=\"doclist\"]/div/div[4]/div[2]/div/div[2]/div/div[2]/div[3]/span/a"))).click();
			wait.until(ExpectedConditions.elementToBeClickable(By.id("picker:ap:3"))).click();
			
			driver.switchTo().defaultContent();
			
		}
		catch (Exception e) 
		{
			System.out.print(e);
		}
	}

	//----------------------------------------------------------
	public void c9()
	{
		try {
			WebDriver driver = new ChromeDriver();

			driver.get("https://www.google.com/");
			driver.manage().window().maximize();
			
			WebDriverWait wait=new WebDriverWait(driver, 20);
			
			driver.navigate().to("https://mail.rediff.com/cgi-bin/login.cgi");
			
			wait.until(ExpectedConditions.elementToBeClickable(By.name("proceed"))).click();
			
			Thread.sleep(2000);
			driver.switchTo().alert().accept();
		} 
		catch (InterruptedException e) 
		{
			e.printStackTrace();
		}
		sop("Successfull test"+" c9");
	}
	
	//----------------------------------------------------------
	//TODO c10
	public void c10()
	{
		WebDriver driver = new ChromeDriver();
		

		driver.get("https://www.google.com/");
		driver.manage().window().maximize();
		
		driver.navigate().to("https://www.cacert.org/");
		
	}
	
	//----------------------------------------------------------
	public void c11()
	{
		try {
			WebDriver driver = new ChromeDriver();

			googleSearch(driver);

			JavascriptExecutor jse = (JavascriptExecutor)driver;
			jse.executeScript("scroll(0, 400)"); 
			Thread.sleep(2000);
			driver.close();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		sop("Successfull test"+" c11");
	}

	public static void main(String[] args)
	{
		ComplexTests ct = new ComplexTests();
		//ct.c1();
		//ct.c2();		//TODO VERIFY LINKS
		//ct.c3();		//TODO VERIFY LINKS 
		//ct.c4();		//TOTO VERIFY
		//ct.c5();		//TOTO VERIFY
		
		//google calendar
		//ct.c6();	
		
		//ct.c7(); 		//TODO
		
		//ct.c8();      //refresh page
		
		//ct.c9();
		
		//ct.c10();     //TODO
		
		//ct.c11();
	}


}
