package readmegenerator;

import annotation.BOJ;
import annotation.SolveDate;
import problem.Problem;

@BOJ(number = 1000,solveDate = @SolveDate)
public interface ReadmeGenerator<T extends Problem> {

    void generate();
}
