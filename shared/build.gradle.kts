import org.jetbrains.compose.desktop.application.dsl.TargetFormat

plugins {
    kotlin("multiplatform")
    id("com.android.library")
    id("org.jetbrains.compose")
}

kotlin {
    android {
        compilations.all {
            kotlinOptions {
                jvmTarget = "1.8"
            }
        }
    }

    jvm("desktop")

    listOf(
        iosX64(),
        iosArm64(),
        iosSimulatorArm64()
    ).forEach {
        it.binaries.framework {
            baseName = "shared"
            isStatic = true
        }
    }

    sourceSets {
        val desktopMain by getting

        val commonMain by getting {
            dependencies {
                // compose
                implementation(compose.runtime)
                implementation(compose.foundation)
                implementation(compose.material)
                @OptIn(org.jetbrains.compose.ExperimentalComposeLibrary::class)
                implementation(compose.components.resources)

                // math
                implementation(libs.kotlin.math)

                // MVI
                implementation(libs.arkivanov.mvikotlin)
                implementation(libs.arkivanov.mvikotlin.main)
                implementation(libs.arkivanov.mvikotlin.extensions.coroutines)
            }
        }

        val androidMain by getting
        val iosX64Main by getting
        val iosArm64Main by getting
        val iosSimulatorArm64Main by getting
        val iosMain by creating {
            dependsOn(commonMain)
            iosX64Main.dependsOn(this)
            iosArm64Main.dependsOn(this)
            iosSimulatorArm64Main.dependsOn(this)
        }
        desktopMain.dependencies {
            implementation(compose.desktop.currentOs)

            implementation(libs.coroutines.core)

            // MVI
            implementation(libs.arkivanov.mvikotlin)
            implementation(libs.arkivanov.mvikotlin.main)
        }
    }
}

android {
    namespace = libs.versions.namespace.get()
    compileSdk = 34
    defaultConfig {
        minSdk = 26
        targetSdk = 33
    }
}

compose.desktop {
    application {
        mainClass = libs.versions.namespace.get() + ".MainKt"

        nativeDistributions {
            targetFormats(TargetFormat.Dmg, TargetFormat.Msi, TargetFormat.Deb)
            packageName = libs.versions.namespace.get()
            packageVersion = "1.0.0"
        }
    }
}

