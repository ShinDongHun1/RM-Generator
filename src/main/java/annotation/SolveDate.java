package annotation;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;
import java.time.LocalDate;

import static java.lang.annotation.ElementType.ANNOTATION_TYPE;
import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target({TYPE, ANNOTATION_TYPE})//어노테이션에 붙는 어노테이션
@Retention(RUNTIME)
public @interface SolveDate {

    int year() default 0;
    int month() default 1;
    int day() default 1;


}
