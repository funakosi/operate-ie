package com.example;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

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
	    WebElement reserveDay = driver.findElement(By.id("reserve_day"));
	    reserveDay.clear();
	    reserveDay.sendKeys("25");
	    driver.findElement(By.id("guestname")).sendKeys("山田　太郎");
	    driver.findElement(By.id("goto_next")).click();
	    driver.findElement(By.id("commit")).click();;
	    WebElement comment = driver.findElement(By.tagName("h1"));
	    assertThat(comment.getText(), is("予約を完了しました。"));
	    Thread.sleep(3000);
	}

}
