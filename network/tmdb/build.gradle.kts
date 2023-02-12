plugins {
    id(libs.plugins.java.library.get().pluginId)
    alias(libs.plugins.kotlin.jvm)
}

dependencies {
    api(libs.retrofit.core)
    api(libs.retrofit.gson)
    api(libs.gson)
    api(libs.okhttp.logging)
    api(libs.coroutine.core)
    testImplementation(libs.okhttp.mock)
    testImplementation(libs.junit)
    testImplementation(libs.coroutine.test)
    testImplementation(libs.mockk)
}