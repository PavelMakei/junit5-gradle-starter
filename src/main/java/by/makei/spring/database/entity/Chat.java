package by.makei.spring.database.entity;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Data//g,s,e&h,toStr,value,RequiredArgsConstructor
@NoArgsConstructor//needs to Hibernate
@AllArgsConstructor
@Builder
@Entity //сущность хайбернейта
@Table (name = "chat")//с маленькой
public class Chat implements BaseEntity<Long>{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @Column(name = "name", unique = true, nullable = false)//можно и не писать, но правила хорошего тона
    private String name;

    @OneToMany(mappedBy = "chat")
    @Builder.Default
    private List<UserChat> userChats = new ArrayList<>();


}
