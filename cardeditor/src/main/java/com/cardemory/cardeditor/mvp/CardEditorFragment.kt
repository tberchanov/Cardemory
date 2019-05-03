package com.cardemory.cardeditor.mvp

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.os.Handler
import android.view.View
import androidx.core.content.FileProvider
import com.cardemory.carddata.entity.Card
import com.cardemory.carddata.entity.CardSet
import com.cardemory.cardeditor.R
import com.cardemory.cardeditor.mvp.CardEditorContract.Companion.REQUEST_TAKE_PHOTO
import com.cardemory.common.mvp.BaseFragment
import com.cardemory.common.util.hideKeyboard
import com.cardemory.common.util.showKeyboard
import com.theartofdev.edmodo.cropper.CropImage
import com.theartofdev.edmodo.cropper.CropImageView
import kotlinx.android.synthetic.main.fragment_cardeditor.*
import timber.log.Timber
import java.io.File


class CardEditorFragment :
    BaseFragment<CardEditorContract.View, CardEditorContract.Presenter>(),
    CardEditorContract.View {

    override val layoutResId = R.layout.fragment_cardeditor

    override val titleRes by lazy {
        if (getCardArg() == null)
            R.string.title_create
        else
            R.string.title_edit
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        saveCardButton.setOnClickListener {
            presenter.onSaveCardClicked(getCard())
        }
        scanTextCardView.setOnClickListener { presenter.onScanTextClicked() }

        showCardDataIfNeeded()

        cardTitleEditText.showKeyboard()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when (requestCode) {
            REQUEST_TAKE_PHOTO -> onTakePhotoResult(resultCode)
            CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE -> onCropPhotoResult(resultCode, data)
        }
    }

    private fun onTakePhotoResult(resultCode: Int) {
        Timber.d("onTakePhotoResult: $resultCode")
        if (resultCode == Activity.RESULT_OK) {
            presenter.onTakePhotoResult()
        }
    }

    private fun onCropPhotoResult(resultCode: Int, data: Intent?) {
        Timber.d("onCropPhotoResult: $resultCode")
        val result = CropImage.getActivityResult(data)
        if (resultCode == Activity.RESULT_OK) {
            val resultUri = result.uri
            // TOOD process crop result
        } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
            Timber.e(result.error, "onCropPhotoResult error!")
        }
    }

    private fun showCardDataIfNeeded() {
        getCardArg()?.also {
            cardTitleEditText.setText(it.title)
            cardDescriptionEditText.setText(it.description)
        }
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

    private fun getCard(): Card {
        val cardArg = getCardArg()
        return Card(
            cardArg?.id ?: Card.UNDEFINED_ID,
            cardArg?.cardSetId ?: getCardSetArg()!!.id,
            cardTitleEditText.text.toString(),
            cardDescriptionEditText.text.toString(),
            cardArg?.memoryRank ?: 0.0,
            cardArg?.lastTrainMillis ?: -1
        )
    }

    override fun hideKeyboard() {
        cardTitleEditText.hideKeyboard()
    }

    override fun showCropPhotoScreen(photoFile: File) {
        val photoUri: Uri = FileProvider.getUriForFile(
            activity!!,
            activity!!.packageName,
            photoFile
        )

        CropImage.activity(photoUri)
            .setGuidelines(CropImageView.Guidelines.ON)
            .setActivityTitle(getString(R.string.crop_title))
            .start(context!!, this)
    }

    companion object {

        private const val CARD_KEY = "CARD_KEY"
        private const val CARD_SET_KEY = "CARD_SET_KEY"

        fun newInstance(card: Card): CardEditorFragment {
            val fragment = CardEditorFragment()
            fragment.arguments = Bundle().apply {
                putParcelable(CARD_KEY, card)
            }
            return fragment
        }

        fun newInstance(cardSet: CardSet): CardEditorFragment {
            val fragment = CardEditorFragment()
            fragment.arguments = Bundle().apply {
                putParcelable(CARD_SET_KEY, cardSet)
            }
            return fragment
        }

        private fun CardEditorFragment.getCardArg(): Card? =
            arguments?.getParcelable(CARD_KEY)

        private fun CardEditorFragment.getCardSetArg(): CardSet? =
            arguments?.getParcelable(CARD_SET_KEY)
    }
}