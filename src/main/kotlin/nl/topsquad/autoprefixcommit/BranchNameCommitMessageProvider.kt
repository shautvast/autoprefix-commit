package nl.topsquad.autoprefixcommit

import com.intellij.openapi.project.Project
import com.intellij.openapi.vcs.changes.LocalChangeList
import com.intellij.openapi.vcs.changes.ui.CommitMessageProvider
import com.intellij.util.diff.Diff
import git4idea.repo.GitRepositoryManager
import org.jetbrains.annotations.NotNull

class BranchNameCommitMessageProvider: CommitMessageProvider {
    override fun getCommitMessage(@NotNull forChangelist: LocalChangeList, project: Project): String? {
        val repository = GitRepositoryManager.getInstance(project).repositories.firstOrNull()
        repository?.let {
            val currentBranch = it.currentBranchName
            return currentBranch
        }
        return ""
    }
}