package by.makei.spring.integration.service;

import by.makei.spring.config.DatabaseProperties;
import by.makei.spring.dto.CompanyReadDto;
import by.makei.spring.service.CompanyService;
import by.makei.spring.integration.annotation.It;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.test.context.TestConstructor;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;

//@ExtendWith(SpringExtension.class)
//@ContextConfiguration(
//        classes = ApplicationRunner.class,
//        initializers = ConfigDataApplicationContextInitializer.class)// to find yaml configuration of spring context
//instead of top we can use
//@SpringBootTest //but we can use classes = ApplicationRunner.class
//
//@ActiveProfiles("test")//позволяет загрузить дополнительные проперти для тестов
@RequiredArgsConstructor//fields have to be final
@TestConstructor(autowireMode = TestConstructor.AutowireMode.ALL)//можно вынести в spring.properties для тестов, т.е. установить по умолчанию
@It
public class CompanyServiceTestIt {
    private static final Long COMPANY_ID = 1l;

    private final DatabaseProperties databaseProperties;
    private final CompanyService companyService;


    @Test
    void findById() {

        var actualResult = companyService.findById(COMPANY_ID);

        assertTrue(actualResult.isPresent());

        var expectedResult = new CompanyReadDto(COMPANY_ID);
        actualResult.ifPresent(actual -> Assertions.assertEquals(expectedResult, actual));


    }

}
