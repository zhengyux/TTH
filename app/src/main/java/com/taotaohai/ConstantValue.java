package com.taotaohai;


public interface ConstantValue {

    public String ENCONDING = "utf-8";
    public static final int SDKAPP_ID=1400060989;
    public static final String APP_ID = "wxbcaaaa2dfcb79995";//微信支付APP_ID
    public static final String APP_Secret="b8c0c72e27e0e10fa18ea90ded199620";//微信APP_Secret

    public static int LOGINCODE = 10086;



    /**
     * 服务器返回的状态码
     */
    String SUCCESS = "0";
    /**
     * 服务器端
     */
  //  public String URL = "http://taotaohai.lmlm.cn/";//测试域名

    public String URL = "http://www.taotaohai.com/";//域名
    public String URL_VIDEO= "api/video";//视频列表
    public String URL_SEARCH_VIDEO= "api/search/video";//视频搜索列表
    public static final String VIDEOSHARE = "http://www.taotaohai.com/video/share/"; //视频分享


    /**
     * 图片请求接口
     */
 //   public String IMGURL = "http://taotaohai.lmlm.cn/file/upload";//测试域名
    public String IMGURL = "http://www.taotaohai.com/file/upload";//测试域名
;

    public String CARDATA = "{\"rtcode\":1,\"rtmsg\":\"success\",\"datas\":[{\"key\":\"A\",\"datas\":[{\"keyid\":\"1\",\"brand\":\"\\u5965\\u8fea\"},{\"keyid\":\"3\",\"brand\":\"\\u963f\\u5c14\\u6cd5\\u00b7\\u7f57\\u5bc6\\u6b27\"},{\"keyid\":\"2\",\"brand\":\"\\u963f\\u65af\\u987f\\u00b7\\u9a6c\\u4e01\"}]},{\"key\":\"B\",\"datas\":[{\"keyid\":\"20\",\"brand\":\"\\u4fdd\\u6590\\u5229\"},{\"keyid\":\"11\",\"brand\":\"\\u4fdd\\u65f6\\u6377\"},{\"keyid\":\"6\",\"brand\":\"\\u522b\\u514b\"},{\"keyid\":\"13\",\"brand\":\"\\u5317\\u4eac\\u6c7d\\u8f66\"},{\"keyid\":\"14\",\"brand\":\"\\u5317\\u6c7d\\u5236\\u9020\"},{\"keyid\":\"17\",\"brand\":\"\\u5317\\u6c7d\\u5a01\\u65fa\"},{\"keyid\":\"156\",\"brand\":\"\\u5317\\u6c7d\\u5e7b\\u901f\"},{\"keyid\":\"115\",\"brand\":\"\\u5317\\u6c7d\\u7ec5\\u5b9d\"},{\"keyid\":\"12\",\"brand\":\"\\u5954\\u817e\"},{\"keyid\":\"9\",\"brand\":\"\\u5954\\u9a70\"},{\"keyid\":\"7\",\"brand\":\"\\u5b9d\\u9a6c\"},{\"keyid\":\"15\",\"brand\":\"\\u5b9d\\u9a8f\"},{\"keyid\":\"144\",\"brand\":\"\\u5b9d\\u9f99\"},{\"keyid\":\"16\",\"brand\":\"\\u5bbe\\u5229\"},{\"keyid\":\"18\",\"brand\":\"\\u5e03\\u52a0\\u8fea\"},{\"keyid\":\"5\",\"brand\":\"\\u672c\\u7530\"},{\"keyid\":\"10\",\"brand\":\"\\u6807\\u81f4\"},{\"keyid\":\"8\",\"brand\":\"\\u6bd4\\u4e9a\\u8fea\"}]},{\"key\":\"C\",\"datas\":[{\"keyid\":\"24\",\"brand\":\"\\u660c\\u6cb3\"},{\"keyid\":\"22\",\"brand\":\"\\u957f\\u57ce\"},{\"keyid\":\"23\",\"brand\":\"\\u957f\\u5b89\\u5546\\u7528\"},{\"keyid\":\"21\",\"brand\":\"\\u957f\\u5b89\\u8f7f\\u8f66\"}]},{\"key\":\"D\",\"datas\":[{\"keyid\":\"31\",\"brand\":\"DS\"},{\"keyid\":\"27\",\"brand\":\"\\u4e1c\\u5357\"},{\"keyid\":\"33\",\"brand\":\"\\u4e1c\\u98ce\"},{\"keyid\":\"28\",\"brand\":\"\\u4e1c\\u98ce\\u5c0f\\u5eb7\"},{\"keyid\":\"32\",\"brand\":\"\\u4e1c\\u98ce\\u98ce\\u5ea6\"},{\"keyid\":\"30\",\"brand\":\"\\u4e1c\\u98ce\\u98ce\\u795e\"},{\"keyid\":\"26\",\"brand\":\"\\u4e1c\\u98ce\\u98ce\\u884c\"},{\"keyid\":\"25\",\"brand\":\"\\u5927\\u4f17\"},{\"keyid\":\"142\",\"brand\":\"\\u5927\\u8fea\\u6c7d\\u8f66\"},{\"keyid\":\"34\",\"brand\":\"\\u5927\\u901a\"},{\"keyid\":\"29\",\"brand\":\"\\u9053\\u5947\"}]},{\"key\":\"F\",\"datas\":[{\"keyid\":\"36\",\"brand\":\"\\u4e30\\u7530\"},{\"keyid\":\"38\",\"brand\":\"\\u6cd5\\u62c9\\u5229\"},{\"keyid\":\"35\",\"brand\":\"\\u798f\\u7279\"},{\"keyid\":\"39\",\"brand\":\"\\u798f\\u7530\"},{\"keyid\":\"40\",\"brand\":\"\\u798f\\u8fea\"},{\"keyid\":\"37\",\"brand\":\"\\u83f2\\u4e9a\\u7279\"},{\"keyid\":\"41\",\"brand\":\"\\u98de\\u9a70\\u5546\\u52a1\\u8f66\"},{\"keyid\":\"162\",\"brand\":\"\\u798f\\u6c7d\\u542f\\u817e\"}]},{\"key\":\"G\",\"datas\":[{\"keyid\":\"47\",\"brand\":\"GMC\"},{\"keyid\":\"48\",\"brand\":\"\\u5149\\u5188\"},{\"keyid\":\"44\",\"brand\":\"\\u5e7f\\u6c7d\"},{\"keyid\":\"45\",\"brand\":\"\\u5e7f\\u6c7d\\u5409\\u5965\"},{\"keyid\":\"46\",\"brand\":\"\\u89c2\\u81f4\\u6c7d\\u8f66\"}]},{\"key\":\"H\",\"datas\":[{\"keyid\":\"146\",\"brand\":\"\\u534e\\u666e\"},{\"keyid\":\"52\",\"brand\":\"\\u534e\\u6cf0\"},{\"keyid\":\"147\",\"brand\":\"\\u534e\\u7fd4\\u5bcc\\u5947\"},{\"keyid\":\"160\",\"brand\":\"\\u534e\\u9882\"},{\"keyid\":\"50\",\"brand\":\"\\u54c8\\u5f17\"},{\"keyid\":\"56\",\"brand\":\"\\u54c8\\u98de\"},{\"keyid\":\"58\",\"brand\":\"\\u6052\\u5929\\u6c7d\\u8f66\"},{\"keyid\":\"145\",\"brand\":\"\\u608d\\u9a6c\"},{\"keyid\":\"59\",\"brand\":\"\\u6c47\\u4f17\"},{\"keyid\":\"57\",\"brand\":\"\\u6d77\\u683c\"},{\"keyid\":\"51\",\"brand\":\"\\u6d77\\u9a6c\"},{\"keyid\":\"54\",\"brand\":\"\\u6d77\\u9a6c\\u5546\\u7528\\u8f66\"},{\"keyid\":\"53\",\"brand\":\"\\u7ea2\\u65d7\"},{\"keyid\":\"55\",\"brand\":\"\\u9ec4\\u6d77\"}]},{\"key\":\"J\",\"datas\":[{\"keyid\":\"61\",\"brand\":\"Jeep\"},{\"keyid\":\"70\",\"brand\":\"\\u4e5d\\u9f99\"},{\"keyid\":\"143\",\"brand\":\"\\u5409\\u5229\"},{\"keyid\":\"62\",\"brand\":\"\\u5409\\u5229\\u5168\\u7403\\u9e70\"},{\"keyid\":\"63\",\"brand\":\"\\u5409\\u5229\\u5e1d\\u8c6a\"},{\"keyid\":\"65\",\"brand\":\"\\u5409\\u5229\\u82f1\\u4f26\"},{\"keyid\":\"64\",\"brand\":\"\\u6377\\u8c79\"},{\"keyid\":\"68\",\"brand\":\"\\u6c5f\\u5357\"},{\"keyid\":\"60\",\"brand\":\"\\u6c5f\\u6dee\"},{\"keyid\":\"66\",\"brand\":\"\\u6c5f\\u94c3\"},{\"keyid\":\"71\",\"brand\":\"\\u91d1\\u65c5\\u5ba2\\u8f66\"},{\"keyid\":\"67\",\"brand\":\"\\u91d1\\u676f\"},{\"keyid\":\"69\",\"brand\":\"\\u91d1\\u9f99\\u8054\\u5408\"}]},{\"key\":\"K\",\"datas\":[{\"keyid\":\"74\",\"brand\":\"\\u514b\\u83b1\\u65af\\u52d2\"},{\"keyid\":\"157\",\"brand\":\"\\u51ef\\u7ffc\"},{\"keyid\":\"73\",\"brand\":\"\\u51ef\\u8fea\\u62c9\\u514b\"},{\"keyid\":\"158\",\"brand\":\"\\u5361\\u5a01\"},{\"keyid\":\"77\",\"brand\":\"\\u5361\\u5c14\\u68ee\"},{\"keyid\":\"75\",\"brand\":\"\\u5f00\\u745e\"},{\"keyid\":\"76\",\"brand\":\"\\u79d1\\u5c3c\\u585e\\u514b\"}]},{\"key\":\"L\",\"datas\":[{\"keyid\":\"82\",\"brand\":\"\\u5170\\u535a\\u57fa\\u5c3c\"},{\"keyid\":\"81\",\"brand\":\"\\u529b\\u5e06\"},{\"keyid\":\"86\",\"brand\":\"\\u52b3\\u65af\\u83b1\\u65af\"},{\"keyid\":\"87\",\"brand\":\"\\u6797\\u80af\"},{\"keyid\":\"85\",\"brand\":\"\\u730e\\u8c79\\u6c7d\\u8f66\"},{\"keyid\":\"89\",\"brand\":\"\\u7406\\u5ff5\"},{\"keyid\":\"88\",\"brand\":\"\\u83b2\\u82b1\"},{\"keyid\":\"90\",\"brand\":\"\\u8def\\u7279\\u65af\"},{\"keyid\":\"79\",\"brand\":\"\\u8def\\u864e\"},{\"keyid\":\"78\",\"brand\":\"\\u94c3\\u6728\"},{\"keyid\":\"83\",\"brand\":\"\\u9646\\u98ce\"},{\"keyid\":\"80\",\"brand\":\"\\u96f7\\u514b\\u8428\\u65af\"},{\"keyid\":\"84\",\"brand\":\"\\u96f7\\u8bfa\"}]},{\"key\":\"M\",\"datas\":[{\"keyid\":\"93\",\"brand\":\"MG\"},{\"keyid\":\"94\",\"brand\":\"MINI\"},{\"keyid\":\"98\",\"brand\":\"\\u6469\\u6839\"},{\"keyid\":\"96\",\"brand\":\"\\u739b\\u838e\\u62c9\\u8482\"},{\"keyid\":\"99\",\"brand\":\"\\u7f8e\\u4e9a\"},{\"keyid\":\"97\",\"brand\":\"\\u8fc8\\u51ef\\u4f26\"},{\"keyid\":\"95\",\"brand\":\"\\u8fc8\\u5df4\\u8d6b\"},{\"keyid\":\"92\",\"brand\":\"\\u9a6c\\u81ea\\u8fbe\"}]},{\"key\":\"N\",\"datas\":[{\"keyid\":\"100\",\"brand\":\"\\u7eb3\\u667a\\u6377\"}]},{\"key\":\"O\",\"datas\":[{\"keyid\":\"102\",\"brand\":\"\\u6b27\\u5b9d\"},{\"keyid\":\"103\",\"brand\":\"\\u6b27\\u6717\"},{\"keyid\":\"101\",\"brand\":\"\\u8bb4\\u6b4c\"}]},{\"key\":\"Q\",\"datas\":[{\"keyid\":\"106\",\"brand\":\"\\u542f\\u8fb0\"},{\"keyid\":\"105\",\"brand\":\"\\u5947\\u745e\"},{\"keyid\":\"107\",\"brand\":\"\\u5e86\\u94c3\"},{\"keyid\":\"104\",\"brand\":\"\\u8d77\\u4e9a\"}]},{\"key\":\"R\",\"datas\":[{\"keyid\":\"108\",\"brand\":\"\\u65e5\\u4ea7\"},{\"keyid\":\"110\",\"brand\":\"\\u745e\\u9e92\"},{\"keyid\":\"109\",\"brand\":\"\\u8363\\u5a01\"}]},{\"key\":\"S\",\"datas\":[{\"keyid\":\"116\",\"brand\":\"Smart\"},{\"keyid\":\"111\",\"brand\":\"\\u4e09\\u83f1\"},{\"keyid\":\"118\",\"brand\":\"\\u4e16\\u7235\"},{\"keyid\":\"117\",\"brand\":\"\\u53cc\\u73af\"},{\"keyid\":\"114\",\"brand\":\"\\u53cc\\u9f99\"},{\"keyid\":\"113\",\"brand\":\"\\u65af\\u5df4\\u9c81\"},{\"keyid\":\"112\",\"brand\":\"\\u65af\\u67ef\\u8fbe\"},{\"keyid\":\"149\",\"brand\":\"\\u8428\\u535a\"},{\"keyid\":\"169\",\"brand\":\"\\u601d\\u94ed\"}]},{\"key\":\"T\",\"datas\":[{\"keyid\":\"120\",\"brand\":\"TESLA\"},{\"keyid\":\"150\",\"brand\":\"\\u5929\\u9a6c\"}]},{\"key\":\"W\",\"datas\":[{\"keyid\":\"121\",\"brand\":\"\\u4e94\\u83f1\"},{\"keyid\":\"124\",\"brand\":\"\\u5a01\\u5179\\u66fc\"},{\"keyid\":\"123\",\"brand\":\"\\u5a01\\u9e9f\"},{\"keyid\":\"122\",\"brand\":\"\\u6c83\\u5c14\\u6c83\"},{\"keyid\":\"163\",\"brand\":\"\\u4e94\\u5341\\u94c3\"}]},{\"key\":\"X\",\"datas\":[{\"keyid\":\"155\",\"brand\":\"\\u590f\\u5229\"},{\"keyid\":\"130\",\"brand\":\"\\u65b0\\u51ef\"},{\"keyid\":\"151\",\"brand\":\"\\u65b0\\u5927\\u5730\"},{\"keyid\":\"148\",\"brand\":\"\\u65b0\\u96c5\\u9014\"},{\"keyid\":\"125\",\"brand\":\"\\u73b0\\u4ee3\"},{\"keyid\":\"128\",\"brand\":\"\\u897f\\u96c5\\u7279\"},{\"keyid\":\"126\",\"brand\":\"\\u96ea\\u4f5b\\u5170\"},{\"keyid\":\"127\",\"brand\":\"\\u96ea\\u94c1\\u9f99\"}]},{\"key\":\"Y\",\"datas\":[{\"keyid\":\"131\",\"brand\":\"\\u4e00\\u6c7d\"},{\"keyid\":\"135\",\"brand\":\"\\u4f9d\\u7ef4\\u67ef\"},{\"keyid\":\"136\",\"brand\":\"\\u626c\\u5dde\\u4e9a\\u661f\\u5ba2\\u8f66\"},{\"keyid\":\"134\",\"brand\":\"\\u6c38\\u6e90\"},{\"keyid\":\"159\",\"brand\":\"\\u82f1\\u81f4\"},{\"keyid\":\"132\",\"brand\":\"\\u82f1\\u83f2\\u5c3c\\u8fea\"},{\"keyid\":\"133\",\"brand\":\"\\u91ce\\u9a6c\\u6c7d\\u8f66\"}]},{\"key\":\"Z\",\"datas\":[{\"keyid\":\"139\",\"brand\":\"\\u4e2d\\u5174\"},{\"keyid\":\"137\",\"brand\":\"\\u4e2d\\u534e\"},{\"keyid\":\"152\",\"brand\":\"\\u4e2d\\u5ba2\\u534e\\u5317\"},{\"keyid\":\"140\",\"brand\":\"\\u4e2d\\u6b27\"},{\"keyid\":\"154\",\"brand\":\"\\u4e2d\\u987a\"},{\"keyid\":\"138\",\"brand\":\"\\u4f17\\u6cf0\"}]}]}";
}
