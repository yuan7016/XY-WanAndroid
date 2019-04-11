package com.xyuan.wanandroid.ui.fragments

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.alibaba.android.arouter.launcher.ARouter
import com.xyuan.wanandroid.R
import com.xyuan.wanandroid.adapter.SystemAdapter
import com.xyuan.wanandroid.base.BaseLazyLoadFragment
import com.xyuan.wanandroid.constant.PathManager
import com.xyuan.wanandroid.data.ArticleResponse
import com.xyuan.wanandroid.data.SystemBean
import com.xyuan.wanandroid.listener.BaseLiveDataObserver
import com.xyuan.wanandroid.ui.CommonSystemTabActivity
import com.xyuan.wanandroid.util.AppLog
import com.xyuan.wanandroid.viewmodel.SystemViewModel
import kotlinx.android.synthetic.main.fragement_system.*

/**
 * Created by YuanZhiQiang on 2019/03/08 15:31.
 * 体系
 */
class SystemFragment : BaseLazyLoadFragment(){

    private lateinit var mRecyclerView : RecyclerView
    private lateinit var mSwipeRefreshLayout : SwipeRefreshLayout

    private val mViewModel : SystemViewModel by lazy {
        ViewModelProviders.of(this).get(SystemViewModel::class.java)
    }

    override fun getLayoutRes(): Int {
        return R.layout.fragement_system
    }

    override fun initView(rootView: View?) {
        mRecyclerView = rootView!!.findViewById(R.id.recyclerViewSystem)
        mSwipeRefreshLayout = rootView.findViewById(R.id.swipeRefreshLayoutSystem)

        mRecyclerView.layoutManager = LinearLayoutManager(activity)
        mSwipeRefreshLayout.setColorSchemeResources(R.color.Blue_2F80ED,R.color.Red_F02B2B,R.color.Green_00A653)
        mSwipeRefreshLayout.setDistanceToTriggerSync(200)
    }

    override fun onFragmentFirstVisible() {
        super.onFragmentFirstVisible()

        mStatusLayoutManager?.showLoadingLayout()
        getTreeData()

        //刷新
        mSwipeRefreshLayout.setOnRefreshListener {
            getTreeData()
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        initStatusLayoutManager(statusLayoutSystem)

    }

    private fun getTreeData(){

        mViewModel.getTreeData().observe(this,object : BaseLiveDataObserver<ArrayList<SystemBean>>(){
            override fun onSuccess(response: ArrayList<SystemBean>) {
                AppLog.e("===onSuccess====${response.size}")
                mSwipeRefreshLayout.isRefreshing = false
                mStatusLayoutManager?.showSuccessLayout()
                if (response.isNotEmpty()){

                    val systemAdapter = SystemAdapter(response)
                    mRecyclerView.adapter = systemAdapter
                    systemAdapter.notifyDataSetChanged()

                    //条目跳转
                    systemAdapter.setOnItemClickListener { adapter, view, position ->
                        val item = adapter.getItem(position) as SystemBean
                        val intent = Intent(activity, CommonSystemTabActivity::class.java)
                        intent.putExtra("title",item.name)
                        intent.putExtra("tabList",item.children)
                        startActivity(intent)
                    }
                }else{
                    onEmpty()
                }
            }

            override fun onEmpty() {
                super.onEmpty()
                mSwipeRefreshLayout.isRefreshing = false
                mStatusLayoutManager?.showEmptyLayout()
            }

            override fun onError(throwable: Throwable?) {
                super.onError(throwable)
                mSwipeRefreshLayout.isRefreshing = false
                mStatusLayoutManager?.showErrorLayout()
            }

        })
    }


    override fun getData() {
        super.getData()

        getTreeData()
    }

}