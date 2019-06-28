package com.cardemory.cardlist.mvp

import android.Manifest
import android.app.AlertDialog
import android.content.pm.PackageManager
import android.os.Bundle
import android.os.Handler
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import com.cardemory.carddata.entity.Card
import com.cardemory.carddata.entity.CardSet
import com.cardemory.cardlist.R
import com.cardemory.cardlist.mvp.CardListContract.Companion.REQUIRED_CARDS_FOR_TRAIN
import com.cardemory.cardlist.ui.CardDiffUtilCallback
import com.cardemory.cardlist.ui.CardListAdapter
import com.cardemory.common.mvp.BaseFragment
import com.cardemory.common.mvp.OnBackPressedListener
import com.cardemory.common.navigation.OnResultListener
import com.cardemory.common.util.EmptyMessageObserver
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.fragment_cardlist.*
import timber.log.Timber


class CardListFragment :
    BaseFragment<CardListContract.View, CardListContract.Presenter>(),
    CardListContract.View,
    OnResultListener,
    OnBackPressedListener {

    private lateinit var cardAdapter: CardListAdapter

    private lateinit var emptyMessageObserver: EmptyMessageObserver

    override val layoutResId = R.layout.fragment_cardlist

    override val title by lazy { getCardSetArg().name }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        actionButton.setOnClickListener { onActionButtonClicked() }
        setupCardRecyclerView()
        presenter.loadCardSet(getCardSetArg())
    }

    private fun onActionButtonClicked() {
        if (cardAdapter.selectionMode) {
            presenter.onDeleteCardsClicked(cardAdapter.getSelectedItems())
        } else {
            presenter.onCreateCardClicked(getCardSetArg())
        }
    }

    override fun showCardSetData(cardSet: CardSet) {
        firstCardLabel.text =
            getString(R.string.create_first_card_format, cardSet.name)
        showCards(cardSet.cards.values.toList())
    }

    private fun showCards(cards: List<Card>) {
        cardAdapter.swapData(cards)
        if (cards.isEmpty()) {
            setEmptyCardsMessageVisibility(true)
        }
    }

    private fun setupCardRecyclerView() {
        cardAdapter = CardListAdapter(
            presenter::onCardClicked,
            ::onTrainClicked,
            REQUIRED_CARDS_FOR_TRAIN,
            presenter::onDeleteCardClicked,
            presenter::onCardSelected
        )
        cardsRecyclerView.adapter = cardAdapter
        emptyMessageObserver = EmptyMessageObserver(
            cardAdapter,
            firstCardLabel,
            createCardArrowImageView
        )
        cardAdapter.registerAdapterDataObserver(emptyMessageObserver)
    }

    private fun onTrainClicked() {
        val cardsMap = cardAdapter.getItems().associateBy { it.id }
        val currentCardSet = getCardSetArg().copy(cards = cardsMap)
        presenter.onTrainClicked(currentCardSet)
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

    override fun showNewCard(card: Card) {
        setEmptyCardsMessageVisibility(false)

        val oldCards = cardAdapter.getItems()
        val newCards = oldCards.toMutableList().apply {
            saveCard(card)
        }

        val cardDiffUtilCallback = CardDiffUtilCallback(oldCards, newCards)
        val diffResult = DiffUtil.calculateDiff(cardDiffUtilCallback)

        cardAdapter.swapData(newCards, false)
        diffResult.dispatchUpdatesTo(cardAdapter)
    }

    private fun setEmptyCardsMessageVisibility(visible: Boolean) {
        val visibility = if (visible) View.VISIBLE else View.GONE
        firstCardLabel.visibility = visibility
        createCardArrowImageView.visibility = visibility
    }

    private fun MutableList<Card>.saveCard(card: Card) {
        indexOfFirst { it.id == card.id }
            .takeUnless { it == -1 }?.let {
                set(it, card)
            } ?: add(card)
    }

    override fun onDestroyView() {
        cardAdapter.unregisterAdapterDataObserver(emptyMessageObserver)
        super.onDestroyView()
    }

    override fun onResult(resultCode: Int, data: Any?) {
        Timber.d("on card saving result: $data")
        presenter.onCardSaved(data as Card)
    }

    override fun showNotEnoughCardsMessage(neededCardsCount: Int) {
        Snackbar.make(
            actionButton,
            getString(R.string.not_enough_cards_format, neededCardsCount),
            Snackbar.LENGTH_LONG
        ).show()
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_card_list, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem) =
        when (item.itemId) {
            R.id.action_export -> {
                onExportClick()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }

    private fun onExportClick() {
        requestWriteExternalPermission()
    }

    private fun requestWriteExternalPermission() {
        if (isPermissionGranted(Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
            exportCardSet()
        } else {
            requestPermissions(
                arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE),
                REQUEST_WRITE_EXTERNAL_PERMISSION
            )
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) = when (requestCode) {
        REQUEST_WRITE_EXTERNAL_PERMISSION ->
            onRequestWriteExternalPermissionResult(grantResults)
        else -> super.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }

    private fun onRequestWriteExternalPermissionResult(grantResults: IntArray) {
        if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            exportCardSet()
        } else {
            presenter.onPermissionDenied()
        }
    }

    private fun exportCardSet() {
        cardAdapter.getItems().associateBy { it.id }.let { cards ->
            getCardSetArg().copy(cards = cards)
        }.let {
            presenter.exportCardSet(it)
        }
    }

    private fun isPermissionGranted(permission: String) =
        ContextCompat.checkSelfPermission(
            context!!,
            permission
        ) == PackageManager.PERMISSION_GRANTED

    override fun showSuccessExportingMessage(exportedFilePath: String) {
        val exportedMessage =
            getString(R.string.success_export_format, exportedFilePath)
        Snackbar.make(
            containerCoordinator,
            exportedMessage, Snackbar.LENGTH_INDEFINITE
        ).show()
    }

    override fun showExportDeniedPermissionMessage() {
        AlertDialog.Builder(context)
            .setTitle(R.string.missing_permission)
            .setMessage(R.string.missing_permission_message)
            .setPositiveButton(R.string.go_to_settings) { _, _ -> presenter.showAppSettings() }
            .setNegativeButton(R.string.cancel, null)
            .create()
            .show()
    }

    override fun setSelectionModeEnabled(enabled: Boolean) {
        cardAdapter.setSelectionModeEnabled(enabled)
        if (enabled) {
            showSelectionTitle()
            R.drawable.ic_delete
        } else {
            showTitle(title)
            R.drawable.ic_plus
        }.also {
            val drawable = ContextCompat.getDrawable(requireContext(), it)
            actionButton.setImageDrawable(drawable)
        }
    }

    override fun selectCardForDeletion(card: Card) {
        cardAdapter.apply {
            selectItem(getItems().indexOf(card))
        }
    }

    override fun getSelectedItemsCount() = cardAdapter.getSelectedItemsCount()

    override fun showSelectionTitle() {
        showTitle(
            getString(
                R.string.selected_format,
                cardAdapter.getSelectedItemsCount()
            )
        )
    }

    override fun clearCards(cards: List<Card>) {
        cardAdapter.removeItems(cards)
    }

    override fun onBackPressed(): Boolean {
        return if (cardAdapter.selectionMode) {
            setSelectionModeEnabled(false)
            true
        } else {
            false
        }
    }

    companion object {
        private const val REQUEST_WRITE_EXTERNAL_PERMISSION = 1

        private const val CARD_SET_KEY = "CARD_SET_KEY"

        fun newInstance(cardSet: CardSet): CardListFragment {
            val fragment = CardListFragment()
            fragment.putCardSetArg(cardSet)
            return fragment
        }

        private fun CardListFragment.putCardSetArg(cardSet: CardSet) {
            arguments = Bundle().apply {
                putParcelable(CARD_SET_KEY, cardSet)
            }
        }

        private fun CardListFragment.getCardSetArg(): CardSet =
            arguments!!.getParcelable(CARD_SET_KEY)!!
    }
}