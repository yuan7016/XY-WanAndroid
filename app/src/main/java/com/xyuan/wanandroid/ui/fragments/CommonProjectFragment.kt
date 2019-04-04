package com.xyuan.wanandroid.ui.fragments

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.alibaba.android.arouter.launcher.ARouter
import com.xyuan.wanandroid.R
import com.xyuan.wanandroid.adapter.ProjectAdapter
import com.xyuan.wanandroid.base.BaseLazyLoadFragment
import com.xyuan.wanandroid.constant.PathManager
import com.xyuan.wanandroid.data.ProjectResponse
import com.xyuan.wanandroid.listener.BaseLiveDataObserver
import com.xyuan.wanandroid.util.AppLog
import com.xyuan.wanandroid.viewmodel.ProjectViewModel
import kotlinx.android.synthetic.main.fragement_project.*

/**
 * Created by YuanZhiQiang on 2019/04/03
 * 项目
 */
class CommonProjectFragment : BaseLazyLoadFragment(){

    private lateinit var mRecyclerView : RecyclerView
    private lateinit var mSwipeRefreshLayout : SwipeRefreshLayout
    private lateinit var mAdapter : ProjectAdapter
    private var mAllProjectList : ArrayList<ProjectResponse.ProjectItemData> = arrayListOf()

    private val mViewModel : ProjectViewModel by lazy {
        ViewModelProviders.of(this).get(ProjectViewModel::class.java)
    }

    private var currentPage: Int = 1 //项目列表 page 从1开始
    private val chapterID: Int by lazy { arguments?.getInt("id") ?: -1 }
    private val chapterName: String? by lazy { arguments?.getString("name","")}

    companion object {
        fun getNewInstance(id: Int , name : String): Fragment {
            val bundle = Bundle()
            bundle.putInt("id", id)
            bundle.putString("name", name)

            val fragment = CommonProjectFragment()
            fragment.arguments = bundle
            return fragment
        }
    }

    override fun getLayoutRes(): Int {
        return R.layout.fragement_common_project
    }

    override fun initView(rootView: View?) {
        mRecyclerView = rootView!!.findViewById(R.id.recyclerViewProject)
        mSwipeRefreshLayout = rootView.findViewById(R.id.swipeRefreshLayoutProject)

        mRecyclerView.layoutManager = LinearLayoutManager(activity)
        mSwipeRefreshLayout.setColorSchemeResources(R.color.Blue_2F80ED,R.color.Red_F02B2B,R.color.Green_00A653)
        mSwipeRefreshLayout.setDistanceToTriggerSync(200)

        mAdapter = ProjectAdapter(mAllProjectList)
        mRecyclerView.adapter = mAdapter
        mAdapter.disableLoadMoreIfNotFullPage(mRecyclerView)

    }

    override fun onFragmentFirstVisible() {
        super.onFragmentFirstVisible()
        AppLog.e("++Project++onFragmentFirstVisible+++chapterName=$chapterName ===id=$chapterID")
        initStatusLayoutManager(statusLayoutProject)

        mStatusLayoutManager?.showLoadingLayout()
        getProjectListData(currentPage,false)

    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        AppLog.e("++Project++onActivityCreated+++chapterName=$chapterName ===id=$chapterID")

        mSwipeRefreshLayout.setOnRefreshListener {
            currentPage = 1
            mAllProjectList.clear()

            getProjectListData(currentPage,false)
        }

        mAdapter.setOnLoadMoreListener({
            getProjectListData(currentPage,true) },mRecyclerView)

        //条目点击
        mAdapter.setOnItemClickListener { adapter, view, position ->
            val projectItem = adapter.getItem(position) as ProjectResponse.ProjectItemData
            ARouter
                    .getInstance()
                    .build(PathManager.WEBVIEW_ACTIVITY_PATH)
                    .withString("loadUrl",projectItem.link)
                    .withString("title",projectItem.title)
                    .navigation()
        }
    }


    private fun getProjectListData(page : Int , isLoadMore : Boolean){

        mViewModel.getProjectListData(chapterID,page).observe(this, object : BaseLiveDataObserver<ProjectResponse>(){
            override fun onSuccess(response: ProjectResponse) {
                mStatusLayoutManager?.showSuccessLayout()
                if (!isLoadMore){
                    mSwipeRefreshLayout.isRefreshing = false
                }

                if (response.datas!!.isNotEmpty()){
                    if (isLoadMore){
                        mAdapter.addData(response.datas!!)
                    }else{
                        mAdapter.setNewData(response.datas!!)
                    }

                }else{
                    onEmpty()
                }

                if (response.hasNextPage()){
                    mAdapter.setEnableLoadMore(true)

                    currentPage ++
                    AppLog.w("===getProjectListData====currentPage=$currentPage")
                    if (isLoadMore){
                        mAdapter.loadMoreComplete()
                    }

                }else{

                    if (isLoadMore){
                        mAdapter.loadMoreEnd()
                    }

                    mAdapter.setEnableLoadMore(false)
                }
            }

            override fun onEmpty() {
                super.onEmpty()
                mStatusLayoutManager?.showEmptyLayout()
            }

            override fun onError(throwable: Throwable?) {
                super.onError(throwable)
                currentPage = 1

                if (isLoadMore){
                    mAdapter.loadMoreFail()
                }else{
                    mSwipeRefreshLayout.isRefreshing = false

                    mStatusLayoutManager?.showErrorLayout()
                }
            }
        })
    }


    override fun getData() {
        super.getData()
        mStatusLayoutManager?.showLoadingLayout()
        getProjectListData(currentPage,false)
    }
}