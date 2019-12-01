package ru.nsu.fit.g16202.birds.dummy

import ru.nsu.fit.g16202.birds.model.Bird

object DummyBirds {
    val ITEMS: MutableList<Bird> = mutableListOf(
        Bird("Синица", "Птица-синица"),
        Bird("Воробей", "Птица-воробей"),
        Bird("Снегирь", "Птица-снегирь"),
        Bird("Соловей", "Птица-соловей"),
        Bird("Сова", "Птица-сова"),
        Bird("Журавль", "Птица-журавль"),
        Bird("Голубь", "Птица-голубь")
    )
}