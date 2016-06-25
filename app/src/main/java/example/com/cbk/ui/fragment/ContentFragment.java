package example.com.cbk.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import example.com.cbk.R;
import example.com.cbk.adapter.FragmentListAdapter;
import example.com.cbk.bean.Info;
import example.com.cbk.ui.activity.DetailActivity;
import example.com.networkthread.HttpHelper;
import example.com.networkthread.Request;
import example.com.networkthread.StringRequest;
import in.srain.cube.views.ptr.PtrClassicFrameLayout;
import in.srain.cube.views.ptr.PtrDefaultHandler;
import in.srain.cube.views.ptr.PtrFrameLayout;
import in.srain.cube.views.ptr.PtrHandler;

/**
 * Created by Administrator on 2016/6/21 0021.
 * www.tngou.net/api/lore/list
 */
public class ContentFragment extends Fragment {
    private static final String TAG = ContentFragment.class.getSimpleName();
    private List<Info> infos = new ArrayList<>();
    private ListView cLv;
    private int classID;
    private int page = 1;
    private FragmentListAdapter adapter;
    private boolean isBottom = false;
    private PtrClassicFrameLayout mRefreshView;
    private boolean isDownLoad = false;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle saved) {
        View view = View.inflate(getContext(), R.layout.frag_content, null);
        initView(view);
        classID = getArguments().getInt("id");
        //Log.i(TAG, "onCreateView: classID="+classID);
        //如果保存到了数据
        if (saved != null) {
            Info[] infoArray = (Info[]) saved.getParcelableArray("infos");
            if (infoArray != null && infoArray.length != 0) {
                infos = Arrays.asList(infoArray);
                adapter = new FragmentListAdapter(infos);
                cLv.setAdapter(adapter);
            } else {
                getDataFromNet();
            }
        } else {
            getDataFromNet();
        }
        return view;
    }

    private void getDataFromNet() {
        String url = "http://www.tngou.net/api/lore/list?id=" + classID + "&" + "page=" + page;
        //http://www.tngou.net/api/lore/list?id=1&page=3
        Log.i(TAG, "getDataFromNet: page=" + page);
        StringRequest request = new StringRequest(url, "GET", new Request.ResponeCallback<String>() {
            @Override
            public void error(Exception e) {
                Log.i(TAG, "error: " + e.getMessage());
            }

            @Override
            public void data(final String info) {
                Log.d(TAG, "data: info" + info);
                List<Info> inf = null;
                try {
                    inf = parserString2List(info);
                    if (inf != null) {
                        //infos.clear();
                        infos.addAll(inf);
                        if (adapter == null) {
                            adapter = new FragmentListAdapter(infos);
                            getActivity().runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    cLv.setAdapter(adapter);
                                }
                            });
                        } else {
                            getActivity().runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    adapter.notifyDataSetChanged();
                                    isDownLoad = false;
                                }
                            });
                        }
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        mRefreshView.refreshComplete();
                    }
                });

            }
        });
        HttpHelper.addRequest(request);
    }

    private List<Info> parserString2List(String json) throws JSONException {
        List<Info> infos = new ArrayList<Info>();


        JSONObject object = new JSONObject(json);
        JSONArray jsonArray = object.getJSONArray("tngou");
        if (jsonArray == null || jsonArray.length() == 0) return null;
        Info info = null;
        for (int i = 0; i < jsonArray.length(); i++) {
            info = new Info();
            info.setImg(jsonArray.getJSONObject(i).optString("img"));
            info.setDescription(jsonArray.getJSONObject(i).optString("description"));
            info.setRcount(jsonArray.getJSONObject(i).optInt("rcount"));
            info.setTime(jsonArray.getJSONObject(i).optLong("time"));
            info.setId(jsonArray.getJSONObject(i).optLong("id"));
            infos.add(info);
        }
        return infos;


    }

    private void initView(View view) {
        mRefreshView = (PtrClassicFrameLayout) view.findViewById(R.id.rotate_header_list_view_frame);
        mRefreshView.setResistance(1.7f);
        mRefreshView.setRatioOfHeaderHeightToRefresh(1.2f);
        mRefreshView.setDurationToClose(200);
        mRefreshView.setDurationToCloseHeader(1000);
        // default is false
        mRefreshView.setPullToRefresh(true);
        // default is true
        mRefreshView.setKeepHeaderWhenRefresh(true);

        mRefreshView.setPtrHandler(new PtrHandler() {
            @Override
            public boolean checkCanDoRefresh(PtrFrameLayout frame, View content, View header) {
                return PtrDefaultHandler.checkContentCanBePulledDown(frame, content, header);
            }

            @Override
            public void onRefreshBegin(PtrFrameLayout frame) {
                getDataFromNet();
            }
        });


        cLv = (ListView) view.findViewById(R.id.centent_Lv);
        ProgressBar progressBar = new ProgressBar(getContext());
        cLv.addFooterView(progressBar);
        cLv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                if (infos != null && infos.size() != 0) {
                    Intent intent = new Intent();
                    intent.setClass(getActivity(), DetailActivity.class);
                    long iid = infos.get(position).getId();
                    Log.d(TAG, "onItemClick: iid=" + iid);
                    intent.putExtra("id", infos.get(position).getId());
                    startActivity(intent);

                }

            }
        });
        cLv.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                if (isBottom && scrollState == SCROLL_STATE_IDLE) {
                    if (!isDownLoad) {
                        page++;
                        getDataFromNet();
                        isDownLoad = true;
                    }
                }
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                isBottom = (firstVisibleItem + visibleItemCount) == totalItemCount;
            }
        });
    }


    @Override
    public void onSaveInstanceState(Bundle outState) {
        if (infos == null || infos.size() == 0) return;
        Info[] infArray = new Info[infos.size()];
        infos.toArray(infArray);
        outState.putParcelableArray("infos", infArray);
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        HttpHelper.stop();
    }
}
