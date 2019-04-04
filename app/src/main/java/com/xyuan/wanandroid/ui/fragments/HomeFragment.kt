package com.xyuan.wanandroid.ui.fragments

import android.os.Bundle
import android.view.View
import androidx.core.widget.NestedScrollView
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.alibaba.android.arouter.launcher.ARouter
import com.hjq.toast.ToastUtils
import com.xyuan.wanandroid.R
import com.xyuan.wanandroid.adapter.HomeAdapter
import com.xyuan.wanandroid.base.BaseLazyLoadFragment
import com.xyuan.wanandroid.constant.PathManager
import com.xyuan.wanandroid.data.ArticleResponse
import com.xyuan.wanandroid.data.BannerBean
import com.xyuan.wanandroid.data.EmptyResponse
import com.xyuan.wanandroid.listener.BaseLiveDataObserver
import com.xyuan.wanandroid.util.AppLog
import com.xyuan.wanandroid.util.GlideImageLoader
import com.xyuan.wanandroid.viewmodel.HomeViewModel
import com.xyuan.xybanner.BannerConfig
import com.xyuan.xybanner.Transformer
import com.xyuan.xybanner.listener.OnBannerClickListener
import kotlinx.android.synthetic.main.fragement_home.*


/**
 * Created by YuanZhiQiang on 2019/03/08 15:31.
 * 首页
 */
class HomeFragment : BaseLazyLoadFragment(){

    private var currentPage = 0

    private lateinit var mAdapter : HomeAdapter
    private var articleList : ArrayList<ArticleResponse.ArticleData> = arrayListOf()

    private val mViewModel : HomeViewModel by lazy {
        ViewModelProviders.of(this).get(HomeViewModel::class.java)
    }

    override fun getLayoutRes(): Int {
        return R.layout.fragement_home
    }

    override fun initView(rootView: View?) {

    }

    override fun onFragmentFirstVisible() {
        super.onFragmentFirstVisible()

        fab_toTop.hide()

        initBanner()
    }

    private fun initBanner() {

        //设置banner样式
        banner.setBannerStyle(BannerConfig.CIRCLE_INDICATOR_TITLE_INSIDE)
        banner.setImageLoader(GlideImageLoader())
        //设置banner动画效果
        banner.setBannerAnimation(Transformer.Tablet)
        banner.isAutoPlay(true)

    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        mAdapter = HomeAdapter(articleList)
        recyclerViewHome.layoutManager = LinearLayoutManager(activity)
        recyclerViewHome.adapter = mAdapter

        initStatusLayoutManager(frameLayoutStatus)

        nested_scroll_view.setOnScrollChangeListener { v: NestedScrollView?, scrollX: Int, scrollY: Int, oldScrollX: Int, oldScrollY: Int ->
            if (scrollY >= 2000){
                fab_toTop.show()
            }else{
                fab_toTop.hide()
            }
        }

        //展开
        fab_toTop.setOnClickListener { view ->
            app_bar_layout.setExpanded(true,true)
            nested_scroll_view.scrollTo(0,0)
        }

        mStatusLayoutManager?.showLoadingLayout()
        getData()

        //刷新
        smartRefreshLayout.setOnRefreshListener {
            currentPage = 0
            articleList.clear()
            smartRefreshLayout.resetNoMoreData()

            getData()
        }

        //上拉加载
        smartRefreshLayout.setOnLoadMoreListener {
            getMoreData()
        }

        //条目点击
        mAdapter.setOnItemClickListener { adapter, view, position ->
            val articleData = articleList[position]
            ARouter
                    .getInstance()
                    .build(PathManager.WEBVIEW_ACTIVITY_PATH)
                    .withString("loadUrl",articleData.link)
                    .withString("title",articleData.title)
                    .navigation()
        }

        //条目里子View点击
        mAdapter.setOnItemChildClickListener { adapter, view, position ->
            val articleData = articleList[position]

            when (view.id){
                R.id.iv_item_home_favorite  -> {  //收藏 或取消收藏

                    mViewModel.collectArticle(articleData.id , articleData.collect).observe(this,object : BaseLiveDataObserver<EmptyResponse>(){
                        override fun onSuccess(response: EmptyResponse) {
                            AppLog.i("==收藏=collectArticle====onSuccess")
                            if (response.success){
                                val collectState = articleData.collect
                                articleData.collect = !collectState
                                mAdapter.setData(position,articleData)
                                ToastUtils.show("操作成功")
                            }

                        }
                        override fun onError(throwable: Throwable?) {
                            super.onError(throwable)
                            AppLog.i("==收藏=collectArticle====onError")
                        }

                    })

                }

            }

        }
    }


