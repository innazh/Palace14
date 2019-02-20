package memorypalace.palace14.classes;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import memorypalace.palace14.R;

public class ObjectListAdapter extends BaseAdapter {

        public class ViewHolder {
            TextView textName;
            ImageView imageView;
            CheckBox checkBox;
        }

        private List<Object_assoc> objectList;
        private Context context;
        private LayoutInflater layoutInflater;

        public ObjectListAdapter(List<Object_assoc> list, Context context, LayoutInflater layoutInflater) {
            this.objectList = list;
            this.context = context;
            this.layoutInflater = layoutInflater;
        }

        @Override
        public int getCount() {
            return objectList.size();
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
            memorypalace.palace14.classes.ObjectListAdapter.ViewHolder viewHolder = null;
            if (rowView == null) {
                rowView = layoutInflater.inflate(R.layout.view_object_list_content, parent, false);
                viewHolder = new memorypalace.palace14.classes.ObjectListAdapter.ViewHolder();

                //Get the elements by ID
                viewHolder.textName = rowView.findViewById(R.id.rLName);
                viewHolder.imageView = rowView.findViewById(R.id.imageView);
                viewHolder.checkBox = rowView.findViewById(R.id.checkBoxSelectObject);

                rowView.setTag(viewHolder);
            } else {
                viewHolder = (memorypalace.palace14.classes.ObjectListAdapter.ViewHolder) convertView.getTag();
            }
            // here setting up names and images
            viewHolder.textName.setText(objectList.get(position).getName()); //+""

            String imageName = objectList.get(position).getIcon();

            switch(imageName){
                case "barstool":
                    viewHolder.imageView.setImageResource(R.drawable.barstool);
                    break;
                case "bookcase":
                    viewHolder.imageView.setImageResource(R.drawable.bookcase);
                    break;
                case "diningset":
                    viewHolder.imageView.setImageResource(R.drawable.diningset);
                    break;
                case "stool":
                    viewHolder.imageView.setImageResource(R.drawable.stool);
                    break;
            }

            return rowView;
        }
}
