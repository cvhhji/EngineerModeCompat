plugins {
    id("com.android.application")
}

android {
    namespace = "com.cvh.engineermodecompat"
    compileSdk = 36

    defaultConfig {
        applicationId = "com.cvh.engineermodecompat"
        minSdk = 26
        targetSdk = 36
        versionCode = 1
        versionName = "1.0.0"
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
