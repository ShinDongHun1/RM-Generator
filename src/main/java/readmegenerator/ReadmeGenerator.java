package readmegenerator;

import org.reflections.Reflections;
import problem.Problem;

import java.io.IOException;

public interface ReadmeGenerator<T extends Problem> {

     static final String README = "README.md";

     static final String TITLE_PREFIX = "## ";// ## 제목 -> 제목 글자 크기 증가

     static final String TABLE_HEAD = "|날짜|번호|제목|난이도|풀이|문제 주소|\n" +
            "|----|---|----|----|---|----|\n";


    static final Reflections REFLECTIONS = new Reflections("");
    void generate() throws IOException;
}
