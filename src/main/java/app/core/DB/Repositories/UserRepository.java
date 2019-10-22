package app.core.DB.Repositories;

import app.core.DB.DataModel.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

    //User findById(Long id);

    User findByName(String name);

    User findByMail(String mail);

    User findByMailAndCred(String mail, String cred);

    User findByCred(String cred);
}
