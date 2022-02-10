package crawling;

import annotation.BaekjoonTier;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.*;

public class BOJCrawler {

    private static final String SOLVED_AC_URL = "https://solved.ac/search?query=";
    private static final String INFO_DIV_CSS = "div.StickyTable__Cell-sc-45ty5n-1";
    private static final String DEFAULT_BOJ_URL = "https://www.acmicpc.net/problem/";


    private BOJCrawler() {}

    public static Map<Class<?>, Object> getProblemNameAndTier(int number) {

        String title = "undefined";
        Map<Class<?>, Object> map = null;
        Connection connect = Jsoup.connect(SOLVED_AC_URL + number);
        try {
            Elements elements = connect.get().select(INFO_DIV_CSS);
            String imagePath = null;
            String name = null;
            for (Element element : elements) {
                Elements a = element.select("a");

                if(a.attr("href").equals(DEFAULT_BOJ_URL+number)){
                    if(imagePath ==null) {
                        imagePath = element.select("img").attr("src").trim();
                        continue;
                    }if(name == null ) {
                        name = element.select("a > span").first().text().trim();
                    }
                    break;
                }
            }




            map = new HashMap<>();
            map.put(BaekjoonTier.class, BaekjoonTier.getByPath(imagePath));
            map.put(String.class, name);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return map;
    }

}
