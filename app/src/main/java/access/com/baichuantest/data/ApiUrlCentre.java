package access.com.baichuantest.data;


import access.com.baichuantest.BuildConfig;

/**
 * Created by Kain on 2016/10/10.
 */
public class ApiUrlCentre {
    // debug
    public static final String BASE_URL_DEBUG = "http://apitest.taodianke.com/";
    // release
    public static final String BASE_URL_RELEASE = "http://api.taodianke.com/";
//    public static final String BASE_URL = BASE_URL_DEBUG;
    public static final String BASE_URL = BuildConfig.DEBUG ? BASE_URL_RELEASE : BASE_URL_RELEASE;

    public static String getUrl(String url) {
        return BASE_URL + url;
    }

    public static final String ADVERT = getUrl("Index/getAdvert?");

    // index goods
    public static final String INDEX = getUrl("Index/index?");

    // index category
    public static final String CATEGORY = getUrl("Index/cate?");

    // goods list
    public static final String GOODSLIST = getUrl("Item/index?");

    // goods search
    public static final String SEARCH = getUrl("Item/search");

    // goods details
    public static final String GOODSDETAILS = getUrl("Item/detail?");

    // article index
    public static final String ARTICLEINDEX = getUrl("News/index?");

    // article category list
    public static final String ARTICLELIST = getUrl("News/getData?");

    // article details
    public static final String ARTICLEDETAILS = getUrl("News/detail?");

    // login
    public static final String LOGIN = getUrl("Public/login?");

    // chart index
    public static final String CHARTINDEX = getUrl("Balance/index?");

    // order
    public static final String ODER = getUrl("Balance/getOrder?");

    // userinfo
    public static final String USERINFO = getUrl("User/index");

    // collect
    public static final String COLLECT = getUrl("User/like?");

    // remove collect
    public static final String REMOVECOLLECT = getUrl("User/delLike?");

    // collect
    public static final String ADDCOLLECT = getUrl("User/addLike?");

    public static final String APPLYSETTLE = getUrl("User/cash?");

    // settle list
    public static final String SETTLELIST = getUrl("User/cashList?");

    // update
    public static final String UPDATE = getUrl("Public/checkUpdate?");

    // launcher load
    public static final String LOADINIT = getUrl("Public/appLoadInit?");

    // bind settle account
    public static final String BINDACCOUNT = getUrl("User/addBank");

    // send verify code
    public static final String SENDCODE = getUrl("Public/sendCode");

    // send verify code
    public static final String REGISTERORFORGET = getUrl("Public/registerForget");

    // apply amount
    public static final String APPLY_AMOUNT = getUrl("Item/highApply?");

    // update user info
    public static final String UPDATE_USER = getUrl("User/updateInfo");

    // my proxy
    public static final String MY_PROXY = getUrl("Proxy/subProxy");

    // my proxy order
    public static final String MY_PROXY_ORDER = getUrl("Proxy/subProxyOrderCommission");

    // notice
    public static final String NEWS_URL = getUrl("Share/item/cid/1");

    // article
    public static final String BBS_URL = getUrl("Share/index");

}
