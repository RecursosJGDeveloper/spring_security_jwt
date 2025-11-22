package security_config.demo_jwt.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import security_config.demo_jwt.models.UserEntity;


@Repository
public interface AuthRepository extends JpaRepository<UserEntity,Long> {
    Optional<UserEntity> findByUsername(String username);

}
