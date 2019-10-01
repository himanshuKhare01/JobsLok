package com.jobslok.Fragment;


import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Spinner;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.jobslok.Activity.ViewTaskActivity;
import com.jobslok.R;
import com.jobslok.ViewModel.PostTaskView;

import java.util.Objects;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * A simple {@link Fragment} subclass.
 */
public class BrowseFragment extends Fragment implements SearchView.OnQueryTextListener {


    private FirestoreRecyclerAdapter<PostTaskView, TaskViewHolder> adapter;

    public BrowseFragment() {
        // Required empty public constructor
    }

    private RecyclerView recyclerView;
    private String type="Cleaning";
    private CollectionReference collectionReference;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_browse, container, false);
        ((AppCompatActivity) Objects.requireNonNull(getActivity())).setSupportActionBar(view.findViewById(R.id.browse_toolbar));
        recyclerView = view.findViewById(R.id.browse_taskviewpager);
        Spinner spinner = view.findViewById(R.id.browse_spinner);
        collectionReference = FirebaseFirestore.getInstance().collection(type);
        showAllTasks(null,collectionReference);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                type = adapterView.getItemAtPosition(i).toString();
                adapter.notifyDataSetChanged();
                collectionReference = FirebaseFirestore.getInstance().collection(type);
                showAllTasks(null,collectionReference);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        return view;
    }

    private void showAllTasks(String query,CollectionReference reference) {
        Query firebaseSearchQuery=reference.orderBy("title");
        if(query!=null&&!query.isEmpty())
            firebaseSearchQuery = firebaseSearchQuery.startAt(query).endAt(query + "\uf8ff");

        FirestoreRecyclerOptions<PostTaskView> options = new FirestoreRecyclerOptions.Builder<PostTaskView>()
                .setQuery(firebaseSearchQuery, PostTaskView.class)
                .build();

        adapter = new FirestoreRecyclerAdapter<PostTaskView, TaskViewHolder>(options) {
            @SuppressLint("SetTextI18n")
            @Override
            protected void onBindViewHolder(@NonNull TaskViewHolder holder, int i, @NonNull PostTaskView model) {

                String title=model.getTitle();
                holder.title.setText(title.substring(0,1).toUpperCase()+title.substring(1));
                holder.date.setText(model.getDate());
                holder.numberOftasker.setText("Number of tasker " + model.getNumberof_tasker());

                if (model.getBudget() != null && !model.getBudget().isEmpty())
                    holder.budget.setText(getString(R.string.rs)+ model.getBudget());
                else
                    holder.budget.setText(getString(R.string.rs) + "/hr" + " - " + model.getHour() + "hr");

                if (model.getRemotely())
                    holder.remote.setText("Remotely");
                else
                    holder.remote.setText(model.getLocation().trim());

                Glide.with(Objects.requireNonNull(getActivity())).load(Uri.parse(model.getImage())).into(holder.circleImageView);

                holder.itemView.setOnClickListener(view -> {
                    DocumentSnapshot snapshot = getSnapshots().getSnapshot(holder.getAdapterPosition());
                    String id= snapshot.getId();
                    String uid=model.getUid();
                    String uri=model.getImage();
                    startActivity(new Intent(getActivity(), ViewTaskActivity.class).putExtra("uid",uid).putExtra("uri",uri).putExtra("pid",id).putExtra("type",type));
                });
            }

            @NonNull
            @Override
            public TaskViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view1 = LayoutInflater.from(parent.getContext()).inflate(R.layout.post_task_view, parent, false);
                return new TaskViewHolder(view1);
            }
        };
        LinearLayoutManager manager = new LinearLayoutManager(getActivity(), RecyclerView.VERTICAL, false);
        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(adapter);
        adapter.startListening();
    }

    @Override
    public void onStart() {
        super.onStart();
        adapter.startListening();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.browse_menu, menu);
        super.onCreateOptionsMenu(menu, inflater);
        SearchView searchView = (SearchView) menu.findItem(R.id.search).getActionView();
        searchView.setOnQueryTextListener(this);
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        collectionReference = FirebaseFirestore.getInstance().collection(type);
        showAllTasks(newText,collectionReference);
        return true;
    }

    class TaskViewHolder extends RecyclerView.ViewHolder {
        TextView title;
        TextView remote;
        TextView date;
        TextView numberOftasker;
        TextView budget;
        CircleImageView circleImageView;
        TextView status;
        TextView noofoffer;

        public TaskViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.post_title);
            remote = itemView.findViewById(R.id.post_remote);
            date = itemView.findViewById(R.id.post_date);
            budget = itemView.findViewById(R.id.post_budget);
            numberOftasker = itemView.findViewById(R.id.post_numberTasker);
            circleImageView = itemView.findViewById(R.id.post_profile_image);
            status=itemView.findViewById(R.id.post_status);
            noofoffer=itemView.findViewById(R.id.post_offers);
        }

    }
}
