/*
package crawling;

import annotation.boj.BOJTier;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import url.URL;

import java.util.HashMap;
import java.util.Map;

public class LeetCodeCrawler {

    private static final String INFO_A_CSS = "a.h-5";
    private static final String INFO_DIV_CSS = "div.mx-2";
    private static final URL DEFAULT_LEETCODE_URL = URL.LeetCode;


    private LeetCodeCrawler() {}

    public static Map<Class<?>, Object> getProblemNameAndDiff(int number) {

        Connection connect = Jsoup.connect(DEFAULT_LEETCODE_URL.getUrl() + number);
        Map<Class<?>, Object> map = new HashMap<>();

        try {
            Elements elements = connect.get().select(INFO_DIV_CSS);

            for (Element element : elements) {

                String[] info = element.select(INFO_A_CSS).text().trim().replaceFirst(" ", "").split("\\.");
                if(info.length > 1){
                    String name = info[1];





                    break;
                }

            }
            */
/*elements.stream().forEach(e -> System.out.println(e.text()));
            System.out.println(elements.text());*//*

            String diff = null;

            //String name = elements.get(0).text().replaceFirst(" ", "").split(".")[1];



            */
/*map.put(BOJTier.class, BOJTier.getByPath(imagePath));
            map.put(String.class, name);*//*

        } catch (Exception e) {
            e.printStackTrace();
        }
        return map;
    }

    public static void main(String[] args) {
        LeetCodeCrawler.getProblemNameAndDiff(1);
    }

}
*/
