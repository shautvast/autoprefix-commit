plugins {
    id("java")
    id("org.jetbrains.kotlin.jvm") version "2.2.0"
    id("org.jetbrains.intellij.platform") version "2.9.0"
}

group = "com.github.shautvast"
version = "1.3"

repositories {
    mavenCentral()
    intellijPlatform {
        defaultRepositories()
    }
}

dependencies {
    intellijPlatform {
        create("IC", "2025.2.2")
        testFramework(org.jetbrains.intellij.platform.gradle.TestFrameworkType.Platform)
        bundledPlugin("Git4Idea")
    }
}

intellijPlatform {
    pluginConfiguration {
        ideaVersion {
            sinceBuild = "252"
        }

        changeNotes = """
      1.1 Initial version: insert branchname in commit dialog. No configuration needed.
      1.2 No code changes. Plugin was mistakenly compiled using jdk22 so it didn't work.
      1.3 minor fix for redundant space after the branch name
    """.trimIndent()
    }
}

