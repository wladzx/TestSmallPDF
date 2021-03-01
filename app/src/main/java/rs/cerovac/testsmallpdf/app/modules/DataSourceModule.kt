package rs.cerovac.testsmallpdf.app.modules


import org.koin.dsl.module
import rs.cerovac.testsmallpdf.data.datasource.commits.CommitsListDataSourceFactory
import rs.cerovac.testsmallpdf.data.datasource.repos.ReposListDataSourceFactory

val reposListDataSourceFactory = module {
    single { ReposListDataSourceFactory(get()) }
}

val commitsListDataSourceFactory = module {
    single { CommitsListDataSourceFactory(get()) }
}