@Suppress("DSL_SCOPE_VIOLATION")
plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.kapt)
    alias(libs.plugins.hilt)
}

android {
    namespace = "com.amalhanaja.movietrek.core.data"
    defaultConfig {
        buildConfigField("String", "API_KEY", "\"99412b15adcfe59f3effe464bcca15b4\"")
    }
    lint {
        checkDependencies = true
    }
}

dependencies {
    api(project(":core:tmdb"))
    api(project(":core:model"))
    implementation(libs.hilt.android)
    kapt(libs.hilt.compiler)
    implementation(libs.paging.runtime)

    testImplementation(libs.paging.common)
    testImplementation(libs.junit)
    testImplementation(libs.mockk)
    testImplementation(libs.coroutine.test)
}