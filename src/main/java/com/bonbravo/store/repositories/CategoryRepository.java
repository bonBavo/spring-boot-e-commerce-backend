package com.bonbravo.store.repositories;

import com.bonbravo.store.models.Category;
import org.springframework.data.repository.CrudRepository;

public interface CategoryRepository extends CrudRepository<Category, Long> {
}
