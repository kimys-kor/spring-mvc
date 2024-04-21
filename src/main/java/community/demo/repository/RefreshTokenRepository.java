package community.demo.repository;


import community.demo.model.RefreshTokenEntity;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

public interface RefreshTokenRepository extends JpaRepository<RefreshTokenEntity, String> {

    boolean existsByRefreshTokenAndUsername(String refreshToken, String username);

    @Transactional
    void deleteByUsernameEquals(@Param("username") String username);
}
