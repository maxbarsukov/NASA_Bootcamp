package ru.myitschool.nasa_bootcamp.data.model.models

class Comment(val id: Long, val comment: String, val userId: String, val date: Long) {
    constructor() : this(-1, "", "", -1)

    override fun toString(): String {
        return "ID: $id\nComment: $comment;\nUserId: $userId\nDate: $date\n\n"
    }
}