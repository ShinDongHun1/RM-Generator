package readmegenerator;


import annotation.boj.BOJ;
import annotation.boj.BOJTier;
import crawling.BOJCrawler;
import gitrepourlparser.GitRepositoryUrlParser;
import mapper.ReadmeMapper;
import problem.BOJProblem;
import url.URL;

import java.io.*;
import java.text.MessageFormat;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 백준 문제에 대한 README 생성기
 */
public class BOJReadmeGenerator implements ReadmeGenerator<BOJProblem>{

    //== 클래스 변수 지정 ==//
    /**
     * 파일 이름 :  README.md -> README
     * 리드미 파일의 제목 : ## 백준(or 원하는 이름)
     *
     * (테이블 표 만들기)
     * |날짜|번호|제목|난이도|풀이|문제 주소|
     * |----|---|----|----|---|----|
     *
     */
    private static final Class<BOJ> bojClass = BOJ.class;
    
    private static final String DEFAULT_TITLE = "백준";//## 뒤에 올 제목 (최상단에 위치)

    private static final URL BOJ_URL = URL.BOJ;
    //== 클래스 변수 지정 종료==//

    private static final String Pattern ="|{0}|" +   //풀이 날짜
            "{1}|" +//문제 번호, #을 안넣어주면 1000 -> 1,000으로 출력됨
            "{2}|" +        //문제 이름
            "<img src=\"{3}\" width=\"20\" height=\"20\" /> {4}|" +//문제 티어의 이미지 + 문제 티어
            "[풀이]({5})|" +//풀이 주소 (깃허브 주소)
            "[문제 주소]({6})|";


    private GitRepositoryUrlParser gitRepositoryUrlParser;

    public BOJReadmeGenerator(GitRepositoryUrlParser gitRepositoryUrlParser) {
        this.gitRepositoryUrlParser = Objects.requireNonNull(gitRepositoryUrlParser);//Null 체크, null이면 오류
    }

    private String title = DEFAULT_TITLE;
    public void setTitle(String title) {//제목 결정
        this.title = title;
    }





    @Override
    public void generate() {


        File file = new File(README);

        List<BOJProblem> existBOJProblem = getExistBOJProblemsFrom(file);
        List<BOJProblem> newBojProblems = getNewBOJProblemsExceptFor(existBOJProblem);

        newBojProblems.addAll(existBOJProblem);
        newBojProblems.sort(Comparator.naturalOrder());//푼 날짜 & 문제 번호 순으로 정렬

        //다시 써
        writeReadMe(newBojProblems);
    }

    private List<BOJProblem> getExistBOJProblemsFrom(File file) {
        return ReadmeMapper.readFile(file, BOJProblem.class);
    }


    private void writeReadMe(List<BOJProblem> bojProblems) {

        File file = new File(README);


        try (BufferedWriter bw = new BufferedWriter(new FileWriter(file))) {

            bw.write("\n");//줄바꿈

            bw.write((TITLE_PREFIX+title));// ## 제목

            bw.write("\n");//줄바꿈

            bw.write(TABLE_HEAD);//"|날짜|번호|제목|난이도|풀이|문제 주소|
            // |----|---|----|----|---|----|\n";



            for (BOJProblem problem : bojProblems) {

                bw.write(
                        MessageFormat.format(Pattern,
                                problem.getSolvedDate(),
                                String.valueOf(problem.getNumber()),
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




    private List<BOJProblem> getNewBOJProblemsExceptFor(List<BOJProblem> existBOJProblems){

        List<Integer> existNumbers = existBOJProblems.stream()
                .filter(Objects::nonNull)
                .map(BOJProblem::getNumber)
                .collect(Collectors.toList());


        List<BOJProblem> result = new ArrayList<>();
        
        REFLECTIONS.getTypesAnnotatedWith(bojClass)
                .stream()
                .filter(aClass -> isNewProblem(existNumbers,aClass))
                .forEach(aClass -> result.add(convertToBOJProblem(aClass)));

        return result;
    }


    private boolean isNewProblem(List<Integer> existNumbers, Class<?> classWithAtBOJ) {

        //가지고 있지 않음 -> 즉 새로운 문제라면 true
        return !existNumbers.contains(
                //이름 혹은 어노테이션이 가진 number를 통해 문제번호를 가져옴
                getNumberFrom(classWithAtBOJ.getSimpleName(), classWithAtBOJ.getDeclaredAnnotation(bojClass))
        );
    }



    private BOJProblem convertToBOJProblem(Class<?> classWithAtBOJ) {
        BOJ atBoj = classWithAtBOJ.getDeclaredAnnotation(bojClass);


        //클래스 이름 or 어노테이션에 붙은 number를 통해 문제 번호 가져오기
        int number = getNumberFrom(classWithAtBOJ.getSimpleName(), atBoj);

        Map<Class<?>, Object> problemNameAndTier = BOJCrawler.getProblemNameAndTier(number);
        BOJTier tier = BOJTier.class.cast(problemNameAndTier.get(BOJTier.class));
        String name = String.class.cast(problemNameAndTier.get(String.class));

        String problemInfoURL = BOJ_URL.getUrl()+number;
        String gitRepoURL = gitRepositoryUrlParser.getFullPath(classWithAtBOJ);

        return BOJProblem.builder()
                .gitRepoUrl(gitRepositoryUrlParser.getFullPath(classWithAtBOJ))//해당 문제의 클래스 정보
                .tier(tier)
                .number(number)
                .problemInfoUrl(problemInfoURL)
                .solvedDate(getSolveDate(atBoj))
                .name(name)
                .build();

    }

    private LocalDate getSolveDate(BOJ atBoj) {
        //기본값이면 현재시간
        if(atBoj.solveDate().year() == -1 || atBoj.solveDate().month() == -1 || atBoj.solveDate().day() == -1){
            return LocalDate.now();
        }
        return LocalDate.of(atBoj.solveDate().year(), atBoj.solveDate().month(), atBoj.solveDate().day());
    }


    private int getNumberFrom(String className, BOJ atBoj) {
        return (atBoj.number() == BOJ.DEFAULT_NUMBER) ?
                extractNumberFrom(className) :
                atBoj.number();
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
