package com.example.todo.service;

import com.example.todo.dto.UsersRequest;
import com.example.todo.dto.UsersResponse;
import com.example.todo.entity.Users;
import com.example.todo.repository.UsersRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UsersService {
    private final UsersRepository usersRepository;

    @Transactional
    public UsersResponse createUsers(UsersRequest request){
        Users users = new Users(request.getName(), request.getEmail(), request.getPassword());
        Users savedUsers = usersRepository.save(users);

        return new UsersResponse(
                savedUsers.getId(),
                savedUsers.getName(),
                savedUsers.getEmail(),
                savedUsers.getCreatedAt(),
                savedUsers.getModifiedAt()
        );
    }

    @Transactional(readOnly = true)
    public List<UsersResponse> findUsers(String name) {
        List<Users> users;
        if(StringUtils.hasText(name)){
            users = usersRepository.findByNameOrderByModifiedAtDesc(name);
        } else{
            users = usersRepository.findAll(Sort.by(Sort.Direction.DESC, "modifiedAt"));
        }
        return users.stream()
                .map(user -> new UsersResponse(
                        user.getId(),
                        user.getName(),
                        user.getEmail(),
                        user.getCreatedAt(),
                        user.getModifiedAt()
                ))
                .toList();
    }

    @Transactional(readOnly = true)
    public UsersResponse findUser(Long usersId) {
        Users users = findUserById(usersId);
        return new UsersResponse(
                users.getId(),
                users.getName(),
                users.getEmail(),
                users.getCreatedAt(),
                users.getModifiedAt()
        );
    }

    @Transactional
    public UsersResponse updateUsers(Long usersId, UsersRequest request) {
        Users users = findUserById(usersId);
        users.updateUsers(request.getName(), request.getEmail(), request.getPassword());

        return new UsersResponse(
                users.getId(),
                users.getName(),
                users.getEmail(),
                users.getCreatedAt(),
                users.getModifiedAt()
        );
    }

    @Transactional
    public void deleteUsers(Long usersId) {
        if (!usersRepository.existsById(usersId)) {
            throw new IllegalArgumentException("UsersId not found!");
        }
        usersRepository.deleteById(usersId);
    }

    private Users findUserById(Long usersId) {
        return usersRepository.findById(usersId).orElseThrow(
                () -> new IllegalArgumentException("UsersId not found!")
        );
    }

}
