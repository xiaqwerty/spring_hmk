package cn.bupt.edu.spring_hmk.dao;

import cn.bupt.edu.spring_hmk.entity.CheckCode;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CheckCodeRepository extends JpaRepository<CheckCode, Long> {
    List<CheckCode> findByEmail(String email);
}
