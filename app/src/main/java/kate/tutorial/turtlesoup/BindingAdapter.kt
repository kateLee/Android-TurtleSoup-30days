package kate.tutorial.turtlesoup

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.bumptech.glide.Glide

/**
 * Created by Kate on 2021/6/18
 */
@BindingAdapter("isRefreshing")
fun isRefreshing(swipeRefreshLayout: SwipeRefreshLayout, isLoading: Boolean) {
    if (!isLoading) swipeRefreshLayout.isRefreshing = false
}

@BindingAdapter("imageUrl")
fun bindImage(view: ImageView, url: String?) {
    if (!url.isNullOrEmpty()) {
        Glide.with(view.context).load(url).centerCrop().fitCenter().into(view)
    }
}
