package by.makei.spring.service;

import by.makei.spring.database.entity.Company;
import by.makei.spring.database.repository.UserRepository;
import by.makei.spring.database.repository.CrudRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final CrudRepository<Integer, Company> companyRepository;
}
