package by.makei.junit.service;

import by.makei.junit.dto.User;
import by.makei.junit.paramresolver.UserServiceParamResolver;
import org.hamcrest.MatcherAssert;
import org.hamcrest.collection.IsMapContaining;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertTrue;

@TestInstance(TestInstance.Lifecycle.PER_METHOD)
// by default
//In the Junit 5 classes and methods should be package private (friendly)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@ExtendWith({//указать все экстеншены, чтобы резолвер смог их опознать
        UserServiceParamResolver.class
})
public class UserServiceTest {

    private static final User IVAN = User.of(1, "Ivan", "123");
    private static final User SASHA = User.of(2, "Sasha", "321");
    private UserService userService;

    UserServiceTest(TestInfo testInfo){
        System.out.println();
    }


    @BeforeAll
    static void init() {
        System.out.println("Before all");
    }

    @BeforeEach
    void prepare(UserService userService) {
        System.out.println("Before each " + this);
//        userService = new UserService();
        this.userService = userService;

    }

    @Test
    @Order(2)
    void usersEmptyIfNoUserAdded(UserService userService) {
        System.out.println("Test 1 " + this);
        List<User> users = userService.getAll();
        assertTrue(users.isEmpty(), "Users should be empty");
    }

    @Test
    @Order(1)
    @DisplayName("method: usersSizeIfUserAdded")
    void usersSizeIfUserAdded() {
        System.out.println("Test 2 " + this);
        userService.add(IVAN);
        userService.add(SASHA);
        List<User> users = userService.getAll();
//        assertEquals(2, users.size());
        assertThat(users).hasSize(2);// AssertJ variant
    }

    @Test
    @Tag("login")
    @Order(3)

    void isLogin() {
        System.out.println("Test 3 " + this);
        userService.add(IVAN);
        Optional<User> maybeUser = userService.login(IVAN.getName(), IVAN.getPassword());
//        assertTrue(maybeUser.isPresent());
        assertThat(maybeUser).isPresent();
//        maybeUser.ifPresent(user -> assertEquals(IVAN, user));
        maybeUser.ifPresent(user -> assertThat(user).isEqualTo(IVAN));
    }

    @Test
    void usersConvertedById() {
        userService.add(IVAN, SASHA);
        Map<Long, User> userMap = userService.getAllConvertedById();

        MatcherAssert.assertThat(userMap, IsMapContaining.hasKey(IVAN.getId())); //hamcrest variant
        assertAll(
                () -> assertThat(userMap).containsKeys(IVAN.getId(), SASHA.getId()),
                () -> assertThat(userMap).containsValues(SASHA, IVAN)
        );
    }

    @Test
    void usersConvertToMapById() {
        userService.add(IVAN);
        userService.add(SASHA);

    }

    @Test
    @Tag("login")
    void loginFailIfPasswordIsIncorrect() {
        userService.add(IVAN);
        Optional<User> maybeUser = userService.login(IVAN.getName(), "dummy");
        assertFalse(maybeUser.isPresent());
    }

    @Test
    @Tag("login")
    void loginFailIfUserNotExists() {
        userService.add(IVAN);
        Optional<User> maybeUser = userService.login("dummy", IVAN.getPassword());
        assertFalse(maybeUser.isPresent());
    }

    @Test
    @Tag("login")
    void throwExceptionIfUsernameOrPasswordIsNull() {
        assertAll(
                () -> assertThrows(IllegalArgumentException.class, () -> userService.login(null, "dummy")),
                () -> {
                    var ex = assertThrows(IllegalArgumentException.class, () -> userService.login("dummy", null));
                    System.out.println(ex.getMessage());
                });

    }


    @AfterEach
    void deleteDataFromDb() {
        System.out.println("After each " + this);
    }

    @AfterAll
    static void close() {
        System.out.println("After all");
    }

    @Nested
    @DisplayName("nested class test")
    @Tag("login")
    class LoginTest{
        //сюда можно перенести все методы с аннотацией тег логин

        @Test
        void testNestedClass1() {
            System.out.println("This is nested class method 1");
            assertTrue(true);
        }
        @Test
        void testNestedClass2(){
            System.out.println("This is nested class method 2");
            assertTrue(true);
        }

    }
}
