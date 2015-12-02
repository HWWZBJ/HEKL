package com.beijing.hekl.hekl.adapter;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.beijing.hekl.hekl.R;

import org.json.JSONObject;

/**
 * Created by HEKL on 15/12/2.
 * Used for 医院类型
 */
public class AdpHosTyeSpinner extends SimpleBaseAdapter<JSONObject>{
    public AdpHosTyeSpinner(Context context) {
        super(context);
    }

    @Override
    public int getItemResource() {
        return R.layout.adp_hostyespinner;
    }

    @Override
    public View getItemView(int position, View convertView, ViewHolder holder) {
        JSONObject jsonObject = datas.get(position);
        TextView tvname = holder.getView(R.id.tv_hosname);
        holder.getView(R.id.image_arrow).setVisibility(View.GONE);
        tvname.setText(jsonObject.optString("TYPE_NAME"));
        return convertView;
    }
}
