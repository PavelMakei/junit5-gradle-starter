package by.makei.junit.extention;

import org.junit.jupiter.api.extension.ConditionEvaluationResult;
import org.junit.jupiter.api.extension.ExecutionCondition;
import org.junit.jupiter.api.extension.ExtensionContext;

public class ConditionalExtension implements ExecutionCondition {
    @Override
    public ConditionEvaluationResult evaluateExecutionCondition(ExtensionContext context) {
        return System.getProperty("skip") != null
                ? ConditionEvaluationResult.disabled("condition is skip")
                : ConditionEvaluationResult.enabled("condition is not skip");
    }
}
