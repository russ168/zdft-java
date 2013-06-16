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
public class TianyaRegTests {
    private Proxy proxy;
    private String sahiBase = "/home/jackie/java/projects/sahi"; // where Sahi is installed or unzipped
    private String userDataDirectory = "/home/jackie/java/projects/sahi/userdata"; //path to the userdata directory
    private String browserType = "chrome";

    private String proxyFile = "";
    private List<Map<String, String>> proxyList;

    private ZdftProxy zdftProxy;


    private List<String> names;

    private DBAdapter dbAdapter;

    @BeforeClass
    public void startSahiProxy() throws Exception {
        Configuration.initJava(sahiBase, userDataDirectory);
        //proxy = new Proxy(9999);
        //proxy.start(true);
        //Thread.sleep(3000);  // waiting proxy is starting

        names= NameDict.ngram("abcdefghijklmnopqrstuvwxyz", 6, 8);

        //zdftProxy = new ZdftProxy(proxyFile);
        dbAdapter = new DBAdapter();

    }

    @AfterClass
    public void stopSahiProxy() {
        //proxy.stop();
        dbAdapter.close();
    }


    @BeforeMethod
    public void setUp() throws Exception {
    }

    @Test
    public void connect() throws Exception {
        String browserType = "chrome";
        Browser browser = new Browser(browserType);
        browser.open();

        //browser.addHttpHeader("Cache-control", "no-cache");

        browser.navigateTo("http://passport.tianya.cn/register/");

        String name;

        for(int i=0, len=names.size(); i<len; i++) {
            String prefix = names.get(i);
            String suffix = NameDict.genSuffix(3, "0123456789");
            name = prefix + suffix;
            browser.textbox("userName").setValue(name);
            browser.textbox("password").focus();

            if(browser.exists(browser.image("wrong.gif"))){
                System.out.println("name is used by others, try to find another name...");
                continue;
            } else {
                System.out.println("Find a good name:" + name);
                break;
            }
        }

        browser.password("password").setValue("I5youyouyou");
        browser.password("password2").setValue("I5youyouyou");
        browser.textbox("email").setValue("2352296163@qq.com");

        // Get Captcha image
        String imgUrl = browser.image("vcodeImg").getAttribute("src");
        System.out.println("image address is:" + imgUrl);

        // Send request and decode Captcha
        String imgFile = dbAdapter.getImagePath(imgUrl);
        System.out.println("cached image file is:" + imgFile);

        String code = dbAdapter.parseCaptcha("http://localhost:8001/ocr/parse", imgFile);

        browser.textbox("vcode").setValue(code);

        browser.submit(0).click();

        /*
        Browser browser2 =  browser.popup("激活账号_天涯社区");
        String result = browser2.div("number").getText();
        System.out.println("QQ number is:" + result);
        browser2.close();
        */

        browser.close();

        // Save result to database

        //dbAdapter.save(name, password, email);

    }

}
