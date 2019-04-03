package com.xyuan.wanandroid.ui.fragments

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.xyuan.wanandroid.R
import com.xyuan.wanandroid.adapter.CommonFragmentPagerAdapter
import com.xyuan.wanandroid.base.BaseLazyLoadFragment
import com.xyuan.wanandroid.data.CommonTabNameBean
import com.xyuan.wanandroid.listener.BaseLiveDataObserver
import com.xyuan.wanandroid.viewmodel.ProjectViewModel
import kotlinx.android.synthetic.main.fragement_project.*

/**
 * Created by YuanZhiQiang on 2019/03/08 15:31.
 * 项目
 */
class ProjectFragment : BaseLazyLoadFragment(){

    private val mViewModel : ProjectViewModel by lazy {
        ViewModelProviders.of(this).get(ProjectViewModel::class.java)
    }


    override fun getLayoutRes(): Int {
        return R.layout.fragement_project
    }

    override fun initView(rootView: View?) {

    }

    override fun onFragmentFirstVisible() {
        super.onFragmentFirstVisible()

        getProjectTabData()
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        // 关联 viewPage 和 tabLayout
        tabLayoutProject.setupWithViewPager(viewPagerProject)

        initStatusLayoutManager(statusLayoutProject)
    }


    private fun getProjectTabData(){
        mViewModel.getProjectName().observe(this, object  : BaseLiveDataObserver<ArrayList<CommonTabNameBean>>(){
            override fun onSuccess(response: ArrayList<CommonTabNameBean>) {
                mStatusLayoutManager?.showSuccessLayout()

                if (response.isNotEmpty()){

                    val titles = arrayListOf<String>()
                    val fragments = arrayListOf<Fragment>()

                    viewPagerProject.offscreenPageLimit = response.size

                    for (data in response) {
                        titles.add(data.name)
                        fragments.add(CommonProjectFragment.getNewInstance(data.id , data.name))
                    }

                    viewPagerProject.adapter = CommonFragmentPagerAdapter(childFragmentManager, titles, fragments)

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

        getProjectTabData()
    }
}