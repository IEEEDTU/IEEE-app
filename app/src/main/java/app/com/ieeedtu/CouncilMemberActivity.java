package app.com.ieeedtu;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import app.com.ieeedtu.POJO.CouncilMember;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CouncilMemberActivity extends AppCompatActivity {

    RecyclerView rvCouncil;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_council_member);

        rvCouncil = (RecyclerView) findViewById(R.id.rv_council);

        Call<List<CouncilMember>> callCouncil = RetroClass.client.getCouncil();
        callCouncil.enqueue(new Callback<List<CouncilMember>>() {
            @Override
            public void onResponse(Call<List<CouncilMember>> call, Response<List<CouncilMember>> response) {
                CouncilAdapter adapter = new CouncilAdapter(response.body());
                rvCouncil.setAdapter(adapter);
                rvCouncil.setLayoutManager(new LinearLayoutManager(CouncilMemberActivity.this));
                rvCouncil.setHasFixedSize(true);
            }

            @Override
            public void onFailure(Call<List<CouncilMember>> call, Throwable t) {

            }
        });

    }

    class CouncilAdapter extends RecyclerView.Adapter<CouncilAdapter.CouncilHolder>{

        List<CouncilMember> list = new ArrayList<>();

        public CouncilAdapter(List<CouncilMember> list) {
            this.list = list;
        }

        @Override
        public CouncilHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new CouncilHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_council_recycler,parent,false));
        }

        @Override
        public void onBindViewHolder(CouncilHolder holder, int position) {

            String name = list.get(position).getMember().getUser().getFirstName()+" "+list.get(position).getMember().getUser().getLastName();
            holder.tvName.setText(name);
            holder.tvDes.setText(list.get(position).getDesignation());


        }

        @Override
        public int getItemCount() {
            return list.size();
        }

        public class CouncilHolder extends RecyclerView.ViewHolder {

            TextView tvName, tvDes;
            ImageView ivImage;

            public CouncilHolder(View itemView) {
                super(itemView);
                tvName = (TextView) itemView.findViewById(R.id.tv_council_member_name);
                tvDes = (TextView) itemView.findViewById(R.id.tv_council_member_designation);
                ivImage = (ImageView) itemView.findViewById(R.id.iv_council_image);

            }
        }
    }
}
