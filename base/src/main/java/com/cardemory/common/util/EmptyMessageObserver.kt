package com.cardemory.common.util

import android.view.View
import androidx.recyclerview.widget.RecyclerView

class EmptyMessageObserver(
    private val adapter: RecyclerView.Adapter<*>,
    private vararg val messageView: View
) : RecyclerView.AdapterDataObserver() {

    override fun onChanged() {
        super.onChanged()
        val visibility = if (adapter.itemCount == 0) View.VISIBLE else View.GONE
        messageView.forEach {
            it.visibility = visibility
        }
    }
}
