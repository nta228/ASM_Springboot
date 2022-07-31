package fpt.t2009m1.asm_springboot.service;

import fpt.t2009m1.asm_springboot.entity.Account;
import fpt.t2009m1.asm_springboot.entity.dto.AccountDto;
import fpt.t2009m1.asm_springboot.entity.dto.CredentialDto;
import fpt.t2009m1.asm_springboot.entity.myenum.AccountStatus;
import fpt.t2009m1.asm_springboot.repository.AccountRepository;
import fpt.t2009m1.asm_springboot.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class AccountService implements UserDetailsService {
    final AccountRepository accountRepository;

    final PasswordEncoder passwordEncoder;

    public Account register(AccountDto accountDto){
        Account account = Account.builder()
                .address(accountDto.getAddress())
                .detail(null)
                .email(accountDto.getEmail())
                .phone(accountDto.getPhone())
                .firstName(accountDto.getFirstName())
                .lastName(accountDto.getLastName())
                .password(passwordEncoder.encode(accountDto.getPassword()))
                .username(accountDto.getUsername())
                .status(AccountStatus.ACTIVE)
                .thumbnail(null)
                .role("USER")
                .build();
        return accountRepository.save(account);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Account account = accountRepository.findByUsername(username);
        if (account == null) {
            throw new UsernameNotFoundException("User not found in database");
        }
        Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
        if(account.getRole().equals("USER")){
            authorities.add(new SimpleGrantedAuthority("USER"));
        } else if (account.getRole().equals("ADMIN")) {
            authorities.add(new SimpleGrantedAuthority("ADMIN"));
        }
        return new User(account.getUsername(), account.getPassword(), authorities);
    }

    public boolean matchPassword(String rawPassword, String hashPassword){
        return passwordEncoder.matches(rawPassword, hashPassword);
    }

    public CredentialDto generateCredential(UserDetails userDetail, HttpServletRequest request) {
        String accessToken = JwtUtil.generateToken(userDetail.getUsername(),
                userDetail.getAuthorities().iterator().next().getAuthority(),
                request.getRequestURI(),
                JwtUtil.ONE_DAY * 7);

        String refreshToken = JwtUtil.generateToken(userDetail.getUsername(),
                userDetail.getAuthorities().iterator().next().getAuthority(),
                request.getRequestURI(),
                JwtUtil.ONE_DAY * 14);
        return new CredentialDto(accessToken, refreshToken);
    }

    public Page<Account> search(String search, int page, int limit){
        return accountRepository.findAllByFirstNameOrLastNameOrAddressOrEmailOrPhoneOrUsernameContains(search, PageRequest.of(page, limit));
    }

    public Page<Account> findByStatus(AccountStatus status, int page, int limit){
        return accountRepository.findAllByStatusEquals(status, PageRequest.of(page, limit));
    }

    public Page<Account> findByRole(String role, int page, int limit){
        return accountRepository.findAllByRoleEquals(role, PageRequest.of(page, limit));
    }

    public Optional<Account> findById(String id){
        return accountRepository.findById(id);
    }

    public Page<Account> findAll(int page, int limit){
        return accountRepository.findAll(PageRequest.of(page, limit));
    }
}
