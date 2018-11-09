package com.example.springresttwitterable.repository;

import com.example.springresttwitterable.entity.Message;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;

public interface MessageRepository extends CrudRepository<Message, Long> {

    Page<Message> findAll(Pageable pageable);
    Page<Message> findByTag(String tag, Pageable pageable);

}
