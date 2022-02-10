package annotation.boj;

import annotation.SolveDate;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target(TYPE)//
@Retention(RUNTIME)
public @interface BOJ {

    int number() default -1; //문제 번호

    SolveDate solveDate() default @SolveDate;

    static final int DEFAULT_NUMBER = -1;




}
