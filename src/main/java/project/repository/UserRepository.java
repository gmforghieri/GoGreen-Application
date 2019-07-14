package project.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import project.bean.User;

@Repository
public interface UserRepository extends JpaRepository<User, String> {

}
