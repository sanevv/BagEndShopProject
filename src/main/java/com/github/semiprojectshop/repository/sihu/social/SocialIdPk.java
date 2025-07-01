package com.github.semiprojectshop.repository.sihu.social;

import com.github.semiprojectshop.config.module.converter.OAuthProviderConverter;
import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@EqualsAndHashCode(of = { "socialId", "socialProvider" })
@NoArgsConstructor
@Getter
@AllArgsConstructor
@Embeddable
public class SocialIdPk implements Serializable {

    @Column(unique = true, nullable = false)
    private String socialId;

    @Convert(converter = OAuthProviderConverter.class)
    private OAuthProvider socialProvider;

}
