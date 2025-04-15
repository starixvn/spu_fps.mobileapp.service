import com.android.build.gradle.internal.api.BaseVariantOutputImpl

plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.jetbrains.kotlin.android)
    `maven-publish`
}


fun libraryOutputName(variantName: String): String {
    return "com.fps.android.sdk-${project.property("VERSION_NAME")}-${variantName}.aar"
}

publishing {
    publications {
        publications {
            create<MavenPublication>("fpsRelease") {
                groupId = "com.fps.android"
                artifactId = "sdk"
                version = project.property("VERSION_NAME") as String
                afterEvaluate {
                    from(components["release"])
                }
            }
        }
    }
    repositories {
        maven {
            url = uri("https://gitlab.nexdev.net/api/v4/projects/2742/packages/maven")
            name = "GitLab"
            credentials(HttpHeaderCredentials::class) {
                name = "Private-Token"
                value = findProperty("gitLabPrivateToken") as String? // the variable resides in $GRADLE_USER_HOME/gradle.properties
            }
            authentication {
                create("header", HttpHeaderAuthentication::class)
            }
        }
    }
}


android {
    namespace = "com.fps.android.sdk"
    compileSdk = 34

    defaultConfig {
        minSdk = 24

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")

        version = project.property("VERSION_NAME") as String
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

    libraryVariants.all {
        val variantName = this.name
        this.outputs.all {
            (this as? BaseVariantOutputImpl)?.apply {
                if (this.outputFileName.endsWith(".aar")) {
                    this.outputFileName = libraryOutputName(variantName)
                }
            }
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
}

dependencies {
    implementation(project(":fpscore"))
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
}
