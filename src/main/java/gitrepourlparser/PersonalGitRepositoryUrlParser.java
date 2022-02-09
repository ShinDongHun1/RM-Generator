package gitrepourlparser;

import java.text.MessageFormat;

public class PersonalGitRepositoryUrlParser implements GitRepositoryUrlParser{

    /**
     * 깃허브 USERNAME
     * 문제 풀이가 저장된 Repository 이름
     * 저장된 메인 브랜치 이름
     *
     * 위 3개를 가지고 저장 주소 자동 생성
     */

    private final String USERNAME;
    private final String REPOSITORY_NAME;
    private final String MAIN_BRANCH_NAME;


    public PersonalGitRepositoryUrlParser(String USERNAME, String REPOSITORY_NAME, String MAIN_BRANCH_NAME) {
        this.USERNAME = USERNAME;
        this.REPOSITORY_NAME = REPOSITORY_NAME;
        this.MAIN_BRANCH_NAME = MAIN_BRANCH_NAME;
    }


    //클래스 정보를 통해 동적으로 생성
    private String packageName;//여러개라면 name1/name2/name3 ~~으로 자동 생성
    private String fileName;//백준1222.java -> 백준 1222가 fileName





    public String getFullPath(final Class<?> clazz) {

        return MessageFormat.format("{0}/{1}/{2}/blob/{3}/{4}/{5}.java",
                GITHUB_URL_PREFIX, // -> https://github.com/
                USERNAME,          // -> ShinDongHun1
                REPOSITORY_NAME,   // -> Algorithm
                MAIN_BRANCH_NAME,  // -> main
                clazz.getPackage().getName().replace(".","/"),// -> /BOJ/src/bfs와dfs
                clazz.getSimpleName()//-> 백준1012
                // -> https://github.com/ShinDongHun1/Algorithm/blob/main/BOJ/src/bfs%EC%99%80dfs/%EB%B0%B1%EC%A4%801012.java
        );
    }
}
