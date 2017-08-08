package app.com.ieeedtu;


import android.content.Intent;
import android.os.Bundle;
import android.provider.CalendarContract;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import app.com.ieeedtu.POJO.SigInfo;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * A simple {@link Fragment} subclass.
 */
public class SIGFragment extends Fragment {

    RecyclerView rvSigs;
    ProgressBar pbSig;
    String year, month, date;

    public SIGFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_sig, container, false);
        rvSigs = (RecyclerView) v.findViewById(R.id.rv_sig);
        pbSig = (ProgressBar) v.findViewById(R.id.pb_sig);
        pbSig.setVisibility(View.VISIBLE);
        Call<List<SigInfo>> sigCall = RetroClass.client.getSigs();
        sigCall.enqueue(new Callback<List<SigInfo>>() {
            @Override
            public void onResponse(Call<List<SigInfo>> call, Response<List<SigInfo>> response) {
                SigsAdapter adapter = new SigsAdapter(response.body());
                rvSigs.setAdapter(adapter);
                pbSig.setVisibility(View.GONE);
                RecyclerView.LayoutManager man = new LinearLayoutManager(getActivity());
                rvSigs.setLayoutManager(man);
                rvSigs.setHasFixedSize(true);
            }

            @Override
            public void onFailure(Call<List<SigInfo>> call, Throwable t) {
                Log.i("Resp","Err"+t.toString());
                pbSig.setVisibility(View.GONE);
                Toast.makeText(getActivity(), "Network error", Toast.LENGTH_SHORT).show();

            }
        });

        return v;
    }

    class SigsAdapter extends RecyclerView.Adapter<SigsAdapter.SigsHolder>{

        List<SigInfo> list = new ArrayList<>();

        public SigsAdapter(List<SigInfo> list) {
            this.list = list;
        }

        @Override
        public SigsHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new SigsHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_sig_recycler,parent,false));
        }

        @Override
        public void onBindViewHolder(SigsHolder holder, int position) {

            String completeDate = list.get(position).getDate();
            date = completeDate.substring(8,10);
            month = completeDate.substring(5,7);
            year = completeDate.substring(0,4);


            switch (month){
                case "01":
                    holder.tvMonth.setText("Jan");
                    break;
                case "02":
                    holder.tvMonth.setText("Feb");
                    break;
                case "03":
                    holder.tvMonth.setText("March");
                    break;
                case "04":
                    holder.tvMonth.setText("April");
                    break;
                case "05":
                    holder.tvMonth.setText("May");
                    break;
                case "06":
                    holder.tvMonth.setText("June");
                    break;
                case "07":
                    holder.tvMonth.setText("July");
                    break;
                case "08":
                    holder.tvMonth.setText("Aug");
                    break;
                case "09":
                    holder.tvMonth.setText("Sept");
                    break;
                case "10":
                    holder.tvMonth.setText("Oct");
                    break;
                case "11":
                    holder.tvMonth.setText("Nov");
                    break;
                case "12":
                    holder.tvMonth.setText("Dec");
                    break;
            }

            holder.tvDate.setText(date);
            holder.tvTopic.setText(list.get(position).getTopic());
            holder.tvFrom.setText("From : "+list.get(position).getFromTime());
            holder.tvTo.setText("To : "+list.get(position).getToTime());



        }

        @Override
        public int getItemCount() {
            return list.size();
        }

        public class SigsHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

            TextView tvDate, tvMonth, tvTopic, tvFrom, tvTo;
            TextView btRemind;

            public SigsHolder(View itemView) {
                super(itemView);
                tvDate = (TextView) itemView.findViewById(R.id.tv_sig_date);
                tvMonth = (TextView) itemView.findViewById(R.id.tv_sig_month);
                tvTopic = (TextView) itemView.findViewById(R.id.tv_sig_topic);
                tvFrom = (TextView) itemView.findViewById(R.id.tv_sig_from);
                tvTo = (TextView) itemView.findViewById(R.id.tv_sig_to);
                btRemind = (TextView) itemView.findViewById(R.id.tv_sig_reminder);
                btRemind.setOnClickListener(this);

                tvTopic.setOnClickListener(this);
                tvDate.setOnClickListener(this);
                tvMonth.setOnClickListener(this);

            }


            @Override
            public void onClick(View view) {
                if(view==btRemind){
                    int pos = getAdapterPosition();
                    String completeDate = list.get(pos).getDate();
                    date = completeDate.substring(8,10);
                    month = completeDate.substring(5,7);
                    year = completeDate.substring(0,4);
                    Log.i("YEAR",year);
                    Calendar begin = Calendar.getInstance();
                    begin.set(Integer.parseInt(year),Integer.parseInt(month),Integer.parseInt(date));
                    Intent intent = new Intent(Intent.ACTION_INSERT)
                            .setData(CalendarContract.Events.CONTENT_URI)
                            .putExtra(CalendarContract.EXTRA_EVENT_BEGIN_TIME, begin.getTimeInMillis()).putExtra(CalendarContract.Events.TITLE,list.get(pos).getTopic())
                            .putExtra(CalendarContract.Events.DESCRIPTION,list.get(pos).getDescription()).putExtra(CalendarContract.Events.EVENT_LOCATION, list.get(pos).getLocation());
                    getActivity().startActivity(intent);

                }

                else if(view==tvTopic||view==tvDate||view==tvDate){
                    int pos = getAdapterPosition();
                    Intent intent = new Intent(getActivity(),DetailsActivity.class);
                    intent.putExtra("Topic",list.get(pos).getTopic()).putExtra("Date",date).putExtra("Month",month).putExtra("Time",list.get(pos).getFromTime())
                            .putExtra("Loc",list.get(pos).getLocation()).putExtra("Desc",list.get(pos).getDescription())
                    .putExtra("CoordiName",list.get(pos).getCoordinators().get(0).getUser().getFirstName()+" "+list.get(pos).getCoordinators().get(0).getUser().getLastName())
                    .putExtra("CoordiCon",Long.toString(list.get(pos).getCoordinators().get(0).getMobile()));

                    getActivity().startActivity(intent);

                }

            }
        }
    }

}
