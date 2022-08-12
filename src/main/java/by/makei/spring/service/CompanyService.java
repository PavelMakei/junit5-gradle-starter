package by.makei.spring.service;

import by.makei.spring.database.entity.Company;
import by.makei.spring.dto.CompanyReadDto;
import by.makei.spring.database.repository.CrudRepository;
import by.makei.spring.listener.entity.AccessType;
import by.makei.spring.listener.entity.EntityEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CompanyService {

    private final CrudRepository<Long, Company> companyRepository;
    private final UserService userService;
    private final ApplicationEventPublisher eventPublisher;

    public Optional<CompanyReadDto> findById(Long id) {
        return companyRepository.findById(id)
            .map(entity -> {
                eventPublisher.publishEvent(new EntityEvent(entity, AccessType.READ));
                return new CompanyReadDto(entity.getId());
            });
    }
}
