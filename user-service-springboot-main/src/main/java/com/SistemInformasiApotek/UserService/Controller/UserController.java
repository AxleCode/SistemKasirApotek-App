package com.SistemInformasiApotek.UserService.Controller;

import com.SistemInformasiApotek.UserService.Handler.UserNotFoundException;
import com.SistemInformasiApotek.UserService.Service.AuthenticationService;
import com.SistemInformasiApotek.UserService.Service.UserService;
import com.SistemInformasiApotek.UserService.User.*;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.core.AuthenticationException;


import java.util.List;
import java.util.Optional;
import java.util.Map;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

    private final AuthenticationService service;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserService userService;
    private final ModelMapper modelMapper;

    @PostConstruct
    public void BuatAkunAdmin() {
        Optional<User> admin = userRepository.findByUsername("admin");
        if (admin.isPresent()) {
            System.out.println("admin already exists.");
        } else {
            String password = passwordEncoder.encode("admin123");
            User newUser = User.builder()
                    .username("admin")
                    .nik(1201190031)
                    .password(password)
                    .role(Role.ADMIN)
                    .build();
            userRepository.save(newUser);
            System.out.println("admin created.");
        }
    }
    @PostMapping("/register")
    public ResponseEntity<RegisterResponse> register(
            @RequestBody RegisterRequest registerRequest
    ){
        return ResponseEntity.ok(service.register(registerRequest));
    }
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {
        try {
            LoginResponse response = service.login(loginRequest);
            return ResponseEntity.ok(response);
        } catch (AuthenticationException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Login failed");
        }
    }
    @GetMapping("/tes-admin")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<String> sayHelloToAdmin() {
        return ResponseEntity.ok("Hello admin");
    }
    @GetMapping("/tes-cashier")
    @PreAuthorize("hasAuthority('CASHIER')")
    public ResponseEntity<String> sayHelloToCashier(){
        return ResponseEntity.ok("Hello cashier");
    }


    @GetMapping("/list")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<?> getAllUsersData() {
        try {
            List<UserDTO> users = userService.getAllUsersData();
            return ResponseEntity.ok(users);
        } catch (UserNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(e.getMessage());
        }
    }

    @PostMapping("/search")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<?> getUserByNik(@RequestBody Map<String, Integer> requestBody) {
        Integer nik = requestBody.get("nik");
        try {
            UserDTO userDTO = userService.getUserByNik(nik);
            return ResponseEntity.ok(userDTO);
        } catch (UserNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @GetMapping("/check/role/{role}")
    public ResponseEntity<Boolean> checkUserByRole(@PathVariable("role") String role) {
        boolean userExists = userRepository.existsByRole(role);
        return ResponseEntity.ok(userExists);
    }

    @PutMapping("/update/role")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<?> updateUserRoleByNik(@RequestBody Map<String, Object> requestBody) {
        Integer nik = (Integer) requestBody.get("nik");
        Role role = Role.valueOf((String) requestBody.get("role"));

        if (nik == null || role == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("NIK dan role harus ada dalam permintaan body.");
        }

        Optional<User> userOptional = userRepository.findByNik(nik);
        if (userOptional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User dengan NIK " + nik + " tidak ditemukan.");
        }

        User user = userOptional.get();
        user.setRole(role);
        userRepository.save(user);

        return ResponseEntity.ok(user);
    }

    @PostMapping("/ubahrole/{nik}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<?> ubahRole(@PathVariable("nik") Integer nik) {
        Optional<User> userOptional = userRepository.findByNik(nik);
        if (userOptional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User dengan NIK " + nik + " tidak ditemukan.");
        }
        User user = userOptional.get();
        user.setRole(Role.ADMIN);
        userRepository.save(user);
        return ResponseEntity.ok("Peran pengguna dengan NIK " + nik + " telah diubah menjadi Admin.");
    }

    @PutMapping("/update-role")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<?> updateRoleByNik(@RequestBody UserDTO updatedUserDto) {
        Integer nik = updatedUserDto.getNik();
        if (nik == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("NIK harus ada dalam request body.");
        }
        // Cek apakah pengguna dengan NIK tersebut ada
        Optional<User> userOptional = userRepository.findByNik(nik);
        if (userOptional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User dengan NIK " + nik + " tidak ditemukan.");
        }
        // Perbarui data pengguna
        User updatedUser = modelMapper.map(updatedUserDto, User.class);
        ResponseEntity<?> response = userService.updateRoleByNik(nik, updatedUser);
        return response;
    }

    @PutMapping("/update")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<?> updateUserByNik(@RequestBody UserDTO updatedUserDto) {
        Integer nik = updatedUserDto.getNik();
        if (nik == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("NIK harus ada dalam request body.");
        }
        // Cek apakah pengguna dengan NIK tersebut ada
        Optional<User> userOptional = userRepository.findByNik(nik);
        if (userOptional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User dengan NIK " + nik + " tidak ditemukan.");
        }
        // Perbarui data pengguna
        User updatedUser = modelMapper.map(updatedUserDto, User.class);
        ResponseEntity<?> response = userService.updateUserByNik(nik, updatedUser);
        return response;
    }

    @DeleteMapping("/delete")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<String> deleteUserByNik(@RequestBody Map<String, Integer> requestBody) {
        Integer nik = requestBody.get("nik");
        if (nik == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("NIK harus ada direquest body.");
        }
        try {
            userService.deleteUserByNik(nik);
            return ResponseEntity.ok("User dengan NIK " + nik + " berhasil dihapus.");
        } catch (UserNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

}
