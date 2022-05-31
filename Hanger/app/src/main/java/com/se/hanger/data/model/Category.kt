package com.se.hanger.data.model

data class Category(
    var categoryWeather: Season,
    var categoryCloth: CategoryCloth
)

enum class Season {
    SPRING, SUMMER, FALL, WINTER
}

enum class CategoryCloth {
    TOP,
    OUTER,
    PANTS,
    ONE_PIECE,
    SKIRT,
    SHOES,
    UNDERWEAR,
    ACCESSORY
}

