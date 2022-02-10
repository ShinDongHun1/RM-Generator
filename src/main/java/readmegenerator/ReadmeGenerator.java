package readmegenerator;

import org.reflections.Reflections;
import problem.Problem;

public interface ReadmeGenerator<T extends Problem> {

    static final Reflections REFLECTIONS = new Reflections("");
    void generate();
}
