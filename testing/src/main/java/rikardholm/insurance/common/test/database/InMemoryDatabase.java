package rikardholm.insurance.common.test.database;

import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;

import java.lang.annotation.*;

@TestExecutionListeners({InMemoryDatabaseTestExecutionListener.class, DependencyInjectionTestExecutionListener.class})
@Documented
@Inherited
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface InMemoryDatabase {
}
