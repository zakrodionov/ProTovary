object Libs {
    //Plugins
    const val android_gradle_plugin = "com.android.tools.build:gradle:${Versions.android_gradle_plugin}"
    const val google_services_plugin = "com.google.gms:google-services:${Versions.google_services_plugin}"
    const val kotlin_gradle_plugin = "org.jetbrains.kotlin:kotlin-gradle-plugin:${Versions.kotlin_gradle_plugin}"
    const val fabric_plugin = "io.fabric.tools:gradle:${Versions.fabric_plugin}"
    const val gradle_versions_plugin = "com.github.ben-manes.versions"
    const val navigation_safe_args_plugin = "androidx.navigation:navigation-safe-args-gradle-plugin:${Versions.navigation_safe_args_plugin}"
    const val ktlint_plugin = "org.jlleitschuh.gradle.ktlint"

    //AndroidX
    const val androidx_app_compat = "androidx.appcompat:appcompat:${Versions.androidx_appcompat}"
    const val androidx_recyclerview = "androidx.recyclerview:recyclerview:${Versions.androidx_recyclerview}"
    const val androidx_cardview = "androidx.cardview:cardview:${Versions.androidx_cardview}"
    const val androidx_material = "com.google.android.material:material:${Versions.androidx_material}"
    const val androidx_core = "androidx.core:core-ktx:${Versions.ktx}"
    const val androidx_constraintlayout = "androidx.constraintlayout:constraintlayout:${Versions.androidx_constraint_layout}"
    const val androidx_fragment = "androidx.fragment:fragment-ktx:${Versions.fragment}"
    const val androidx_multidex = "androidx.multidex:multidex:${Versions.multidex}"

    //Kotlin
    const val coroutines = "org.jetbrains.kotlinx:kotlinx-coroutines-android:${Versions.kotlin_coroutines}"
    const val jdk8 = "org.jetbrains.kotlin:kotlin-stdlib-jdk8:${Versions.kotlin_gradle_plugin}"

    //Koin
    const val koin_scope = "org.koin:koin-androidx-scope:${Versions.koin}"
    const val koin_viewmodel = "org.koin:koin-androidx-viewmodel:${Versions.koin}"

    //Lifecycle
    const val lifecycle_extensions = "androidx.lifecycle:lifecycle-extensions:${Versions.lifecycle_ext}"
    const val lifecycle_livedata = "androidx.lifecycle:lifecycle-livedata-ktx:${Versions.lifecycle}"
    const val lifecycle_viewmodel = "androidx.lifecycle:lifecycle-viewmodel-ktx:${Versions.lifecycle}"

    //Room
    const val room_runtime = "androidx.room:room-runtime:${Versions.room}"
    const val room_compiler = "androidx.room:room-compiler:${Versions.room}"
    const val room_ktx = "androidx.room:room-ktx:${Versions.room}"

    //Networking
    const val retrofit = "com.squareup.retrofit2:retrofit:${Versions.retrofit}"
    const val gson = "com.squareup.retrofit2:converter-gson:${Versions.retrofit}"
    const val okhttp_logging_interceptor = "com.squareup.okhttp3:logging-interceptor:${Versions.okhttp_logging_interceptor}"
    const val stetho = "com.facebook.stetho:stetho-okhttp3:${Versions.stetho}"

    //Glide
    const val glide_runtime = "com.github.bumptech.glide:glide:${Versions.glide}"
    const val glide_compiler = "com.github.bumptech.glide:compiler:${Versions.glide}"

    //Firebase
    const val firebase_core = "com.google.firebase:firebase-core:${Versions.firebase_core}"
    const val firebase_messaging = "com.google.firebase:firebase-messaging:${Versions.firebase_messaging}"

    //Cicerone
    const val cicerone = "ru.terrakok.cicerone:cicerone:${Versions.cicerone}"

    //PageIndicatorView
    const val page_indicator_view = "com.romandanylyk:pageindicatorview:${Versions.page_indicator_view}"

    //CustomTabs
    const val custom_tabs = "com.android.support:customtabs:${Versions.custom_tabs}"

    //EventBus
    const val event_bus = "org.greenrobot:eventbus:${Versions.event_bus}"

    //Crashlytics
    const val crashlytics = "com.crashlytics.sdk.android:crashlytics:${Versions.crashlytics}"

    //SpongyCastle
    const val spongycastle = "com.madgag.spongycastle:prov:${Versions.spongycastle}"

    //Anko
    const val anko = "org.jetbrains.anko:anko:${Versions.anko}"

    //Navigation
    const val navigation_fragment = "androidx.navigation:navigation-fragment-ktx:${Versions.navigation}"
    const val navigation_ui = "androidx.navigation:navigation-ui-ktx:${Versions.navigation}"

    //Permission
    const val permissions_dispatcher = "org.permissionsdispatcher:permissionsdispatcher:${Versions.permissions_dispatcher}"
    const val permissions_dispatcher_processor = "org.permissionsdispatcher:permissionsdispatcher-processor:${Versions.permissions_dispatcher}"

    //Barcode
    const val barcode = "me.dm7.barcodescanner:zbar:${Versions.barcode}"

    //AdapterDelegates
    const val adapter_delegates = "com.hannesdorfmann:adapterdelegates4:${Versions.adapter_delegates}"
    const val adapter_delegates_dsl = "com.hannesdorfmann:adapterdelegates4-kotlin-dsl-layoutcontainer:${Versions.adapter_delegates}"

    //ExpandableLayout
    const val expandable_layout = "com.github.skydoves:expandablelayout:${Versions.expandable_layout}"

    //Html textview
    const val html_textview = "org.sufficientlysecure:html-textview:${Versions.html_textview}"

    //Lives
    const val lives = "com.snakydesign.livedataextensions:lives:2.0.0"

    //Test
    const val junit = "junit:junit:${Versions.junit}"
    const val junit_ext = "androidx.test.ext:junit:${Versions.test_ext_junit}"
    const val runner = "androidx.test:runner:${Versions.test_runner}"
    const val rules = "androidx.test:rules:${Versions.test_rules}"
    const val espresso_contrib = "androidx.test.espresso:espresso-contrib:${Versions.test_espresso}"
    const val espresso_core = "androidx.test.espresso:espresso-core:${Versions.test_espresso}"
    const val espresso_intents = "androidx.test.espresso:espresso-intents:${Versions.test_espresso}"
    const val mockito_core = "org.mockito:mockito-core:${Versions.mockito_core}"
}