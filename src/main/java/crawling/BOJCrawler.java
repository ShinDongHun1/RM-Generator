package crawling;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.select.Elements;

import java.io.IOException;

public class BOJCrawler {
    private static final String BOJ_URL = "https://www.acmicpc.net/problem/";
    private static final String DIV_CSS = "div.page-header";


    private BOJCrawler() {}

    public static final String getProblemName(int number) {

        String title = "undefined";

        Connection connect = Jsoup.connect(BOJ_URL + number);
        try {
            Elements elements = connect.get().select(DIV_CSS);
            title = elements.select("span").select("#problem_title").text();
        } catch (IOException e) {
            e.printStackTrace();
        }


        return title.trim();
    }
}
