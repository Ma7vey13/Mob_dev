package com.mirea.mitrofanovms.mireaproject

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import retrofit2.*
import retrofit2.converter.gson.GsonConverterFactory

class SimpleNewsFragment : Fragment() {

    private lateinit var textView: TextView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_simple_news, container, false)
        textView = view.findViewById(R.id.newsTextView)
        loadPosts()
        return view
    }

    private fun loadPosts() {
        val retrofit = Retrofit.Builder()
            .baseUrl("https://jsonplaceholder.typicode.com/ ")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val api = retrofit.create(PostApi::class.java)
        api.getPosts().enqueue(object : Callback<List<Post>> {
            override fun onResponse(call: Call<List<Post>>, response: Response<List<Post>>) {
                if (response.isSuccessful && response.body() != null) {
                    val posts = response.body()!!
                    val text = StringBuilder()
                    for (post in posts) {
                        text.append("ID: ${post.id}\n")
                            .append("Заголовок: ${post.title}\n")
                            .append("Текст: ${post.body}\n\n")
                    }
                    textView.text = text.toString()
                } else {
                    textView.text = "Ошибка загрузки данных"
                }
            }

            override fun onFailure(call: Call<List<Post>>, t: Throwable) {
                textView.text = "Ошибка сети: ${t.message}"
            }
        })
    }
}