package gitrepourlparser;

import java.text.MessageFormat;

/**
 * 깃허브에 저장되는 위치를 만들어주는 클래스
 */
public class AnAGitRepositoryUrlParser implements GitRepositoryUrlParser {

    private static final String ANA_NAME = "AnA-algorithm";

    //TODO : 년도 바꿀거면 이걸 바꿔야 함
    private static String REPOSITORY_NAME = "2021-algorithm-study";

    private static final String MAIN_BRANCH_NAME = "main";

    private String username;//자기 이름
    private String fileName;//백준1222.java -> 백준 1222가 fileName

    public void setUsername(String username) {
        this.username = username;
    }

    public String getFullPath(final Class<?> clazz) {
        return MessageFormat.format("https://github.com/{0}/{1}/blob/{2}/{3}/{4}.java",
                ANA_NAME,
                REPOSITORY_NAME,
                MAIN_BRANCH_NAME,
                username,
                clazz.getSimpleName()
        );
    }
}
