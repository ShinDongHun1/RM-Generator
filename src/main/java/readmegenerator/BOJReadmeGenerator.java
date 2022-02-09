package readmegenerator;


import annotation.BOJ;
import annotation.BaekjoonTier;
import annotation.SolveDate;
import crawling.BOJCrawler;
import gitrepourlparser.GitRepositoryUrlParser;
import org.reflections.Reflections;
import problem.BOJProblem;

import java.io.*;
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

    //== 클래스 변수 지정 ==//
    private static final String FILE_NAME = "README.md";

    private static final String DEFAULT_PATH= "";//경로는 최상위.

    private static final String TITLE_PREFIX = "## ";// ## 제목 -> 제목 글자 크기 증가


    private static final String DEFAULT_TITLE = "백준";//## 뒤에 올 제목 (최상단에 위치)
    private String title = DEFAULT_TITLE;


    private static final String TABLE_HEAD = "|날짜|번호|제목|난이도|풀이|문제 주소|\n" +
                                             "|----|---|----|----|---|----|\n";

    private static final String BOJ_URL = "https://www.acmicpc.net/problem/";
    //== 클래스 변수 지정 종료==//

    private static final Reflections REFLECTIONS = new Reflections("");


    private GitRepositoryUrlParser gitRepositoryUrlParser;


    public BOJReadmeGenerator(GitRepositoryUrlParser gitRepositoryUrlParser) {
        Objects.requireNonNull(gitRepositoryUrlParser);//Null 체크, null이면 오류

        this.gitRepositoryUrlParser = gitRepositoryUrlParser;
    }


    public void setTitle(String title) {//제목 결정
        this.title = title;
    }





    @Override
    public void generate() {


        List<BOJProblem> bojProblems = getBOJProblems();//백준(BOJ)문제 가져오기

        bojProblems.sort(Comparator.naturalOrder());//푼 날짜 & 문제 번호 순으로 정렬

        writeReadMe(bojProblems);
    }


    private void writeReadMe(List<BOJProblem> bojProblems) {
        File file = new File(DEFAULT_PATH + FILE_NAME);

        try (BufferedWriter bw = new BufferedWriter(new FileWriter(file))) {

            bw.write("\n");//줄바꿈

            bw.write((TITLE_PREFIX+title));// ## 제목

            bw.write("\n");//줄바꿈

            bw.write(TABLE_HEAD);//"|날짜|번호|제목|난이도|풀이|문제 주소|
                                 // |----|---|----|----|---|----|\n";



            for (BOJProblem problem : bojProblems) {
                //"|날짜|번호|제목|난이도|풀이|문제 주소|
                // |----|---|----|----|---|----|\n";
                bw.write(
                        MessageFormat.format("|{0}|" +   //풀이 날짜
                                                "{1,number,#}|" +//문제 번호, #을 안넣어주면 1000 -> 1,000으로 출력됨
                                                "{2}|" +        //문제 이름
                                                "<img src=\"{3}\" width=\"20\" height=\"20\" /> {4}|" +//문제 티어의 이미지 + 문제 티어
                                                "[풀이]({5})|" +//풀이 주소 (깃허브 주소)
                                                "[문제 주소]({6})|",//문제 주소 (BOJ 주소)

                                            problem.getSolvedDate(),
                                            problem.getNumber(),
                                            problem.getName(),
                                            problem.getTier().getImagePath(),
                                            problem.getTier().name(),
                                            problem.getGitRepoUrl(),
                                            problem.getProblemInfoUrl()));
                bw.write("\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }



    private List<BOJProblem> getBOJProblems(){
        List<BOJProblem> result = new ArrayList<>();

        REFLECTIONS.getTypesAnnotatedWith(BOJ.class)//@BOJ가 붙은 클래스 모두 가져오기
                .forEach(bojProblem -> {
                    BOJ atBoj = bojProblem.getDeclaredAnnotation(BOJ.class);//@BOJ가 붙은 클래스에 설정된 @BOJ 정보 읽어오기 [atBOJ는 annotation BOJ 줄인거]
                    result.add(
                            BOJProblem.builder()
                                    .gitRepoUrl(gitRepositoryUrlParser.getFullPath(bojProblem))//해당 문제의 클래스 정보
                                    .tier(atBoj.tier())
                                    .number(atBoj.number())
                                    .problemInfoUrl(BOJ_URL+atBoj.number())
                                    .solvedDate(LocalDate.of(atBoj.solveDate().year(), atBoj.solveDate().month(), atBoj.solveDate().day()))
                                    .name(BOJCrawler.getProblemName(atBoj.number()))
                                    .build()
                    );
                });

        return result;
    }
}
