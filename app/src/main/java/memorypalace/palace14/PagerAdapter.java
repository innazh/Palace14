package memorypalace.palace14;


// LISTEN HERE MATE IT MIGHT GIVE YOU SOME BLOODY ERRORS SO KEEP YOUR EYES PEELED!!
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;


public class PagerAdapter extends FragmentStatePagerAdapter {

    int mNoOfTabs;
    public PagerAdapter(FragmentManager fm, int NumberOfTabs)
    {
        super(fm);
        this.mNoOfTabs = NumberOfTabs;
    }

    @Override
    public Fragment getItem(int position) {
        switch(position){

            case 0:
                Tab1 tab1 = new Tab1();
                return tab1;
            case 1:
                Tab3 tab2 = new Tab3();
                return tab2;
            case 2:
                Tab4 tab3 = new Tab4();
                return tab3;
            default:
                return null;


        }


    }

    @Override
    public int getCount() {
        return 0;
    }
}
