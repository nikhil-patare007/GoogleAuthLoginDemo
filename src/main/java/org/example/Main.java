package org.example;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.assertj.core.api.Assertions;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;


public class Main {
    public static void main(String[] args) {
        System.setProperty("webdriver.chrome.driver", "resources/chromedriver");
        ChromeOptions chromeOptions = new ChromeOptions();
        chromeOptions.addArguments("--disable-extensions");
        chromeOptions.addArguments("--disable-popup-blocking");
        chromeOptions.addArguments("--profile-directory=Default");
        chromeOptions.addArguments("--ignore-certificate-errors");
        chromeOptions.addArguments("--disable-plugins-discovery");
        chromeOptions.addArguments("--incognito");
        chromeOptions.addArguments("user_agent=DN");

        WebDriver driver = new ChromeDriver(chromeOptions);
        driver.get("https://accounts.google.com/o/oauth2/v2/auth/oauthchooseaccount?redirect_uri=https%3A%2F%2Fdevelopers.google.com%2Foauthplayground&prompt=consent&response_type=code&client_id=407408718192.apps.googleusercontent.com&scope=email&access_type=offline&flowName=GeneralOAuthFlow");

        driver.findElement(By.xpath("//input[@type=\"email\"]")).sendKeys("YourEmailAddress");

        driver.findElement(By.xpath("//div[contains(text(), 'Sign in')]")).click();
        driver.findElement(By.xpath("//span[contains(text(), 'Next')]")).click();

        driver.findElement(By.xpath("//input[@type=\"password\"]")).sendKeys("yourPassword");
        driver.findElement(By.xpath("//span[contains(text(), 'Next')]")).click();

        //Authorize Account with googleAuthPlayground

        driver.findElement(By.xpath("//span[contains(text(), 'Continue')]")).click();

        //Here you can exchange authorize code and get the tokens that would expire in 3599 seconds

        String googleAuthToken = driver.findElement(By.xpath("//span[contains(text(), \"access_token\")]/following-sibling::span[@class=\"str\"][1]")).getText();
        String googleIdoken = driver.findElement(By.xpath("//span[contains(text(), \"id_token\")]/following-sibling::span[@class=\"str\"][1]")).getText();

        RestAssured.baseURI = "https://www.googleapis.com/auth/userinfo.email";
        Response response = RestAssured
                .given()
                .queryParam("access_token", googleAuthToken)
                .queryParam("idToken", googleIdoken)
                .queryParam("authorization", "Bearer")
                .when().post("/oauth2/v4/token").then().statusCode(200).extract().response();

        //Now we have successfully logged into google account

        driver.navigate().to("https://in.bookmyshow.com/explore/home/pune");
        driver.findElement(By.xpath("//div[contains(text(), 'Sign in')]")).click();

        WebElement continueWithGoogleOption =
                driver.findElement(By.xpath("//div[@class=\"bwc__sc-dh558f-14 fPrBPf\"]/text()[normalize-space()= 'Continue with Google']"));

        continueWithGoogleOption.click();

        //switch to google login window
        String mainWindow = driver.getWindowHandle();
        for (String window : driver.getWindowHandles()) {
            driver.switchTo().window(window);
        }

        driver.findElement(By.xpath("//div[text()='User Name']")).click(); //update according to your choice

        //again with to main window (book my show)
        driver.switchTo().window(mainWindow);

        driver.findElement(By.xpath("//div[@class=\"bwc__sc-1nbn7v6-9 noIEC\"]/img[@alt=\"Profile\"]")).click();

        String userName = driver.findElement(By.xpath("//div[@class=\"bwc__sc-1fj6cem-2 lhxKkj\"]")).getText();

        Assertions.assertThat(userName)
                .as("Verify UserName is as expected")
                .contains("User Name");

        driver.quit();
        driver.close();
    }
}