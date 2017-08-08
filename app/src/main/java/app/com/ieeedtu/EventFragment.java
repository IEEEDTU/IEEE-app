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

import app.com.ieeedtu.POJO.EventInfo;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * A simple {@link Fragment} subclass.
 */
public class EventFragment extends Fragment {

    RecyclerView rvEvents;
    ProgressBar pbEvents;
    String date, month, year;

    public EventFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_event, container, false);
        rvEvents = (RecyclerView) v.findViewById(R.id.rv_events);
        pbEvents = (ProgressBar) v.findViewById(R.id.pb_events);

        Call<List<EventInfo>> callEve = RetroClass.client.getEvents();
        callEve.enqueue(new Callback<List<EventInfo>>() {
            @Override
            public void onResponse(Call<List<EventInfo>> call, Response<List<EventInfo>> response) {

                pbEvents.setVisibility(View.GONE);
                Log.i("Info",response.body().get(0).getDescription());
                EventAdapter adapter = new EventAdapter(response.body());
                rvEvents.setAdapter(adapter);
                rvEvents.setLayoutManager(new LinearLayoutManager(getActivity()));
                rvEvents.setHasFixedSize(true);
            }

            @Override
            public void onFailure(Call<List<EventInfo>> call, Throwable t) {
                pbEvents.setVisibility(View.GONE);
                Toast.makeText(getActivity(), "Network error", Toast.LENGTH_SHORT).show();
            }
        });

        return v;
    }

    class EventAdapter extends RecyclerView.Adapter<EventFragment.EventAdapter.EventHolder>{

        List<EventInfo> list = new ArrayList<>();

        public EventAdapter(List<EventInfo> list) {
            this.list = list;
        }

        @Override
        public EventFragment.EventAdapter.EventHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new EventFragment.EventAdapter.EventHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_event_recycler,parent,false));
        }

        @Override
        public void onBindViewHolder(EventFragment.EventAdapter.EventHolder holder, int position) {

            String completeDate = list.get(position).getFromDateTime();
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
            holder.tvTopic.setText(list.get(position).getName());

        }

        @Override
        public int getItemCount() {
            return list.size();
        }

        public class EventHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

            TextView tvDate, tvMonth, tvTopic;
            TextView tvRemind;

            public EventHolder(View itemView) {
                super(itemView);
                tvDate = (TextView) itemView.findViewById(R.id.tv_eve_date);
                tvMonth = (TextView) itemView.findViewById(R.id.tv_eve_month);
                tvTopic = (TextView) itemView.findViewById(R.id.tv_eve_topic);
                tvRemind = (TextView) itemView.findViewById(R.id.tv_eve_reminder);
                tvRemind.setOnClickListener(this);

                tvDate.setOnClickListener(this);
                tvTopic.setOnClickListener(this);
                tvMonth.setOnClickListener(this);

            }


            @Override
            public void onClick(View view) {
                if(view==tvRemind){
                    int pos = getAdapterPosition();
                    String completeDate = list.get(pos).getFromDateTime();
                    date = completeDate.substring(8,10);
                    month = completeDate.substring(5,7);
                    year = completeDate.substring(0,4);
                    Calendar begin = Calendar.getInstance();
                    begin.set(Integer.parseInt(year),Integer.parseInt(month),Integer.parseInt(date));
                    Intent intent = new Intent(Intent.ACTION_INSERT)
                            .setData(CalendarContract.Events.CONTENT_URI)
                            .putExtra(CalendarContract.EXTRA_EVENT_BEGIN_TIME, begin.getTimeInMillis()).putExtra(CalendarContract.Events.TITLE,list.get(pos).getName())
                            .putExtra(CalendarContract.Events.DESCRIPTION,list.get(pos).getDescription()).putExtra(CalendarContract.Events.EVENT_LOCATION, list.get(pos).getLocation());
                    getActivity().startActivity(intent);

                }

                else if(view==tvTopic||view==tvDate||view==tvMonth){
                    int pos = getAdapterPosition();
                    Intent intent = new Intent(getActivity(),DetailsActivity.class);
                    intent.putExtra("Date",date).putExtra("Month",month).putExtra("Topic",list.get(pos).getName())
                            .putExtra("Loc",list.get(pos).getLocation()).putExtra("Desc",list.get(pos).getDescription())
                    .putExtra("CoordiName",list.get(pos).getCoordinators().get(0).getUser().getFirstName()+" "+list.get(pos).getCoordinators().get(0).getUser().getLastName())
                    .putExtra("CoordiCon",Long.toString(list.get(pos).getCoordinators().get(0).getMobile()));
                    getActivity().startActivity(intent);

                }

            }
        }
    }

}
