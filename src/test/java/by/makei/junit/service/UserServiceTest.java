package by.makei.junit.service;

import by.makei.junit.dao.UserDao;
import by.makei.junit.dto.User;
import by.makei.junit.extention.ConditionalExtension;
import by.makei.junit.extention.PostProcessingExtension;
import by.makei.junit.extention.UserServiceParamResolver;
import org.hamcrest.MatcherAssert;
import org.hamcrest.collection.IsMapContaining;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.IOException;
import java.time.Duration;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.TimeUnit;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
// by default
//In the Junit 5 classes and methods should be package private (friendly)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@ExtendWith({//указать все экстеншены, чтобы резолвер смог их опознать
        UserServiceParamResolver.class //sets userService
//        , PostProcessingExtension.class
        , ConditionalExtension.class
        , MockitoExtension.class //added with mockito-junit-jupiter
//        , ThrowableExtension.class //кэтчит все экстеншены
//        , GlobalExtension.class
})
public class UserServiceTest extends AbstractTestBase {

    //Mockito initializes all mocks in beforeEach callback
    @Captor
    private ArgumentCaptor<Long> argumentCaptor;
    @Mock
    private UserDao userDao;
    @InjectMocks
    private UserService userService;

    private static final User IVAN = User.of(1, "Ivan", "123");
    private static final User SASHA = User.of(2, "Sasha", "321");


    UserServiceTest(TestInfo testInfo) {
        System.out.println();
    }


    @BeforeAll
    static void init() {
        System.out.println("Before all");
    }

    @BeforeEach
//    void prepare(UserService userService) {
    void prepare() {
        System.out.println("Before each " + this);
        //not needs since @Mock and @InjectMock added

//        this.userDao = Mockito.mock(UserDao.class);//мок не содержит в себе реальный объект и только эмулирует работу его метода
//        this.userDao = Mockito.spy(new UserDao());//spy содержит в себе объект и по сути является прокси, в котором при определённых условиях будет вызван реальный метод объекта getOrDefault
//        this.userService = new UserService(userDao);
    }

    @Test
    void shouldDeleteExistedUser() {
        userService.add(IVAN);
        Mockito.doReturn(true).when(userDao).delete(IVAN.getId());//stub
//        Mockito.doReturn(true).when(userDao).delete(Mockito.any());//stub dummy
//        Mockito.when(userDao.delete(IVAN.getId())).thenReturn(true)
//                .thenReturn(false);//second variant of stub
        var result = userService.delete(IVAN.getId());//при таком использовании не будет корректно работать спай, т.к. будет вызывать метод, которого
        System.out.println(userService.delete(IVAN.getId()));
        System.out.println(userService.delete(IVAN.getId()));

//        ArgumentCaptor<Long> argumentCaptor = ArgumentCaptor.forClass(Long.class);// doesn't need if @Captor

        Mockito.verify(userDao, Mockito.times(3)).delete(argumentCaptor.capture());//позволяет получить аргументы, с которыми был запущен метод
//        Mockito.verify(userDao, Mockito.times(3)).delete(IVAN.getId());//считает количество запусков теста
//        Mockito.verifyNoInteractions(userDao);//ожидается, что не будет вызовов метода мока
        assertThat(argumentCaptor.getValue()).isEqualTo(1);//получает аргумент, который возможно был изменен бизнес логикой в сервисе, перед вызовом в ДАО
        assertThat(result).isTrue();

//        Mockito.reset(userDao); //clear mocks if they need to be used more than once
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
    void isLogin() throws IOException {
        System.out.println("Test 3 " + this);
        userService.add(IVAN);
        Optional<User> maybeUser = userService.login(IVAN.getName(), IVAN.getPassword());
//        assertTrue(maybeUser.isPresent());
        assertThat(maybeUser).isPresent();
//        maybeUser.ifPresent(user -> assertEquals(IVAN, user));
        maybeUser.ifPresent(user -> assertThat(user).isEqualTo(IVAN));
        if (true) {
//            throw new IOException("test reason");
            throw new RuntimeException("test reason");
        }
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

    //    @Test
    @Tag("login")
//    @Disabled("this is just comment")
    @RepeatedTest(value = 5, name = RepeatedTest.LONG_DISPLAY_NAME)
    void loginFailIfPasswordIsIncorrect(RepetitionInfo repetitionInfo) { //Repetition info is not necessary it just let get number of repetition for log for example
        userService.add(IVAN);
        Optional<User> maybeUser = userService.login(IVAN.getName(), "dummy");
        assertFalse(maybeUser.isPresent());
    }

    @Test
    void checkLoginFunctionalityPerformance() {
        var result = assertTimeout(Duration.ofMillis(200L), () -> {
            Thread.sleep(100L);
            return userService.login(IVAN.getName(), IVAN.getPassword());
        });
    }

    @Test
    @Tag("login")
    @Timeout(value = 100, unit = TimeUnit.MILLISECONDS)
    void loginFailIfUserNotExists() throws InterruptedException {
        userService.add(IVAN);
        Thread.sleep(10);
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

    @ParameterizedTest(name = "{arguments} test")
//    @ArgumentsSource()
//    @NullSource
//    @EmptySource
//    @ValueSource(strings ={"Ivan", "Sasha"} )
//            @EnumSource
//    @NullAndEmptySource
    @CsvFileSource(resources = "/login-test-data.csv", delimiter = ',', numLinesToSkip = 0)
//            @CsvSource({
//                    "Ivan",
//                    "Petr"
//            })
//    @MethodSource("getArgumentSource")
    @DisplayName("This is loginParametrizedTest")
    void loginParametrizedTest(String username) {
        userService.add(IVAN, SASHA);
        var maybeUser = userService.login(username, "123");

    }

    static Stream<Arguments> getArgumentSource() {
        return Stream.of(
                Arguments.of("Sasha"),
                Arguments.of("Petr"),
                Arguments.of("Dima")
        );
    }

    @ParameterizedTest
    @MethodSource("getArgumentSource2")
    void loginParametrizedTest2(String username, String password, Optional<User> user) {
        userService.add(IVAN, SASHA);
        var maybeUser = userService.login(username, password);
        assertThat(maybeUser).isEqualTo(user);

    }

    static Stream<Arguments> getArgumentSource2() {
        return Stream.of(
                Arguments.of("Sasha", "321", Optional.of(SASHA)),
                Arguments.of("Petr", "111", Optional.empty()),
                Arguments.of("Ivan", "123", Optional.of(IVAN))
        );
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
    class LoginTest {
        //сюда можно перенести все методы с аннотацией тег логин

        @Test
        void testNestedClass1() {
            System.out.println("This is nested class method 1");
            assertTrue(true);
        }

        @Test
        void testNestedClass2() {
            System.out.println("This is nested class method 2");
            assertTrue(true);

        }

    }
}
