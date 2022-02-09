package gitrepourlparser;

import annotation.BOJ;
import annotation.SolveDate;


public interface GitRepositoryUrlParser {

    public static final String GITHUB_URL_PREFIX = "https://github.com/";

    //깃허브 풀이 저장한 경로를 반환
    String getFullPath(final Class<?> clazz);
}
