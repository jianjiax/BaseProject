package access.com.baichuantest.fragment.fragmentnavigator;

import android.support.v4.app.Fragment;

/**
 * Created by aspsine on 16/3/30.
 */
public interface FragmentNavigatorAdapter {

    public Fragment onCreateFragment(int position, boolean param);

    public String getTag(int position);

    public int getCount();
}
