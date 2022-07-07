package com.ujjwal.memes

import android.content.Intent
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.solver.widgets.ConstraintWidget.VISIBLE
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.cronet.CronetHttpStack
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target


class MainActivity : AppCompatActivity() {
    var nurl:String?=null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        lowermeme()
    }

    private fun lowermeme()
    {
        val progessBar:ProgressBar = findViewById(R.id.progressBar) as ProgressBar
        progessBar.visibility=View.VISIBLE
        // Instantiate the RequestQueue.
        val imageView: ImageView = findViewById<View>(R.id.imageView) as ImageView
        val queue = Volley.newRequestQueue(this)
        val url = "https://meme-api.herokuapp.com/gimme"

// Request a string response from the provided URL.
        val jsonRequest =JsonObjectRequest(Request.Method.GET, url,null,
                Response.Listener { response ->
                    nurl = response.getString("url")
                   // progessBar.visibility=View.INVISIBLE
                    Glide.with(this).load(nurl).listener(object:RequestListener<Drawable>{
                        override fun onLoadFailed(e: GlideException?, model: Any?, target: Target<Drawable>?, isFirstResource: Boolean): Boolean {
                            progessBar.visibility=View.GONE
                            return false
                        }

                        override fun onResourceReady(resource: Drawable?, model: Any?, target: Target<Drawable>?, dataSource: DataSource?, isFirstResource: Boolean): Boolean {
                            progessBar.visibility=View.GONE
                            return false
                        }
                    }).into(imageView)

                }, Response.ErrorListener {
            Toast.makeText(this,"something went wrong",Toast.LENGTH_LONG).show()

        })

// Add the request to the RequestQueue.
        queue.add(jsonRequest)


    }
    fun share(view: View)
    {
      val intent = Intent(Intent.ACTION_SEND)
        intent.putExtra(Intent.EXTRA_TEXT,"hey! checkout this cool meme $nurl")
        intent.type="plain/text"
        val chooser = Intent.createChooser(intent, "share with...")

// Verify the original intent will resolve to at least one activity
       if (intent.resolveActivity(packageManager) != null) {
            startActivity(chooser)
        }
    }
    fun next(view: View)
    {
      lowermeme()
    }
}
