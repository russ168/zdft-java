package register;

import common.DBAdapter;
import common.NameDict;
import common.ZdftProxy;
import net.sf.sahi.Proxy;
import net.sf.sahi.client.Browser;
import net.sf.sahi.config.Configuration;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: jackie
 * Date: 6/2/13
 * Time: 5:52 AM
 * To change this template use File | Settings | File Templates.
 */
public class ZdftRegisterIntegretionTests {
    private Proxy proxy = new Proxy(9999);
    private String sahiBase = "/home/jackie/sahi"; // where Sahi is installed or unzipped
    private String userDataDirectory = "/home/jackie/sahi/userdata"; //path to the userdata directory
    private String browserType = "chrome";

    private String proxyFile = "";
    private List<Map<String, String>> proxyList;

    private ZdftProxy zdftProxy;


    private List<String> names;

    private DBAdapter dbAdapter;




    @BeforeClass
    public void startSahiProxy() throws Exception {
        Configuration.initJava(sahiBase, userDataDirectory);
        proxy.start(true);
        Thread.sleep(3000);  // waiting proxy is starting

        names= NameDict.ngram("abcdefghijklmnopqrstuvwxyz", 4, 6);
        //zdftProxy = new ZdftProxy(proxyFile);
        dbAdapter = new DBAdapter();

    }

    @AfterClass
    public void stopSahiProxy() {
        proxy.stop();
        dbAdapter.close();
    }


    @BeforeMethod
    public void setUp() throws Exception {
    }

    @Test
    public void connect() throws Exception {
        String sahiBase = "/home/jackie/sahi"; // where Sahi is installed or unzipped
        String userDataDirectory = "/home/jackie/sahi/userdata"; //path to the userdata directory
        Configuration.initJava(sahiBase, userDataDirectory);
        String browserType = "chrome";
        Browser browser = new Browser(browserType);
        browser.open();
        browser.navigateTo("http://localhost:8001/static/register.html");

        // Datafill name
        String prefix = names.get(0);
        String suffix = NameDict.genSuffix(3, "0123456789");
        String name = prefix + suffix;
        browser.textbox("name").setValue(name);

        // Get Captcha image
        String imgUrl = browser.image("captcha.jpg").getAttribute("src");
        System.out.println("image address is:" + imgUrl);

        // Send request and decode Captcha
        String imgFile = dbAdapter.getImagePath(imgUrl);
        String code = dbAdapter.parseCaptcha("http://localhost:8001/ocr/parse", imgFile);
        browser.textbox("code").setValue(code);

        browser.submit(0).click();

        Browser browser2 =  browser.popup("Result");
        String result = browser2.div("number").getText();
        System.out.println("QQ number is:" + result);

        // Save result to database
        dbAdapter.saveQQ(result, "12345678");

        browser2.close();

        browser.close();
    }

}
