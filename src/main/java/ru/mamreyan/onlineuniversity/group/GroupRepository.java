package ru.mamreyan.onlineuniversity.group;

import org.springframework.data.repository.CrudRepository;

public interface GroupRepository extends CrudRepository<Group, Long> {
    Iterable<Group> findByName(String name);
}