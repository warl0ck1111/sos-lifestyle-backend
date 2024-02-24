package com.example.sosinventory.appuser;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * @author Okala Bashir .O.
 */

@Repository
public interface AppUserRepository extends JpaRepository<AppUser, Long> {

    Boolean existsByEmail(String email);

    Boolean existsByPhoneNumber(String phoneNumber);

    Optional<AppUser> findByEmail(String email);

    Page<AppUser> findByFirstNameOrLastNameContainingIgnoreCase(String keyword, String keyword1, PageRequest of);

    void deleteByEmail(String email);

//    @Modifying
//    @Query(value = "UPDATE AppUser a SET a.profilePhotoFileName = :fileName WHERE a.email = :email")
//    void updateProfileImageFileName(@Param("fileName") String fileName, @Param("email") String email);

    Boolean existsByUsername(String username);

    @Query("SELECT a.id as userId, a.username, a.role, a.email, a.dateOfBirth, a.firstName, a.lastName, a.gender, a.phoneNumber FROM AppUser a WHERE a.role = :role")
    Page<AppUserResponseInterface> findAllByRole(@Param("role") String role, Pageable pageable);


    @Query("SELECT a.id as userId, a.username, a.role, a.email, a.dateOfBirth, a.firstName, a.lastName, a.gender, a.phoneNumber FROM AppUser a")
    Page<AppUserResponseInterface> findAllAppUsers(Pageable pageable);


    @Query("SELECT u FROM AppUser u WHERE LOWER(u.email) LIKE LOWER(CONCAT('%', ?1, '%'))")
    Page<AppUser> searchUsers(String keyword, PageRequest pageRequest);
}
