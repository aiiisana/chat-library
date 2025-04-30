plugins {
    id("com.android.library") version "7.4.0"
    id("kotlin-android") version "1.8.10"
    `maven-publish`
}

android {
    namespace = "advanced.lab.chatlibrary"
    compileSdk = 34

    defaultConfig {
        minSdk = 24
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }

    kotlinOptions {
        jvmTarget = "1.8"
    }

    buildFeatures {
        viewBinding = true
    }
}

dependencies {
    implementation("androidx.core:core-ktx:1.12.0")
    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.10.0")

    implementation("com.squareup.okhttp3:okhttp:4.12.0")
    implementation("com.squareup.okhttp3:logging-interceptor:4.12.0")

    implementation("androidx.recyclerview:recyclerview:1.3.2")

    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.6.4")
    implementation("io.insert-koin:koin-android:3.4.0")

    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
}

val githubProperties = Properties().apply {
    val githubPropertiesFile = rootProject.file("github.properties")
    if (githubPropertiesFile.exists()) {
        load(githubPropertiesFile.inputStream())
    }
}

afterEvaluate {
    publishing {
        publications {
            create<MavenPublication>("release") {
                from(components["release"])
                groupId = "com.github.aiiisana"
                artifactId = "chatlibrary"
                version = "1.1.8"
            }
        }

        repositories {
            maven {
                name = "GitHubPackages"
                url = uri("https://maven.pkg.github.com/aiiisana/chat-library")
                credentials {
                    username = githubProperties["gpr.usr"] as String? ?: System.getenv("GPR_USER")
                    password = githubProperties["gpr.key"] as String? ?: System.getenv("GPR_API_KEY")
                }
            }
        }
    }
}