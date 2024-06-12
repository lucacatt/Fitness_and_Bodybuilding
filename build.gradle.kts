// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    alias(libs.plugins.androidApplication) apply false
    alias(libs.plugins.jetbrainsKotlinAndroid) apply false
    id("com.google.gms.google-services") version "4.4.2" apply false
}

buildscript {
    repositories {
        google()
        mavenCentral()
    }
    dependencies {
        classpath("com.android.tools.build:gradle:8.4.0") // Verifica la versione più recente
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:1.6.21") // Verifica la versione più recente
        classpath("androidx.navigation:navigation-safe-args-gradle-plugin:2.5.0") // Aggiorna questa riga alla versione 2.5.0 o superiore
    }
}

allprojects {
    repositories {

    }
}

tasks.register<Delete>("clean") {
    delete(rootProject.buildDir)
}
