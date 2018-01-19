package com.linecy.copy.data.datasource

import com.linecy.core.data.model.HomeModel
import com.linecy.core.data.model.ItemList
import com.linecy.core.data.model.TabModel
import com.linecy.core.utils.MoshiUtils

/**
 * @author by linecy.
 */
object MockResponse {


  fun createTabList(): List<TabModel> {
    val list = ArrayList<TabModel>()
    list.add(TabModel(1, "创意", "技术与审美结合，探索视觉的无限可能"))
    list.add(TabModel(2, "音乐", "全球最酷、最炫、最有态度的音乐集合"))
    list.add(TabModel(3, "旅行", "发现世界的奇妙和辽阔"))
    list.add(TabModel(4, "科普", "每天获得新知识"))
    list.add(TabModel(5, "搞笑", "哈哈哈哈哈哈哈哈"))
    list.add(TabModel(6, "时尚", "优雅地行走在潮流尖端"))
    list.add(TabModel(7, "运动", "冲浪、滑板、跑酷、骑行、生命停不下来"))
    list.add(TabModel(8, "动画", "有趣的人永远不缺童心"))
    list.add(TabModel(9, "广告", "为广告人的精彩创意人点赞"))
    list.add(TabModel(10, "开胃", "眼球和味蕾，一个都不放过"))
    list.add(TabModel(11, "生活", "匠心、健康、生活感悟"))
    list.add(TabModel(12, "剧情", "用一个好故事、描述生活的不可思议"))
    list.add(TabModel(13, "预告", "电影、剧集、戏剧抢先看"))
    list.add(TabModel(14, "集锦", "最好的部分 + 有化学反应的混剪"))
    list.add(TabModel(15, "记录", "告诉他们为什么与众不同"))
    list.add(TabModel(16, "游戏", "欢迎来到惊险刺激的新世界"))
    list.add(TabModel(17, "萌宠", "来自汪星、喵星、蠢萌的你"))
    list.add(TabModel(18, "综艺", "全球网红在表演什么"))

    return list
  }

