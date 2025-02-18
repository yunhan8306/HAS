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

rootProject.name = "has"
include(":app")
include(":core:design-system")
include(":feature:navigation")
include(":core:model")
include(":feature:gallery")
include(":feature:item")
include(":feature:essential")
include(":common:compose")
include(":common:util")
include(":feature:gender")
include(":feature:splash")
include(":feature:mypage")
include(":feature:search")
include(":feature:feed")
include(":common:resource")
include(":feature:style")
include(":feature:webview")
include(":feature:contact")
include(":feature:manage")
include(":domain")
include(":data")
include(":di")
