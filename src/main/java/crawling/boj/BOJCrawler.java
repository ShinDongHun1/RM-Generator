package crawling.boj;

import annotation.boj.BaekjoonTier;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import url.URL;

import java.util.*;

public class BOJCrawler {

    private static final String SOLVED_AC_URL = "https://solved.ac/search?query=";
    private static final String INFO_DIV_CSS = "div.StickyTable__Cell-sc-45ty5n-1";
    private static final URL DEFAULT_BOJ_URL = URL.BOJ;


    private BOJCrawler() {}

    public static Map<Class<?>, Object> getProblemNameAndTier(int number) {

        Connection connect = Jsoup.connect(SOLVED_AC_URL + number);
        Map<Class<?>, Object> map = new HashMap<>();

        try {
            Elements elements = connect.get().select(INFO_DIV_CSS);

            String imagePath = null;
            String name = null;
            for (Element element : elements) {
                
                if(checkProblemInfoURL(number, element)){

                    if(imagePath ==null) {
                        imagePath = element.select("img").attr("src").trim();
                        continue;
                    }
                    name = element.select("a > span").first().text().trim();
                    break;
                }
            }

            map.put(BaekjoonTier.class, BaekjoonTier.getByPath(imagePath));
            map.put(String.class, name);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return map;
    }

    private static boolean checkProblemInfoURL(int number, Element element) {
        return element.select("a").attr("href").equals(DEFAULT_BOJ_URL.getUrl()+ number);
    }

}
