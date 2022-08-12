package by.makei.spring.database.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "users")
@NoArgsConstructor
@Data
@AllArgsConstructor
@Builder
public class User implements BaseEntity<Long>{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String username;

    @Column(name = "birth_date")//можно и не указывать, т.к. по умолчанию используется стратегия преобразования CamelCase в Sql, можно переопределить через properties spring.jpa.hibernate.naming.implicit-strategy , spring.jpa.hibernate.naming.physical-strategy
    private LocalDate birthDate;

    private String firstname;
    private String lastname;

    @Enumerated(value = EnumType.STRING)//т.к. по умолчанию это Ordinal
    private Role role;

    @ManyToOne(fetch = FetchType.LAZY)//решение проблемы N+1
    @JoinColumn(name = "company_id")
    private Company company;


    @OneToMany(mappedBy = "user")
    @Builder.Default
    private List<UserChat> userChats = new ArrayList<>();



}
