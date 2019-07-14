package project.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import project.bean.UserData;

@Repository
public interface UserDataRepository extends JpaRepository<UserData, String> {

}
