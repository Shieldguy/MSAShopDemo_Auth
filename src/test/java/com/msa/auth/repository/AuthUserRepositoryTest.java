package com.msa.auth.repository;

import com.msa.common.domain.AuthUser;
import com.msa.common.domain.UserRole;
import com.msa.common.domain.types.RoleType;
import lombok.extern.slf4j.Slf4j;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.Collection;

import static org.assertj.core.api.Assertions.assertThat;

@ActiveProfiles("test")
@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Slf4j
public class AuthUserRepositoryTest {
    @Autowired
    private TestEntityManager   entityManager;

    @Autowired
    private AuthUserRepository  authUserRepository;

    @Before
    public void setupTestData() {
        UserRole masterRole = UserRole.builder().name("Role1").roleType(RoleType.MASTER).build();
        UserRole userRole = UserRole.builder().name("Role2").roleType(RoleType.USER).build();

        masterRole = entityManager.persist(masterRole);
        entityManager.flush();
        userRole = entityManager.persist(userRole);
        entityManager.flush();

        Collection<UserRole> collection = new ArrayList<>();
        collection.add(masterRole);
        collection.add(userRole);

        AuthUser authUser = AuthUser.builder().username("testUser").password("111122223333").enabled(true)
                .roles(collection).build();
        entityManager.persist(authUser);
        entityManager.flush();
    }

    @Test
    public void whenFindByUsername() {
        // when
        AuthUser searchUser = authUserRepository.findByUsernameEquals("testUser");

        // then
        assertThat(searchUser.getUsername()).isEqualTo("testUser");
        assertThat(searchUser.getPassword()).isEqualTo("111122223333");
        assertThat(searchUser.isEnabled()).isTrue();
        assertThat(searchUser.getRoles()).containsExactlyInAnyOrder(
                UserRole.builder().name("Role1").roleType(RoleType.MASTER).build(),
                UserRole.builder().name("Role2").roleType(RoleType.USER).build());
    }
}
