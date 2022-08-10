package by.makei.junit.extention;

import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.TestExecutionExceptionHandler;

import java.io.IOException;

public class ThrowableExtension implements TestExecutionExceptionHandler {
    @Override
    public void handleTestExecutionException(ExtensionContext context, Throwable throwable) throws Throwable {
        if(throwable instanceof IOException){
            System.out.println("Если это любой эксепшен кроме IOException, идём дальше. Иначе фейлим тест");
            throw throwable;
        }
    }
}
