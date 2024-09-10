package com.mballem.demo_park_api;

import com.mballem.demo_park_api.entity.User;
import com.mballem.demo_park_api.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;

@RequiredArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;

    @Transactional
    public User salvar(User user) {
        return userRepository.save(user);
    }

    @Transactional
    public User buscarPorId(Long id) {
        return userRepository.findById(id).orElseThrow(
                ()-> new RuntimeException("User not found"));
    }
}