  fun createHomeData(size: Int): HomeModel {
    val str = "{\"type\":\"video\",\"data\":{\"dataType\":\"VideoBeanForClient\",\"id\":76102,\"title\":\"特条 |「公牛历险记」终极预告\",\"slogan\":\"冒险之旅即将爆笑开启。\",\"description\":\"由打造「冰川时代」系列和「里约大冒险」系列的蓝天工作室原班人马制作的「公牛历险记」，将于 2018 年 1 月 19 日以 2D、3D、中国巨幕 3D、杜比全景声、杜比视界制式登陆全国影院与观众见面。公牛费迪南和小女孩一家人温馨十足的成长之路、与萌物小伙伴们众志成城反抗出逃的坚定决心，以及冒险途中「笑」果感动齐飞的精彩故事，都让人期待不已。一起走进电影院，享受这部开年最欢脱的动画电影吧！\",\"provider\":{\"name\":\"投稿\",\"alias\":\"PGC2\",\"icon\":\"\"},\"category\":\"预告\",\"author\":{\"id\":2173,\"icon\":\"http://img.kaiyanapp.com/003829087e85ce7310b2210d9575ce67.jpeg\",\"name\":\"开眼预告精选\",\"description\":\"电影、剧集、戏剧抢先看\",\"link\":\"\",\"latestReleaseTime\":1516252058000,\"videoNum\":410,\"adTrack\":null,\"follow\":{\"itemType\":\"author\",\"itemId\":2173,\"followed\":false},\"shield\":{\"itemType\":\"author\",\"itemId\":2173,\"shielded\":false},\"approvedNotReadyVideoCount\":0,\"ifPgc\":true},\"cover\":{\"feed\":\"http://img.kaiyanapp.com/81292cc5a61d11fd5c433e0982554638.jpeg?imageMogr2/quality/60/format/jpg\",\"detail\":\"http://img.kaiyanapp.com/81292cc5a61d11fd5c433e0982554638.jpeg?imageMogr2/quality/60/format/jpg\",\"blurred\":\"http://img.kaiyanapp.com/e185a642f31607cfc8fad2399a8ef800.jpeg?imageMogr2/quality/60/format/jpg\",\"sharing\":null,\"homepage\":\"http://img.kaiyanapp.com/81292cc5a61d11fd5c433e0982554638.jpeg?imageView2/1/w/720/h/560/format/jpg/q/75|watermark/1/image/aHR0cDovL2ltZy5rYWl5YW5hcHAuY29tL2JsYWNrXzMwLnBuZw==/dissolve/100/gravity/Center/dx/0/dy/0|imageslim\"},\"playUrl\":\"http://baobab.kaiyanapp.com/api/v1/playUrl?vid=76102&editionType=default&source=aliyun\",\"thumbPlayUrl\":\"\",\"duration\":131,\"webUrl\":{\"raw\":\"http://www.eyepetizer.net/detail.html?vid=76102\",\"forWeibo\":\"http://www.eyepetizer.net/detail.html?vid=76102\"},\"releaseTime\":1516252058000,\"library\":\"DAILY\",\"playInfo\":[{\"height\":480,\"width\":854,\"urlList\":[{\"name\":\"aliyun\",\"url\":\"http://baobab.kaiyanapp.com/api/v1/playUrl?vid=76102&editionType=normal&source=aliyun\",\"size\":10548052},{\"name\":\"qcloud\",\"url\":\"http://baobab.kaiyanapp.com/api/v1/playUrl?vid=76102&editionType=normal&source=qcloud\",\"size\":10548052},{\"name\":\"ucloud\",\"url\":\"http://baobab.kaiyanapp.com/api/v1/playUrl?vid=76102&editionType=normal&source=ucloud\",\"size\":10548052}],\"name\":\"标清\",\"type\":\"normal\",\"url\":\"http://baobab.kaiyanapp.com/api/v1/playUrl?vid=76102&editionType=normal&source=aliyun\"},{\"height\":720,\"width\":1280,\"urlList\":[{\"name\":\"aliyun\",\"url\":\"http://baobab.kaiyanapp.com/api/v1/playUrl?vid=76102&editionType=high&source=aliyun\",\"size\":23214561},{\"name\":\"qcloud\",\"url\":\"http://baobab.kaiyanapp.com/api/v1/playUrl?vid=76102&editionType=high&source=qcloud\",\"size\":23214561},{\"name\":\"ucloud\",\"url\":\"http://baobab.kaiyanapp.com/api/v1/playUrl?vid=76102&editionType=high&source=ucloud\",\"size\":23214561}],\"name\":\"高清\",\"type\":\"high\",\"url\":\"http://baobab.kaiyanapp.com/api/v1/playUrl?vid=76102&editionType=high&source=aliyun\"}],\"consumption\":{\"collectionCount\":87,\"shareCount\":101,\"replyCount\":2},\"campaign\":null,\"waterMarks\":null,\"adTrack\":null,\"tags\":[{\"id\":719,\"name\":\"影音资讯\",\"actionUrl\":\"eyepetizer://tag/719/?title=%E5%BD%B1%E9%9F%B3%E8%B5%84%E8%AE%AF\",\"adTrack\":null,\"desc\":\"\",\"bgPicture\":\"http://img.kaiyanapp.com/ffec9c5b73df21d31d3b210d3fd776b1.jpeg?imageMogr2/quality/60/format/jpg\",\"headerImage\":\"http://img.kaiyanapp.com/ffec9c5b73df21d31d3b210d3fd776b1.jpeg?imageMogr2/quality/60/format/jpg\",\"tagRecType\":\"IMPORTANT\"},{\"id\":154,\"name\":\"喜剧\",\"actionUrl\":\"eyepetizer://tag/154/?title=%E5%96%9C%E5%89%A7\",\"adTrack\":null,\"desc\":null,\"bgPicture\":\"http://img.kaiyanapp.com/c3076dda6eeb73de6227e3af243b0561.jpeg?imageMogr2/quality/100\",\"headerImage\":\"http://img.kaiyanapp.com/ffec9c5b73df21d31d3b210d3fd776b1.jpeg?imageMogr2/quality/60/format/jpg\",\"tagRecType\":\"IMPORTANT\"},{\"id\":154,\"name\":\"喜剧\",\"actionUrl\":\"eyepetizer://tag/14/?title=%E5%8A%A8%E7%94%BB\",\"adTrack\":null,\"desc\":null,\"bgPicture\":\"http://img.kaiyanapp.com/c4e5c0f76d21abbd23c9626af3c9f481.jpeg?imageMogr2/quality/100\",\"headerImage\":\"http://img.kaiyanapp.com/88da8548d31005032c37df4889d2104c.jpeg?imageMogr2/quality/100\",\"tagRecType\":\"NORMAL\"},{\"id\":22,\"name\":\"预告\",\"actionUrl\":\"eyepetizer://tag/22/?title=%E9%A2%84%E5%91%8A\",\"adTrack\":null,\"desc\":null,\"bgPicture\":\"http://img.kaiyanapp.com/05383ae22a23cc6fcc008681e7ff5a1c.jpeg?imageMogr2/quality/60\",\"headerImage\":\"http://img.kaiyanapp.com/05383ae22a23cc6fcc008681e7ff5a1c.jpeg?imageMogr2/quality/60\",\"tagRecType\":\"NORMAL\"}],\"type\":\"NORMAL\",\"titlePgc\":\"\",\"descriptionPgc\":\"\",\"remark\":null,\"idx\":0,\"shareAdTrack\":null,\"favoriteAdTrack\":null,\"webAdTrack\":null,\"date\":1516237200000,\"promotion\":null,\"label\":null,\"labelList\":[],\"descriptionEditor\":\"由打造「冰川时代」系列和「里约大冒险」系列的蓝天工作室原班人马制作的「公牛历险记」，将于 2018 年 1 月 19 日以 2D、3D、中国巨幕 3D、杜比全景声、杜比视界制式登陆全国影院与观众见面。公牛费迪南和小女孩一家人温馨十足的成长之路、与萌物小伙伴们众志成城反抗出逃的坚定决心，以及冒险途中「笑」果感动齐飞的精彩故事，都让人期待不已。一起走进电影院，享受这部开年最欢脱的动画电影吧！\",\"collected\":false,\"played\":false,\"subtitles\":[],\"lastViewTime\":null,\"playlists\":null,\"src\":null},\"tag\":\"0\",\"id\":0,\"adIndex\":-1}";
    val item = MoshiUtils.strToObject(str, ItemList::class.java)

    val list = ArrayList<ItemList>()
    val header = createHeader()
    if (null != header) {
      list.add(header)
    }
    if (null != item) {
      for (i in 0 until size) {
        list.add(item)
      }
    }

    return HomeModel(itemList = list, count = list.size, total = 0,
        nextPageUrl = "http://baobab.kaiyanapp.com/api/v4/tabs/selected?date=1516150800000&num=2&page=2",
        adExist = false, date = 1516323600000L, nextPublishTime = 1516410000000L, dialog = null,
        topIssue = null, refreshCount = 0, lastStartId = 0)
  }


  fun createHeader(): ItemList? {
    val header = "{\"type\":\"textHeader\",\"data\":{\"dataType\":\"TextHeader\",\"text\":\"- Jan. 18, Brunch -\",\"font\":\"lobster\",\"adTrack\":null},\"tag\":\"1\",\"id\":0,\"adIndex\":-1}"
    return MoshiUtils.strToObject(header, ItemList::class.java)
  }

  fun createFooter(): ItemList? {
    var footer = "{\"type\":\"textFooter\",\"data\":{\"dataType\":\"TextFooter\",\"text\":\"查看往期编辑精选\",\"font\":\"normal\",\"actionUrl\":\"eyepetizer://feed?issueIndex=1\",\"adTrack\":null},\"tag\":null,\"id\":0,\"adIndex\":-1}"
    return MoshiUtils.strToObject(footer, ItemList::class.java)
  }


}