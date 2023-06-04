package com.service.auth.repository;

import com.service.auth.model.entity.UserEntity;
import org.apache.catalina.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import javax.crypto.spec.OAEPParameterSpec;
import java.util.Optional;

@Repository
public interface UserRepository extends CrudRepository<UserEntity,Long> {

    public Optional<UserEntity>findByUsername(String username);
}
