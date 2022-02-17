package com.cardemory.cardsetlist.navigation

import com.cardemory.cardsetlist.mvp.privacy.PrivacyPolicyFragment
import ru.terrakok.cicerone.android.support.SupportAppScreen

class PrivacyPolicyScreen : SupportAppScreen() {

    override fun getFragment() = PrivacyPolicyFragment.newInstance()
}