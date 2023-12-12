package com.michaelrichards.movieloversapp.model

import com.google.gson.annotations.SerializedName

data class Short (
    val name: String,
    val description: String?,
    val image: String,
    val contentRating: String?,
    val genre: List<String>,
    val datePublished: String?,
    val actor: List<Crew>,
    val url: String,
    val keyWords: String,


    @SerializedName("@context")
    val context: String,

    val director: List<Crew>?,
    val creator: List<Crew>?,

    val trailer: Trailer?

    ) {
    override fun toString(): String {
        return "Short(\n" +
                "name='$name', \n" +
                "description='$description',\n" +
                "image='$image',\n" +
                "contentRating='$contentRating',\n" +
                "genre=$genre,\n" +
                "datePublished='$datePublished',\n," +
                "actor=$actor,\n" +
                "url='$url',\n" +
                "keyWords='$keyWords',\n" +
                "context='$context',\n" +
                "director=$director,\n" +
                "creator=$creator\n" +
                ")"
    }
}