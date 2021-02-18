import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;


public class Main {
    public static void main(String[] args) {
        String tags = "azur_lane";
        int a = 0;
        try {
            Document doc  = Jsoup.connect("https://yande.re/post").get();

            // 寻找class为xx的元素,返回ArrayList。
            Elements items = doc.getElementsByClass("thumb");

            ArrayList<String> URLS = new ArrayList<>();
            ArrayList<String> TAGS = new ArrayList<>();
            for (Element i : items) {
                // 将图片的URL单独提取出来。
                // child()通过索引得到元素的子元素。
                // attr()取元素的属性值。
                URLS.add(i.child(0).attr("src"));
                TAGS.add(i.child(0).attr("title"));
            }

            // 查看有多少张符合要求的图片
            for(String str1:TAGS) {
                if ((str1.indexOf(tags)) != -1){
                    a++;
                }
            }
            System.out.println(a);

            // 新建一个文件夹储存下载的图片
            File file = new File("Pics");
            file.mkdir();
            System.out.println(file.getAbsolutePath());

            int cnt = 0;
            int i = 0;
            for(String str: URLS) {
                if (TAGS.get(i).contains(tags)) {
                    System.out.println(">> 正在下载：" + str);
                    // 获取response
                    Connection.Response imgRes = Jsoup.connect(str).ignoreContentType(true).execute();
                    FileOutputStream out = (new FileOutputStream(new File(file, cnt + ".jpg")));
                    // imgRes.body()就是图片数据
                    out.write(imgRes.bodyAsBytes());
                    out.close();
                    cnt++;
                }
                i++;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
