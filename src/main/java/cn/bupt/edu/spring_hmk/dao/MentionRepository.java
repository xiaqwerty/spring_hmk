package cn.bupt.edu.spring_hmk.dao;

import cn.bupt.edu.spring_hmk.entity.Mention;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface MentionRepository extends JpaRepository<Mention, Long> {
    @Query(value = "select m.B from Mention  m where m.A = ?1")
    List<Long> findBByA(long a);

    @Query(value = "select m.A from Mention m where m.B = ?1")
    List<Long> findAByB(long b);

    @Query(value = "select m.B from Mention m where m.A=?1")
    List<Long> findUsersByA(long a);

    @Query(value = "select m.A from Mention m where m.B=?1")
    List<Long> findUsersByB(long b);

    @Query(value = "select m from Mention m where m.A = ?1 and m.B=?2")
    List<Mention> findByAAndB(long a, long b);

}
