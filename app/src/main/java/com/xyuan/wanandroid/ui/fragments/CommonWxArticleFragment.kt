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
import com.xyuan.wanandroid.adapter.HomeAdapter
import com.xyuan.wanandroid.base.BaseLazyLoadFragment
import com.xyuan.wanandroid.constant.PathManager
import com.xyuan.wanandroid.data.ArticleResponse
import com.xyuan.wanandroid.listener.BaseLiveDataObserver
import com.xyuan.wanandroid.util.AppLog
import com.xyuan.wanandroid.viewmodel.WechatViewModel
import kotlinx.android.synthetic.main.fragement_common_article.*

/**
 * Created by YuanZhiQiang on 2019/03/08 15:31.
 * 公众号
 */
class CommonWxArticleFragment : BaseLazyLoadFragment(){

    private lateinit var mRecyclerView : RecyclerView
    private lateinit var mSwipeRefreshLayout : SwipeRefreshLayout
    private lateinit var mAdapter : HomeAdapter
    private var articleList : ArrayList<ArticleResponse.ArticleData> = arrayListOf()

    private val mViewModel : WechatViewModel by lazy {
        ViewModelProviders.of(this).get(WechatViewModel::class.java)
    }

    private var currentPage: Int = 0
    private val chapterID: Int by lazy { arguments?.getInt("id") ?: -1 }
    private val chapterName: String? by lazy { arguments?.getString("name","")}

    companion object {
        fun getNewInstance(id: Int , name : String): Fragment {
            val bundle = Bundle()
            bundle.putInt("id", id)
            bundle.putString("name", name)

            val fragment = CommonWxArticleFragment()
            fragment.arguments = bundle
            return fragment
        }
    }

    override fun getLayoutRes(): Int {
        return R.layout.fragement_common_article
    }

    override fun initView(rootView: View?) {
        mRecyclerView = rootView!!.findViewById(R.id.recyclerViewCommonArticle)
        mSwipeRefreshLayout = rootView.findViewById(R.id.swipeRefreshLayoutCommonArticle)

        mRecyclerView.layoutManager = LinearLayoutManager(activity)
        mSwipeRefreshLayout.setColorSchemeResources(R.color.Blue_2F80ED,R.color.Red_F02B2B,R.color.Green_00A653)
        mSwipeRefreshLayout.setDistanceToTriggerSync(200)

        mAdapter = HomeAdapter(articleList)
        mRecyclerView.adapter = mAdapter

        mAdapter.disableLoadMoreIfNotFullPage(mRecyclerView)
    }

    override fun onFragmentFirstVisible() {
        super.onFragmentFirstVisible()
        AppLog.e("++++onFragmentFirstVisible+++chapterName=$chapterName ===id=$chapterID")
        initStatusLayoutManager(statusLayoutCommonArticle)

        mStatusLayoutManager?.showLoadingLayout()
        getArticleData(currentPage,false)

    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        AppLog.e("++++onActivityCreated+++chapterName=$chapterName ===id=$chapterID")

        mSwipeRefreshLayout.setOnRefreshListener {
            currentPage = 0
            articleList.clear()

            getArticleData(currentPage,false)
        }

        mAdapter.setOnLoadMoreListener({
            getArticleData(currentPage,true) },mRecyclerView)

        //条目点击
        mAdapter.setOnItemClickListener { adapter, view, position ->
            val articleItem = adapter.getItem(position) as ArticleResponse.ArticleData
            ARouter
                    .getInstance()
                    .build(PathManager.WEBVIEW_ACTIVITY_PATH)
                    .withString("loadUrl",articleItem.link)
                    .withString("title",articleItem.title)
                    .navigation()
        }
    }


    private fun getArticleData(page : Int , isLoadMore : Boolean){

        mViewModel.getWxArticleData(chapterID,page).observe(this, object : BaseLiveDataObserver<ArticleResponse>(){
            override fun onSuccess(response: ArticleResponse) {
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
                    AppLog.w("===getArticleData====currentPage=$currentPage")

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
                currentPage = 0

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
        getArticleData(currentPage,false)
    }
}