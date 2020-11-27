package jp.techacademy.moe.hatori.apiapp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.app.Activity
import android.content.Intent
import android.util.Log
import androidx.core.content.res.ResourcesCompat
import kotlinx.android.synthetic.main.activity_web_view.*

class WebViewActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_web_view)

        //★新着一覧からWebページへの処理
        //新着一覧ORお気に入り一覧のどちらがクリックされたかの判定
        val shop = intent.getSerializableExtra(KEY_SHOP) as? Shop
        val favshop:Array<String> = intent.getStringArrayExtra(KEY_SHOP_Fav)


        if (shop != null){
            shop?.let { validShop ->
                val url = if (shop.couponUrls.sp.isNotEmpty()) shop.couponUrls.sp else shop.couponUrls.pc
                Log.d("Test",url)
                webView.loadUrl(url)
                //★fabにお気に入りの情報をおくる fab.~(intent~)
                val favoriteShop = FavoriteShop.findBy(validShop.id)
                val resId = getStarIconResId(favoriteShop)
                fab.setImageDrawable(resources.getDrawable(resId, null))
            }
        }else{
            webView.loadUrl(favshop[1].toString())
           // webView.loadUrl(intent.getStringExtra(KEY_SHOP_Fav))

            fab.setImageDrawable(resources.getDrawable(R.drawable.ic_star,null))
        }


        //★フローティングボタンがクリックされたとき
        fab.setOnClickListener { view ->




        }
    }

    private fun getStarIconResId(shop: FavoriteShop?): Int {
        return if (shop == null) R.drawable.ic_star_border else R.drawable.ic_star
    }

    companion object {
        private const val KEY_SHOP = "key_shop"
        //private const val KEY_SHOP_Fav_id = "key_shop_fav"
        private const val KEY_SHOP_Fav = "key_shop_fav"


        //★新着一覧がクリックされた場合
        fun start(activity: Activity, shop: Shop) {
            Log.d("Test","新着順ページ："+shop.toString())
            activity.startActivity(Intent(activity, WebViewActivity::class.java).putExtra(KEY_SHOP,shop))
        }
        //★お気に入り一覧がクリックされた場合
        fun startFav(activity: Activity, favoriteShop: FavoriteShop) {
//            activity.startActivity(Intent(activity, WebViewActivity::class.java).putExtra(
//                KEY_SHOP_Fav,favoriteShop.url))
            activity.startActivity(Intent(activity, WebViewActivity::class.java).putExtra(
                KEY_SHOP_Fav, arrayOf(favoriteShop.id,favoriteShop.url)
            ))
//            Log.d("Test","新着順ページ："+arrayListOf(favoriteShop.id,favoriteShop.url))
        }


    }

}