package cn.bupt.edu.spring_hmk.dao;

import cn.bupt.edu.spring_hmk.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Long> {
    List<User> findByEmail(String email);
    boolean existsByEmail(String email);
    List<User> findByUserId(Long userId);
}
