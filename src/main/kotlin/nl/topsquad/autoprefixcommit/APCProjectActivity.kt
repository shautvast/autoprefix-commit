package nl.topsquad.autoprefixcommit

import com.intellij.openapi.diagnostic.thisLogger
import com.intellij.openapi.project.Project
import com.intellij.openapi.startup.ProjectActivity

class APCProjectActivity: ProjectActivity {

    override suspend fun execute(project : Project){
        thisLogger().warn("Autoprefix-commit plugin is active")
    }
}