package ru.innopolis.attendance.data;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.innopolis.attendance.models.InnoUser;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<InnoUser, Long> {
    Optional<InnoUser> findByEmail(String email);
}
