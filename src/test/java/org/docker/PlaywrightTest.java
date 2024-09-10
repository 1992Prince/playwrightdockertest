package org.docker;

import com.microsoft.playwright.*;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.logging.Logger;

public class PlaywrightTest {
    private static final Logger logger = Logger.getLogger(PlaywrightTest.class.getName());
    private Playwright playwright;
    private Browser browser;

    private BrowserContext browserContext;
    private String applicationUrl = "http://advantageonlineshopping.com/#/";

    @Parameters({"googleUrl"})
    @BeforeClass
    public void setUp() {
        //this.applicationUrl = applicationUrl;

        // Log the Java version to confirm JDK 17
        logger.info("Running on Java version: " + System.getProperty("java.version"));

        ArrayList<String> arguments = new ArrayList<>();
        arguments.add("--start-maximized");

        playwright = Playwright.create();
        browser = playwright.chromium().launch(new BrowserType.LaunchOptions()
                .setHeadless(false).setArgs(arguments));
        browserContext = browser.newContext(
                new Browser.NewContextOptions().setViewportSize(null));
    }



    @Test
    public void testGoogleHomePage() throws InterruptedException {
        Page page = browserContext.newPage();
        page.navigate(applicationUrl);
        page.locator("(//*[@id='menuSearch'])[1]").click();

        page.locator("//input[@id='autoComplete']").type("laptop", new Locator.TypeOptions().setDelay(2));

        page.locator("//p[text()='HP ENVY - 17T TOUCH LAPTOP']").click();

        page.locator("//button[text()='ADD TO CART']").click();

        page.locator("//*[@id='checkOutPopUp']").click();

        String tezt = page.locator("(//*[contains(text(),'TOTAL')])[4]").innerText();

        System.out.println("Text is - " + tezt);

        Thread.sleep(5000);
    }

    @Test
    public void testUserManagementPage() throws InterruptedException {
        Page page = browserContext.newPage();
        page.navigate(applicationUrl);
        page.locator("//*[@id='menuUser']").click();

        page.locator("//*[@name='username']").type("Pikachu", new Locator.TypeOptions().setDelay(2));

        page.locator("//*[@name='password']").type("Pikachu");

        page.locator("//*[@id='sign_in_btn']").click();

        String error = page.locator("//*[@id='signInResultMessage']").innerText();

        System.out.println("Error is - " + error);

        Thread.sleep(5000);
    }

    @AfterClass
    public void tearDown() {
        if (browser != null) {
            browser.close();
        }
        if (playwright != null) {
            playwright.close();
        }
    }
}