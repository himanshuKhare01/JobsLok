package com.jobslok.Fragment;


import android.annotation.SuppressLint;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.jobslok.R;
import com.jobslok.ViewModel.PostTaskView;

import java.util.Objects;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentOne extends Fragment {


    private FirestoreRecyclerAdapter<PostTaskView, ChatHolder> adapter;

    public FragmentOne() {
        // Required empty public constructor
    }

    private RecyclerView recyclerView;
    private CollectionReference collectionReference;
    private String uid;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_one, container, false);
        recyclerView = v.findViewById(R.id.one_recyclerView);
        uid = Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getUid();
        collectionReference = FirebaseFirestore.getInstance().collection(getResources().getStringArray(R.array.task_type)[0]);
        FirestoreRecyclerOptions<PostTaskView> options = new FirestoreRecyclerOptions.Builder<PostTaskView>()
                .setQuery(collectionReference, PostTaskView.class)
                .build();
        adapter = new FirestoreRecyclerAdapter<PostTaskView, ChatHolder>(options) {
            @SuppressLint("SetTextI18n")
            @Override
            public void onBindViewHolder(@NonNull ChatHolder holder, int position, @NonNull PostTaskView model) {
                if(model.getUid().equals(uid)) {
                    holder.title.setText(model.getTitle().substring(0,1).toUpperCase() + model.getTitle().substring(1));
                    holder.date.setText(model.getDate());
                    holder.numberOftasker.setText("Number of tasker " + model.getNumberof_tasker());
                    if (model.getBudget() != null && !model.getBudget().isEmpty())
                        holder.budget.setText(getString(R.string.rs) + model.getBudget());
                    else
                        holder.budget.setText(getString(R.string.rs) + model.getPricehour() + "/hr" + " - " + model.getHour() + "hr");
                    if (model.getRemotely())
                        holder.remote.setText("Remotely");
                    else
                        holder.remote.setText(model.getLocation().trim());
                    Glide.with(Objects.requireNonNull(getActivity())).load(Uri.parse(model.getImage())).into(holder.circleImageView);
                }else{
                    holder.itemView.setVisibility(View.GONE);
                }
            }

            @NonNull
            @Override
            public ChatHolder onCreateViewHolder(ViewGroup group, int i) {
                View view = LayoutInflater.from(group.getContext())
                        .inflate(R.layout.post_task_view, group, false);
                return new ChatHolder(view);
            }

            @Override
            public void onError(@NonNull FirebaseFirestoreException e) {
                super.onError(e);
                Toast.makeText(getActivity(), "" + e.getMessage(), Toast.LENGTH_SHORT).show();
            }

        };
        LinearLayoutManager manager = new LinearLayoutManager(getActivity(), RecyclerView.VERTICAL, false);
        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(adapter);
        adapter.startListening();
        return v;
    }

    @Override
    public void onStart() {
        super.onStart();
        adapter.startListening();
    }

    class ChatHolder extends RecyclerView.ViewHolder {
        TextView title;
        TextView remote;
        TextView date;
        TextView numberOftasker;
        TextView budget;
        CircleImageView circleImageView;

        ChatHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.post_title);
            remote = itemView.findViewById(R.id.post_remote);
            date = itemView.findViewById(R.id.post_date);
            budget = itemView.findViewById(R.id.post_budget);
            numberOftasker = itemView.findViewById(R.id.post_numberTasker);
            circleImageView = itemView.findViewById(R.id.post_profile_image);
        }
    }
}
