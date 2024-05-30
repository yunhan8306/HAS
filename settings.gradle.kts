pluginManagement {
    includeBuild("build-logic")
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
        maven("https://jitpack.io")
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
        maven("https://jitpack.io")
    }
}

rootProject.name = "myStash"
include(":app")
include(":core:design-system")
include(":feature:navigation")
include(":core:data-base")
include(":core:di")
include(":core:data")
include(":core:model")
include(":feature:gallery")
include(":feature:item")
include(":feature:essential")
include(":common:compose")
include(":common:util")
include(":feature:gender")
include(":feature:splash")
include(":core:data-store")