    override fun getData() {
        super.getData()
        getArticleData(0)

        getBannerData()
    }

    private fun getArticleData(page : Int){

        mViewModel.getArticleData(page).observe(this,object : BaseLiveDataObserver<ArticleResponse>(){
            override fun onSuccess(response: ArticleResponse) {
                AppLog.w("===getData====onSuccess")
                smartRefreshLayout.finishRefresh()
                mStatusLayoutManager?.showSuccessLayout()
                if (response.datas!!.isNotEmpty()){
                    AppLog.w("===getData====isNotEmpty---size=${response.datas!!.size}")
                    articleList.addAll(response.datas!!)

                    mAdapter.notifyDataSetChanged()
                }else{
                    onEmpty()
                }

                if (response.hasNextPage()){
                    currentPage ++
                    AppLog.w("===getData====currentPage=$currentPage")

                    smartRefreshLayout.setEnableLoadMore(true)
                }else{
                    smartRefreshLayout.setEnableLoadMore(false)
                }
            }

            override fun onEmpty() {
                super.onEmpty()
                currentPage = 0
                mStatusLayoutManager?.showEmptyLayout()
            }

            override fun onError(throwable: Throwable?) {
                super.onError(throwable)
                currentPage = 0
                smartRefreshLayout.finishRefresh()
                mStatusLayoutManager?.showErrorLayout()
            }
        })

    }

    private fun getMoreData(){
        AppLog.w("===getMoreData==currentPage=$currentPage")
        mViewModel.getArticleData(currentPage).observe(this,object : BaseLiveDataObserver<ArticleResponse>(){
            override fun onSuccess(response: ArticleResponse) {
                AppLog.w("===getMoreData====onSuccess-")
                if (response.datas!!.isNotEmpty()){
                    mAdapter.addData(response.datas!!)
                }

                if (response.hasNextPage()){
                    currentPage ++

                    smartRefreshLayout.finishLoadMore()
                    AppLog.w("===getMoreData===finishLoadMore--=currentPage=$currentPage")
                }else{
                    smartRefreshLayout.finishLoadMoreWithNoMoreData()
                    AppLog.w("===getMoreData===finishLoadMoreWithNoMoreData--=currentPage=$currentPage")
                }

            }

            override fun onError(throwable: Throwable?) {
                super.onError(throwable)
                smartRefreshLayout.finishLoadMore(false)
                AppLog.w("===getMoreData====onError")
            }
        })
    }

    private fun getBannerData(){

        mViewModel.getBannerData().observe(this,object : BaseLiveDataObserver<ArrayList<BannerBean>>(){
            override fun onSuccess(response: ArrayList<BannerBean>) {
                if (response.isNotEmpty()){

                    val images : ArrayList<Any> = arrayListOf()
                    val titles : ArrayList<String> = arrayListOf()
                    for (banner in response) {
                        images.add(banner.imagePath)
                        titles.add(banner.title)
                    }

                    if (images.isNotEmpty()){
                        banner.setImages(images)
                    }

                    if (titles.isNotEmpty()){
                        banner.setBannerTitles(titles)
                    }

                    banner.start()


                    banner.setOnBannerListener(object : OnBannerClickListener{
                        override fun onBannerClick(position: Int) {
                            ToastUtils.show("点击了:$position===${response[position].title}")
                        }
                    })

                }
            }

        })
    }

    override fun onResume() {
        super.onResume()
        banner.startAutoPlay()
    }

    override fun onStop() {
        super.onStop()
        banner.stopAutoPlay()
    }

}