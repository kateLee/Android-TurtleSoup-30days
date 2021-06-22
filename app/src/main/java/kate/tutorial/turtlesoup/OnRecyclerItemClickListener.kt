package kate.tutorial.turtlesoup

import android.view.GestureDetector
import android.view.MotionEvent
import android.view.View
import androidx.recyclerview.widget.RecyclerView

/**
 * Created by Kate on 2021/6/22
 */
class OnRecyclerItemTouchListener(val onItemClick: (RecyclerView.ViewHolder) -> Unit = {},
                                  val onItemLongClick: (RecyclerView.ViewHolder) -> Unit = {}) : RecyclerView.OnItemTouchListener {
    private lateinit var gestureDetector: GestureDetector

    private fun initGestureDetector(recyclerView: RecyclerView): GestureDetector {
        return GestureDetector(recyclerView.context, object : GestureDetector.SimpleOnGestureListener() {
            override fun onSingleTapUp(e: MotionEvent): Boolean {
                val child: View? = recyclerView.findChildViewUnder(e.x, e.y)
                child?.let { onItemClick(recyclerView.getChildViewHolder(child))}
                return true
            }

            override fun onLongPress(e: MotionEvent) {
                val child: View? = recyclerView.findChildViewUnder(e.x, e.y)
                child?.let { onItemLongClick(recyclerView.getChildViewHolder(child))}
            }
        })
    }

    override fun onInterceptTouchEvent(recyclerView: RecyclerView, e: MotionEvent): Boolean {
        if (!this::gestureDetector.isInitialized) gestureDetector = initGestureDetector(recyclerView)
        return gestureDetector.onTouchEvent(e)
    }
    override fun onTouchEvent(recyclerView: RecyclerView, e: MotionEvent) {
        gestureDetector.onTouchEvent(e)
    }
    override fun onRequestDisallowInterceptTouchEvent(disallowIntercept: Boolean) {
    }
}
