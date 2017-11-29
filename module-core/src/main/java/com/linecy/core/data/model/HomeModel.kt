package com.linecy.core.data.model

/**
 * @author by linecy
 */
data class HomeModel(var nextPageUrl: String?, var nextPublishTime: Long,
    var newestIssueType: String?, var dialog: Any?,
    var issueList: List<IssueListModel>?) {

  data class IssueListModel(var releaseTime: Long, var type: String?,
      var date: Long, var publishTime: Long, var count: Int,
      var itemList: List<ItemListModel>?) {

    data class ItemListModel(var type: String?, var data: DataModel?, var tag: Any?) {

      data class DataModel(var dataType: String?, var id: Int, var title: String?,
          var description: String?, var image: String?, var actionUrl: String?,
          var adTrack: Any?, var isShade: Boolean,
          var label: Any?, var labelList: Any?, var header: Any?, var category: String?,
          var duration: Long?, var playUrl: String, var cover: CoverModel?,
          var author: AuthorModel?,
          var releaseTime: Long?, var consumption: ConsumptionModel?) {
        data class CoverModel(var feed: String?, var detail: String?,
            var blurred: String?, var sharing: String?, var homepage: String?)

        data class ConsumptionModel(var collectionCount: Int, var shareCount: Int,
            var replyCount: Int)

        data class AuthorModel(var icon: String)
      }
    }
  }
}