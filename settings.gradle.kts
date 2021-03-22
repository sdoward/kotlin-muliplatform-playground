pluginManagement {
    repositories {
        google()
        jcenter()
        gradlePluginPortal()
        mavenCentral()
    }
    
}
rootProject.name = "Translations"

enableFeaturePreview("GRADLE_METADATA")
include(":androidApp")
include(":translations")

