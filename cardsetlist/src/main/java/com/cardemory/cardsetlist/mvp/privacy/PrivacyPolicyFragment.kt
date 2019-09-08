package com.cardemory.cardsetlist.mvp.privacy

import android.os.Bundle
import android.os.Handler
import android.view.View
import com.cardemory.cardsetlist.R
import com.cardemory.common.mvp.BaseFragment
import kotlinx.android.synthetic.main.fragment_privacy_policy.*

class PrivacyPolicyFragment :
    BaseFragment<PrivacyPolicyContract.View, PrivacyPolicyContract.Presenter>(),
    PrivacyPolicyContract.View {

    override val layoutResId = R.layout.fragment_privacy_policy

    override val titleRes = R.string.privacy_policy

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        webView.loadUrl(PRIVACY_POLICY_LOCATION)
    }

    override fun onStart() {
        super.onStart()
        Handler().post {
            setBackButtonVisibility(true)
        }
    }

    override fun onStop() {
        setBackButtonVisibility(false)
        super.onStop()
    }

    companion object {
        private const val PRIVACY_POLICY_LOCATION = "file:///android_asset/privacy_policy.html"

        fun newInstance() = PrivacyPolicyFragment()
    }
}
