package com.example.githubuserapp.widget

import android.content.Context
import android.content.Intent
import android.database.Cursor
import android.graphics.Bitmap
import android.os.Binder
import android.widget.RemoteViews
import android.widget.RemoteViewsService
import com.bumptech.glide.Glide
import com.example.githubuserapp.R
import com.example.githubuserapp.activity.DetailActivity
import com.example.githubuserapp.activity.MainActivity
import com.example.githubuserapp.model.User
import com.example.githubuserapp.utils.MappingHelper

class StackRemoteViewsFactory(private val context: Context) : RemoteViewsService.RemoteViewsFactory {
    private var userItems = ArrayList<User>()
    private var images = ArrayList<Bitmap>()
    private var cursor: Cursor? = null

    override fun onCreate() {

    }

    override fun getLoadingView(): RemoteViews? = null

    override fun getItemId(position: Int): Long = 0

    override fun onDataSetChanged() {
        cursor?.close()
        userItems.clear()
        images.clear()

        val identityToken = Binder.clearCallingIdentity()

        cursor = context.contentResolver.query(MainActivity.URI_FAVORITE, null, null, null, null, null)
        userItems = MappingHelper.cursorToArrayList(cursor)
        userItems.forEachIndexed { index, _ ->
            val bitmap = Glide.with(context)
                .asBitmap()
                .load(userItems[index].avatarUrl)
                .submit()
                .get()
            images.add(bitmap)
        }

        Binder.restoreCallingIdentity(identityToken)
    }

    override fun hasStableIds(): Boolean = false

    override fun getViewAt(position: Int): RemoteViews {
        val rv = RemoteViews(context.packageName, R.layout.item_favorite_widget)

        if (images.size >= 1) {
            val intent = Intent(context, DetailActivity::class.java)
                .putExtra("username", userItems[position].username)
                .putExtra("id", userItems[position].id)
                .putExtra("avatarUrl", userItems[position].avatarUrl)

            rv.setImageViewBitmap(R.id.iv_item_widget_favorite, images[position])
            rv.setOnClickFillInIntent(R.id.iv_item_widget_favorite, intent)
        }

        return rv
    }

    override fun getCount(): Int  = images.size

    override fun getViewTypeCount(): Int = 1

    override fun onDestroy() {

    }
}