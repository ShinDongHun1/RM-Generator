package gitrepourlparser;

import java.text.MessageFormat;

/**
 * 계속 오류난다면 패키지 이름 이전까지 직접 설정해주는 parset
 */
public class AbsolutePathBeforePackageNameParser implements GitRepositoryUrlParser {

    private String absolutePathBeforePackageName;


    public void setAbsolutePathBeforePackageName(String absolutePathBeforePackageName) {
        this.absolutePathBeforePackageName = absolutePathBeforePackageName;
    }

    public String getFullPath(final Class<?> clazz) {
        return MessageFormat.format("{0}/{1}/{2}.java",
                absolutePathBeforePackageName,
                clazz.getPackage().getName().replace(".", "/"),
                clazz.getSimpleName()
        );
    }
}