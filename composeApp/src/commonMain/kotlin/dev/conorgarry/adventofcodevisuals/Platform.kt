package dev.conorgarry.adventofcodevisuals

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform