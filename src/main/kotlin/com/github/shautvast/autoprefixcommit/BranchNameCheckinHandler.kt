package com.github.shautvast.autoprefixcommit

import com.intellij.openapi.vcs.CheckinProjectPanel
import com.intellij.openapi.vcs.changes.CommitContext
import com.intellij.openapi.vcs.checkin.CheckinHandler
import com.intellij.openapi.vcs.checkin.CheckinHandlerFactory
import git4idea.repo.GitRepositoryManager
import java.awt.event.FocusAdapter
import java.awt.event.FocusEvent

/**
 * Handles Git branch name injection into commit messages when the commit dialog is opened
 */
class BranchNameCheckinHandlerFactory : CheckinHandlerFactory() {

    override fun createHandler(panel: CheckinProjectPanel, commitContext: CommitContext): CheckinHandler {
        return object : CheckinHandler() {
            init {
                (panel.preferredFocusedComponent)?.addFocusListener(object : FocusAdapter() {
                    override fun focusGained(e: FocusEvent?) {
                        // update commit message
                        val message = panel.commitMessage
                        val repository = GitRepositoryManager.getInstance(panel.project).repositories.firstOrNull()
                        repository?.let {
                            it.currentBranchName?.let { b ->
                                if (!message.startsWith(b)) {
                                    panel.commitMessage = "#$b $message"
                                }
                            }
                        }
                    }
                })
            }
        }
    }
}