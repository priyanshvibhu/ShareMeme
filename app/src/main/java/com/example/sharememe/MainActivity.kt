package com.example.sharememe

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.bumptech.glide.Glide
import com.bumptech.glide.RequestBuilder
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.sharememe.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {
    var currentUrl:String?=null
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        loadMeme();
    }
    private fun loadMeme(){
        val url="https://meme-api.com/gimme/wholesomememes"

        val jsonObjectRequest = JsonObjectRequest(
            Request.Method.GET, url,null,
            Response.Listener { response ->
                    currentUrl=response.getString("url")
                    Glide.with(this).load(currentUrl).skipMemoryCache(true).diskCacheStrategy(
                        DiskCacheStrategy.NONE).placeholder(R.drawable.placeholder).into(binding.memeImageView)

                // Display the first 500 characters of the response string
            },
            Response.ErrorListener {  })

// Add the request to the RequestQueue.
        MySingleton.getInstance(this).addToRequestQueue(jsonObjectRequest)
    }

    fun shareMeme(view: View) {
        val intent=Intent(Intent.ACTION_SEND)
        intent.type="text/plain"
        intent.putExtra(Intent.EXTRA_TEXT,"Check this meme out $currentUrl")
        val chooser=Intent.createChooser(intent,"Share it using ")
        startActivity(chooser)
    }
    fun nextMeme(view: View) {
        loadMeme()
    }
}

private fun <TranscodeType> RequestBuilder<TranscodeType>.into(memeImageView: Any) {

}
