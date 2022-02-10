/*
package problem;

import annotation.leetcode.Difficulty;

import java.time.LocalDate;

*/
/**
 * 문제에 대한 정보
 *//*

public class LeetCodeProblem implements Problem, Comparable<LeetCodeProblem>{

    private String problemInfoUrl;      //문제 정보 주소 -> LeetCode 주소
    private String gitRepoUrl;      //문제 풀이 주소 -> 깃허브 레파지토리 주소

    private String name;            //문제 이름
    private int number;             //문제 번호
    private Difficulty difficulty;      //문제 레벨
    private LocalDate solvedDate;   //2022-10-02



    public LeetCodeProblem(String problemInfoUrl, String gitRepoUrl, String name, int number, Difficulty difficulty, LocalDate solvedDate) {
        this.problemInfoUrl = problemInfoUrl;
        this.gitRepoUrl = gitRepoUrl;
        this.name = name;
        this.number = number;
        this.difficulty = difficulty;
        this.solvedDate = solvedDate;
    }


    @Override
    public int compareTo(LeetCodeProblem o) {
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

    public Difficulty getDifficulty() {
        return difficulty;
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
        private Difficulty difficulty;//문제 레벨
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
        public Builder difficulty(Difficulty difficulty){
            this.difficulty = difficulty;
            return this;
        }
        public Builder solvedDate(LocalDate solvedDate){
            this.solvedDate = solvedDate;
            return this;
        }
        public LeetCodeProblem build() {
            return new LeetCodeProblem(problemInfoUrl,
                    gitRepoUrl , name, number,difficulty, solvedDate);
        }
    }
}

*/
