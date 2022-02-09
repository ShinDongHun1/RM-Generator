package readmegenerator;

import annotation.BOJ;
import annotation.SolveDate;
import problem.Problem;

public interface ReadmeGenerator<T extends Problem> {

    void generate();
}
