package nl.topsquad.autoprefixcommit

import com.intellij.openapi.vcs.CheckinProjectPanel
import com.intellij.openapi.vcs.changes.CommitContext
import com.intellij.openapi.vcs.checkin.CheckinHandler
import com.intellij.openapi.vcs.checkin.CheckinHandlerFactory
import git4idea.repo.GitRepositoryManager

/**
 * Handles Git branch name injection into commit messages when the commit dialog is opened
 */
class BranchNameCheckinHandlerFactory : CheckinHandlerFactory() {

    override fun createHandler(panel: CheckinProjectPanel, commitContext: CommitContext): CheckinHandler {
        return object : CheckinHandler() {
            init {
                (panel.preferredFocusedComponent)?.addFocusListener(object : java.awt.event.FocusAdapter() {
                    override fun focusGained(e: java.awt.event.FocusEvent?) {
                        // update commit message
                        val currentMessage = panel.commitMessage
                        val modifiedMessage = modifyCommitMessage(currentMessage)
                        panel.commitMessage = modifiedMessage
                    }
                })
            }

            fun modifyCommitMessage(message: String): String {
                val repository = GitRepositoryManager.getInstance(panel.project).repositories.firstOrNull()

                return repository?.let {
                    val branchName = it.currentBranchName
                    if (branchName != null && !message.startsWith("${branchName} ")) {
                        "${it.currentBranchName ?: ""} ${message} "
                    } else {
                        message
                    }
                } ?: message
            }
        }
    }
}