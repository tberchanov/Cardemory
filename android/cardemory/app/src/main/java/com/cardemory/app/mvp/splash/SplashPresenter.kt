package com.cardemory.app.mvp.splash

import com.cardemory.app.navigation.SplashNavigation
import com.cardemory.carddata.data.db.AppDatabase
import com.cardemory.carddata.interactor.GetAllCardsInteractor
import com.cardemory.carddata.interactor.PrepopulateDbInteractor
import com.cardemory.common.mvp.BasePresenter
import kotlinx.coroutines.launch
import timber.log.Timber

class SplashPresenter(
    private val splashNavigation: SplashNavigation,
    private val prepopulateDbInteractor: PrepopulateDbInteractor,
    private val getAllCardsInteractor: GetAllCardsInteractor,
    private val appDatabase: AppDatabase
) : BasePresenter<SplashContract.View>(),
    SplashContract.Presenter {

    override fun attachView(view: SplashContract.View) {
        super.attachView(view)
        if (appDatabase.isOpen) {
            splashNavigation.showMainScreen()
        } else {
            AppDatabase.setOnDatabaseOpenListener(::onDbOpened)
            interactWithDatabase()
        }
    }

    private fun onDbOpened(dbFirstTimeCreated: Boolean) {
        launch {
            if (dbFirstTimeCreated) {
                prepopulateDbInteractor.run(Unit).either(
                    { Timber.e("prepopulateDbInteractor failure: $it") },
                    { Timber.d("prepopulateDbInteractor success") }
                )
            }
            invalidateOnDpOpenListener()
            splashNavigation.showMainScreen()
        }
    }

    private fun invalidateOnDpOpenListener() {
        AppDatabase.setOnDatabaseOpenListener { /*none*/ }
    }

    private fun interactWithDatabase() {
        getAllCardsInteractor(Unit)
    }
}