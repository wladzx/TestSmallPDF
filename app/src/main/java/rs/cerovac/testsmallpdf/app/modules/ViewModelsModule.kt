package rs.cerovac.testsmallpdf.app.modules

import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import rs.cerovac.testsmallpdf.ui.commit_details.CommitDetailsViewModel
import rs.cerovac.testsmallpdf.ui.user_details.UserDetailsViewModel
import rs.cerovac.testsmallpdf.ui.user_repos.UserReposViewModel

val userDetailsViewModel = module {
    viewModel { UserDetailsViewModel(get()) }
}

val userReposViewModel = module {
    viewModel { UserReposViewModel(get()) }
}

val commitDetailsViewModel= module {
    viewModel { CommitDetailsViewModel(get()) }
}
