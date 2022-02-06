package readmegenerator;

import problem.Problem;


public interface ReadmeGenerator<T extends Problem> {

    void generate();
}
