package com.linecy.core.data.model

/**
 * @author by linecy
 */
data class HomeModel(var itemList: List<ItemList>?,
    var count: Int,
    var total: Int,
    var nextPageUrl: String?,
    var adExist: Boolean,
    var date: Long,
    var nextPublishTime: Long,
    var dialog: Any?,
    var topIssue: Any?,
    var refreshCount: Int,
    var lastStartId: Int) {

  data class ItemList(var type: String?, var data: Data?, var tag: Any?, var id: Int) {

    data class Data(var dataType: String?, var id: Int, var title: String?, var slogan: String?,
        var description: String?, var image: String?, var provider: Provider?,
        var category: String?,
        var actionUrl: String?,
        var adTrack: Any?, var isShade: Boolean,
        var label: Any?, var labelList: Any?, var header: Any?,
        var duration: Long?, var playUrl: String, var cover: Cover?,
        var author: Author?,
        var releaseTime: Long?, var consumption: Consumption?, var campaign: Any?,
        var waterMarks: Any?) {

      data class Provider(var name: String?, var alias: String?, var icon: String?)
      data class Cover(var feed: String?, var detail: String?,
          var blurred: String?, var sharing: String?, var homepage: String?)

      data class Consumption(var collectionCount: Int, var shareCount: Int,
          var replyCount: Int)

      data class Author(var id: Int, var icon: String, var name: String?, var description: String?,
          var link: String?)
    }
  }
}