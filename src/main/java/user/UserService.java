package user;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import user.exception.EmailInvalidException;
import user.exception.UserAlreadyExistException;
import org.apache.commons.validator.routines.EmailValidator;
import user.exception.WeakPasswordException;

import java.util.HashSet;
import java.util.Set;

@Service
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(email);
        if (user == null) {
            throw new UsernameNotFoundException("User not found with email: " + email);
        }
        return new org.springframework.security.core.userdetails.User(
                user.getEmail(), user.getPassword(), getAuthorities(user));
    }

    private Set<SimpleGrantedAuthority> getAuthorities(User user) {
        return new HashSet<>();
    }

    public Long register(User user) {
        if (!EmailValidator.getInstance().isValid(user.getEmail())) {
            throw new EmailInvalidException(user.getEmail());
        }
        if (checkPassword(user.getPassword()) < 40) {
            throw new WeakPasswordException(user.getPassword());
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        try {
            userRepository.save(user);
        } catch (Exception e) {
            throw new UserAlreadyExistException(user);
        }
        return user.getId();
    }

    public User getUserByEmail(String email) throws UsernameNotFoundException {
        return userRepository.findByEmail(email);
    }

    public static int checkPassword(String password) {
        int balls = 0;
        balls += password.length() >= 8? 10: 0;
        balls += password.chars()
                .filter(i -> "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz".contains("" + (char) i)).count() > 1? 10: 0;
        balls += password.chars()
                .filter(i -> "0123456789".contains("" + (char) i)).count() > 1? 10: 0;
        balls += password.chars()
                .anyMatch(i -> "ABCDEFGHIJKLMNOPQRSTUVWXYZ".contains("" + (char) i))? 10: 0;
        balls += password.chars()
                .anyMatch(i -> "abcdefghijklmnopqrstuvwxyz".contains("" + (char) i))? 10: 0;
        balls += password.chars()
                .anyMatch(i -> "!@#$%^&*[]".contains("" + (char) i))? 10: 0;
        return balls;
    }
}
