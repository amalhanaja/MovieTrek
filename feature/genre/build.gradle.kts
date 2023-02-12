@Suppress("DSL_SCOPE_VIOLATION")
plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.kapt)
    alias(libs.plugins.hilt)
}

android {
    namespace = "com.amalhanaja.movietrek.genre"
    defaultConfig {
        consumerProguardFiles("consumer-rules.pro")
    }
    lint {
        checkDependencies = true
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = libs.versions.compose.compiler.get()
    }
}

dependencies {
    implementation(project(":core:designsystem"))
    implementation(project(":core:data"))
    implementation(libs.androidx.core)
    implementation(libs.coroutine.core)
    implementation(libs.hilt.android)
    implementation(libs.hilt.compose.nav)
    implementation(libs.lifecycle.viewmodel)
    implementation(libs.lifecycle.compose)
    kapt(libs.hilt.compiler)
    implementation(libs.accompanist.navigation.animation)

    testImplementation(libs.junit)
    testImplementation(libs.coroutine.test)
    testImplementation(libs.mockk)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(libs.compose.test.junit)
}