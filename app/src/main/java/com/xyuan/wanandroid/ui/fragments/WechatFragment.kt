package com.xyuan.wanandroid.ui.fragments

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.xyuan.wanandroid.R
import com.xyuan.wanandroid.adapter.WxNameAdapter
import com.xyuan.wanandroid.base.BaseLazyLoadFragment
import com.xyuan.wanandroid.data.WechatNameBean
import com.xyuan.wanandroid.listener.BaseLiveDataObserver
import com.xyuan.wanandroid.viewmodel.WechatViewModel
import kotlinx.android.synthetic.main.fragement_wechat.*

/**
 * Created by YuanZhiQiang on 2019/03/08 15:31.
 * 公众号
 */
class WechatFragment : BaseLazyLoadFragment(){

    private val mViewModel : WechatViewModel by lazy {
        ViewModelProviders.of(this).get(WechatViewModel::class.java)
    }


    override fun getLayoutRes(): Int {
        return R.layout.fragement_wechat
    }

    override fun initView(rootView: View?) {

    }

    override fun onFragmentFirstVisible() {
        super.onFragmentFirstVisible()

        getWxNameData()
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        // 关联 viewPage 和 tabLayout
        tabLayoutWechat.setupWithViewPager(viewPagerWechat)

        initStatusLayoutManager(statusLayoutWechat)
    }

    private fun getWxNameData(){
        mStatusLayoutManager?.showLoadingLayout()

        mViewModel.getWxName().observe(this,object : BaseLiveDataObserver<ArrayList<WechatNameBean>>(){
            override fun onSuccess(response: ArrayList<WechatNameBean>) {
                mStatusLayoutManager?.showSuccessLayout()

                if (response.isNotEmpty()){

                    val titles = arrayListOf<String>()
                    val fragments = arrayListOf<Fragment>()

                    viewPagerWechat.offscreenPageLimit = response.size

                    for (data in response) {
                        titles.add(data.name)
                        fragments.add(CommonWxArticleFragment.getNewInstance(data.id , data.name))
                    }

                    viewPagerWechat.adapter = WxNameAdapter(childFragmentManager, titles, fragments)

                }
            }

            override fun onError(throwable: Throwable?) {
                super.onError(throwable)

                mStatusLayoutManager?.showErrorLayout()
            }
        })
    }


    override fun getData() {
        super.getData()

        getWxNameData()
    }
}