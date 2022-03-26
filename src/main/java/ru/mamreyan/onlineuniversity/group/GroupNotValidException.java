package ru.mamreyan.onlineuniversity.group;

class GroupNotValidException extends RuntimeException {
    public GroupNotValidException(Group group) {
        super("Group is not valid: " + group.toString());
    }
}