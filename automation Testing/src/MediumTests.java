/**
 * These are the Medium Test Exercises from the selenium exercises automation xlsx file
 * Contains tests from m1 to m5 
 * 
 * @author ybaheti
 * @date 26-June-2019
 */

import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.Scanner;
import java.util.Random;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;


public class MediumTests 
{
	static String exePath = "C:\\Users\\ybaheti\\Downloads\\chromedriver_win32\\chromedriver.exe";
	WebDriver driver;
	Scanner sc;

	//Constructor 
	public MediumTests() 
	{
		System.setProperty("webdriver.chrome.driver", exePath); 
		this.sc=new Scanner(System.in);
	}


	//HELPER FUNCTIONS

	//google search
	public String googleSearch()
	{
		String searchItem = null;

		this.driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
		this.driver.get("https://www.google.com");
		this.driver.manage().window().maximize();
		//Take input from user and search
		System.out.println("Enter Search Item");
		searchItem = sc.nextLine();
		System.out.println("Hello");
		this.driver.findElement(By.name("q")).sendKeys(searchItem);
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		this.driver.findElement(By.name("btnK")).click();
		return searchItem;

	}

	//Gets the current page number 
	public int getPageNo()
	{
		//Scroll to top of the page
		JavascriptExecutor js = ((JavascriptExecutor) driver);
		js.executeScript("window.scrollTo(0, 0)");

		//Parse the result stats and gets the age number
		String resultStats = this.driver.findElement(By.id("resultStats")).getText();
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
	public static Boolean checkPage(int currPage,int nextPage)
	{

		if(nextPage - 1 != currPage)
		{
			System.out.println("Wrong navigation");
			return true;
		}
		return false;
	}


	//------------------------------------------------------------
	//Medium test 1 --google invalid sign in
	public void m1()
	{
		WebDriver ndriver = new ChromeDriver();
		try 
		{
			ndriver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
			ndriver.get("https://www.google.com");
			ndriver.findElement(By.id("gb_70")).click();

			System.out.print("Enter username :");
			String usernameInput=sc.next();		//take input from user for username
			WebElement username = ndriver.findElement(By.xpath("//*[@id=\"identifierId\"]"));
			username.sendKeys(usernameInput);

			ndriver.findElement(By.xpath("//*[@id=\"identifierNext\"]/span/span")).click();
			Thread.sleep(1000);

			try 
			{
				String invalidUsername = username.getAttribute("aria-invalid");
				if(invalidUsername.equals("true")) 
				{
					System.out.println("Username does not exist");
					return;
				}
			} 
			catch (Exception e) 
			{

			}

			//take input from user for password
			String passwordInput=sc.next();		

			WebElement password = ndriver.findElement(By.xpath("//*[@id=\"password\"]/div[1]/div/div[1]/input"));
			password.sendKeys(passwordInput);
			ndriver.findElement(By.xpath("//*[@id=\"passwordNext\"]/span")).click();

			Thread.sleep(1000);
			String invalidPassword = password.getAttribute("aria-invalid");
			if(invalidPassword.equals("true")) 
			{
				System.out.println("Invalid Password");
			}
			else
			{
				System.out.println("Successful login");
			}

		} 
		catch (Exception e) 
		{
			e.printStackTrace();
		}
		ndriver.close();
	}


	//------------------------------------------------------------
	//Medium test 2 -- Search Result Navigation testing
	//
	public void m2()
	{
		this.driver = new ChromeDriver();
		this.driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);

		//google search
		googleSearch();

		int currPage = getPageNo();

		//Scroll down to bottom of page
		JavascriptExecutor js = ((JavascriptExecutor) driver);
		js.executeScript("window.scrollTo(0, document.body.scrollHeight)");

		//Clicking on next
		this.driver.findElement(By.xpath("//*[@id=\"pnnext\"]/span[2]")).click();

		//Verifying the page number
		int nextPage = getPageNo();
		System.out.println(currPage+" nextpage="+nextPage);
		if(checkPage(currPage, nextPage))
		{

			System.out.println("Wrong navigation");
			return;
		}
		//setting current page number 
		currPage = nextPage;

		//Scrolling down to the bottom again 
		js.executeScript("window.scrollTo(0, document.body.scrollHeight)");
		List<WebElement> pageNumbers = this.driver.findElements(By.xpath("//*[@id=\"nav\"]/tbody/tr/td"));

		//Generating random Number and clicking on that page
		int randomPage = getRandomNumberInRange(1, 10);
		int clickedPage = Integer.parseInt(pageNumbers.get(randomPage).getText());
		pageNumbers.get(randomPage).click();
		currPage = getPageNo();
		System.out.println(clickedPage+" "+currPage);
		if(clickedPage != currPage)
		{
			System.out.println("Wrong navigation");
			return;
		}
		this.driver.findElement(By.xpath("//*[@id=\"pnprev\"]/span[2]")).click();
		nextPage = currPage;
		currPage = getPageNo();
		System.out.println(currPage+" nextpage="+nextPage);
		if(checkPage(currPage, nextPage))
		{
			System.out.println("Wrong navigation");
			return;
		}
		System.out.println("Successful Test M2!");
		this.driver.close();
	}


