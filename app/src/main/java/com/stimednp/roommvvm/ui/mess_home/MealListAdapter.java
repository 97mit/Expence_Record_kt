package com.stimednp.roommvvm.ui.mess_home;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TableRow;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.stimednp.roommvvm.R;
import com.stimednp.roommvvm.data.db.entity.Meals;
import com.stimednp.roommvvm.data.db.entity.MenuItems;

import java.lang.reflect.Type;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;


public class MealListAdapter extends RecyclerView.Adapter<MealListAdapter.ViewHolder> {
    private List<Meals> meals = new ArrayList<>();
    private Context context;
    int mExpandedPosition=-1,previousExpandedPosition = -1;

    public interface ItemClickListener {
        void onItemClick(Meals currentMeals);
    };
    ItemClickListener itemClickListener;

    public MealListAdapter(Context context, ItemClickListener itemClickListener ) {
        this.itemClickListener = itemClickListener;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.meals_item,parent,false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Type type = new TypeToken<List<MenuItems>>(){}.getType();
        final boolean isExpanded = position==mExpandedPosition;
        Meals currentMeals = meals.get(position);
        List<MenuItems> mealList = new Gson().fromJson(currentMeals.getOrder_details(),type);
        SimpleDateFormat sdf = new SimpleDateFormat("EEE, d MMM yyyy");
        SimpleDateFormat pdf = new SimpleDateFormat("yyyy-MM-dd");
        try {
            holder.mealDate.setText(sdf.format(pdf.parse(currentMeals.getMealDate())));
        } catch (ParseException e) {
            e.printStackTrace();
            holder.mealDate.setText(currentMeals.getMealDate());
        }
        holder.detailsViewContainer.setVisibility(isExpanded?View.VISIBLE:View.GONE);
        holder.detailsViewContainer.removeAllViews();
        int totalSum = 0;
        for(MenuItems meal:mealList){
            totalSum+=meal.getPrice()*meal.getItemCount();
            LinearLayout ll = new LinearLayout(context);
            ll.setOrientation(LinearLayout.HORIZONTAL);

            TextView tv_title= new TextView(context);
            tv_title.setText(meal.getTitle());
            tv_title.setPadding(5,5,5,5);
            tv_title.setTextSize(15);
            tv_title.setWidth(200);
            ViewGroup.LayoutParams params = new TableRow.LayoutParams(0, ViewGroup.LayoutParams.WRAP_CONTENT, 1f);
            tv_title.setLayoutParams(params);

            TextView tv_count= new TextView(context);
            tv_count.setText(String.valueOf(meal.getItemCount())+"X "+context.getResources().getString(R.string.rupee)+String.valueOf(meal.getPrice())+" = ");
            tv_count.setPadding(5,5,5,5);
            tv_count.setTextSize(15);
            tv_count.setLayoutParams(new ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.WRAP_CONTENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT));

            TextView tv_price= new TextView(context);
            tv_price.setText(context.getResources().getString(R.string.rupee)+String.valueOf(meal.getPrice()*meal.getItemCount()));
            tv_price.setPadding(10,5,5,5);
            tv_price.setTextSize(15);
            tv_price.setLayoutParams(new ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.WRAP_CONTENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT));
            ll.addView(tv_title);
            ll.addView(tv_count);
            ll.addView(tv_price);

            holder.detailsViewContainer.addView(ll);
            //tv.clearComposingText();
        }
        holder.totalCostOfTheDay.setText(new StringBuilder().append(context.getResources().getText(R.string.rupee)).append("").append(totalSum).toString());
        if (isExpanded)
            previousExpandedPosition = position;


        holder.mealDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                mExpandedPosition = isExpanded ? -1:position;
                notifyItemChanged(previousExpandedPosition);
                notifyItemChanged(position);
            }
        });
        holder.mealDate.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                itemClickListener.onItemClick(currentMeals);
                return true;
            }
        });
    }

    @Override
    public int getItemCount() {
        return meals.size();
    }

    public void setMeals(List<Meals> meals){
        this.meals = meals;
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView totalCostOfTheDay,mealDate;
        private LinearLayout detailsViewContainer;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            mealDate = itemView.findViewById(R.id.tv_date);
            totalCostOfTheDay = itemView.findViewById(R.id.tv_total);
            detailsViewContainer = itemView.findViewById(R.id.detailsViewContainer);
        }
    }
    public Meals getMealsAt(int position){
        return meals.get(position);
    }
}
