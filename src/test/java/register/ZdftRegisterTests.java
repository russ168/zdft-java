package register;

import com.fasterxml.jackson.databind.ObjectMapper;
import net.sf.sahi.Proxy;
import net.sf.sahi.client.Browser;
import net.sf.sahi.config.Configuration;
import org.testng.annotations.*;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Map;
import java.util.Properties;

/**
 * Created with IntelliJ IDEA.
 * User: jackie
 * Date: 6/2/13
 * Time: 5:52 AM
 * To change this template use File | Settings | File Templates.
 */
public class ZdftRegisterTests {
    private Proxy proxy = new Proxy(9999);
    private String sahiBase = "/home/jackie/sahi"; // where Sahi is installed or unzipped
    private String userDataDirectory = "/home/jackie/sahi/userdata"; //path to the userdata directory
    private String browserType = "chrome";

    @BeforeClass
    public void startSahiProxy() throws InterruptedException {
        Configuration.initJava(sahiBase, userDataDirectory);
        proxy.start(true);
        Thread.sleep(3000);  // waiting proxy is starting
    }

    @AfterClass
    public void stopSahiProxy() {
        proxy.stop();
    }



    @Test
    public void connect() {
        //String sahiBase = "C:\\Users\\Administrator\\sahi"; // where Sahi is installed or unzipped
        //String userDataDirectory = "C:\\Users\\Administrator\\sahi\\userdata"; //path to the userdata directory

        String sahiBase = "/home/jackie/sahi"; // where Sahi is installed or unzipped
        String userDataDirectory = "/home/jackie/sahi/userdata"; //path to the userdata directory

        Configuration.initJava(sahiBase, userDataDirectory);
        // Sets up configuration for proxy. Sets Controller to java mode.

        String browserType = "chrome";

        // You can specify the browser you want to run the tests on.
        // browserType can take any value defined in
        // sahi/userdata/config/browser_types.xml

        // Create a browser and open it
        Browser browser = new Browser(browserType);
        browser.open();

        browser.navigateTo("http://localhost:8001/static/register.html");
        browser.textbox("name").setValue("aa");
        String img = browser.image("captcha.jpg").getAttribute("src");
        System.out.println("image address is:" + img);

        browser.textbox("code").setValue("123");

        browser.submit(0).click();

        Browser browser2 =  browser.popup("Result");
        String result = browser2.div("number").getText();
        System.out.println("result is:" + result);

        browser2.close();

        browser.close();
    }

}
