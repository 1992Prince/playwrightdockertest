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
    private String googleUrl;

    @Parameters({"googleUrl"})
    @BeforeClass
    public void setUp(String googleUrl) {
        this.googleUrl = googleUrl;

        ArrayList<String> arguments = new ArrayList<>();
        arguments.add("--start-maximized");

        playwright = Playwright.create();
        browser = playwright.chromium().launch(new BrowserType.LaunchOptions()
                .setHeadless(true).setArgs(arguments));
        browserContext = browser.newContext(
                new Browser.NewContextOptions().setViewportSize(null));
    }

    @Test
    public void testGoogleHomePage() {
        Page page = browserContext.newPage();
        page.navigate(googleUrl);
        assert page.title().contains("Google");
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