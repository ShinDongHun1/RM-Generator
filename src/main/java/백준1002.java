import annotation.BOJ;
import annotation.SolveDate;
import gitrepourlparser.PersonalGitRepositoryUrlParser;
import readmegenerator.BOJReadmeGenerator;

@BOJ(solveDate = @SolveDate)
public class 백준1002 {
    public static void main(String[] args) {
        //== 개인 리포지토리에 리드미 만드는 코드 ==//
        PersonalGitRepositoryUrlParser parser = new PersonalGitRepositoryUrlParser("ShinDongHun1","BOJ", "main" );

        BOJReadmeGenerator rg= new BOJReadmeGenerator(parser);

        rg.setTitle("동훈이의 백준 문제 풀이");//안해줄 경우 기본값인 "백준" 사용

        rg.generate();

    }
}
