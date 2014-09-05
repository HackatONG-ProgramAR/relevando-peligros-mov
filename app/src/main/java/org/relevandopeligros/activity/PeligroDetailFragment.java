package org.relevandopeligros.activity;

import android.app.Fragment;
import android.content.Context;
import android.database.DataSetObserver;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import org.relevandopeligros.data.Peligro;
import org.relevandopeligros.relevandopeligrosapp.R;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * A fragment representing a single Peligro detail screen.
 * This fragment is either contained in a {@link PeligroListActivity}
 * in two-pane mode (on tablets) or a {@link PeligroDetailActivity}
 * on handsets.
 */
public class PeligroDetailFragment extends Fragment {
    /**
     * The fragment argument representing the item ID that this fragment
     * represents.
     */
    public static final String PELIGRO_SELECTED = "peligro_selected";

    /**
     * The dummy content this fragment is presenting.
     */


    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public PeligroDetailFragment() {
    }

    @InjectView(R.id.peligro_detail_title)
    TextView peligroDetailTitle;
    @InjectView(R.id.peligro_detail_description)
    TextView peligroDetailDescription;
    @InjectView(R.id.peligro_detail_grid_view)
    GridView peligroGridView;


    private Peligro peligro;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        peligro = (Peligro) getArguments().getSerializable(PELIGRO_SELECTED);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_peligro_detail, container, false);
        ButterKnife.inject(this, rootView);
        return rootView;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        peligroDetailTitle.setText(peligro.getTitulo());
        peligroDetailDescription.setText(peligro.getDescripcion());
        peligroGridView.setAdapter(new PeligroGridAdapter(getActivity(), peligro.getImagenes()));
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.reset(this);
    }

    static class PeligroGridAdapter implements ListAdapter {

        private final Context context;
        private final List<Peligro.ImagenPeligro> imagenPeligroList;
        private final LayoutInflater inflater;

        PeligroGridAdapter(Context context, List<Peligro.ImagenPeligro> imagenPeligroList) {
            this.context = context;
            this.imagenPeligroList = imagenPeligroList;
            inflater = LayoutInflater.from(context);
        }

        @Override
        public void registerDataSetObserver(DataSetObserver dataSetObserver) {

        }

        @Override
        public void unregisterDataSetObserver(DataSetObserver dataSetObserver) {

        }

        @Override
        public int getCount() {
            return imagenPeligroList.size();
        }

        @Override
        public Object getItem(int i) {
            return imagenPeligroList.get(i);
        }

        @Override
        public long getItemId(int i) {
            return 0;
        }

        @Override
        public boolean hasStableIds() {
            return false;
        }

        @Override
        public View getView(int position, View view, ViewGroup parent) {
            ViewHolder holder;
            if (view != null) {
                holder = (ViewHolder) view.getTag();
            } else {
                view = inflater.inflate(R.layout.peligro_detail_grid_item, parent, false);
                holder = new ViewHolder(view);
                view.setTag(holder);
            }
            Peligro.ImagenPeligro imagenPeligro = imagenPeligroList.get(position);

            holder.peligroImageTitle.setText(imagenPeligro.getTitulo());
            Picasso.with(context).load(imagenPeligro.getPath()).into(holder.peligroImage);

            return view;
        }

        @Override
        public int getItemViewType(int i) {
            return 0;
        }

        @Override
        public int getViewTypeCount() {
            return 1;
        }

        @Override
        public boolean isEmpty() {
            return imagenPeligroList.isEmpty();
        }

        @Override
        public boolean areAllItemsEnabled() {
            return false;
        }

        @Override
        public boolean isEnabled(int i) {
            return false;
        }

        static class ViewHolder {
            @InjectView(R.id.peligro_grid_image)
            ImageView peligroImage;
            @InjectView(R.id.peligro_grid_image_description)
            TextView peligroImageTitle;

            public ViewHolder(View view) {
                ButterKnife.inject(this, view);
            }
        }
    }
}
