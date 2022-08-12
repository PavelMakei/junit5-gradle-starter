package by.makei.spring.database.entity;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.HashMap;
import java.util.Map;


@Data//g,s,e&h,toStr,value,RequiredArgsConstructor
@NoArgsConstructor//needs to Hibernate
@AllArgsConstructor
@Builder
@Entity //сущность хайбернейта
@Table (name = "company")//с маленькой
public class Company implements BaseEntity<Long>{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @Column(name = "name", unique = true, nullable = false)//можно и не писать, но правила хорошего тона
    private String name;


    @Builder.Default
    @ElementCollection
    @CollectionTable(name = "company_locales", joinColumns = @JoinColumn(name = "company_id"))
    @MapKeyColumn(name = "lang")
    @Column(name = "description")
    private Map<String,String> locales = new HashMap<>();

}