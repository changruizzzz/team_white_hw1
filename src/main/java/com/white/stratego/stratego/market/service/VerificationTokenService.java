package com.white.stratego.stratego.market.service;

import com.white.stratego.stratego.market.model.User;
import com.white.stratego.stratego.market.model.VerificationToken;
import com.white.stratego.stratego.market.repository.UserRepository;
import com.white.stratego.stratego.market.repository.VerificationTokenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class VerificationTokenService {
    private UserRepository userRepository;
    private VerificationTokenRepository verificationTokenRepository;
    private SendingMailService sendingMailService;

    @Autowired
    public VerificationTokenService(UserRepository userRepository, VerificationTokenRepository verificationTokenRepository, SendingMailService sendingMailService){
        this.userRepository = userRepository;
        this.verificationTokenRepository = verificationTokenRepository;
        this.sendingMailService = sendingMailService;
    }

    public void createVerification(String email){
        User user = userRepository.findByEmail(email);
        if (user == null) {
            user = new User();
            user.setEmail(email);
            userRepository.save(user);
        }

        List<VerificationToken> verificationTokens = verificationTokenRepository.findByUserEmail(email);
        VerificationToken verificationToken;
        if (verificationTokens.isEmpty()) {
            verificationToken = new VerificationToken();
            verificationToken.setUser(user);
            verificationTokenRepository.save(verificationToken);
        } else {
            verificationToken = verificationTokens.get(0);
        }

        sendingMailService.sendVerificationMail(email, verificationToken.getToken());
    }

    public ResponseEntity<String> verifyEmail(String token){
        List<VerificationToken> verificationTokens = verificationTokenRepository.findByToken(token);
        if (verificationTokens.isEmpty()) {
            return ResponseEntity.badRequest().body("Invalid token.");
        }

        VerificationToken verificationToken = verificationTokens.get(0);
        if (verificationToken.getExpiredDateTime().isBefore(LocalDateTime.now())) {
            return ResponseEntity.unprocessableEntity().body("Expired token.");
        }

        verificationToken.setConfirmedDateTime(LocalDateTime.now());
        verificationToken.setStatus(VerificationToken.STATUS_VERIFIED);
        User user = verificationToken.getUser();
        user.setIsActive(true);
        userRepository.save(user);
        verificationTokenRepository.save(verificationToken);

        return ResponseEntity.ok("You have successfully verified your email address.");
    }
}