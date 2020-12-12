package cn.bupt.edu.spring_hmk.dao;

import cn.bupt.edu.spring_hmk.entity.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface PersonRepository extends JpaRepository<Person, Integer>
{
}
