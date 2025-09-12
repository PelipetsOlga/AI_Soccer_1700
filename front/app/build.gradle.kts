buildscript {
    dependencies {
        classpath("org.yaml:snakeyaml:2.0")
    }
}

import org.yaml.snakeyaml.Yaml
import java.io.FileInputStream
import org.gradle.internal.os.OperatingSystem
import java.io.File

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
}

// Load configuration from YAML file
@Suppress("UNCHECKED_CAST")
fun loadYamlConfig(): Map<String, Any> {
    val yaml = Yaml()
    val configFile = file("../../back/config.yaml")
    return yaml.load(FileInputStream(configFile)) as Map<String, Any>
}

val config = loadYamlConfig()
@Suppress("UNCHECKED_CAST")
val appConfig = config["app"] as Map<String, Any>
@Suppress("UNCHECKED_CAST")
val privacyConfig = config["privacy"] as Map<String, Any>
@Suppress("UNCHECKED_CAST")
val requestParamsConfig = config["request_params"] as Map<String, Any>
@Suppress("UNCHECKED_CAST")
val appsflyerConfig = config["appsflyer"] as Map<String, Any>

val APP_NAME = appConfig["name"] as String
val APP_ID = appConfig["app_id"] as String
val BUNDLE_ID = appConfig["bundle_id"] as String

val APPSFLYER_DEV_KEY = appsflyerConfig["appsflyer_dev_key"] as String

val DOMAIN = appConfig["domain"] as String
val INSTALLER_PARAM = requestParamsConfig["installer_param"] as String
val USER_ID_PARAM = requestParamsConfig["user_id_param"] as String
val GAID_PARAM = requestParamsConfig["google_ad_id_param"] as String
val APPSFLYER_ID_PARAM = requestParamsConfig["appsflyer_id_param"] as String
val APPSFLYER_SOURCE_PARAM = requestParamsConfig["appsflyer_source_param"] as String
val APPSFLYER_CAMPAIGN_PARAM = requestParamsConfig["appsflyer_campaign_param"] as String
val PRIVACY_CALLBACK = privacyConfig["callback"] as String
val PRIVACY_ACCEPTED_PARAM = privacyConfig["accepted_param"] as String

val SUPPORT_LINK = "https://$DOMAIN/contact.html"
val PRIVACY_LINK = "https://$DOMAIN/privacy/"

android {
    namespace = "com.application"
    compileSdk = 36

    defaultConfig {
        applicationId = BUNDLE_ID
        minSdk = 28
        targetSdk = 36
        versionCode = 1
        versionName = "1.0"

        setProperty("archivesBaseName", "$applicationId-$versionCode($versionName)")

        // BuildConfig constants - same for all build types
        buildConfigField("String", "INSTALLER_PARAM", "\"$INSTALLER_PARAM\"")
        buildConfigField("String", "USER_ID_PARAM", "\"$USER_ID_PARAM\"")
        buildConfigField("String", "GAID_PARAM", "\"$GAID_PARAM\"")
        buildConfigField("String", "APPSFLYER_ID_PARAM", "\"$APPSFLYER_ID_PARAM\"")
        buildConfigField("String", "APPSFLYER_SOURCE_PARAM", "\"$APPSFLYER_SOURCE_PARAM\"")
        buildConfigField("String", "APPSFLYER_CAMPAIGN_PARAM", "\"$APPSFLYER_CAMPAIGN_PARAM\"")
        buildConfigField("String", "APPSFLYER_DEV_KEY", "\"$APPSFLYER_DEV_KEY\"")
        buildConfigField("String", "SUPPORT_LINK", "\"$SUPPORT_LINK\"")
        buildConfigField("String", "PRIVACY_LINK", "\"$PRIVACY_LINK\"")
        buildConfigField("String", "PRIVACY_CALLBACK", "\"$PRIVACY_CALLBACK\"")
        buildConfigField("String", "PRIVACY_ACCEPTED_PARAM", "\"$PRIVACY_ACCEPTED_PARAM\"")

        manifestPlaceholders["appName"] = APP_NAME
    }

    signingConfigs {
        create("release") {
            storeFile = file("keystore/$APP_ID.keystore")
            storePassword = APP_ID
            keyAlias = APP_ID
            keyPassword = APP_ID
        }
    }

    buildTypes {
        release {
            signingConfig = signingConfigs.getByName("release")
            isMinifyEnabled = true
            isShrinkResources = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = JavaVersion.VERSION_17.toString()
    }
    buildFeatures {
        compose = true
        buildConfig = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.12"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
            pickFirsts += listOf(
                "META-INF/versions/9/OSGI-INF/MANIFEST.MF"
            )
        }
    }
}

dependencies {
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)
}

tasks.register("checkAndGenerateKeystore") {
    group = "build"
    description =
        "Checks for the keystore file, clears the keystore folder if missing, and runs the keystore generation script"

    doLast {
        val keystoreDir = File("keystore")
        val keystoreFile = File(keystoreDir, "$APP_ID.keystore")

        // Check if the keystore file exists
        if (!keystoreFile.exists()) {
            println("Keystore file not found: ${keystoreFile.absolutePath}")

            // Delete everything in the keystore folder
            if (keystoreDir.exists()) {
                keystoreDir.listFiles()?.forEach { file ->
                    if (file.delete()) {
                        println("Deleted file: ${file.absolutePath}")
                    } else {
                        println("Failed to delete file: ${file.absolutePath}")
                    }
                }
                println("Keystore folder cleared: ${keystoreDir.absolutePath}")
            }

            val scriptsDir = File(rootDir, "scripts")

            val scriptSh  = File(scriptsDir, "generate_keystore.sh").absolutePath
            val scriptPs1 = File(scriptsDir, "generate_keystore.ps1").absolutePath

            val result = if (OperatingSystem.current().isWindows) {
                project.exec {
                    workingDir = keystoreDir
                    commandLine(
                        "powershell", "-NoProfile", "-ExecutionPolicy", "Bypass",
                        "-File", scriptPs1, APP_ID
                    )
                }
            } else {
                project.exec {
                    workingDir = keystoreDir
                    commandLine("zsh", scriptSh, APP_ID)
                }
            }

            if (result.exitValue == 0) {
                println("Keystore generation completed successfully.")
            } else {
                throw GradleException("Keystore generation failed with exit code: ${result.exitValue}")
            }
        } else {
            println("Keystore file already exists: ${keystoreFile.absolutePath}")
        }
    }
}

// Make the keystore check run before every build
tasks.named("preBuild") {
    dependsOn("checkAndGenerateKeystore")
}

