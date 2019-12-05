package com.example.naversearchapistudy

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.fragment_blog.btn_search
import kotlinx.android.synthetic.main.fragment_blog.edit_text
import kotlinx.android.synthetic.main.fragment_blog.input_text
import kotlinx.android.synthetic.main.fragment_movie.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class KinFragment: Fragment() {

    val restClient: RequestInterface =
        ClientManager.getRetrofitService(RequestInterface::class.java)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_kin, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        btn_search.setOnClickListener {
            if(input_text != null) {
                val edit = edit_text.text.toString()
                searchKinList(edit)
            }
        }
    }

    private fun searchKinList(keyWord: String) {
        restClient.requestKin(keyWord).enqueue(object : Callback<KinData> {
            override fun onFailure(call: Call<KinData>, t: Throwable) {
                error(message = t.toString())
            }

            override fun onResponse(call: Call<KinData>, response: Response<KinData>) {
                if(response.isSuccessful) {
                    response.body()?.items?.let { kinListAdapter(it) }
                }
            }
        })

    }

    private fun kinListAdapter(kin: List<KinItems>) {

        recycleview.adapter = KinAdapter(kin)
        recycleview.layoutManager = LinearLayoutManager(activity)
        recycleview.addItemDecoration(
            DividerItemDecoration(
                activity, DividerItemDecoration.VERTICAL
            )
        )
    }

}