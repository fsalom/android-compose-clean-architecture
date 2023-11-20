pluginManagement {
    repositories {
        mavenCentral()
        gradlePluginPortal()
        google()
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()    // no need to declare this in `allProjects` if its declared here
        mavenCentral()
    }
}
rootProject.name = "learnWithMe"
include(":app")
 