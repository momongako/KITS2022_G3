package com.example.projectemarketg3.repository;

import com.example.projectemarketg3.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);

    @Transactional
    @Modifying
    @Query("UPDATE User u SET u.enabled = TRUE WHERE u.email = ?1")
    void enableUser(String email);

    User getByEmail(String email);

    @Query("select u from User u where upper(u.ranking.name) like upper(concat('%', ?1, '%')) order by u.rank_date DESC")
    List<User> findByRanking_NameContainsIgnoreCaseOrderByRank_dateDesc(String name);

    @Query("select distinct u from User u where u.ranking.name = ?1 order by u.rank_date DESC")
    List<User> findDistinctByRanking_NameOrderByRank_dateDesc(String name);

    List<User> findByNameLikeIgnoreCaseOrderByRanking_IdDesc(String name);

    List<User> findByNameContainsIgnoreCaseOrderByRanking_IdDesc(String name);



    List<User> findByNameStartsWithIgnoreCaseOrderByNameAsc(String name);

    User getUserById(Long id);

}