package by.makei.junit.dao;

import java.util.HashMap;
import java.util.Map;

public class UserDaoSpy extends UserDao{

private final UserDao userDao;

    private Map<Long, Boolean> answers = new HashMap<>();// не универсальный, т.к. ограничены дженериками

    public UserDaoSpy(UserDao userDao) {
        this.userDao = userDao;
    }
//    private Answer1


    @Override
    public boolean delete(Long userId) {
//        invocations++;//счётчик запусков
        return answers.getOrDefault(userId, userDao.delete(userId));
    }
}
