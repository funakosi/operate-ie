package com.example;

import static com.codeborne.selenide.Selenide.*;
import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

import org.junit.experimental.theories.DataPoint;
import org.junit.experimental.theories.Theories;
import org.junit.experimental.theories.Theory;
import org.junit.runner.RunWith;
import org.openqa.selenium.By;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.WebDriverRunner;
import com.codeborne.selenide.ex.ElementNotFound;

@RunWith(Theories.class)
public class SelenideSample {

	//slf4j使用
	private final static Logger log = LoggerFactory.getLogger("TestLog");

	@DataPoint
	public static String browser1 = WebDriverRunner.FIREFOX;
	@DataPoint
	public static String browser2 = WebDriverRunner.INTERNET_EXPLORER;

	public SelenideSample() {
		//初期化
	}

	@Theory
	public void 各種ブラウザでテスト(String browser) {
		String PATH = "";
		if (browser.equals(WebDriverRunner.FIREFOX)) {
			Configuration.browser = WebDriverRunner.FIREFOX;
			PATH = "exe/geckodriver.exe";
		    System.setProperty("webdriver.gecko.driver", PATH);
		} else if (browser.equals(WebDriverRunner.INTERNET_EXPLORER)) {
			Configuration.browser = WebDriverRunner.INTERNET_EXPLORER;
		    PATH = "exe/IEDriverServer.exe";
		    System.setProperty("webdriver.ie.driver", PATH);
		}

		String method = new Object(){}.getClass().getEnclosingMethod().getName();
		try {
			open("http://example.selenium.jp/reserveApp/");
			$("#reserve_day").val("25");
			$("#guestname").val("山田　太郎");
			$("#goto_next").click();
			$("#commit").click();
			assertThat($(By.tagName("h1")).getText(),is("予約を完了しました。"));
			log.info("{}-{}..PASSED",method,browser);
			//log.info(method.concat(browser).concat("..passed"));
		} catch (ElementNotFound e) {
			log.info("{}-{}..FAILED",method,browser);
			//log.error(method.concat("..failed"));
			log.error(e.getLocalizedMessage());
			fail(e.getMessage());
		} finally {
			close();
		}
	}
}
