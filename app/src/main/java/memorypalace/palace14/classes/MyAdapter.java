package memorypalace.palace14.classes;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import memorypalace.palace14.R;

/**An adapter class to create list views with image and text inside each element**/
public class MyAdapter extends BaseAdapter         //has a class viewholder which holds
{
    public class ViewHolder {
        TextView textName;
        ImageView imageView;
    }

    private List<Palace> palacesList;
    private Context context;
    private LayoutInflater layoutInflater;

    public MyAdapter(List<Palace> list, Context context, LayoutInflater layoutInflater) {
        this.palacesList = list;
        this.context = context;
        this.layoutInflater = layoutInflater;
    }

    @Override
    public int getCount() {
        return palacesList.size();
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
        ViewHolder viewHolder = null;
        if (rowView == null) {
            rowView = layoutInflater.inflate(R.layout.view_palace_list_content, parent, false);
            viewHolder = new ViewHolder();

            viewHolder.textName = rowView.findViewById(R.id.rLName);
            viewHolder.imageView = rowView.findViewById(R.id.imageView);

            rowView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        // here setting up names and images
        viewHolder.textName.setText(palacesList.get(position).getName()); //+""

        String imageName = palacesList.get(position).getImageName();

        switch(imageName){
            case "one":
                viewHolder.imageView.setImageResource(R.drawable.one);
                break;
            case "two":
                viewHolder.imageView.setImageResource(R.drawable.two);
                break;
            case "three":
                viewHolder.imageView.setImageResource(R.drawable.three);
                break;
            case "four":
                viewHolder.imageView.setImageResource(R.drawable.four);
                break;
        }

        return rowView;
    }
}

