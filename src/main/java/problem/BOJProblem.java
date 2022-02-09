package problem;

import annotation.BaekjoonTier;

import java.time.LocalDate;

/**
 * 문제에 대한 정보
 */
public class BOJProblem implements Problem, Comparable<BOJProblem>{

    private String problemInfoUrl;      //문제 정보 주소 -> 백준 주소
    private String gitRepoUrl;      //문제 풀이 주소 -> 깃허브 레파지토리 주소

    private String name;            //문제 이름
    private int number;             //문제 번호
    private BaekjoonTier tier;      //문제 레벨
    private LocalDate solvedDate;   //2022-10-02



    public BOJProblem(String problemInfoUrl, String gitRepoUrl, String name, int number, BaekjoonTier tier, LocalDate solvedDate) {
        this.problemInfoUrl = problemInfoUrl;
        this.gitRepoUrl = gitRepoUrl;
        this.name = name;
        this.number = number;
        this.tier = tier;
        this.solvedDate = solvedDate;
    }


    @Override
    public int compareTo(BOJProblem o) {
        //오름차순 정렬
        if(solvedDate.isBefore(o.solvedDate)) return -1;
        if(solvedDate.isAfter(o.solvedDate)) return 1;

        if(solvedDate.isEqual(o.solvedDate)){//만약 요일이 같다면
            return number - o.number;//문제 번호로 비교
        }

        return 1;
    }

    //Getter
    public String getProblemInfoUrl() {
        return problemInfoUrl;
    }

    public String getGitRepoUrl() {
        return gitRepoUrl;
    }

    public String getName() {
        return name;
    }

    public int getNumber() {
        return number;
    }

    public BaekjoonTier getTier() {
        return tier;
    }

    public LocalDate getSolvedDate() {
        return solvedDate;
    }


    //== 빌더 반환 ==//
    public static Builder builder(){
        return new Builder();
    }

    //== 빌더 ==//
    public static class Builder {
        private String problemInfoUrl;//문제 주소
        private String gitRepoUrl;//문제 주소
        private String name;//문제 이름
        private int number;//문제 번호
        private BaekjoonTier tier;//문제 레벨
        private LocalDate solvedDate;//2022-10-02

        public Builder problemInfoUrl(String problemInfoUrl){
            this.problemInfoUrl = problemInfoUrl;
            return this;
        }
        public Builder gitRepoUrl(String gitRepoUrl){
            this.gitRepoUrl = gitRepoUrl;
            return this;
        }

        public Builder name(String name){
            this.name = name;
            return this;
        }
        public Builder number(int number){
            this.number = number;
            return this;
        }
        public Builder tier(BaekjoonTier tier){
            this.tier = tier;
            return this;
        }
        public Builder solvedDate(LocalDate solvedDate){
            this.solvedDate = solvedDate;
            return this;
        }
        public BOJProblem build() {
            return new BOJProblem(problemInfoUrl,
                    gitRepoUrl , name, number,tier, solvedDate);
        }
    }
}

