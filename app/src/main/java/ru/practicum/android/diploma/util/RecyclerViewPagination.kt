import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

const val DEFAULT_THRESHOLD = 5

class EndReachedListener(
    private val layoutManager: LinearLayoutManager,
    private val threshold: Int = DEFAULT_THRESHOLD,
    private val onEndReached: () -> Unit
) : RecyclerView.OnScrollListener() {

    private var isLoading = false
    private var isEndReached = false

    override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
        if (dy <= 0 || isLoading || isEndReached) return

        val visibleItemCount = layoutManager.childCount
        val totalItemCount = layoutManager.itemCount
        val firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition()

        if (visibleItemCount + firstVisibleItemPosition + threshold >= totalItemCount) {
            isLoading = true
            onEndReached()
        }
    }

    fun endReachedProcessed(hasMoreItems: Boolean) {
        isLoading = false
        isEndReached = !hasMoreItems
    }
}

fun RecyclerView.addEndReachedListener(
    layoutManager: LinearLayoutManager,
    threshold: Int = 5,
    onEndReached: () -> Unit
): EndReachedListener {
    val listener = EndReachedListener(layoutManager, threshold, onEndReached)
    this.addOnScrollListener(listener)
    return listener
}
