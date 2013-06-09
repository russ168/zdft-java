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
public class ZdftProxy {
    public String proxyFilePath;
    public ArrayList<Tuple<String, String>> proxyList = new ArrayList<Tuple<String, String>>(50);
    private int index = 0;


    public ZdftProxy(String proxyFilePath) throws Exception{
        this.proxyFilePath = proxyFilePath;
        parse();
    }

    public int size() {
        return proxyList.size();
    }

    public Tuple<String, String> get(int i) {
        if(i >= proxyList.size()) return null;
        return proxyList.get(i);
    }

    public Tuple<String, String> getNext() {
        if(index == proxyList.size()) return null;
        return proxyList.get(index++);
    }

    public static void setHttpProxy(String host, String port ) throws Exception{
        Properties systemProperties = System.getProperties();
        systemProperties.setProperty("http.proxyHost", host);
        systemProperties.setProperty("http.proxyPort", port);
        //systemProperties.setProperty("http.nonProxyHosts", "" + getHttpNonProxyHosts());
    }

    private void parse() {
        File file = new File(proxyFilePath);
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new FileReader(file));
            String tempString = null;
            while ((tempString = reader.readLine()) != null) {
                Map<String, Map<String, Object>> map = Json.parse(tempString);
                proxyList.add(new Tuple<String, String>(String.valueOf(map.get("host")), String.valueOf(map.get("port"))));
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

    public class Tuple<X, Y> {
        public final X x;
        public final Y y;
        public Tuple(X x, Y y) {
            this.x = x;
            this.y = y;
        }
    }
}
