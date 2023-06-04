package com.service.auth.service;

import com.service.auth.config.JwtProvider;
import com.service.auth.model.dto.TokenDto;
import com.service.auth.model.dto.UserDto;
import com.service.auth.model.entity.UserEntity;
import com.service.auth.repository.UserRepository;
import org.apache.catalina.User;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.ui.ModelMap;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@Service
public class AuthService {
    @Autowired
    private UserRepository repository;
    @Autowired
    private PasswordEncoder encoder;
    @Autowired
    private ModelMapper mapper;
    @Autowired
    private JwtProvider provider;

    public UserDto save(UserDto user){
        Optional<UserEntity> response=repository.findByUsername(user.getUsername());
        if(response.isPresent()){
            throw  new ResponseStatusException(HttpStatus.CONFLICT,"User is already exist");
        }
        UserEntity userDb=repository.save(new UserEntity(user.getUsername(),encoder.encode(user.getPassword())));
        return mapper.map(userDb,UserDto.class);
    }
    public TokenDto login(UserDto user){
       UserEntity userEntity= repository.findByUsername(user.getUsername()).orElseThrow(()->new ResponseStatusException(HttpStatus.UNAUTHORIZED));
        if(encoder.matches(user.getPassword(),userEntity.getPassword())){
            return new TokenDto(provider.createToken(userEntity));
        }else{
            throw  new ResponseStatusException(HttpStatus.UNAUTHORIZED);
        }
    }
    public TokenDto validate(String token){
        provider.validate(token);
        String username=provider.getUsernameFromToken(token);
        repository.findByUsername(username).orElseThrow(()-> new ResponseStatusException(HttpStatus.UNAUTHORIZED));
        return new TokenDto(token);
    }

}