	//------------------------------------------------------------
	//Medium Test 3 -- Search Link Navigation Testing
	//
	public void lastLinkClick()
	{
		JavascriptExecutor js = ((JavascriptExecutor) driver);
		js.executeScript("window.scrollTo(0, document.body.scrollHeight)");
		List<WebElement> searchLinks = this.driver.findElements(By.className("iUh30"));//findElements(By.className("r"));
		System.out.println(searchLinks.size());
		js.executeScript("window.scrollTo(0, 0)");
		for(int link_no=searchLinks.size()-1; link_no>=0; link_no--)
		{
			WebElement link = searchLinks.get(link_no);
			String linkurl = link.getText();
			if(link.getText().length()!=0)
			{
				try 
				{
					//js.executeScript("arguments[0].scrollIntoView(true);", link);
					Thread.sleep(1000);
					link.click();
					if(this.driver.getCurrentUrl().equals(linkurl))
					{
						System.out.println("Url does not match!");
					}
					else
					{
						System.out.println("Url matches!");
					}
					break;
				}
				catch (InterruptedException e) 
				{
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		this.driver.navigate().back();
	}

	public void m3()
	{
		this.driver = new ChromeDriver();
		this.driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);

		//google Search
		googleSearch();

		for(int test=0;test<5;test++)
		{
			lastLinkClick();
			List<WebElement> pageNumbers = this.driver.findElements(By.xpath("//*[@id=\"nav\"]/tbody/tr/td"));
			int randomPage = getRandomNumberInRange(1, 10);
			pageNumbers.get(randomPage).click();
		}
		System.out.println("Successful Test M3!");
		this.driver.close();
	}

	//---------------------------------------------------------------
	//Medium Test 4

	public int checkRelatedSearchitems(String searchItem)
	{
		List<WebElement> relatedSearches = this.driver.findElements(By.className("nVcaUb"));
		int unrelated_count = 0;
		for(WebElement related_item:relatedSearches)
		{
			if(!related_item.getText().contains(searchItem))
			{
				unrelated_count++;
			}
		}
		return unrelated_count;
	}


	public void m4()
	{
		this.driver = new ChromeDriver();
		String searchItem = googleSearch();

		//Check related suggestionsfor search item
		int unrelated_count = checkRelatedSearchitems(searchItem);
		System.out.println(unrelated_count+" suggested items do not have the search Term "+searchItem);

		System.out.println("Successfully completed Test M4");
		this.driver.close();
	}

	//---------------------------------------------------------------
	//Medium Test 5

	public void m5()
	{
		this.driver = new ChromeDriver();
		this.driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);

		//input and search on google.com
		String searchItem = googleSearch();

		List<WebElement> relatedSearches = this.driver.findElements(By.className("nVcaUb"));

		WebElement newItem = relatedSearches.get(relatedSearches.size()-1);
		searchItem = newItem.getText();
		JavascriptExecutor js = ((JavascriptExecutor) driver);
		js.executeScript("window.scrollTo(0, document.body.scrollHeight)");
		newItem.click();

		//Check related suggestionsfor search item
		int unrelated_count = checkRelatedSearchitems(searchItem);
		System.out.println(unrelated_count+" suggested items do not have the search Term "+searchItem);

		System.out.println("Successfully completed Test M5");
		this.driver.close();
	}


	public static void main(String[] args)
	{
		MediumTests testDriver = new MediumTests();
		//testDriver.m1();
		//testDriver.m2();
		//testDriver.m3();
		//testDriver.m4();
		//testDriver.m5();
		//testDriver.testing();
	}



	//testing function 
	public void testing()
	{
		/*this.driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);

			//Goes to google.com and maximises the browser window
			this.driver.get("https://www.google.com");
			this.driver.manage().window().maximize();
			this.driver.findElement(By.name("q")).sendKeys("sad");
			this.driver.findElement(By.name("btnK")).click();

			JavascriptExecutor js = ((JavascriptExecutor) driver);
			js.executeScript("window.scrollTo(0, document.body.scrollHeight)");
			List<WebElement> pageNumbers = this.driver.findElements(By.xpath("//*[@id=\"nav\"]/tbody/tr/td"));
			int count=0;
			for(int i =1;i<11;i++)
			{
				System.out.println(pageNumbers.get(i).getText());
			}
		 */
		WebDriver driver=new ChromeDriver();

		// Maximize browser

		driver.manage().window().maximize();

		// Pass application URL

		driver.get("http://manos.malihu.gr/repository/custom-scrollbar/demo/examples/complete_examples.html");

		// Create instance of Javascript executor

		JavascriptExecutor je = (JavascriptExecutor) driver;

		//Identify the WebElement which will appear after scrolling down

		WebElement element = driver.findElement(By.xpath(".//*[@id='mCSB_3_container']/p[3]"));

		// now execute query which actually will scroll until that element is not appeared on page.

		je.executeScript("arguments[0].scrollIntoView(true);",element);

		// Extract the text and verify

		System.out.println(element.getText());
	}

}
