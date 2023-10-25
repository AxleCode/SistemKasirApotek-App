package com.SistemInformasiApotek.UserService.Service;

import com.SistemInformasiApotek.UserService.Handler.UserNotFoundException;
import com.SistemInformasiApotek.UserService.User.*;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, ModelMapper modelMapper, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
        this.passwordEncoder = passwordEncoder;
    }

    public List<UserDTO> getAllUsersData() {
        List<User> users = userRepository.findAll();
        if (users.isEmpty()) {
            throw new UserNotFoundException("Tidak ada pengguna yang ditemukan");
        }
        return users.stream()
                .map(user -> modelMapper.map(user, UserDTO.class))
                .collect(Collectors.toList());
    }

    public ResponseEntity<?> updateRoleByNik(Integer nik, User updatedUser) {
        Optional<User> userOptional = userRepository.findByNik(nik);
        if (userOptional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User dengan NIK " + nik + " tidak ditemukan.");
        }
        User user = userOptional.get();
        user.setRole(updatedUser.getRole());
        userRepository.save(user);
        return ResponseEntity.ok(user);
    }
    public ResponseEntity<?> updateUserByNik(Integer nik, User updatedUser) {
        Optional<User> userOptional = userRepository.findByNik(nik);
        if (userOptional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User dengan NIK " + nik + " tidak ditemukan.");
        }
        User user = userOptional.get();
        user.setNik(updatedUser.getNik());
        user.setUsername(updatedUser.getUsername());
        String encodedPassword = passwordEncoder.encode(updatedUser.getPassword());
        user.setPassword(encodedPassword);
        user.setRole(updatedUser.getRole());
        userRepository.save(user);
        return ResponseEntity.ok(user);
 }
    public void deleteUserByNik(Integer nik) {
        Optional<User> userOptional = userRepository.findByNik(nik);
        if (userOptional.isEmpty()) {
            throw new UserNotFoundException("User dengan NIK " + nik + " tidak ditemukan.");
        }
        userRepository.delete(userOptional.get());
    }

    public UserDTO getUserByNik(Integer nik) {
        User user = userRepository.findByNik(nik).orElse(null);
        if (user != null) {
            return modelMapper.map(user, UserDTO.class);
        }
        throw new UserNotFoundException("User dengan NIK " + nik + " tidak ditemukan");
    }

}
