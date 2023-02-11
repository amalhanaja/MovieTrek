plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
}

android {
    namespace = "com.amalhanaja.movietrek.core.designsystem"
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
    implementation(libs.androidx.core)
    api(libs.compose.ui)
    api(libs.compose.preview)
    api(libs.compose.material3)
    api(libs.compose.material.icons)
    debugApi(libs.compose.tooling)
    debugApi(libs.compose.test.manifest)

    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(libs.compose.test.junit)
}