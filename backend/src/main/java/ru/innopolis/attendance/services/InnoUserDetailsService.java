package ru.innopolis.attendance.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.innopolis.attendance.data.UserRepository;
import ru.innopolis.attendance.models.InnoUser;
import ru.innopolis.attendance.models.InnoUserDetails;

import java.util.Optional;

@Service
public class InnoUserDetailsService implements UserDetailsService {

    private UserRepository userRepository;

    @Autowired
    public InnoUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Optional<InnoUser> user = userRepository.findByEmail(email);

        if (!user.isPresent()) {
            throw new UsernameNotFoundException("Email not found");
        }

        return new InnoUserDetails(user.get());
    }
}
