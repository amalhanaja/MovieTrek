pluginManagement {
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    versionCatalogs {
        create("libs") {
            from(files("gradle/libs.version.toml"))
        }
        create("apps") {
            from(files("gradle/apps.version.toml"))
        }
    }
    repositories {
        google()
        mavenCentral()
        maven(url = "https://jitpack.io")
    }
}

rootProject.name = "MovieTrek"
include(":app")
include(":core:designsystem")
include(":feature:genre")
include(":core:tmdb")
include(":core:model")
include(":core:data")
