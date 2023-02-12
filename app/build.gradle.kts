@Suppress("DSL_SCOPE_VIOLATION")
plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.kapt)
    alias(libs.plugins.hilt)
}

fun generateVersionCode(minSdk: Int, major: Int, minor: Int, patch: Int): Int {
    return (minSdk * 10_000_000) + (major * 10_000) + (minor * 100) + patch
}

fun generateVersionName(major: Int, minor: Int, patch: Int, versionIdentifier: String? = null, buildNumber: Long? = null): String {
    val builder = StringBuilder("$major.$minor.$patch")
    versionIdentifier?.let { builder.append("-$it") }
    buildNumber?.let { builder.append("+$it") }
    return builder.toString()
}

android {
    namespace = "com.amalhanaja.movietrek"
    compileSdk = libs.versions.compileSdk.get().toInt()

    defaultConfig {
        val major = apps.versions.major.get().toInt()
        val minor = apps.versions.minor.get().toInt()
        val patch = apps.versions.patch.get().toInt()
        applicationId = "com.amalhanaja.movietrek"
        versionCode = generateVersionCode(minSdk!!, major, minor, patch)
        versionName = generateVersionName(
            major = major,
            minor = minor,
            patch = patch,
            buildNumber = TimeUnit.MILLISECONDS.toMinutes(System.currentTimeMillis())
        )

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
    }

    buildTypes {
        getByName("release") {
            isMinifyEnabled = true
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
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
    implementation(project(":feature:genre"))
    implementation(project(":feature:discovermovie"))
    implementation(libs.androidx.core)
    implementation(libs.compose.activity)
    implementation(libs.compose.ui)
    implementation(libs.compose.preview)
    implementation(libs.compose.material3)
    implementation(libs.accompanist.navigation.animation)
    implementation(libs.hilt.android)
    kapt(libs.hilt.compiler)

    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(libs.compose.test.junit)
    debugImplementation(libs.compose.tooling)
    debugImplementation(libs.compose.test.manifest)
}