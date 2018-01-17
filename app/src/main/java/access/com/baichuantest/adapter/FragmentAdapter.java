package access.com.baichuantest.adapter;

import android.support.v4.app.Fragment;


import java.util.ArrayList;

import access.com.baichuantest.activity.MainActivity;
import access.com.baichuantest.fragment.ArticleFragment;
import access.com.baichuantest.fragment.Fragment1;
import access.com.baichuantest.fragment.Fragment2;
import access.com.baichuantest.fragment.HomeFragment;
import access.com.baichuantest.fragment.MainFragment;
import access.com.baichuantest.fragment.fragmentnavigator.FragmentNavigatorAdapter;


/**
 * Created by aspsine on 16/3/31.
 */
public class FragmentAdapter implements FragmentNavigatorAdapter {

//    private static final String TABS[] = {"home", "category", "chart", "article", "user"};
    private final String TABS_HOME = "home";
    private final String TABS_CATEGORY = "category";
    private final String TABS_CHART = "chart";
    private final String TABS_ARTICLE = "article";
    private final String TABS_USER = "user";

    private ArrayList<String> tagList;
    private ArrayList<Fragment> fragmentList;

    public FragmentAdapter(ArrayList<Integer> indexList) {
        tagList = new ArrayList<>();
        fragmentList = new ArrayList<>();
        for (int index : indexList) {
            switch (index) {
                case MainActivity.TAB_INDEX_HOME:
                    tagList.add(TABS_HOME);
                    fragmentList.add(new HomeFragment());
                    break;
                case MainActivity.TAB_INDEX_CATEGORY:
                    tagList.add(TABS_CATEGORY);
                    fragmentList.add(new ArticleFragment());
                    break;
                case MainActivity.TAB_INDEX_CHART:
                    tagList.add(TABS_CHART);
                    fragmentList.add(new Fragment2());
                    break;
//                case MainActivity.TAB_INDEX_ARTICLE:
//                    tagList.add(TABS_ARTICLE);
//                    fragmentList.add(new ArticleNewFragment());
//                    break;
//                case MainActivity.TAB_INDEX_USER:
//                    tagList.add(TABS_USER);
//                    fragmentList.add(new UserFragment());
//                    break;
            }
        }
    }

    @Override
    public Fragment onCreateFragment(int position, boolean isLocation) {
//        switch (position) {
//            case MainActivity.TAB_INDEX_HOME:
//                return new HomeFragmentCopy();
//            case MainActivity.TAB_INDEX_CATEGORY:
//                return new CategoryFragment();
//            case MainActivity.TAB_INDEX_CHART:
//                return new ChartFragment();
//            case MainActivity.TAB_INDEX_ARTICLE:
//                return new ArticleNewFragment();
//            case MainActivity.TAB_INDEX_USER:
//                return new UserFragment();
//        }
        return fragmentList.get(position);
//        return MainFragment.newInstance(tagList.get(position));
    }

    @Override
    public String getTag(int position) {
//        if (position == 1) {
//            return FragmentOne.TAG;
//        }
        return MainFragment.TAG + tagList.get(position);
    }

    @Override
    public int getCount() {
        return tagList.size();
    }
}
