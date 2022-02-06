package annotation;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static annotation.BaekjoonTier.UNDEFINED;
import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target(TYPE)//클래스에 붙일 어노테이션
@Retention(RUNTIME)
public @interface BOJ {

    int number() default 0; //문제 번호

    BaekjoonTier tier() default UNDEFINED;//문제 단계
}
