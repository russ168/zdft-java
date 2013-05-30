package common;

import com.mongodb.*;
import org.apache.http.HttpEntity;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.UnknownHostException;

/**
 * User: Dong ai hua
 * Date: 13-5-30
 * Time: 上午11:27
 * To change this template use File | Settings | File Templates.
 */
public class Captcha {

    public String parse(String parseUrl, String imageUrl) throws Exception {
        HttpClient htttpClient = new DefaultHttpClient();
        HttpPost httpPost = new HttpPost(parseUrl);

        MultipartEntity reqEntity = new MultipartEntity();
        httpPost.setEntity(reqEntity);

        FileBody bin = new FileBody(new File("temp"));
        reqEntity.addPart("Filedata", bin);

        System.out.println("executing: " + httpPost.getRequestLine());

        org.apache.http.HttpResponse response = htttpClient.execute(httpPost);
        HttpEntity responseEntity = response.getEntity();

        System.out.println("-----------------------------");
        System.out.println(response.getStatusLine());

        if(responseEntity != null) {
            String code =  inputStream2String(responseEntity.getContent());
            htttpClient.getConnectionManager().shutdown();
            return code;
        }

        htttpClient.getConnectionManager().shutdown();
        throw new Exception("parse error");
    }

    private String inputStream2String(InputStream in) throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();

        int i = -1;
        while ((i = in.read()) != -1) {
            baos.write(i);
        }
        return baos.toString();
    }

    public String getImagePath(String imageUrl) throws Exception {
        try {
            MongoClient mongoClient = new MongoClient();
            DB db = mongoClient.getDB("test");
            DBCollection coll = db.getCollection("imgurls");
            BasicDBObject q = new BasicDBObject()
                    .append("url", imageUrl);
            DBCursor cursor = coll.find(q);
            if (cursor.hasNext()) {
                DBObject result = cursor.next();
                cursor.close();
                mongoClient.close();
                return result.get("imagePath").toString();
            } else {
                cursor.close();
                mongoClient.close();
                return "";
            }

        } catch (UnknownHostException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            throw new Exception("get image path error");
        }

    }
}
