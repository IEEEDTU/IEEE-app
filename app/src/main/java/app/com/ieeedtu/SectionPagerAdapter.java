package app.com.ieeedtu;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

public class SectionPagerAdapter extends FragmentPagerAdapter {
    public static int frag = 0;

    public SectionPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return new NewsFragment();
            case 1:
                return new SIGFragment();
            case 2:
                return new EventFragment();
            default:
                return null;

        }
    }

    @Override
    public int getCount() {
        return 3;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case 0:
                return "NEWS";
            case 1:
                return "SIG";
            case 2:
                return "EVENTS";
        }
        return null;
    }
}
