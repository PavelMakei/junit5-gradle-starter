package by.makei.spring.database.repository;

import by.makei.spring.bpp.Auditing;
import by.makei.spring.bpp.Transaction;
import by.makei.spring.database.entity.Company;
import by.makei.spring.database.pool.ConnectionPool;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Slf4j
@Repository
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
@Transaction
@Auditing
@RequiredArgsConstructor
public class CompanyRepository implements CrudRepository<Long, Company> {

    private final ConnectionPool pool1;
    private final List<ConnectionPool> pools;
    @Value("${db.pool.size}")
    private final Integer poolSize;

    @PostConstruct
    private void init() {
        log.warn("init company repository");
    }

    @Override
    public Optional<Company> findById(Long id) {
        System.out.println("findById method...");
        return Optional.of(new Company(id,null, Collections.emptyMap()));
    }

    @Override
    public void delete(Company entity) {
        System.out.println("delete method...");
    }
}