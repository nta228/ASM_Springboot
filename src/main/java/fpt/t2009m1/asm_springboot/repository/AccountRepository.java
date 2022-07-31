package fpt.t2009m1.asm_springboot.repository;

import fpt.t2009m1.asm_springboot.entity.Account;
import fpt.t2009m1.asm_springboot.entity.myenum.AccountStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;

@Repository
public interface AccountRepository extends JpaRepository<Account, String> {
    Account findByUsername(String username);

    Page<Account> findAllByFirstNameOrLastNameOrAddressOrEmailOrPhoneOrUsernameContains(String search, Pageable pageable);

    Page<Account> findAllByStatusEquals(AccountStatus status, Pageable pageable);

    Page<Account> findAllByRoleEquals(String role, Pageable pageable);

    Page<Account> findAll(Pageable pageable);
}
