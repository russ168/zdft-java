package common;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Properties;

/**
 * User: Dong ai hua
 * Date: 13-5-30
 * Time: 上午11:33
 * To change this template use File | Settings | File Templates.
 */
public class Proxy {

    public void setHttpProxy(String host, String port ) throws Exception{
        Properties systemProperties = System.getProperties();
        systemProperties.setProperty("http.proxyHost", host);
        systemProperties.setProperty("http.proxyPort", port);
        //systemProperties.setProperty("http.nonProxyHosts", "" + getHttpNonProxyHosts());
    }

    public List<Map<String, Map<String, Object>>> parse(String filePath) {
        List list = new ArrayList(50);
        File file = new File(filePath);
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new FileReader(file));
            String tempString = null;
            while ((tempString = reader.readLine()) != null) {
                Map<String, Map<String, Object>> map = Json.parse(tempString);
                list.add(map);
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
        return list;
    }

}
