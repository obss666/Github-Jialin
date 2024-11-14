package com.example.github_jialin.data.model

data class UserResponse(
    val status: String?,
    val avatar_url: String?,
    val bio: String?,
    val blog: String?,
    val company: String?,
    val email: String?,
    val followers: Int?,
    val followers_url: String?,
    val following: Int?,
    val following_url: String?,
    val id: Int?,
    val location: String?,
    val login: String?,
    val name: String?,
    val twitter_username: String?,
    val url: String?,
) {
    fun toUserInfo(): UserInfo {
        return UserInfo(login?:"",
            name?:"unknown",
            bio?:"unknown",
            company?:"unknown",
            location?:"unknown",
            blog?.takeIf { it.isNotEmpty() }?: "unknown",
            email?:"unknown",
            followers?:0,
            following?:0,
            id?:0,
            avatar_url?:""
        )
    }
}

data class UserInfo(val login: String, val name: String, val bio: String, val company: String,
                    val location: String, val blog: String, val email: String,
                    val followers: Int = 0, val following: Int = 0, val id: Int, val avatarUrl:String)