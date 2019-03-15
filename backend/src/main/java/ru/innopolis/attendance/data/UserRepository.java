package ru.innopolis.attendance.data;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.innopolis.attendance.models.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long>{
}
