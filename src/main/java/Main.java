import gitrepourlparser.GitRepositoryUrlParser;
import gitrepourlparser.PersonalGitRepositoryUrlParser;
import readmegenerator.BOJReadmeGenerator;
import readmegenerator.ReadmeGenerator;

public class Main {

    public static void main(String[] args) {
        GitRepositoryUrlParser parser = new PersonalGitRepositoryUrlParser("ShinDongHun1","BOJ", "main" );

        ReadmeGenerator rg= new BOJReadmeGenerator(parser);

        rg.generate();
    }
}
