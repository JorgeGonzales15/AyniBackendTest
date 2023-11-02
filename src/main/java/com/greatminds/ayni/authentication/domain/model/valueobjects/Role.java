package com.greatminds.ayni.authentication.domain.model.valueobjects;

import com.greatminds.ayni.authentication.domain.model.valueobjects.ERole;
import jakarta.persistence.*;
import lombok.Getter;

@Embeddable
public class Role {
    @Enumerated(EnumType.STRING)
    @Column(length = 20)
    @Getter
    private ERole role;

    public Role(String role) {
        if(role.equals("farmer")) this.role = ERole.ROLE_FARMER;
        else this.role = ERole.ROLE_MERCHANT;
    }

    public Role() {}
}
