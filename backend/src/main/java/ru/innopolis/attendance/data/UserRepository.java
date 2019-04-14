package ru.innopolis.attendance.data;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.innopolis.attendance.models.UserProfile;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserProfile, Long> {

    UserProfile getById(long id);

    Optional<UserProfile> findById(long id);

    Optional<UserProfile> findByEmail(String email);

    boolean existsByEmail(String email);

    List<UserProfile> findAll(Specification<UserProfile> specification, Sort sort);
}
