package ru.company.notification.service;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.company.notification.dto.request.UserRequest;
import ru.company.notification.dto.response.UserResponse;
import ru.company.notification.mapper.UserMapper;
import ru.company.notification.model.User;
import ru.company.notification.repository.UserRepository;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final NotificationService notificationService;

    @Transactional
    public UserResponse createUser(UserRequest request) {
        User newUser = userMapper.toUser(request);
        return userMapper.toUserResponse(userRepository.save(newUser));
    }

    @Transactional(readOnly = true)
    public UserResponse getUserById(UUID id) {
        return userMapper.toUserResponse(this.findUserById(id));
    }

    @Transactional
    public UserResponse updateUser(UUID id, UserRequest request) {
        User existingUser = this.findUserById(id);
        userMapper.updateUserFromRequest(request, existingUser);
        User updatedUser = userRepository.save(existingUser);
        notificationService.markSendingTimeNull(existingUser); // Выставляем время отправки уведомления == null
        return userMapper.toUserResponse(updatedUser);
    }

    @Transactional
    public void deleteUser(UUID id) {
        User existingUser = this.findUserById(id);
        userRepository.delete(existingUser);
    }

    @Transactional(readOnly = true)
    public List<UserResponse> getAllUsers() {
        // при необходимости сделать пагинацию
        return userMapper.toList(userRepository.findAll());
    }

    private User findUserById(UUID id) {
        return userRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("User not found"));
    }
}
