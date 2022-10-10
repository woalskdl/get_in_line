package com.jay.getinline.repository;

import com.jay.getinline.domain.Event;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.repository.Repository;

import java.util.List;
import java.util.Optional;

@NoRepositoryBean
public interface EventReadOnlyRepository<T, ID> extends Repository<T, ID> {

    // 기본적으로 Repository 를 extend 할 경우 CRUD 기능이 전체가 추가가 되어버린다.

    // from CrudRepository
    Optional<T> findById(ID id);
    List<T> findAll();
    Iterable<T> findAllById(Iterable<ID> ids);

    // from PagingAndSortingRepository
    List<T> findAll(Sort sort);
    Page<T> findAll(Pageable pageable);
}
