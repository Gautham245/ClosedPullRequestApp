package com.example.closedpullrequestapp.data.remote.dto

data class Base(
    val label: String,
    val ref: String,
    val repo: Repo,
    val sha: String,
    val user: UserXX
)