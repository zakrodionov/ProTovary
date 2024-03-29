import java.io.FileInputStream
import java.util.*

plugins {
    id("com.android.application")
    id("kotlin-android")
    id("kotlin-android-extensions")
    id("kotlin-kapt")
    id("com.google.firebase.crashlytics")
    id("androidx.navigation.safeargs.kotlin")
    id("org.jlleitschuh.gradle.ktlint")
}

android {
    compileSdkVersion(AndroidConfig.COMPILE_SDK_VERSION)
    buildToolsVersion(AndroidConfig.BUILD_TOOLS_VERSION)

    defaultConfig {
        applicationId = AndroidConfig.APPLICATION_ID
        minSdkVersion(AndroidConfig.MIN_SDK_VERSION)
        targetSdkVersion(AndroidConfig.TARGET_SDK_VERSION)
        versionCode = AndroidConfig.VERSION_CODE
        versionName = AndroidConfig.VERSION_NAME
        multiDexEnabled = AndroidConfig.MULTI_DEX_ENABLED
        vectorDrawables.useSupportLibrary = AndroidConfig.SUPPORT_LIBRARY_VECTOR_DRAWABLES
        testInstrumentationRunner = AndroidConfig.TEST_INSTRUMENTATION_RUNNER

        setProperty("archivesBaseName", AndroidConfig.APK_NAME)
        flavorDimensions("default")

        androidExtensions {
            isExperimental = true
        }

        resConfigs("ru")
    }

    signingConfigs {
        register("release") {
            val keystorePropertiesFile = rootProject.file("keystore.properties")
            val keystoreProperties =
                Properties().apply { load(FileInputStream(keystorePropertiesFile)) }

            storeFile = rootProject.file(keystoreProperties["storeFile"] as String)
            storePassword = keystoreProperties["storePassword"] as String
            keyPassword = keystoreProperties["keyPassword"] as String
            keyAlias = keystoreProperties["keyAlias"] as String

            isV1SigningEnabled = true
            isV2SigningEnabled = true
        }
    }

    buildTypes {
        getByName("release") {
            isDebuggable = false
            isMinifyEnabled = true
            isShrinkResources = true
            signingConfig = signingConfigs["release"]

            proguardFiles(getDefaultProguardFile("proguard-android.txt"), "proguard-rules.pro")
            buildConfigField("String", "API_ENDPOINT", AndroidConfig.API_PROD_URL)
            buildConfigField("String", "API_IMAGE_URL", AndroidConfig.API_IMAGE_URL)
            addManifestPlaceholders(mapOf("enableCrashReporting" to "true"))
        }
        getByName("debug") {
            isDebuggable = true
            isZipAlignEnabled = false
            multiDexEnabled = true

            proguardFiles(getDefaultProguardFile("proguard-android.txt"), "proguard-rules.pro")
            buildConfigField("String", "API_ENDPOINT", AndroidConfig.API_DEV_URL)
            buildConfigField("String", "API_IMAGE_URL", AndroidConfig.API_IMAGE_URL)
            addManifestPlaceholders(mapOf("enableCrashReporting" to "false"))
        }
        create("staging") {
            initWith(getByName("debug"))
            isMinifyEnabled = true
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }

    kotlinOptions {
        jvmTarget = JavaVersion.VERSION_1_8.toString()
    }

    bundle {
        density {
            enableSplit = true
        }
        abi {
            enableSplit = true
        }
        language {
            enableSplit = false
        }
    }
}

kapt {
    useBuildCache = true
}

dependencies {
    implementation(fileTree(mapOf("dir" to "libs", "include" to listOf("*.jar"))))

    // AndroidX
    implementation(Libs.androidx_app_compat)
    implementation(Libs.androidx_material)
    implementation(Libs.androidx_recyclerview)
    implementation(Libs.androidx_cardview)
    implementation(Libs.androidx_core)
    implementation(Libs.androidx_constraintlayout)
    implementation(Libs.androidx_fragment)
    implementation(Libs.androidx_multidex)

    // Kotlin
    implementation(Libs.jdk8)
    implementation(Libs.coroutines)

    // Networking
    implementation(Libs.retrofit)
    implementation(Libs.gson)
    implementation(Libs.okhttp_logging_interceptor)
    implementation(Libs.stetho)

    // Glide
    implementation(Libs.glide_runtime)
    kapt(Libs.glide_compiler)

    // Koin
    implementation(Libs.koin_scope)
    implementation(Libs.koin_viewmodel)

    // Room
    implementation(Libs.room_runtime)
    implementation(Libs.room_ktx)
    kapt(Libs.room_compiler)

    // Lifecycle
    implementation(Libs.lifecycle_extensions)
    implementation(Libs.lifecycle_livedata)
    implementation(Libs.lifecycle_viewmodel)

    // Firebase
    implementation(platform(Libs.firebase_bom))
    implementation(Libs.firebase_analytics_ktx)
    implementation(Libs.firebase_crashlytics_ktx)
    implementation(Libs.firebase_messaging_ktx)

    // Crashlytics
    implementation(Libs.crashlytics) {
        isTransitive = true
    }

    // Anko
    implementation(Libs.anko)

    // Navigation
    implementation(Libs.navigation_fragment)
    implementation(Libs.navigation_ui)

    // Permission
    implementation(Libs.permissions_dispatcher)
    kapt(Libs.permissions_dispatcher_processor)

    // Barcode
    implementation(Libs.barcode)

    // AdapterDelegates
    implementation(Libs.adapter_delegates)
    implementation(Libs.adapter_delegates_dsl)

    // ExpandableLayout
    implementation(Libs.expandable_layout)

    // Html textview
    implementation(Libs.html_textview)

    //Lives
    implementation(Libs.lives)

    // Test
    testImplementation(Libs.junit)
    testImplementation(Libs.mockito_core)
    androidTestImplementation(Libs.junit_ext)
    androidTestImplementation(Libs.runner)
    androidTestImplementation(Libs.rules)
    androidTestImplementation(Libs.espresso_core)
    androidTestImplementation(Libs.espresso_contrib)
    androidTestImplementation(Libs.espresso_intents)
}

repositories {
    mavenCentral()
    maven("https://repository.jetbrains.com/all")
}

apply(mapOf("plugin" to "com.google.gms.google-services"))
