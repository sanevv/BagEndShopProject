package com.github.semiprojectshop.config.module.converter;

import com.github.semiprojectshop.repository.sihu.social.OAuthProvider;
import org.springframework.stereotype.Component;

@Component
public class OAuthProviderConverter extends MyConverter<OAuthProvider> {
    public OAuthProviderConverter() {
        super(OAuthProvider.class);
    }
}
