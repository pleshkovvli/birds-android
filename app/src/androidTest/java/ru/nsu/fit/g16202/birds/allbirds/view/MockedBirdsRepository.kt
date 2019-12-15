package ru.nsu.fit.g16202.birds.allbirds.view

import ru.nsu.fit.g16202.birds.allbirds.repository.BirdsRepository
import ru.nsu.fit.g16202.birds.bird.entity.Bird

object MockedBirdsRepository : BirdsRepository {
    override val birds: List<Bird> = mutableListOf(
        Bird(
            "1",
            "Синица", "Птица-синица",
            "https://images.unsplash.com/photo-1548246782-3f8a36b6b035?ixlib=rb-1.2.1&ixid=eyJhcHBfaWQiOjEyMDd9&auto=format&fit=crop&w=1428&q=80",
            "https://www.xeno-canto.org/sounds/uploaded/YQNGFTBRRT/XC237160-BRTI_Chiricahuas_3May2013_Harter_1.mp3"
        ),
        Bird(
            "2",
            "Воробей", "Птица-воробей",
            "https://images.unsplash.com/photo-1550589677-a611b0e1bc8f?ixlib=rb-1.2.1&ixid=eyJhcHBfaWQiOjEyMDd9&auto=format&fit=crop&w=1350&q=80",
            "https://www.xeno-canto.org/sounds/uploaded/FWAVTDTPIQ/XC143001-Accipiter%20brevi%202-20000516.mp3"
        ),
        Bird(
            "3",
            "Снегирь", "Птица-снегирь",
            "https://cdn.pixabay.com/photo/2018/10/08/14/46/bird-3732867_960_720.jpg",
            "https://www.xeno-canto.org/sounds/uploaded/BLMSIUFTFU/XC509548-190420_0899_Afu_Gp.mp3"
        ),
        Bird(
            "4",
            "Соловей", "Птица-соловей",
            "https://cdn.pixabay.com/photo/2014/07/08/12/36/bird-386725_960_720.jpg",
            "https://www.xeno-canto.org/sounds/uploaded/BLMSIUFTFU/XC509553-190425_0907_Ser-ser.mp3"
        ),
        Bird(
            "5",
            "Сова", "Птица-сова",
            "https://cdn.pixabay.com/photo/2018/10/08/14/46/bird-3732867_960_720.jpg",
            "https://www.xeno-canto.org/sounds/uploaded/BLMSIUFTFU/XC509548-190420_0899_Afu_Gp.mp3"
        ),
        Bird(
            "6",
            "Журавль", "Птица-журавль",
            "https://images.unsplash.com/photo-1550589677-a611b0e1bc8f?ixlib=rb-1.2.1&ixid=eyJhcHBfaWQiOjEyMDd9&auto=format&fit=crop&w=1350&q=80",
            "https://www.xeno-canto.org/sounds/uploaded/FWAVTDTPIQ/XC143001-Accipiter%20brevi%202-20000516.mp3"
        ),
        Bird(
            "7",
            "Голубь", "Птица-голубь",
            "https://cdn.pixabay.com/photo/2018/10/08/14/46/bird-3732867_960_720.jpg",
            "https://www.xeno-canto.org/sounds/uploaded/BLMSIUFTFU/XC509548-190420_0899_Afu_Gp.mp3"
        ),

        Bird(
            "8",
            "Павлин", "Птица-павлин",
            "https://images.unsplash.com/photo-1550589677-a611b0e1bc8f?ixlib=rb-1.2.1&ixid=eyJhcHBfaWQiOjEyMDd9&auto=format&fit=crop&w=1350&q=80",
            "https://www.xeno-canto.org/sounds/uploaded/FWAVTDTPIQ/XC143001-Accipiter%20brevi%202-20000516.mp3"
        ),

        Bird(
            "9",
            "Курица", "Птица-курица",
            "https://cdn.pixabay.com/photo/2018/10/08/14/46/bird-3732867_960_720.jpg",
            "https://www.xeno-canto.org/sounds/uploaded/BLMSIUFTFU/XC509548-190420_0899_Afu_Gp.mp3"
        ),

        Bird(
            "10",
            "Утка", "Птица-утка",
            "https://images.unsplash.com/photo-1548246782-3f8a36b6b035?ixlib=rb-1.2.1&ixid=eyJhcHBfaWQiOjEyMDd9&auto=format&fit=crop&w=1428&q=80",
            "https://www.xeno-canto.org/sounds/uploaded/YQNGFTBRRT/XC237160-BRTI_Chiricahuas_3May2013_Harter_1.mp3"
        )
    )
}