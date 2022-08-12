package by.makei.spring.service;

import by.makei.spring.database.entity.Company;
import by.makei.spring.dto.CompanyReadDto;
import by.makei.spring.database.repository.CrudRepository;
import by.makei.spring.listener.entity.EntityEvent;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.ApplicationEventPublisher;

import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.verifyNoMoreInteractions;


@ExtendWith(MockitoExtension.class)//needs to work with annotations
class CompanyServiceTest2 {
    private static final Long COMPANY_ID = 1l;

    @Mock
    private CrudRepository<Long, Company> companyRepository;
    @Mock
    private UserService userService;
    @Mock
    private ApplicationEventPublisher eventPublisher;
    @InjectMocks
    private CompanyService companyService;

    @Test
    void findById() {

        Mockito.doReturn(Optional.of(new Company(COMPANY_ID, null, Collections.emptyMap())))
                .when(companyRepository).findById(COMPANY_ID);//здесь мокается функционал заглушки

        Optional<CompanyReadDto> actualResult = companyService.findById(COMPANY_ID);
        assertTrue(actualResult.isPresent());
        var expectedResult = new CompanyReadDto(COMPANY_ID);
        actualResult.ifPresent(actual -> assertEquals(expectedResult, actual));

        Mockito.verify(eventPublisher).publishEvent(ArgumentMatchers.any(EntityEvent.class));// проверка, что моки были вызваны
        verifyNoMoreInteractions(eventPublisher, userService);


    }
}