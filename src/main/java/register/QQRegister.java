package register;

import com.fasterxml.jackson.databind.ObjectMapper;
import net.sf.sahi.Proxy;
import net.sf.sahi.client.Browser;
import net.sf.sahi.config.Configuration;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.Callable;

/**
 * User: Dong ai hua
 * Date: 13-5-30
 * Time: 上午10:42
 * To change this template use File | Settings | File Templates.
 */
public class QQRegister implements Callable<Object> {
    private String name;
    private static ObjectMapper objectMapper = new ObjectMapper();
    private static String browserType = "chrome";

    public QQRegister(String name) {
        this.name = name;
    }

    public String call()  throws Exception{
        return "123";
    }


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
        Browser browser = new Browser(browserType);
        browser.open();

        browser.navigateTo("http://zc.qq.com/");
        browser.link("nav_1").click();
        String name = "testname";
        browser.textbox("nick").setValue(name);
        browser.password("password").setValue("I5youyouyou");
        browser.password("pass_again").setValue("I5youyouyou");
        browser.link("Male").click();

        browser.navigateTo("http://zc.qq.com");
        browser.span("QQ Account").click();
        browser.link("Male").click();
        browser.textbox("year_value").setValue("1980");
        browser.textbox("month_value").setValue("1");
        browser.textbox("day_value").setValue("1");
        browser.textbox("province_value").setValue("Beijing");
        browser.textbox("city_value").setValue("Dongcheng");

        browser.textbox("code").setValue("123");

        browser.submit(0).click();

        String QQnumber = browser.popup("_blank").span("defaultUin").getValue();
        System.out.println(QQnumber);

        browser.close();
    }
}