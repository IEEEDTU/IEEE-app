package app.com.ieeedtu;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import app.com.ieeedtu.POJO.NewsInfo;
import it.sephiroth.android.library.picasso.Picasso;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * A simple {@link Fragment} subclass.
 */
public class NewsFragment extends Fragment {
    RecyclerView rvNews;
    ProgressBar pbNews;

    TextView tvNo;
    public NewsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_news, container, false);
        pbNews = (ProgressBar) v.findViewById(R.id.pb_news);
        rvNews = (RecyclerView) v.findViewById(R.id.rv_news);
        tvNo = (TextView) v.findViewById(R.id.tv_no_news);
        pbNews.setVisibility(View.VISIBLE);

        Call<List<NewsInfo>> newsCall = RetroClass.client.getNews();
        newsCall.enqueue(new Callback<List<NewsInfo>>() {
            @Override
            public void onResponse(Call<List<NewsInfo>> call, Response<List<NewsInfo>> response) {

                pbNews.setVisibility(View.GONE);
                if (response.body() != null && !response.body().isEmpty()) {
                    NewsAdapter adapter = new NewsAdapter(response.body());
                    Log.i("Resp", "True");
                    rvNews.setAdapter(adapter);
                    RecyclerView.LayoutManager manager = new LinearLayoutManager(getActivity());
                    rvNews.setLayoutManager(manager);
                    rvNews.setHasFixedSize(true);
                }

                else {
                    tvNo.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onFailure(Call<List<NewsInfo>> call, Throwable t) {
                Log.i("Resp", "False" + t.toString());
                pbNews.setVisibility(View.GONE);
                Toast.makeText(getActivity(), "Network error", Toast.LENGTH_SHORT).show();
            }
        });


        return v;
    }

    private class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.NewsHolder> {

        List<NewsInfo> list = new ArrayList<>();

        public NewsAdapter(List<NewsInfo> list) {
            this.list = list;
        }

        @Override
        public NewsHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new NewsHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_news_recycler, parent, false));
        }

        @Override
        public void onBindViewHolder(NewsHolder holder, int position) {
            holder.tvHeading.setText(list.get(position).getHeading());

            String date = list.get(position).getPublishedOn().substring(0, 10);
            holder.tvDate.setText(date);
            holder.tvBody.setText(list.get(position).getBody());

            String im = list.get(position).getImage();
            if (im != null && !im.isEmpty()) {
                Picasso.with(getActivity()).load(im).into(holder.ivImage);
//                Picasso.with(getActivity()).load(R.drawable.eg).into(holder.ivImage);
            } else {
                holder.ivImage.setVisibility(View.GONE);
            }
        }

        @Override
        public int getItemCount() {
            return list.size();
        }

        public class NewsHolder extends RecyclerView.ViewHolder {

            TextView tvHeading, tvDate, tvBody;
            ImageView ivImage;

            public NewsHolder(View itemView) {
                super(itemView);
                tvHeading = (TextView) itemView.findViewById(R.id.tv_news_heading);
                tvDate = (TextView) itemView.findViewById(R.id.tv_news_date);
                tvBody = (TextView) itemView.findViewById(R.id.tv_news_body);
                ivImage = (ImageView) itemView.findViewById(R.id.iv_news_im);
            }
        }
    }
}
