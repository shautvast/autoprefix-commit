package nl.topsquad.autoprefixcommit

import com.intellij.openapi.application.ApplicationManager
import com.intellij.openapi.vcs.CheckinProjectPanel
import com.intellij.openapi.vcs.changes.CommitContext
import com.intellij.openapi.vcs.checkin.CheckinHandler
import com.intellij.openapi.vcs.checkin.CheckinHandlerFactory
import git4idea.repo.GitRepositoryManager
import javax.swing.Timer

/**
 * Implementation that uses a timer to delay commit message modification
 */
class DelayedBranchNameHandlerFactory : CheckinHandlerFactory() {
    override fun createHandler(panel: CheckinProjectPanel, commitContext: CommitContext): CheckinHandler {
        return object : CheckinHandler() {
            
            init {
                // Use a timer to delay the modification slightly
                val timer = Timer(100) { // 100ms delay
                    ApplicationManager.getApplication().invokeLater {
                        modifyCommitMessage(panel)
                    }
                }
                timer.isRepeats = false
                timer.start()
            }
            
            private fun modifyCommitMessage(panel: CheckinProjectPanel) {
                val repository = GitRepositoryManager.getInstance(panel.project).repositories.firstOrNull()
                repository?.let {
                    val currentBranch = it.currentBranchName
                    val currentMessage = panel.commitMessage
                    
                    if (currentBranch != null && !currentMessage.contains(currentBranch)) {
                        val newMessage = if (currentMessage.isEmpty()) {
                            "$currentBranch: "
                        } else {
                            "$currentBranch: $currentMessage"
                        }
                        panel.commitMessage = newMessage
                    }
                }
            }
        }
    }
}
