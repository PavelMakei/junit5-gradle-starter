package by.makei.spring.integration.database.repository;

import by.makei.spring.database.entity.Company;
import by.makei.spring.integration.annotation.IT;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionException;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionTemplate;

import javax.persistence.EntityManager;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@IT
@RequiredArgsConstructor
//@Transactional //желательно использовать спринговый транзакшонал, т.к. он предоставляет более широкий функционал
@Slf4j
//@Commit
class CompanyRepositoryTest {

    private final EntityManager entityManager;

    @Autowired
    private PlatformTransactionManager transactionManager;
    private TransactionTemplate transactionTemplate;

    @Test
    void findById() {
//        transactionTemplate = new TransactionTemplate(transactionManager);
//
//        try {
//            transactionTemplate.execute(status -> {
                var company = entityManager.find(Company.class, 1);
                assertNotNull(company);
                assertThat(company.getLocales()).hasSize(2);
                log.info("++++++++" + company.getName());
//                return "Ref-0";
//            });

//
//        } catch (TransactionException e) {
//            log.error("exception, rollback?");
//        }
    }

    @Test
    void save() {
        var company = Company.builder()
                .name("Apple1")
                .locales(Map.of(
                        "ru", "Apple описание",
                        "en", "Apple description"
                ))
                .build();
        entityManager.persist(company);
        assertNotNull(company.getId());
    }
}