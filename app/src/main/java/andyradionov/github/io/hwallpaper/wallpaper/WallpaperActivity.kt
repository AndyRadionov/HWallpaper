package andyradionov.github.io.hwallpaper.wallpaper

import android.app.WallpaperManager
import android.graphics.Bitmap
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.SearchView
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.inputmethod.EditorInfo
import andyradionov.github.io.hwallpaper.R
import andyradionov.github.io.hwallpaper.app.App
import andyradionov.github.io.hwallpaper.app.GlideApp
import andyradionov.github.io.hwallpaper.model.dto.Image
import com.bumptech.glide.request.target.SimpleTarget
import com.bumptech.glide.request.transition.Transition
import de.mateware.snacky.Snacky
import kotlinx.android.synthetic.main.activity_wallpaper.*
import javax.inject.Inject

private const val EXTRA_QUERY = "extra_query"
private const val EMPTY_QUERY = ""

class WallpaperActivity : AppCompatActivity(), WallpaperContract.View, ImagesAdapter.OnImageClickListener {

    @Inject lateinit var presenter: WallpaperContract.Presenter
    private lateinit var imagesAdapter: ImagesAdapter
    private var query: String = EMPTY_QUERY


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_wallpaper)

        App.appComponent.inject(this)
        setUpRecycler()
        presenter.attachView(this)
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.detachView()
    }

    override fun onResume() {
        super.onResume()
        if (query.isEmpty()) {
            presenter.getLatestImages()
        }
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle?) {
        super.onRestoreInstanceState(savedInstanceState)
        query = savedInstanceState?.getString(EXTRA_QUERY) ?: EMPTY_QUERY
    }

    override fun onSaveInstanceState(outState: Bundle?) {
        super.onSaveInstanceState(outState)
        outState?.putString(EXTRA_QUERY, query)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.main, menu)

        val searchAction = menu.findItem(R.id.action_search)

        val searchView = searchAction.actionView as SearchView
        searchView.maxWidth = Integer.MAX_VALUE
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(q: String): Boolean {
                showLoading()
                query = q
                presenter.searchImages(query)
                return false
            }

            override fun onQueryTextChange(newText: String): Boolean {
                return false
            }
        })

        searchAction.setOnActionExpandListener(object : MenuItem.OnActionExpandListener {
            override fun onMenuItemActionExpand(item: MenuItem): Boolean {
                searchView.imeOptions = EditorInfo.IME_FLAG_FORCE_ASCII
                return true
            }

            override fun onMenuItemActionCollapse(item: MenuItem): Boolean {
                invalidateOptionsMenu()
                query = EMPTY_QUERY
                presenter.getLatestImages()
                showLoading()
                return true
            }
        })

        if (!query.isEmpty()) {
            searchAction.expandActionView()
            searchView.setQuery(query, true)
        }

        return true
    }

    override fun showImages(images: List<Image>) {
        setVisibility(container = true)
        imagesAdapter.updateData(images)
    }

    override fun showError() {
        setVisibility(empty = true)
        imagesAdapter.clearData();
    }

    override fun noInternet() {
        setVisibility(internet = true)
    }

    override fun onClick(imageUrl: String) {
        GlideApp.with(this)
                .asBitmap()
                .load(imageUrl)
                .into(object : SimpleTarget<Bitmap>() {
                    override fun onResourceReady(resource: Bitmap, transition: Transition<in Bitmap>?) {
                        try {
                            WallpaperManager.getInstance(App.appContext).setBitmap(resource)
                            Snacky.builder().setText("Wallpaper set!")
                                    .setActivity(this@WallpaperActivity).success().show()

                        } catch (e: Exception) {
                            Snacky.builder().setText("Error setting wallpaper!")
                                    .setActivity(this@WallpaperActivity).error().show()
                        }
                    }
                })
    }

    private fun setUpRecycler() {
        imagesAdapter = ImagesAdapter(this)

        val columnsNumber = resources.getInteger(R.integer.columns_number)
        val layoutManager = GridLayoutManager(this, columnsNumber)

        rv_images_container.layoutManager = layoutManager
        rv_images_container.adapter = imagesAdapter
    }

    private fun showLoading() {
        setVisibility(loading = true)
        imagesAdapter.clearData();
    }

    private fun setVisibility(container: Boolean = false,
                              loading: Boolean = false,
                              empty: Boolean = false,
                              internet: Boolean = false) {

        rv_images_container.visibility = if (container) View.VISIBLE else View.INVISIBLE
        pb_loading.visibility = if (loading) View.VISIBLE else View.INVISIBLE
        tv_empty_view.visibility = if (empty) View.VISIBLE else View.INVISIBLE
        iv_no_wifi.visibility = if (internet) View.VISIBLE else View.INVISIBLE
    }
}
