package readmegenerator;


import annotation.BOJ;
import annotation.BaekjoonTier;
import annotation.SolveDate;
import crawling.BOJCrawler;
import gitrepourlparser.GitRepositoryUrlParser;
import org.reflections.Reflections;
import problem.BOJProblem;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.MessageFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;

/**
 * 백준 문제에 대한 README 생성기
 */
public class BOJReadmeGenerator implements ReadmeGenerator<BOJProblem>{

    private GitRepositoryUrlParser gitRepositoryUrlParser;

    private static final String PATH_NAME = "README.md";

    private static final String TITLE_PREFIX = "## ";


    private String title = "백준";//README 파일 제목, 기본값은 백준



    private static final String TABLE_HEAD = "|날짜|번호|제목|난이도|풀이|문제 주소|\n|---|---|---|---|---|---|\n";


    private static final String BOJ_URL = "https://www.acmicpc.net/problem/";


    public BOJReadmeGenerator(GitRepositoryUrlParser gitRepositoryUrlParser) {
        Objects.requireNonNull(gitRepositoryUrlParser);

        this.gitRepositoryUrlParser = gitRepositoryUrlParser;
    }


    public void setTitle(String title) {
        this.title = title;
    }


    @Override
    public void generate() {
        Reflections reflections = new Reflections("");
        List<BOJProblem> bojProblems = getBOJProblems(reflections, BOJ.class);
        bojProblems.sort(Comparator.naturalOrder());
        writeReadMe(bojProblems);
    }

    private void writeReadMe(List<BOJProblem> bojProblems) {
        File file = new File(PATH_NAME);

        try (FileOutputStream stream = new FileOutputStream(file)) {

            stream.write("\n".getBytes());

            stream.write((TITLE_PREFIX+title).getBytes());

            stream.write("\n".getBytes());

            stream.write(TABLE_HEAD.getBytes());

            for (BOJProblem problem : bojProblems) {
                //"|날짜|번호|제목|난이도|풀이|문제 주소|\n|---|---|---|---|---|\n";
                stream.write(
                        MessageFormat.format("|{0}|" +
                                                "{1}|" +
                                                "{2}|" +
                                                "<img src=\"{3}\" width=\"20\" height=\"20\" /> {4}|" +
                                                "[풀이]({5})|" +
                                                "[문제 주소]({6})|",
                                            problem.getSolvedDate(),
                                            problem.getNumber(),
                                            problem.getName(),
                                            problem.getTier().getImagePath(),
                                            problem.getTier().name(),
                                            problem.getGitRepoUrl(),
                                            problem.getProblemInfoUrl())
                                .getBytes());
                stream.write("\n".getBytes());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    private List<BOJProblem> getBOJProblems(Reflections reflections, final Class<BOJ> bojClass ){
        List<BOJProblem> result = new ArrayList<>();


        for (Class<?> bogProblemClass : reflections.getTypesAnnotatedWith(bojClass)) {//어노테이션이 붙은 클래스들 모두 가져오기 -> 문제들 다 가져옴


            for (BOJ anno : bogProblemClass.getDeclaredAnnotationsByType(bojClass)) {//해당 클래스의 어노테이션 중 BOJ가 붙은 것들에 대한 정보를 가져옴
                result.add(BOJProblem.builder()
                                .gitRepoUrl(gitRepositoryUrlParser.getFullPath(bogProblemClass))
                                .tier(anno.tier())
                                .number(anno.number())
                                .problemInfoUrl(BOJ_URL+anno.number())
                                .solvedDate(LocalDate.of(anno.solveDate().year(), anno.solveDate().month(), anno.solveDate().day()))//풀이 시간은 현재 시간
                                .name(BOJCrawler.getProblemName(anno.number()))
                        .build());
            }
        }
        return result;
    }
}
