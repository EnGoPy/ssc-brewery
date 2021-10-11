package guru.sfg.brewery.web.controllers;

import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.LdapShaPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.password.StandardPasswordEncoder;
import org.springframework.util.DigestUtils;

import java.nio.charset.StandardCharsets;

import static org.junit.jupiter.api.Assertions.assertTrue;


public class PasswordEncodingTest {

    static final String PASSWORD = "password";

    @Test
    void bcryptTest(){
        PasswordEncoder bcrypt = new BCryptPasswordEncoder();

        System.out.println(bcrypt.encode(PASSWORD));
        System.out.println(bcrypt.encode(PASSWORD));
    }

    @Test
    void testSha256(){
        PasswordEncoder sha256 = new StandardPasswordEncoder();
        System.out.println(sha256.encode(PASSWORD));
        System.out.println(sha256.encode(PASSWORD));
    }

    @Test
    void testLDAP(){
        PasswordEncoder ldap = new LdapShaPasswordEncoder();
        System.out.println(ldap.encode(PASSWORD));
        System.out.println(ldap.encode(PASSWORD));

        String encodedPass = ldap.encode(PASSWORD);

        assertTrue(ldap.matches(PASSWORD, encodedPass));
    }

    @Test
    void hashingExample(){
        System.out.println(DigestUtils.md5DigestAsHex(PASSWORD.getBytes(StandardCharsets.UTF_8)));
        System.out.println(DigestUtils.md5DigestAsHex(PASSWORD.getBytes(StandardCharsets.UTF_8)));

        String salted = PASSWORD + "ThisIsMySaltValue";

        System.out.println(DigestUtils.md5DigestAsHex(salted  .getBytes(StandardCharsets.UTF_8)));
    }
}
