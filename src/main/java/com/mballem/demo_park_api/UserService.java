package com.mballem.demo_park_api;

import com.mballem.demo_park_api.DBExcep.DBUsername;
import com.mballem.demo_park_api.DBExcep.EntityNotFoundExcep;
import com.mballem.demo_park_api.entity.User;
import com.mballem.demo_park_api.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@RequiredArgsConstructor
@Service
public class UserService {

//    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
    private final UserRepository userRepository;

    @Transactional
    public User salvar(User user) {
        try {
            return userRepository.save(user);
        }
        catch (DataIntegrityViolationException e) {
            throw new DBUsername(String.format("Username {%s} already exists", user.getUsername()));
        }
    }

    @Transactional
    public User buscarPorId(Long id) {
        return userRepository.findById(id).orElseThrow(
                ()-> new EntityNotFoundExcep(String.format("User of id = %s not found",id)));
    }

    @Transactional
    public User attSenha(Long id, String senha) {
        User user = buscarPorId(id);
        user.setPassword(senha);
        return user;
    }

    @Transactional
    public List<User> buscarTudo(){
        return userRepository.findAll();
    }

    public boolean verificarSenha(String rawPassword, String encodedPassword) {
        return rawPassword.equals(encodedPassword);
    }
}
