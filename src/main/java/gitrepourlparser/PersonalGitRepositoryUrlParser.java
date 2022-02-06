package gitrepourlparser;

import java.text.MessageFormat;

public class PersonalGitRepositoryUrlParser implements GitRepositoryUrlParser{

    private final String USERNAME;


    private final String REPOSITORY_NAME;


    public PersonalGitRepositoryUrlParser(String USERNAME, String REPOSITORY_NAME, String MAIN_BRANCH_NAME) {
        this.USERNAME = USERNAME;
        this.REPOSITORY_NAME = REPOSITORY_NAME;
        this.MAIN_BRANCH_NAME = MAIN_BRANCH_NAME;
    }




    private final String MAIN_BRANCH_NAME;

    private String packageName;//여러개라면 name1/name2/name3 ~~으로 자동 생성
    private String fileName;//백준1222.java -> 백준 1222가 fileName



    public String getFullPath(final Class<?> clazz) {
        return MessageFormat.format("https://github.com/{0}/{1}/blob/{2}/{3}/{4}.java",
                USERNAME,
                REPOSITORY_NAME,
                MAIN_BRANCH_NAME,
                clazz.getPackageName().replace(".", "/"),//name1.name2 -> name1/name2로 변경
                clazz.getSimpleName()
        );
    }
}
