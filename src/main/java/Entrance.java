import com.fasterxml.jackson.databind.ObjectMapper;
import net.sf.sahi.Proxy;
import net.sf.sahi.config.Configuration;
import register.QQRegister;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * User: Dong ai hua
 * Date: 13-st5-30
 * Time: 上午0:36
 * To change this template use File | Settings | File Templates.
 */
public class Entrance   {
    private static Proxy proxy = new Proxy(9999);
    private static String sahiBase = "/home/jackie/sahi"; // where Sahi is installed or unzipped
    private static String userDataDirectory = "/home/jackie/sahi/userdata"; //path to the userdata directory

    public static void main(String[] args) throws InterruptedException {
        Configuration.initJava(sahiBase, userDataDirectory);
        proxy.start(true);
        Thread.sleep(3000);  // waiting proxy is starting





        ExecutorService executorService = Executors.newCachedThreadPool();
        List<Future<Object>> resultList = new ArrayList<Future<Object>>();

        // 创建10个任务并执行
        for (int i = 0; i < 10; i++) {
            // 使用ExecutorService执行Callable类型的任务，并将结果保存在future变量中
            Future<Object> future = executorService.submit(new QQRegister("QQ register"));
            // 将任务执行结果存储到List中
            resultList.add(future);
        }
        executorService.shutdown();

        // 遍历任务的结果
        for (Future<Object> fs : resultList) {
            try {
                System.out.println(fs.get()); // 打印各个线程（任务）执行的结果
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                executorService.shutdownNow();
                e.printStackTrace();
                return;
            }
        }
    }
}
