package by.makei.junit.service;

import by.makei.junit.dto.User;
import org.junit.jupiter.api.*;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_METHOD)
// by default
//In the Junit 5 classes and methods should be package private (friendly)
public class UserServiceTest {

    private static final User IVAN = User.of(1, "Ivan", "123");
    private static final User SASHA = User.of(2, "Sasha", "321");
    private UserService userService;


    @BeforeAll
    static void init() {
        System.out.println("Before all");
    }

    @BeforeEach
    void prepare() {
        System.out.println("Before each " + this);
        userService = new UserService();

    }

    @Test
    void usersEmptyIfNoUserAdded() {
        System.out.println("Test 1 " + this);
        List<User> users = userService.getAll();
        assertTrue(users.isEmpty(), "Users should be empty");
    }

    @Test
    void usersSizeIfUserAdded() {
        System.out.println("Test 2 " + this);
        userService.add(IVAN);
        userService.add(SASHA);
        List<User> users = userService.getAll();
        assertEquals(2, users.size());
    }

    @Test
    void isLogin() {
        userService.add(IVAN);
        Optional<User> maybeUser = userService.login(IVAN.getName(), IVAN.getPassword());
        assertTrue(maybeUser.isPresent());
        maybeUser.ifPresent(user -> assertEquals(IVAN, user));
    }

    @Test
    void loginFailIfPasswordIsIncorrect(){
        userService.add(IVAN);
        Optional<User> maybeUser = userService.login(IVAN.getName(), "dummy");
        assertFalse(maybeUser.isPresent());
    }

    @Test
    void loginFailIfUserNotExists(){
        userService.add(IVAN);
        Optional<User> maybeUser = userService.login("dummy", IVAN.getPassword());
        assertFalse(maybeUser.isPresent());
    }


    @AfterEach
    void deleteDataFromDb() {
        System.out.println("After each " + this);
    }

    @AfterAll
    static void close() {
        System.out.println("After all");
    }
}
