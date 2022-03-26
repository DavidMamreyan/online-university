package ru.mamreyan.onlineuniversity.group;

public class GroupNotFoundException extends RuntimeException {
    public GroupNotFoundException(Long id) {
        super("Could not find group " + id);
    }
}