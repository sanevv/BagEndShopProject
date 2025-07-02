package com.github.semiprojectshop.repository.sihu.social;

import com.github.semiprojectshop.config.module.converter.OAuthProviderConverter;
import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Embeddable;
import lombok.*;

import java.io.Serializable;

@EqualsAndHashCode(of = { "socialId", "socialProvider" })
@NoArgsConstructor
@Getter
@AllArgsConstructor(staticName = "of")
@Embeddable
public class SocialIdPk implements Serializable {

    @Column(unique = true, nullable = false)
    private String socialId;

    @Convert(converter = OAuthProviderConverter.class)
    private OAuthProvider socialProvider;

}
