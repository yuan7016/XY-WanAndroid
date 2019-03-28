package com.xyuan.wanandroid.util

import android.text.TextUtils
import java.util.regex.Pattern

/**
 * Created by YuanZhiQiang on 2019/03/28 14:40
 * Desc:
 */
object HtmlUtil {


    private const val regEx_html = "<[^>]+>" // 定义HTML标签的正则表达式
    private const val regEx_space = "\\s*|\t|\r|\n"//定义空格回车换行符


    /**
     * @param htmlStr
     * @return 删除Html标签
     */
    fun delHTMLTag(htmlStr: String): String {
        var htmlStr = htmlStr

        val p_html = Pattern.compile(regEx_html, Pattern.CASE_INSENSITIVE)
        val m_html = p_html.matcher(htmlStr)
        htmlStr = m_html.replaceAll("") // 过滤html标签

        val p_space = Pattern.compile(regEx_space, Pattern.CASE_INSENSITIVE)
        val m_space = p_space.matcher(htmlStr)
        htmlStr = m_space.replaceAll("") // 过滤空格回车标签
        return htmlStr.trim { it <= ' ' } // 返回文本字符串
    }

    fun getTextFromHtml(htmlStr: String): String {
        var htmlStr = htmlStr
        htmlStr = delHTMLTag(htmlStr)
        htmlStr = htmlStr.replace(" ".toRegex(), "")
        return htmlStr
    }


    /**
     * html 解码
     * @param source
     * @return
     */
    fun htmlDecode(str: String): String {

        var source = str
        if (TextUtils.isEmpty(source)) {
            return ""
        }
        source = source.replace("&lt;", "<")
        source = source.replace("&gt;", ">")
        source = source.replace("&amp;", "&")
        source = source.replace("&mdash;", "—")
        source = source.replace("&quot;", "\"")
        source = source.replace("&nbsp;", " ")
        source = source.replace("&ldquo;", "\"")
        source = source.replace("&rdquo;", "\"")

        return getTextFromHtml(source)
    }
}