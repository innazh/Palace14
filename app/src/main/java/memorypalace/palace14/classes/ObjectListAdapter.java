package memorypalace.palace14.classes;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import memorypalace.palace14.R;

public class ObjectListAdapter extends RecyclerView.Adapter<ObjectListAdapter.ViewHolder> {

    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView textName;
        private ImageView imageView;
        private CheckBox checkBox;
        private View container;

        public ViewHolder(View view) {
            super(view);
            textName = view.findViewById(R.id.rLName);
            imageView = view.findViewById(R.id.imageView);
            checkBox = view.findViewById(R.id.checkBoxSelectObject);
            container = itemView.findViewById(R.id.cont_item_root);
            //////////
            checkBox.setOnClickListener(this); //if doesnt work then use a different icon just like in the example
            container.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if (v.getId() == R.id.cont_item_root){
                System.out.println("ObjectListAdapter:Onclick - if ");
                itemClickCallback.onItemClick(getAdapterPosition());
            } else {
                System.out.println("ObjectListAdapter:Onclick - else ");
                itemClickCallback.onSecondaryIconClick(getAdapterPosition());
            }
        }
    }

    private List<Object_assoc> objectList;
    private LayoutInflater layoutInflater;
    private ItemClickCallback itemClickCallback;

    public interface ItemClickCallback {
        void onItemClick(int p);
        void onSecondaryIconClick(int p);
    }

    public void setItemClickCallback(final ItemClickCallback itemClickCallback) {
        this.itemClickCallback = itemClickCallback;
    }


    public ObjectListAdapter(List<Object_assoc> list, Context context) {
        this.objectList = list;
        this.layoutInflater = LayoutInflater.from(context);

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = layoutInflater.inflate(R.layout.view_object_list_content, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        Object_assoc item = this.objectList.get(i);

        viewHolder.textName.setText(item.getName());
        if(item.isSelected()) viewHolder.checkBox.setChecked(true);
        else viewHolder.checkBox.setChecked(false);

        String imageName = item.getIcon();

        switch (imageName) {
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
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemCount() {
        return this.objectList.size();
    }

    public void updateRecords(List<Object_assoc> objects) {
        this.objectList = objects;

        notifyDataSetChanged();
    }

    public void setListData(ArrayList<Object_assoc> exerciseList) {
        this.objectList.clear();
        this.objectList.addAll(exerciseList);
    }
}
