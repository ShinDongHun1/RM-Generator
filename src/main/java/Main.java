import crawling.BOJCrawler;
import gitrepourlparser.AnAGitRepositoryUrlParser;
import gitrepourlparser.GitRepositoryUrlParser;
import gitrepourlparser.PersonalGitRepositoryUrlParser;
import readmegenerator.BOJReadmeGenerator;

public class Main {

    public static void main(String[] args) {


        GitRepositoryUrlParser parser = new PersonalGitRepositoryUrlParser("ShinDongHun1","BOJ", "main" );

        BOJReadmeGenerator rg= new BOJReadmeGenerator(parser);

        rg.setTitle("동훈이의 백준 문제 풀이");//안해줄 경우 기본값인 "백준" 사용

        rg.generate();



        //== ANA 하루하나 알고리즘 리드미 만드는 코드 ==//
        AnAGitRepositoryUrlParser anaParser = new AnAGitRepositoryUrlParser();

        rg = new BOJReadmeGenerator(anaParser);

        rg.setTitle("동훈이의 하루하나 알고리즘");//안해줄 경우 기본값인 "백준" 사용

        rg.generate();

    }
}
