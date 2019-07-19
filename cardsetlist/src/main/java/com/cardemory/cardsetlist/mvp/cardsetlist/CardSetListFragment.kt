package com.cardemory.cardsetlist.mvp.cardsetlist

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import com.cardemory.carddata.entity.CardSet
import com.cardemory.cardsetlist.R
import com.cardemory.cardsetlist.ui.CardSetListAdapter
import com.cardemory.cardsetlist.ui.tutorial.CardSetListTutorialSpotlight
import com.cardemory.common.mvp.BaseFragment
import com.cardemory.common.mvp.OnBackPressedListener
import com.cardemory.common.util.EmptyMessageObserver
import kotlinx.android.synthetic.main.dialog_welcome.view.*
import kotlinx.android.synthetic.main.fragment_card_set_list.*
import javax.inject.Inject

class CardSetListFragment :
    BaseFragment<CardSetListContract.View, CardSetListContract.Presenter>(),
    CardSetListContract.View,
    OnBackPressedListener {

    private var cardSetAdapter: CardSetListAdapter? = null

    private lateinit var emptyMessageObserver: EmptyMessageObserver

    @Inject
    lateinit var cardSetListTutorialSpotlight: CardSetListTutorialSpotlight

    override val layoutResId = R.layout.fragment_card_set_list

    override val titleRes = R.string.title

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        actionButton.setOnClickListener { onActionButtonClicked() }

        cardSetAdapter = CardSetListAdapter(
            presenter::onCardSetClicked,
            presenter::onEditCardSetClicked,
            presenter::onDeleteCardSetClicked,
            presenter::onCardSetSelected
        )
        cardSetsRecyclerView.adapter = cardSetAdapter
        emptyMessageObserver =
            EmptyMessageObserver(cardSetAdapter!!, firstCardSetLabel, arrowImageView)
        cardSetAdapter!!.registerAdapterDataObserver(emptyMessageObserver)
    }

    private fun onActionButtonClicked() {
        if (cardSetAdapter!!.selectionMode) {
            presenter.onDeleteCardSetsClicked(cardSetAdapter!!.getSelectedItems())
        } else {
            presenter.onCreateCardSetClicked()
        }
    }

    override fun showCardSets(cardSets: List<CardSet>) {
        cardSetAdapter?.swapData(cardSets)
        if (cardSets.isEmpty()) {
            firstCardSetLabel.visibility = View.VISIBLE
            arrowImageView.visibility = View.VISIBLE
        }
    }

    override fun selectCardSetForDeletion(cardSet: CardSet) {
        cardSetAdapter?.apply {
            selectItem(getItems().indexOf(cardSet))
        }
    }

    override fun setSelectionModeEnabled(enabled: Boolean) {
        setBackButtonVisibility(enabled)
        cardSetAdapter!!.setSelectionModeEnabled(enabled)
        if (enabled) {
            showSelectionTitle()
            R.drawable.ic_delete
        } else {
            showTitle(titleRes)
            R.drawable.ic_plus
        }.also {
            val drawable = ContextCompat.getDrawable(requireContext(), it)
            actionButton.setImageDrawable(drawable)
        }
    }

    override fun onBackPressed(): Boolean {
        return when {
            cardSetListTutorialSpotlight.spotlightVisible -> {
                cardSetListTutorialSpotlight.closeSpotlight()
                true
            }
            cardSetAdapter!!.selectionMode -> {
                setSelectionModeEnabled(false)
                true
            }
            else -> false
        }
    }

    override fun showSelectionTitle() {
        showTitle(
            getString(
                R.string.selected_format,
                cardSetAdapter!!.getSelectedItemsCount()
            )
        )
    }

    override fun clearCardSets(cardSets: List<CardSet>) {
        cardSetAdapter!!.removeItems(cardSets)
    }

    override fun getSelectedItemsCount() = cardSetAdapter!!.getSelectedItemsCount()

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_cardset_list, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem) =
        when (item.itemId) {
            R.id.action_privacy_policy -> {
                presenter.onPrivacyPolicyClicked()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }

    override fun showWelcomeMessage() {
        @SuppressLint("InflateParams")
        val view = layoutInflater.inflate(R.layout.dialog_welcome, null)
        val dialog = AlertDialog.Builder(requireContext())
            .setView(view)
            .setCancelable(false)
            .create()
        view.startTutorialButton.setOnClickListener {
            presenter.onStartTutorialClicked()
            dialog.dismiss()
        }
        view.skipTutorialButton.setOnClickListener {
            presenter.onSkipTutorialClicked()
            dialog.dismiss()
        }

        with(dialog) {
            show()
            window?.setBackgroundDrawableResource(R.color.transparent)
        }
    }

    override fun showTutorialActionButton() {
        cardSetListTutorialSpotlight
            .createSpotlight(actionButton)
            .start()
    }

    override fun onDestroyView() {
        cardSetAdapter!!.unregisterAdapterDataObserver(emptyMessageObserver)
        cardSetAdapter = null
        super.onDestroyView()
    }

    companion object {

        fun newInstance() = CardSetListFragment()
    }
}
