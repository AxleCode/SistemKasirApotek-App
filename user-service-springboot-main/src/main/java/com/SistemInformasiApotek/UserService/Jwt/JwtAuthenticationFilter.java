package com.SistemInformasiApotek.UserService.Jwt;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import java.io.IOException;

@Component
@RequiredArgsConstructor
// OncePerRequestFilter adalah kelas abstract pada framework Spring Security kelas ini berfungsi sebagai filter untuk setiap permintaan masuk ke aplikasi web
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    private final JwtService jwtService;
    private final UserDetailsService userDetailsService;
    @Override
    protected void doFilterInternal(
            HttpServletRequest request,//Parameter ini adalah objek yang mewakili permintaan HTTP
            HttpServletResponse response,//Parameter ini adalah objek yang mewakili tanggapan HTTP yang akan dikirim kembali ke klien
            FilterChain filterChain//Parameter ini adalah objek yang mewakili rantai filter dalam aplikasi.
    ) throws ServletException, IOException {
        // Mendapatkan nilai header Authorization dari permintaan
        final String authHeader = request.getHeader("Authorization");
        final String jwt;
        final String userUsername;

        // Memeriksa apakah header Authorization ada dan memiliki format yang benar (Bearer token)
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            // Jika tidak ada atau tidak sesuai format, lanjutkan ke filter berikutnya
            filterChain.doFilter(request, response);
            return;
        }

        // Jika header Authorization sesuai format, ambil token JWT-nya
        jwt = authHeader.substring(7);
        // Ekstrak username pengguna dari token JWT menggunakan JwtService
        userUsername = jwtService.extractUsername(jwt); //todo extract the userUsername from JWT token;
        if (userUsername != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            UserDetails userDetails = this.userDetailsService.loadUserByUsername(userUsername);
            // Periksa apakah token JWT valid untuk UserDetails yang ditemukan
            if (jwtService.isTokenValid(jwt, userDetails)) {
                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                        userDetails,
                        null,
                        userDetails.getAuthorities()
                );
                // Set detail autentikasi berdasarkan informasi permintaan saat ini
                authToken.setDetails(
                        new WebAuthenticationDetailsSource().buildDetails(request)
                );
                // Setel autentikasi yang berhasil dalam SecurityContextHolder
                SecurityContextHolder.getContext().setAuthentication(authToken);
            }
        }
        // Lanjutkan ke filter berikutnya dalam rantai filter
        filterChain.doFilter(request, response);
    }
}

