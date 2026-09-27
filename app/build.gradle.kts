plugins {
    id("com.android.application")
}

val fixedSigningStoreFile = providers.gradleProperty("engineerModeSigningStoreFile").orNull
val fixedSigningStorePassword = providers.gradleProperty("engineerModeSigningStorePassword").orNull
val fixedSigningKeyAlias = providers.gradleProperty("engineerModeSigningKeyAlias").orNull
val fixedSigningKeyPassword = providers.gradleProperty("engineerModeSigningKeyPassword").orNull
val assembleRequested = gradle.startParameter.taskNames.any {
    it == "assemble" || it.startsWith("assemble") || it.startsWith(":app:assemble")
}
val missingSigningProperties = buildList {
    if (fixedSigningStoreFile.isNullOrBlank()) add("engineerModeSigningStoreFile")
    if (fixedSigningStorePassword.isNullOrBlank()) add("engineerModeSigningStorePassword")
    if (fixedSigningKeyAlias.isNullOrBlank()) add("engineerModeSigningKeyAlias")
    if (fixedSigningKeyPassword.isNullOrBlank()) add("engineerModeSigningKeyPassword")
}
if (assembleRequested && missingSigningProperties.isNotEmpty()) {
    throw GradleException("Stable APK signing is required. Add these properties to the user Gradle properties file: ${missingSigningProperties.joinToString()}")
}

android {
    namespace = "com.engineermode.cvh"
    compileSdk = 36

    defaultConfig {
        applicationId = "com.engineermode.cvh"
        minSdk = 26
        targetSdk = 36
        versionCode = 2
        versionName = "1.1.0"
    }

    signingConfigs {
        create("stable") {
            if (!fixedSigningStoreFile.isNullOrBlank()) storeFile = file(fixedSigningStoreFile)
            storePassword = fixedSigningStorePassword
            keyAlias = fixedSigningKeyAlias
            keyPassword = fixedSigningKeyPassword
        }
    }

    buildTypes {
        getByName("debug") {
            signingConfig = signingConfigs.getByName("stable")
        }
        getByName("release") {
            signingConfig = signingConfigs.getByName("stable")
            isMinifyEnabled = false
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }

    packaging {
        resources {
            merges += "META-INF/xposed/*"
        }
    }
}

dependencies {
    compileOnly("io.github.libxposed:api:102.0.0")
}
