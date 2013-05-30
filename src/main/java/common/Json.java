package common;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.Map;

/**
 * User: Dong ai hua
 * Date: 13-5-30
 * Time: 上午11:35
 * To change this template use File | Settings | File Templates.
 */
public class Json {
    private static ObjectMapper objectMapper = new ObjectMapper();

    public  static Map<String, Map<String, Object>>parse(String string) throws IOException {
                return objectMapper.readValue(string, Map.class);
    }
}
