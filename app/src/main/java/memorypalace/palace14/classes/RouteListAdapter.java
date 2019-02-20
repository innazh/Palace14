package memorypalace.palace14.classes;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import memorypalace.palace14.R;

public class RouteListAdapter extends BaseAdapter{

        public class ViewHolder {
            TextView textName;
        }

        private List<Route> routeList;
        private Context context;
        private LayoutInflater layoutInflater;

        public RouteListAdapter(List<Route> list, Context context, LayoutInflater layoutInflater) {
            this.routeList = list;
            this.context = context;
            this.layoutInflater = layoutInflater;
        }

        @Override
        public int getCount() {
            return routeList.size();
        }

        @Override
        public Object getItem(int position) {
            return position;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) // inflating the layout and initializing widgets
        {

            View rowView = convertView;
            memorypalace.palace14.classes.RouteListAdapter.ViewHolder viewHolder = null;
            if (rowView == null) {
                rowView = layoutInflater.inflate(R.layout.view_route_list_content, parent, false);
                viewHolder = new memorypalace.palace14.classes.RouteListAdapter.ViewHolder();

                //Get the elements by ID
                viewHolder.textName = rowView.findViewById(R.id.rLName);

                rowView.setTag(viewHolder);
            } else {
                viewHolder = (memorypalace.palace14.classes.RouteListAdapter.ViewHolder) convertView.getTag();
            }
            // here setting up names and images
            viewHolder.textName.setText(routeList.get(position).getName()); //+""

            return rowView;
        }
}

