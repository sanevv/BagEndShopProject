package com.github.semiprojectshop.repository.sihu.social;

import org.springframework.data.jpa.repository.JpaRepository;

public interface SocialIdsJpa extends JpaRepository<SocialId, SocialIdPk>, SocialIdsJpaCustom {
}
