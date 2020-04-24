package com.example;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Collection;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;

import com.codeborne.selenide.WebDriverRunner;
import com.example.common.DateTools;

@RunWith(Parameterized.class)
public class SeleniumSample {

	String browser;
	private  WebDriver driver = null;

	public SeleniumSample(String browser) {
		this.browser = browser;
	}

	@Parameters(name = "{0}")
	public static Collection<Object[]> data() throws Throwable {
        return Arrays.asList(new Object[][] {
            { WebDriverRunner.FIREFOX },
            { WebDriverRunner.INTERNET_EXPLORER }
        });
    }

	@Before
	public void setUp() {
		if (browser.equals(WebDriverRunner.FIREFOX)) {
			System.setProperty("webdriver.gecko.driver", "exe\\geckodriver.exe");
		    driver = new FirefoxDriver();
		} else if (browser.equals(WebDriverRunner.INTERNET_EXPLORER)) {
			System.setProperty("webdriver.ie.driver","exe/IEDriverServer.exe");
		    driver = new InternetExplorerDriver();
		}
	}

	@After
	public void tearDown() {
		driver.quit();
	}

	@Test
	public void 各種ブラウザでテスト() throws InterruptedException {
		driver.get("http://example.selenium.jp/reserveApp/");
	    WebElement reserveYear = driver.findElement(By.id("reserve_year"));
	    WebElement reserveMonth = driver.findElement(By.id("reserve_month"));
	    WebElement reserveDay = driver.findElement(By.id("reserve_day"));
	    LocalDate nextDay = DateTools.getNextDay(
	    		reserveYear.getAttribute("value"),
	    		reserveMonth.getAttribute("value"),
	    		reserveDay.getAttribute("value"));
	    reserveYear.clear();
	    reserveYear.sendKeys(String.valueOf(nextDay.getYear()));
	    reserveMonth.clear();
	    reserveMonth.sendKeys(String.valueOf(nextDay.getMonthValue()));
	    reserveDay.clear();
	    reserveDay.sendKeys(String.valueOf(nextDay.getDayOfMonth()));
	    driver.findElement(By.id("guestname")).sendKeys("山田　太郎");
	    driver.findElement(By.id("goto_next")).click();
	    driver.findElement(By.id("commit")).click();;
	    WebElement comment = driver.findElement(By.tagName("h1"));
	    assertThat(comment.getText(), is("予約を完了しました。"));
	    Thread.sleep(3000);
	}

}
