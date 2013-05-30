package common;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import java.io.IOException;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

/**
 * User: Dong ai hua
 * Date: 13-5-30
 * Time: 下午12:47
 * To change this template use File | Settings | File Templates.
 */
public class JsonTests {
    @AfterMethod
    public void closeAll() {

    }

    /**
     * This test basically verifies that search with a single shard active (cause we indexed to it) and other
     * shards possibly not active at all (cause they haven't allocated) will still work.
     */
    @Test
    public void testParseJson() throws IOException {
        String string = "{\"host\":\"localhost\", \"port\":1234}";
        Map<String, Map<String, Object>> map =  Json.parse(string);
        Set<String> key = map.keySet();
        Iterator<String> iter = key.iterator();
        while (iter.hasNext()) {
            String field = iter.next();
            System.out.println(field + ":" + map.get(field));
        }
    }
}
