package by.makei.junit.dao;

import org.mockito.stubbing.Answer;
import org.mockito.stubbing.Answer1;

import java.util.HashMap;
import java.util.Map;

public class UserDaoMock extends UserDao{

    private Map<Long, Boolean> answers = new HashMap<>();// не универсальный, т.к. ограничены дженериками
//    private Answer1


    @Override
    public boolean delete(Long userId) {
        return answers.getOrDefault(userId, false);
    }
}
