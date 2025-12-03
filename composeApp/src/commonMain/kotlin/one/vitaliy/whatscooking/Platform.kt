package one.vitaliy.whatscooking

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform
