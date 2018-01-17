package access.com.baichuantest.adapter;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.LinkedHashMap;

import access.com.baichuantest.R;

/**
 * Created by Administrator on 2017/4/1.
 */

public class BottomMenuAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context context;

    private int selection;

    private int cartNum;

    private Resources res;

    private LinkedHashMap<Integer, String> dataMap;

    private ArrayList<Integer> fontIdList;

    private int colorId;

    private Handler handler;

    public BottomMenuAdapter(Context context, LinkedHashMap<Integer, String> dataMap,Handler handler) {
        this.context = context;
        this.dataMap = dataMap;
        this.handler = handler;
        this.colorId = context.getResources().getColor(R.color.wholeColor);
        res = context.getResources();
        fontIdList = new ArrayList<>(dataMap.keySet());
    }

    public void setSelect(int selection) {
        this.selection = selection;
        notifyDataSetChanged();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new TextViewHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_main_bottom_menu, parent, false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        int fontId = fontIdList.get(position);
        TextViewHolder filterViewHolder = (TextViewHolder) holder;
        filterViewHolder.bottom_menu_iv.setImageResource(fontId);
        filterViewHolder.bottom_menu_tv.setText(dataMap.get(fontId));

        Drawable drawable = filterViewHolder.bottom_menu_iv.getDrawable().mutate();

        if (selection == position) {
            filterViewHolder.bottom_menu_tv.setTextColor(colorId);
            drawable.setColorFilter(colorId, PorterDuff.Mode.SRC_ATOP);
        } else {
            filterViewHolder.bottom_menu_tv.setTextColor(res.getColor(R.color.t666666));
            drawable.setColorFilter(context.getResources().getColor(R.color.t666666), PorterDuff.Mode.SRC_ATOP);
        }
        filterViewHolder.tab_home_ll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Message msg = Message.obtain();
                msg.what = 1;
                msg.arg1 = position;
                handler.sendMessage(msg);
            }
        });
    }

    public static class TextViewHolder extends RecyclerView.ViewHolder {

        ImageView bottom_menu_iv;
        TextView bottom_menu_tv;
        LinearLayout root_ll;
        LinearLayout tab_home_ll;

        public TextViewHolder(View paramView) {
            super(paramView);
            bottom_menu_iv = (ImageView) paramView.findViewById(R.id.bottom_menu_iv);
            bottom_menu_tv = (TextView) paramView.findViewById(R.id.bottom_menu_tv);
            root_ll = (LinearLayout) paramView.findViewById(R.id.root_ll);
            tab_home_ll = (LinearLayout) paramView.findViewById(R.id.tab_home_ll);
        }
    }

    @Override
    public int getItemCount() {
        return dataMap == null ? 0 : dataMap.size();
    }
}
