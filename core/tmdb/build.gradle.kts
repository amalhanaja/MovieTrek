plugins {
    id("kotlin")
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