package com.example.sosinventory.token;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface TokenRepository extends JpaRepository<Token, Integer> {

    //    Integer@Query(value = """
//            select t from Token t inner join Users u\s
//            on t.user.id = u.id\s
//            where u.id = :id and (t.expired = false or t.revoked = false)\s
//            """)
    List<Token> findAllById(Long id);

    Optional<Token> findByToken(String token);

    boolean existsByToken(String Token);

    boolean existsByEmailAddressAndTokenAndTokenType(String email, String token, TokenType tokenType);

    void deleteByEmailAddressAndTokenType(String email, TokenType tokenType);

    void deleteByToken(String token);
}
