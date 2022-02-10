
import gitrepourlparser.AbsolutePathBeforePackageNameParser;
import gitrepourlparser.PersonalGitRepositoryUrlParser;
import readmegenerator.BOJReadmeGenerator;


public class Main {

    public static void main(String[] args) {


        //== 개인 리포지토리에 리드미 만드는 코드 ==//
        PersonalGitRepositoryUrlParser parser = new PersonalGitRepositoryUrlParser("ShinDongHun1","BOJ", "main" );

        BOJReadmeGenerator rg= new BOJReadmeGenerator(parser);

        rg.setTitle("동훈이의 백준 문제 풀이");//안해줄 경우 기본값인 "백준" 사용

        rg.generate();




        //== 위에가 작동 안 할 때 - > 패키지 이름 전까지만 정해서 리드미 만드는 코드==//
        AbsolutePathBeforePackageNameParser abParser = new AbsolutePathBeforePackageNameParser();
        abParser.setAbsolutePathBeforePackageName("https://github.com/ShinDongHun1/Algorithm/blob/main/BOJ/src");// 마지마 /를 넣어도 되고 빼도 되나 빼는게 이쁨 ^^

        rg= new BOJReadmeGenerator(abParser);

        rg.setTitle("동훈이의 백준 문제 풀이");//안해줄 경우 기본값인 "백준" 사용

        rg.generate();

    }
}
