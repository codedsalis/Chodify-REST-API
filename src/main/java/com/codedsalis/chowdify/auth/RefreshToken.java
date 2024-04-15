package com.codedsalis.chowdify.auth;

import java.time.Instant;
import java.util.UUID;

import com.codedsalis.chowdify.shared.BaseEntity;
import com.codedsalis.chowdify.user.User;

import io.micrometer.common.lang.NonNull;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class RefreshToken extends BaseEntity {
    @OneToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id", nullable = false)
    public User user;

    @NonNull
    public UUID token;

    @NonNull
    public Instant expirationTime;
}
