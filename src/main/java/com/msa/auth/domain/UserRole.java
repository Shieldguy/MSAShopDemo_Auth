package com.msa.auth.domain;

import com.msa.auth.domain.types.RoleType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserRole extends EditableDomainData<Integer> {
    @Column(nullable = false, length = 50, unique = true)
    private String      name;
    @Column(nullable = false)
    private RoleType roleType;

    public String getRoleName() { return roleType.name(); }
}
