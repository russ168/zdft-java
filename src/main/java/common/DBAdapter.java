package common;

import com.mongodb.*;
import org.apache.http.HttpEntity;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.bson.types.ObjectId;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.UnknownHostException;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: jackie
 * Date: 6/8/13
 * Time: 7:19 AM
 * To change this template use File | Settings | File Templates.
 */
public class DBAdapter {
    private String type;
    private String host;
    private int port;
    private String user;
    private String password;
    private Map<String, String> options;

    private MongoClient mongoClient;

    public DBAdapter(String type, String host, int port) throws UnknownHostException {
        mongoClient = new MongoClient(host, port);
    }

    public DBAdapter() throws UnknownHostException {
        mongoClient = new MongoClient("localhost", 27017);
    }

    public void close() {
        mongoClient.close();
    }

    public String getImagePath(String imageUrl) throws Exception {
        DB db = mongoClient.getDB("test");
        DBCollection coll = db.getCollection("imgurls");
        BasicDBObject q = new BasicDBObject()
                .append("url", imageUrl);
        DBCursor cursor = coll.find(q);
        if (cursor.hasNext()) {
            DBObject result = cursor.next();
            cursor.close();
            mongoClient.close();
            return result.get("filePath").toString();
        } else {
            cursor.close();
            return"";
        }

    }

    public String parseCaptcha(String parseUrl, String imageUrl) throws Exception {
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
            String code = inputStream2String(responseEntity.getContent());
            htttpClient.getConnectionManager().shutdown();
            return code;
        }
        else {
            throw new Exception("Parse captcha failure");
        }

    }

    private String inputStream2String(InputStream in) throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();

        int i = -1;
        while ((i = in.read()) != -1) {
            baos.write(i);
        }
        return baos.toString();
    }

    public void upInsertQQ(String qqnumber) throws Exception {
        DB db = mongoClient.getDB("test");
        DBCollection coll = db.getCollection("qq_numbers");
        BasicDBObject q = new BasicDBObject()
                .append("qq", qqnumber);
        WriteResult rs = coll.update(q, q, true, false);
    }

    public void saveQQ(String qqnumber, String password) throws Exception {
        DB db = mongoClient.getDB("test");
        DBCollection coll = db.getCollection("qq_numbers");
        BasicDBObject q = new BasicDBObject()
                .append("qq", qqnumber)
                .append("password", password);
        WriteResult rs = coll.insert(q);
    }

}
