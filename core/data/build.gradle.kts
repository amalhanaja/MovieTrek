plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
}

android {
    namespace = "com.amalhanaja.movietrek.core.data"
    lint {
        checkDependencies = true
    }
}

dependencies {
//    implementation(project(":network:tmdb"))
}