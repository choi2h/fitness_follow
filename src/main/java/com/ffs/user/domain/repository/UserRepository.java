package com.ffs.user.domain.repository;

import com.ffs.user.domain.User;
import com.ffs.user.domain.UserType;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends Repository<User, Long> {

    User save(User member);

    Optional<User> findById(Long id);

    Optional<User> findByLoginId(String loginId);

    Optional<User> findByName(String name);

    List<User> findAll();

    @Query("SELECT u FROM User u WHERE u.branch.id = :id and u.userType = :user_type")
    List<User> findAllByBranchIdAndUserType(@Param("id") Long id, @Param("user_type") UserType userType);

    @Query("SELECT um FROM User  u " +
            "INNER JOIN UserMatching um ON (um.employeeId = u.id) " +
            "WHERE u.id = :userId AND u.userType = :user_type")
    List<User> findAllChargeUserByEmployeeId(@Param("userId") Long id, @Param("user_type") UserType userType);

    @Query("SELECT um FROM User  u " +
            "INNER JOIN UserMatching um ON (um.memberId = u.id) " +
            "WHERE u.id = :userId AND u.userType = :user_type")
    List<User> findAllChargeUserByMemberId(@Param("userId") Long id, @Param("user_type") UserType userType);
}
