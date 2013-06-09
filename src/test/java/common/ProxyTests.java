package common;

import com.fasterxml.jackson.databind.ObjectMapper;
import net.sf.sahi.client.Browser;
import net.sf.sahi.config.Configuration;
import net.sf.sahi.Proxy;
import org.testng.annotations.*;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Map;
import java.util.Properties;

/**
 * User: Dong ai hua
 * Date: 13-5-30
 * Time: 下午2:13
 * To change this template use File | Settings | File Templates.
 */
public class ProxyTests {
    private Proxy proxy = new Proxy(9999);
    private ObjectMapper objectMapper = new ObjectMapper();
    private String sahiBase = "/home/jackie/sahi"; // where Sahi is installed or unzipped
    private String userDataDirectory = "/home/jackie/sahi/userdata"; //path to the userdata directory
    private String browserType = "chrome";

    @BeforeClass
    public void startSahiProxy() throws InterruptedException {
        System.out.println("Sahi proxy is starting...");
        Configuration.initJava(sahiBase, userDataDirectory);
        proxy.start(true);
        Thread.sleep(3000);  // waiting proxy is starting
    }

    @AfterClass
    public void stopSahiProxy() {
        System.out.println("Sahi proxy is closing...");
        proxy.stop();
    }


    @Test
    public void changeProxy() throws Exception {
        File file = new File("/home/jackie/scripts/scanProxies/items.jl");
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new FileReader(file));
            String tempString = null;
            int line = 1;
            while ((tempString = reader.readLine()) != null) {
                System.out.println("line " + line + ": " + tempString);
                line++;
                //parse line as json object

                Map<String, Map<String, Object>> maps = objectMapper.readValue(tempString, Map.class);
                Properties systemProperties = System.getProperties();
                Boolean isHttpProxyEnabled = true;
                if (isHttpProxyEnabled) {
                    systemProperties.setProperty("http.proxyHost", String.valueOf(maps.get("host")));
                    systemProperties.setProperty("http.proxyPort", String.valueOf(maps.get("port")));
                    //systemProperties.setProperty("http.proxyHost", "91.230.54.60");
                    //systemProperties.setProperty("http.proxyPort", "8080");

                    //systemProperties.setProperty("http.nonProxyHosts", "" + getHttpNonProxyHosts());
                    connect();
                }
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e1) {
                }
            }
        }

    }

    private void connect() {
        //String sahiBase = "C:\\Users\\Administrator\\sahi"; // where Sahi is installed or unzipped
        //String userDataDirectory = "C:\\Users\\Administrator\\sahi\\userdata"; //path to the userdata directory



        // You can specify the browser you want to run the tests on.
        // browserType can take any value defined in
        // sahi/userdata/config/browser_types.xml

        // Create a browser and open it
        Browser browser = new Browser(browserType);
        browser.open();

        browser.navigateTo("http://www.baidu.com");
        System.out.println(browser.title());

        browser.close();
    }

}
