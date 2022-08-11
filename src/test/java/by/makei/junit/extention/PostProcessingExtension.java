package by.makei.junit.extention;

import by.makei.junit.dao.UserDao;
import by.makei.junit.service.UserService;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.TestInstancePostProcessor;

import java.lang.reflect.Field;

public class PostProcessingExtension implements TestInstancePostProcessor {

    @Override
    public void postProcessTestInstance(Object testInstance, ExtensionContext context) throws Exception {
//        testInstance - инстанс теста который только создан.
//        именно этот экстеншон используется Спрингом для внедрения своих зависимостей

        System.out.println("post processing extension");
        Field[] fields = testInstance.getClass().getDeclaredFields();
        for (Field field: fields){
            if(field.getName().equals("userService")){
                field.setAccessible(true);
                field.set(testInstance, new UserService(null));
            }
        }

    }
}
