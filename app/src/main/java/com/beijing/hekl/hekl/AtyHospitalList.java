package com.beijing.hekl.hekl;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Spinner;

import com.beijing.hekl.hekl.Utils.ToastUtil;
import com.beijing.hekl.hekl.adapter.AdpHosTyeSpinner;
import com.beijing.hekl.hekl.adapter.CommonAdapter;
import com.beijing.hekl.hekl.adapter.CommonViewHolder;
import com.beijing.hekl.hekl.app.HttpUrls;
import com.beijing.hekl.hekl.base.BaseFragmentActivity;
import com.beijing.hekl.hekl.okhttp.callback.ResultCallback;
import com.beijing.hekl.hekl.okhttp.request.OkHttpRequest;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.socks.library.KLog;
import com.squareup.okhttp.Request;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by HEKL on 15/12/1.
 */
public class AtyHospitalList extends BaseFragmentActivity implements PullToRefreshBase.OnRefreshListener2, View.OnClickListener {
    private PullToRefreshListView pullToRefreshListView;
    private AdpHosTyeSpinner mSpinnerAdapter;
    private CommonAdapter mAdapter;
    private boolean isFirst = false;
    private String unit_type = "";
    private String name;
    private int pageSize = 1;

    @Override
    protected void onCreate(Bundle arg0) {
        super.onCreate(arg0);
        setContentView(R.layout.atyhospitallist);
        initView();
        loadData();
        loadHospitalType();
    }

    private void initView() {
        if (getIntent().hasExtra("name"))
            name = getIntent().getStringExtra("name");
        initTitle();
        titleLeftBtn.setOnClickListener(this);
        titleTextV.setText(name);
        pullToRefreshListView = (PullToRefreshListView) findViewById(R.id.hospital_list);
        ListView mListView = pullToRefreshListView.getRefreshableView();
        Spinner spinner = (Spinner) findViewById(R.id.spi_hospital);
        pullToRefreshListView.setOnRefreshListener(this);
        mSpinnerAdapter = new AdpHosTyeSpinner(AtyHospitalList.this);
        spinner.setAdapter(mSpinnerAdapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                unit_type = mSpinnerAdapter.datas.get(position).optInt("TYPE_CODE") + "";
                if (isFirst) {
                    pageSize = 1;
                    loadData();
                } else
                    isFirst = true;
                parent.setVisibility(View.VISIBLE);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                parent.setVisibility(View.VISIBLE);
            }
        });
        mAdapter = new CommonAdapter<JSONObject>(AtyHospitalList.this) {
            @Override
            public void onBoundView(CommonViewHolder helper, JSONObject item) {
                helper.setText(R.id.hospital_name, item.optString("UNIT_NAME"));
                helper.setText(R.id.hospital_type, item.optString("TYPE_NAME"));
            }

            @Override
            public int viewLayout() {
                return R.layout.hospital_list_item;
            }
        };
        mListView.setAdapter(mAdapter);
//        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                Intent intent = new Intent(HospitalListAcitvity.this, ResultActivity.class);
//                JSONObject object = (JSONObject) mAdapter.mDatas.get(position - 1);
//                intent.putExtra("name", object.optString("UNIT_NAME"));
//                intent.putExtra("code", "" + object.optInt("UNIT_CODE"));
//                startActivity(intent);
//            }
//        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.title_back:
                onBackPressed();
                break;

        }
    }

    private void loadHospitalType() {
        //192.168.16.45:8899/RegisteredSearch/SearchInfo?TYPE=findUnitType
        Map<String, String> params = new HashMap<String, String>();
        params.put("TYPE", "findUnitType");
        new OkHttpRequest.Builder().url(HttpUrls.REGISTER_SEARCH).params(params).get(new ResultCallback<String>() {
            @Override
            public void onError(Request request, Exception e) {
                KLog.e(e.toString());
            }

            @Override
            public void onResponse(String data) {
                try {
                    JSONObject response = new JSONObject(data);
                    if ("1".equals(response.optString("code"))) {
                        JSONArray array = response.getJSONArray("result");
                        List<JSONObject> jsonObjects = new ArrayList<JSONObject>();
                        for (int i = 0; i < array.length(); i++) {
                            jsonObjects.add(array.getJSONObject(i));
                        }
                        mSpinnerAdapter.addAll(jsonObjects);
                    } else {
                        ToastUtil.showShort(response.optString("message"));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    //加载数据
    private void loadData() {
        //http://192.168.16.45:8899/RegisteredSearch/SearchInfo?
        // TYPE=findUnitByAreaNameAndUnitName&NAME=%E5%8C%97%E4%BA%AC&UNIT_TYPE=&PAGESIZE=1&PAGENUM=10
        Map<String, String> params = new HashMap<String, String>();
        params.put("TYPE", "findUnitByAreaNameAndUnitName");
        params.put("NAME", name);
        params.put("UNIT_TYPE", unit_type);
        params.put("PAGESIZE", pageSize + "");
        params.put("PAGENUM", "20");
        new OkHttpRequest.Builder()
                .tag(AtyHospitalList.this.getPackageName())
                .url(HttpUrls.REGISTER_SEARCH)
                .params(params)
                .get(new ResultCallback<String>() {
                    @Override
                    public void onError(Request request, Exception e) {
                        KLog.d("onError , e = " + e.getMessage());
                    }

                    @Override
                    public void onBefore(Request request) {
                        super.onBefore(request);
                    }

                    @Override
                    public void onAfter() {
                        pullToRefreshListView.onRefreshComplete();
                        super.onAfter();
                    }

                    @Override
                    public void onResponse(String datas) {
                        KLog.e("-----" + datas);
                        if (datas != null && datas.length() > 0) {
                            try {
                                JSONObject jsonObject = new JSONObject(datas);
                                if ("1".equals(jsonObject.optString("code"))) {
                                    JSONArray array = jsonObject.getJSONArray("result");
                                    List<JSONObject> jsonObjects = new ArrayList<JSONObject>();
                                    for (int i = 0; i < array.length(); i++) {
                                        jsonObjects.add(array.getJSONObject(i));
                                    }
                                    if (jsonObjects.size() > 0) {
                                        if (pageSize == 1) {
                                            mAdapter.onBoundData(jsonObjects);
                                        } else {
                                            mAdapter.addAll(jsonObjects);
                                        }
                                        pageSize++;
                                    } else {
                                        if (pageSize == 1)
                                            mAdapter.removeAll();
                                        ToastUtil.showShort(AtyHospitalList.this,"没有更多了..");
                                    }

                                } else {
                                    ToastUtil.showShort(jsonObject.optString("message"));
                                }

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                });


    }
    @Override
    public void onPullDownToRefresh(PullToRefreshBase refreshView) {
        pageSize = 1;
        loadData();
    }

    @Override
    public void onPullUpToRefresh(PullToRefreshBase refreshView) {
        loadData();
    }

}
