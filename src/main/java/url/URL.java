package url;

public enum URL {
    BOJ("https://www.acmicpc.net/problem/"),
    LeetCode("https://leetcode.com/problemset/all/?page=1&search=");



    URL(String url) {
        this.url = url;
    }
    private String url;

    public String getUrl(){
        return this.url;
    }
}
