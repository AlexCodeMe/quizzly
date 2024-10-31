package com.alexcasey.quizzly.service;

import org.springframework.stereotype.Service;

import com.alexcasey.quizzly.dto.UserDto;
import com.alexcasey.quizzly.exception.AccountNotFoundException;
import com.alexcasey.quizzly.mapper.UserMapper;
import com.alexcasey.quizzly.model.Account;
import com.alexcasey.quizzly.model.User;
import com.alexcasey.quizzly.repository.AccountRepository;
import com.alexcasey.quizzly.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final AccountRepository accountRepository;
    private final UserMapper userMapper;

    public UserDto createUser(UserDto userDto) {
        Account account = accountRepository.findById(userDto.getAccountId())
                .orElseThrow(() -> new AccountNotFoundException("Account not found"));

        User user = userMapper.toEntity(userDto);
        user.setAccount(account);
        
        return userMapper.toDto(userRepository.save(user));
    }
}
