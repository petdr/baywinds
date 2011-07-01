package com.emailross.ozwinds;

import android.content.Context;
import android.view.*;
import android.widget.*;
import java.util.List;

public class ObservationAdapter extends ArrayAdapter<Observation> {
    private List<Observation> observations;

    public ObservationAdapter(Context context, List<Observation> os) {
        super(context, R.layout.row, os);
        this.observations = os;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = convertView;

        if (v == null) {
            LayoutInflater vi = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = vi.inflate(R.layout.row, null);
        }

        Observation o = observations.get(position);

        TextView name = (TextView) v.findViewById(R.id.name);
        name.setText(o.getName());

        TextView wind_strength = (TextView) v.findViewById(R.id.wind_strength);
        wind_strength.setText(o.getWindStrength());

        TextView gust_strength = (TextView) v.findViewById(R.id.gust_strength);
        gust_strength.setText(o.getGustStrength());

        TextView wind_direction = (TextView) v.findViewById(R.id.wind_direction);
        wind_direction.setText(o.getWindDirection());

        return v;
    }

}
// vim: ts=4 sw=4 et
