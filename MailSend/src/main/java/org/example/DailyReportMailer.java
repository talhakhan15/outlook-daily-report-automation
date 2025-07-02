package org.example;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.TimeoutException;

import java.awt.*;
import java.awt.datatransfer.StringSelection;
import java.awt.event.KeyEvent;
import java.time.Duration;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DailyReportMailer {
    private WebDriver driver;
    private WebDriverWait wait;
    private static final Logger logger = Logger.getLogger(DailyReportMailer.class.getName());
    String Rec = "";
    private String Subject = "Daily Report";

    // Setup driver with Chrome options
    void setupDriver() {
        try {
            System.setProperty("webdriver.chrome.driver", "C:\\chromedriver\\chromedriver.exe");
            ChromeOptions options = new ChromeOptions();
            options.addArguments("--start-maximized");
            driver = new ChromeDriver(options);
            wait = new WebDriverWait(driver, Duration.ofSeconds(20));
            logger.info("Driver setup successful.");
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Driver setup failed: ", e);
            throw e;
        }
    }

    void signin() {
        driver.get("https://login.microsoftonline.com/common/oauth2/v2.0/authorize?client_id=9199bf20-a13f-4107-85dc-02114787ef48&scope=https%3A%2F%2Foutlook.office.com%2F.default%20openid%20profile%20offline_access&redirect_uri=https%3A%2F%2Foutlook.live.com%2Fmail%2F&client-request-id=4041ce95-db08-ce11-c1e0-b55daf16d644&response_mode=fragment&response_type=code&x-client-SKU=msal.js.browser&x-client-VER=4.4.0&client_info=1&code_challenge=Uk8aLZ0RTSbfUx0CxQY6KBHHg2LVplbQ40lk96K_9sU&code_challenge_method=S256&prompt=select_account&nonce=0196804d-102c-7f20-a12b-f79dc0c42001&state=eyJpZCI6IjAxOTY4MDRkLTEwMmItNzI0Yi1hMzkwLWQ2NDQxMTA4ZTE4MCIsIm1ldGEiOnsiaW50ZXJhY3Rpb25UeXBlIjoicmVkaXJlY3QifX0%3D&claims=%7B%22access_token%22%3A%7B%22xms_cc%22%3A%7B%22values%22%3A%5B%22CP1%22%5D%7D%7D%7D&cobrandid=ab0455a0-8d03-46b9-b18b-df2f57b9e44c&fl=dob,flname,wld");
        logger.info("Opened Signin Page");
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@id='i0116']"))).sendKeys("your@mail.co" + Keys.ENTER);
        logger.info("email entered");
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@id='i0118']"))).sendKeys("Any123Password" + Keys.ENTER);
        logger.info("password entered");
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[text()='No']"))).click();
    }

    void outlook() throws InterruptedException {
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[@aria-label='New mail']"))).click();
        logger.info("Drafting an email");
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@id='0']"))).sendKeys("SomeReciever");
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@placeholder='Add a subject']"))).sendKeys(Subject);
        driver.findElement(By.xpath("//div[@class='XnGcL' and @id='editorParent_1']")).click();
        // wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[@aria-label='Attach file']"))).click();

        wait.until(ExpectedConditions.elementToBeClickable(By.xpath(" //button[@id=5]"))).click();
        //here we have clicked and browse windows explorer panel/window opens
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[@aria-label='Attach file']"))).click();

        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//span[text()='Browse this computer']"))).click();


// Pause to let Windows Explorer open (optional: Thread.sleep)
        Thread.sleep(3000);

// Call robot upload
        uploadFileWithRobot("C:\\Users\\Lenovo\\Desktop\\DailyReport.docx");
        Thread.sleep(2000);
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[@class='fui-Button r1alrhcs fui-SplitButton__primaryActionButton EOi57 ___t5c72m0 ffp7eso f1p3nwhy f11589ue f1q5o8ev f1pdflbu f1phragk f15wkkf3 f1s2uweq fr80ssc f1ukrpxl fecsdlb f1rq72xc fnp9lpt f1h0usnq fs4ktlq f16h9ulv fx2bmrt f1d6v5y2 f1rirnrt f1uu00uk fkvaka8 f1ux7til f9a0qzu f1lkg8j3 fkc42ay fq7113v ff1wgvm fiob0tu f1j6scgf f1x4h75k f4xjyn1 fbgcvur f1ks1yx8 f1o6qegi fcnxywj fmxjhhp f9ddjv3 f17t0x8g f194v5ow f1qgg65p fk7jm04 fhgccpy f32wu9k fu5nqqq f13prjl2 f1czftr5 f1nl83rv f12k37oa fr96u23 f1x37qnr fn4c73s']"))).click();
    }

    void uploadFileWithRobot(String filePath) throws InterruptedException {
        try {
            Robot robot = new Robot();
            robot.setAutoDelay(500);

            // Copy file path to clipboard
            StringSelection selection = new StringSelection(filePath);
            Toolkit.getDefaultToolkit().getSystemClipboard().setContents(selection, null);

            // Paste (Ctrl+V)
            robot.keyPress(KeyEvent.VK_CONTROL);
            robot.keyPress(KeyEvent.VK_V);
            robot.keyRelease(KeyEvent.VK_V);
            robot.keyRelease(KeyEvent.VK_CONTROL);

            // Hit Enter
            robot.keyPress(KeyEvent.VK_ENTER);
            robot.keyRelease(KeyEvent.VK_ENTER);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}