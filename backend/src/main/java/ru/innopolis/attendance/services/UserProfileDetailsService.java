package ru.innopolis.attendance.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.innopolis.attendance.data.UserRepository;
import ru.innopolis.attendance.models.UserProfile;
import ru.innopolis.attendance.models.UserProfileDetails;

import java.util.Optional;

@Service
public class UserProfileDetailsService implements UserDetailsService {

    private UserRepository userRepository;

    @Autowired
    public UserProfileDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Optional<UserProfile> user = userRepository.findByEmail(email);

        if (!user.isPresent()) {
            throw new UsernameNotFoundException("Email: " + email + " not found.");
        }

        return new UserProfileDetails(user.get());
    }
}
