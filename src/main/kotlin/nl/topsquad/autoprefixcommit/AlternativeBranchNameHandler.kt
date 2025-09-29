package nl.topsquad.autoprefixcommit

import com.intellij.openapi.vcs.CheckinProjectPanel
import com.intellij.openapi.vcs.changes.CommitContext
import com.intellij.openapi.vcs.checkin.CheckinHandler
import com.intellij.openapi.vcs.checkin.CheckinHandlerFactory
import git4idea.repo.GitRepositoryManager

/**
 * Alternative implementation that modifies the commit message right before commit
 */
class AlternativeBranchNameHandlerFactory : CheckinHandlerFactory() {
    override fun createHandler(panel: CheckinProjectPanel, commitContext: CommitContext): CheckinHandler {
        return object : CheckinHandler() {
            
            override fun beforeCheckin(): ReturnResult {
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
                return ReturnResult.COMMIT
            }
        }
    }
}
