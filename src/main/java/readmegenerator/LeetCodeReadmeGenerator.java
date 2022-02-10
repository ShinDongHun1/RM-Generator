/*
package readmegenerator;


import annotation.leetcode.LeetCode;
import gitrepourlparser.GitRepositoryUrlParser;
import problem.LeetCodeProblem;
import url.URL;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.MessageFormat;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

*/
/**
 * 백준 문제에 대한 README 생성기
 *//*

public class LeetCodeReadmeGenerator implements ReadmeGenerator<LeetCodeProblem>{

    //== 클래스 변수 지정 ==//
    */
/**
     * 파일 이름 :  README.md -> README
     * 리드미 파일의 제목 : ## 백준(or 원하는 이름)
     *
     * (테이블 표 만들기)
     * |날짜|번호|제목|난이도|풀이|문제 주소|
     * |----|---|----|----|---|----|
     *
     *//*


    private static final String DEFAULT_TITLE = "LeetCode";//## 뒤에 올 제목 (최상단에 위치)
    private static final Class<LeetCode> leetCodeClass = LeetCode.class;


    private static final URL LEETCODE_URL = URL.LeetCode;
    //== 클래스 변수 지정 종료==//



    private GitRepositoryUrlParser gitRepositoryUrlParser;

    public LeetCodeReadmeGenerator(GitRepositoryUrlParser gitRepositoryUrlParser) {
        this.gitRepositoryUrlParser = Objects.requireNonNull(gitRepositoryUrlParser);//Null 체크, null이면 오류
    }

    private String title = DEFAULT_TITLE;
    public void setTitle(String title) {//제목 결정
        this.title = title;
    }





    @Override
    public void generate() {

        File file = new File(README);

        List<LeetCodeProblem> existLeetCodeProblem = getExistLeetCodeProblemsFrom(file);
        List<LeetCodeProblem> newLeetCodeProblems = getNewLeetCodeProblemsExceptFor(existLeetCodeProblem);

        newLeetCodeProblems.addAll(existLeetCodeProblem);
        newLeetCodeProblems.sort(Comparator.naturalOrder());//푼 날짜 & 문제 번호 순으로 정렬

        //다시 써
        writeReadMe(newLeetCodeProblems);
    }

    private List<LeetCodeProblem> getExistLeetCodeProblemsFrom(File file) {
        //return ReadmeMapper.readFile(file, LeetCodeProblem.class);
        return new ArrayList<>();
    }


    private void writeReadMe(List<LeetCodeProblem> LeetCodeProblems) {
        File file = new File(README);

        try (BufferedWriter bw = new BufferedWriter(new FileWriter(file))) {

            bw.write("\n");//줄바꿈

            bw.write((TITLE_PREFIX+title));// ## 제목

            bw.write("\n");//줄바꿈

            bw.write(TABLE_HEAD);//"|날짜|번호|제목|난이도|풀이|문제 주소|
            // |----|---|----|----|---|----|\n";



            for (LeetCodeProblem problem : LeetCodeProblems) {
                //"|날짜|번호|제목|난이도|풀이|문제 주소|
                // |----|---|----|----|---|----|\n";
                bw.write(
                        MessageFormat.format("|{0}|" +   //풀이 날짜
                                        "{1,number,#}|" +//문제 번호, #을 안넣어주면 1000 -> 1,000으로 출력됨
                                        "{2}|" +        //문제 이름
                                        "<img src=\"{3}\" width=\"20\" height=\"20\" /> {4}|" +//문제 티어의 이미지 + 문제 티어
                                        "[풀이]({5})|" +//풀이 주소 (깃허브 주소)
                                        "[문제 주소]({6})|",//문제 주소 (LeetCode 주소)

                                problem.getSolvedDate(),
                                problem.getNumber(),
                                problem.getName(),
                                problem.getDifficulty().name(),
                                problem.getGitRepoUrl(),
                                problem.getProblemInfoUrl()));
                bw.write("\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    private List<LeetCodeProblem> getNewLeetCodeProblemsExceptFor(List<LeetCodeProblem> existLeetCodeProblems){

        List<Integer> existNumbers = existLeetCodeProblems.stream()
                .filter(Objects::nonNull)
                .map(LeetCodeProblem::getNumber)
                .collect(Collectors.toList());


        List<LeetCodeProblem> result = new ArrayList<>();
        
        REFLECTIONS.getTypesAnnotatedWith(leetCodeClass)
                .stream()
                .filter(aClass -> isNewProblem(existNumbers,aClass))
                .forEach(aClass -> result.add(convertToLeetCodeProblem(aClass)));

        return result;
    }


    private boolean isNewProblem(List<Integer> existNumbers, Class<?> classWithAtLeetCode) {

        //가지고 있지 않음 -> 즉 새로운 문제라면 true
        return !existNumbers.contains(
                //이름 혹은 어노테이션이 가진 number를 통해 문제번호를 가져옴
                getNumberFrom(classWithAtLeetCode.getSimpleName(), classWithAtLeetCode.getDeclaredAnnotation(leetCodeClass))
        );
    }



    private LeetCodeProblem convertToLeetCodeProblem(Class<?> classWithAtLeetCode) {
        LeetCode atLeetCode = classWithAtLeetCode.getDeclaredAnnotation(leetCodeClass);


        //클래스 이름 or 어노테이션에 붙은 number를 통해 문제 번호 가져오기
        int number = getNumberFrom(classWithAtLeetCode.getSimpleName(), atLeetCode);

        Map<Class<?>, Object> problemNameAndTier = LeetCodeCrawler.getProblemNameAndTier(number);
        LeetCodeTier tier = LeetCodeTier.class.cast(problemNameAndTier.get(LeetCodeTier.class));
        String name = String.class.cast(problemNameAndTier.get(String.class));

        String problemInfoURL = LEETCODE_URL.getUrl()+number;
        String gitRepoURL = gitRepositoryUrlParser.getFullPath(classWithAtLeetCode);

        return LeetCodeProblem.builder()
                .gitRepoUrl(gitRepositoryUrlParser.getFullPath(classWithAtLeetCode))//해당 문제의 클래스 정보
                .tier(tier)
                .number(number)
                .problemInfoUrl(problemInfoURL)
                .solvedDate(getSolveDate(atLeetCode))
                .name(name)
                .build();

    }

    private LocalDate getSolveDate(LeetCode atLeetCode) {
        //기본값이면 현재시간
        if(atLeetCode.solveDate().year() == -1 || atLeetCode.solveDate().month() == -1 || atLeetCode.solveDate().day() == -1){
            return LocalDate.now();
        }
        return LocalDate.of(atLeetCode.solveDate().year(), atLeetCode.solveDate().month(), atLeetCode.solveDate().day());
    }


    private int getNumberFrom(String className, LeetCode atLeetCode) {
        return (atLeetCode.number() == LeetCode.DEFAULT_NUMBER) ?
                extractNumberFrom(className) :
                atLeetCode.number();
    }



    private int extractNumberFrom(String className) {
        if(!Character.isDigit(className.charAt(className.length()-1))){
            throw new IllegalStateException("클래스 이름 형식이 잘못되었습니다. 형식 [~~~~문제번호 | ex) Q1002, 백준1002 등]");
        }
        for(int i = className.length() -1; i >= 0; i--) {
            if (!Character.isDigit(className.charAt(i))) {
                return Integer.parseInt(className.substring(i+1));
            }
        }
        throw new IllegalStateException("클래스 이름 형식이 잘못되었습니다. 형식 [~~~~문제번호 | ex) Q1002, 백준1002 등]");
    }


}
*/
