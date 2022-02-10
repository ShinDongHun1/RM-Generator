package url;

public enum URL {
    BOJ("https://www.acmicpc.net/problem/");



    URL(String url) {
        this.url = url;
    }
    private String url;

    public String getUrl(){
        return this.url;
    }
}
