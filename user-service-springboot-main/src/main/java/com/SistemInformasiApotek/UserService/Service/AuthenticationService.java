package com.SistemInformasiApotek.UserService.Service;

import com.SistemInformasiApotek.UserService.Controller.RegisterResponse;
import com.SistemInformasiApotek.UserService.Controller.LoginRequest;
import com.SistemInformasiApotek.UserService.Controller.LoginResponse;
import com.SistemInformasiApotek.UserService.Controller.RegisterRequest;
import com.SistemInformasiApotek.UserService.Jwt.JwtService;
import com.SistemInformasiApotek.UserService.User.Role;
import com.SistemInformasiApotek.UserService.User.User;
import com.SistemInformasiApotek.UserService.User.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    @Autowired
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    public RegisterResponse register(RegisterRequest registerRequest) {
        // Membangun objek User menggunakan builder pattern dan data dari RegisterRequest
        var user = User.builder()
                .nik(registerRequest.getNik())
                .username(registerRequest.getUsername())
                .password(passwordEncoder.encode(registerRequest.getPassword()))
                .role(Role.CASHIER)
                .build();
        userRepository.save(user);
        String message = "Username " + user.getUsername() + " berhasil registrasi.";
        return RegisterResponse.builder()
                .message(message)
                .build();
    }

    public LoginResponse login(LoginRequest loginRequest) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getUsername(),
                        loginRequest.getPassword()
                )
        );
        // Mencari pengguna berdasarkan username dari UserRepository dan melemparkan exception jika tidak ditemukan
        var user = userRepository.findByUsername(loginRequest.getUsername())
                .orElseThrow();
        // Menghasilkan token JWT menggunakan jwtService untuk pengguna yang berhasil login
        var jwtToken = jwtService.generateToken(user);
        // Mengembalikan objek LoginResponse yang berisi token JWT
        return LoginResponse
                .builder()
                .token(jwtToken)
                .build();
    }
}
