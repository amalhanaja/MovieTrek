## Compose playground: MovieTrek

Compose playground for learning purpose

* UI completely in [Jetpack Compose](https://developer.android.com/jetpack/compose)
* Uses [Kotlin Coroutines](https://kotlinlang.org/docs/reference/coroutines/coroutines-guide.html)
* Uses [Kotlin Flow](https://kotlinlang.org/docs/flow.html)
* Uses many of
  the [Architecture Components](https://developer.android.com/topic/libraries/architecture/)
* Uses [Hilt](https://dagger.dev/hilt/) for dependency injection
*
Uses [Java 8+ API desugaring support](https://developer.android.com/studio/write/java8-support#library-desugaring)
for date and time usage

## Prerequisites

* Android Studio Electric Eel | 2022.1.1 Patch 1
* Min SDK 26
* Target SDK 33
* AGP 7.4.1
* Java 11
* Kotlin 1.8.10

## How to build

* Generate debug apk `./gradlew assembleDebug`
* Run unit test `./gradlew testDebug`
* Install on connected device `./gradlew installDebug`