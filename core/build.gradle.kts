plugins {
  id("com.android.application")
}

android {
  namespace = "rpengine.core.gles32core"
  compileSdk = 33

  defaultConfig {
    applicationId = "rpengine.core.gles32core"
    minSdk = 28
    targetSdk = 33
    versionCode = 1
    versionName = "1.0"
    ndkVersion = "26.1.10909125"

    vectorDrawables {
      useSupportLibrary = true
    }
    externalNativeBuild {
      cmake {
        abiFilters("arm64-v8a", "armeabi-v7a")
      }
    }
  }

  signingConfigs {
    create("release") {
      storeFile = file("release.key")

      if (rootProject.file("secrets.gradle.kts").exists()) {
        apply(from = rootProject.file("secrets.gradle.kts"))
      } else {
        storePassword = System.getenv("STORE_PASSWORD") ?: "default_store_password"
        keyAlias = System.getenv("KEY_ALIAS") ?: "default_key_alias"
        keyPassword = System.getenv("KEY_PASSWORD") ?: "default_key_password"
      }
    }
  }

  externalNativeBuild {
    cmake {
      path(file("src/main/cpp/CMakeLists.txt"))
    }
  }

  compileOptions {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
  }

  buildTypes {
    getByName("release") {
      isMinifyEnabled = true
      proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"),"proguard-rules.pro")
			signingConfig=signingConfigs.getByName("release")
			isDebuggable=false
    }
  }

  buildFeatures {
    viewBinding = false
    prefab = true
  }
}