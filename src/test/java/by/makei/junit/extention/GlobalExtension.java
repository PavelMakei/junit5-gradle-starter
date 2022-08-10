package by.makei.junit.extention;

import org.junit.jupiter.api.extension.AfterAllCallback;
import org.junit.jupiter.api.extension.AfterTestExecutionCallback;
import org.junit.jupiter.api.extension.BeforeAllCallback;
import org.junit.jupiter.api.extension.ExtensionContext;

public class GlobalExtension implements BeforeAllCallback, AfterAllCallback, AfterTestExecutionCallback {
    @Override
    public void beforeAll(ExtensionContext context) throws Exception {
        System.out.println("before all callback");
    }

    @Override
    public void afterAll(ExtensionContext context) throws Exception {
        System.out.println("after all callback");
    }

    @Override
    public void afterTestExecution(ExtensionContext context) throws Exception {
        System.out.println("after test execution callback");
    }
}