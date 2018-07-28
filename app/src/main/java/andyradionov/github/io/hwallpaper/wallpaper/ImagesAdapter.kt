package andyradionov.github.io.hwallpaper.wallpaper

import android.support.v7.widget.CardView
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import andyradionov.github.io.hwallpaper.R
import andyradionov.github.io.hwallpaper.app.App
import andyradionov.github.io.hwallpaper.app.GlideApp
import andyradionov.github.io.hwallpaper.model.dto.Image

/**
 * @author Andrey Radionov
 */
class ImagesAdapter(private val mClickListener: OnImageClickListener)
    : RecyclerView.Adapter<ImagesAdapter.ImageViewHolder>() {

    private val images = ArrayList<Image>()

    interface OnImageClickListener {
        fun onClick(imageUrl: String)
    }

    fun clearData() {
        images.clear()
        notifyDataSetChanged()
    }

    fun updateData(articles: List<Image>) {
        images.clear()
        images.addAll(articles)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageViewHolder {
        val cardView = LayoutInflater.from(parent.context)
                .inflate(R.layout.item_image_card, parent, false) as CardView
        return ImageViewHolder(cardView)
    }

    override fun onBindViewHolder(holder: ImageViewHolder, position: Int) {
        holder.bind(position)
    }

    override fun getItemCount(): Int {
        return images.size
    }

    inner class ImageViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView),
            View.OnClickListener {

        fun bind(position: Int) {
            val button = itemView.findViewById<Button>(R.id.btn_save)
            button.setOnClickListener(this)

            val image = images[position]
            val imageView = itemView.findViewById<ImageView>(R.id.iv_image)

            GlideApp.with(App.appContext)
                    .load(image.mediumImage)
                    .centerCrop()
                    .placeholder(R.drawable.loading_indicator)
                    .thumbnail(0.1f)
                    .into(imageView);
        }

        override fun onClick(v: View) {
            val url = images[adapterPosition].mediumImage
            mClickListener.onClick(url)
        }
    }
}