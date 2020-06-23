package com.example.kiosk_employer
import com.google.firebase.Timestamp
import java.time.LocalDateTime

data class Store(
    val name: String? = null,
    val menuList: List<Menu>? = null,
    var table: List<Table>? = null,
    val location: String? = null
)

data class Menu(
    val name: String? = null,
    val price: Int? = null,
    val image: String? = null,
    val menuAbout: String? = null,
    val menuRatings: List<MenuRating>? = null
)

data class MenuRating(
    val rating: Int? = null,
    val review: String? = null
)

data class Table(
    val num: Int? = null,
    var availability: Boolean? = null
)

data class Order(
    val name: String? = null,
    val table: Int? = null,
    val menu: List<Pair<String, Int>>? = null,
    val timestamp: Timestamp
)
/*
data class Type(
    val first: String? = null,
    val second: Int? = null
)*/