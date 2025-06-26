package com.bonbravo.store.repositories;

import com.bonbravo.store.models.Profile;
import org.springframework.data.repository.CrudRepository;

public interface ProfileRepository extends CrudRepository<Profile, Long> {
}
