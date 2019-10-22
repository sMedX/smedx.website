package app.core.DB.Services;


import app.core.DB.DataModel.User;
import app.core.DB.Repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.apache.commons.codec.digest.DigestUtils;

@Service("userService")
@Transactional
public class UserService {

    @Autowired
    private UserRepository userRepository;


    public User findUserByHash(String credentialsHash){
        return userRepository.findByCred(credentialsHash);
    }

    public User findByMail(String mail) {
        User user = userRepository.findByMail(mail);
        try {
            System.out.println("Requested user by mail " + mail + " found:" + (user != null ? user.getMail() : "???"));
        } catch (Exception e) {
            System.out.println("Error in findByMail by mail:" + mail);
        }
        return user;
    }
    public User authenticate(String email, String password) {
        String pwdHex = DigestUtils.md5Hex(password);
        User user = userRepository.findByMailAndCred(email, pwdHex);
        System.out.println("Requested user: <" + email + "> found user " + user.getMail() + " where hashes is [" + pwdHex + " == " + user.getCred() + "]");
        return user;

    }
}
