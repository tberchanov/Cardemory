package com.cardemory.train.ui

import android.animation.Animator
import android.animation.AnimatorInflater
import android.animation.AnimatorSet
import android.content.Context
import android.view.View
import com.cardemory.common.ui.BaseAdapter
import com.cardemory.common.ui.BaseHolder
import com.cardemory.train.R
import com.cardemory.train.ui.TrainCardStackAdapter.TrainCardStackHolder
import com.cardemory.train.ui.model.TrainCard
import kotlinx.android.synthetic.main.item_train_card_stack.view.*


class TrainCardStackAdapter(
    private val context: Context
) : BaseAdapter<TrainCard, TrainCardStackHolder>() {

    private lateinit var setRightOut: AnimatorSet
    private lateinit var setLeftIn: AnimatorSet

    init {
        loadAnimations()
    }

    private fun loadAnimations() {
        setRightOut = AnimatorInflater.loadAnimator(
            context,
            R.animator.out_animation
        ) as AnimatorSet
        setLeftIn = AnimatorInflater.loadAnimator(
            context,
            R.animator.in_animation
        ) as AnimatorSet
    }

    override fun layoutId(viewType: Int) = R.layout.item_train_card_stack

    override fun getHolder(view: View, viewType: Int) = TrainCardStackHolder(view)

    fun removeAnimationListeners() {
        setRightOut.removeAllListeners()
        setLeftIn.removeAllListeners()
    }

    inner class TrainCardStackHolder(itemView: View) : BaseHolder<TrainCard>(itemView) {

        override fun bind(uiEntity: TrainCard, position: Int) {
            super.bind(uiEntity, position)
            itemView.trainCardTitleTextView.text = uiEntity.card.title
            itemView.trainCardDescriptionTextView.text = uiEntity.card.description
            if (uiEntity.isBackVisible) {
                showCardBack()
            } else {
                showCardFront()
            }
            changeCameraDistance(itemView)

            itemView.trainCardContainer.setOnClickListener {
                flipCard(itemView.cardFrontLayout, itemView.cardBackLayout)
            }

            FlipAnimationListener(itemView).also {
                setRightOut.addListener(it)
                setLeftIn.addListener(it)
            }
        }

        private fun showCardBack() {
            itemView.cardFrontLayout.alpha = 0f
            itemView.cardBackLayout.alpha = 1f
            itemView.cardFrontLayout.rotationY = 180f
            itemView.cardBackLayout.rotationY = 0f
        }

        private fun showCardFront() {
            itemView.cardFrontLayout.alpha = 1f
            itemView.cardBackLayout.alpha = 0f
            itemView.cardFrontLayout.rotationY = 0f
            itemView.cardBackLayout.rotationY = 180f
        }

        private fun flipCard(frontLayout: View, backLayout: View) {
            if (data.isBackVisible) {
                setRightOut.setTarget(backLayout)
                setLeftIn.setTarget(frontLayout)
                setRightOut.start()
                setLeftIn.start()
            } else {
                setRightOut.setTarget(frontLayout)
                setLeftIn.setTarget(backLayout)
                setRightOut.start()
                setLeftIn.start()
            }
            data.isBackVisible = !data.isBackVisible
        }

        private fun changeCameraDistance(itemView: View) {
            val scale = itemView.resources.displayMetrics.density * FLIP_CAMERA_DISTANCE
            itemView.cardFrontLayout.cameraDistance = scale
            itemView.cardBackLayout.cameraDistance = scale
        }
    }

    private class FlipAnimationListener(
        private val animatedView: View
    ) : Animator.AnimatorListener {

        override fun onAnimationRepeat(animation: Animator?) {
            animatedView.isClickable = false
        }

        override fun onAnimationEnd(animation: Animator?) {
            animatedView.isClickable = true
        }

        override fun onAnimationCancel(animation: Animator?) {
            animatedView.isClickable = true
        }

        override fun onAnimationStart(animation: Animator?) {
            animatedView.isClickable = false
        }
    }

    companion object {
        private const val FLIP_CAMERA_DISTANCE = 100_000
    }
}
