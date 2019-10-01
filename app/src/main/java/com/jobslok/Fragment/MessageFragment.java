package com.jobslok.Fragment;


import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Spinner;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.jobslok.Activity.ChatActivity;
import com.jobslok.R;
import com.jobslok.ViewModel.MessageTemplate;

import java.util.Objects;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * A simple {@link Fragment} subclass.
 */
public class MessageFragment extends Fragment {

    private RecyclerView recyclerView;
    private CollectionReference referenceToAssigned;
    private CollectionReference referenceToAccepted;
    private String uid;
    private Spinner spinner;
    private FirestoreRecyclerAdapter<MessageTemplate, MessageViewHolder> adapter_assign;
    private FirestoreRecyclerAdapter<MessageTemplate, MessageViewHolder> adapter_accept;

    public MessageFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_message, container, false);
        recyclerView=view.findViewById(R.id.recyclerViewofMessages);
        spinner=view.findViewById(R.id.message_type);
        uid= FirebaseAuth.getInstance().getCurrentUser().getUid();
        referenceToAccepted= FirebaseFirestore.getInstance().collection("users").document(uid).collection("accept");
        referenceToAssigned=FirebaseFirestore.getInstance().collection("users").document(uid).collection("assign");
        LinearLayoutManager manager=new LinearLayoutManager(getActivity(),RecyclerView.VERTICAL,false);
        recyclerView.setLayoutManager(manager);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                if(i==0)
                   showAllAccepted();
                else
                    showAllAssigned();

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        return view;
    }

    private void showAllAssigned() {

        FirestoreRecyclerOptions<MessageTemplate> options_assign=new FirestoreRecyclerOptions.Builder<MessageTemplate>()
                .setQuery(referenceToAssigned,MessageTemplate.class)
                .build();

        adapter_assign = new FirestoreRecyclerAdapter<MessageTemplate, MessageViewHolder>(options_assign) {
            @Override
            protected void onBindViewHolder(@NonNull MessageViewHolder messageViewHolder, int i, @NonNull MessageTemplate messageTemplate) {
                if(!messageTemplate.getCategory().isEmpty()) {
                    messageViewHolder.name.setText(messageTemplate.getName());
                    Glide.with(Objects.requireNonNull(getActivity())).load(messageTemplate.getImage()).into(messageViewHolder.circleImageView);
                    messageViewHolder.category.setText(messageTemplate.getCategory());
                    if (messageTemplate.getCount().equals("0")) {
                        messageViewHolder.unreadmessage.setVisibility(View.GONE);
                        messageViewHolder.lastmessage.setTypeface(null, Typeface.NORMAL);
                    } else {
                        messageViewHolder.unreadmessage.setVisibility(View.VISIBLE);
                        messageViewHolder.unreadmessage.setText(messageTemplate.getCount());
                        messageViewHolder.lastmessage.setTypeface(null, Typeface.BOLD);
                    }
                    messageViewHolder.lastmessage.setText(messageTemplate.getLastmessage());
                    messageViewHolder.itemView.setOnClickListener(view1 -> {
                        DocumentSnapshot snapshot=getSnapshots().getSnapshot(messageViewHolder.getAdapterPosition());
                        String refernceid=snapshot.getId();
                        Intent intent=new Intent(getActivity(),ChatActivity.class);
                        intent.putExtra("name",messageTemplate.getName());
                        intent.putExtra("image",messageTemplate.getImage());
                        intent.putExtra("category",messageTemplate.getCategory());
                        intent.putExtra("posterID",messageTemplate.getPosterID());
                        intent.putExtra("taskerID",messageTemplate.getTaskerID());
                        intent.putExtra("postID",messageTemplate.getPostID());
                        intent.putExtra("is",messageTemplate.getIs());
                        intent.putExtra("reference", refernceid);
                        intent.putExtra("assign_accept","assign");
                        startActivity(intent);
                    });
                }else
                    messageViewHolder.itemView.setVisibility(View.GONE);
            }

            @NonNull
            @Override
            public MessageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view1 = LayoutInflater.from(getActivity()).inflate(R.layout.chatuser_model, parent, false);
                return new MessageViewHolder(view1);
            }
        };
        recyclerView.setAdapter(adapter_assign);
        adapter_assign.startListening();
    }

    private void showAllAccepted() {
        FirestoreRecyclerOptions<MessageTemplate> options_accept=new FirestoreRecyclerOptions.Builder<MessageTemplate>()
                .setQuery(referenceToAccepted,MessageTemplate.class)
                .build();
        adapter_accept = new FirestoreRecyclerAdapter<MessageTemplate, MessageViewHolder>(options_accept) {
            @Override
            protected void onBindViewHolder(@NonNull MessageViewHolder messageViewHolder, int i, @NonNull MessageTemplate messageTemplate) {
                messageViewHolder.name.setText(messageTemplate.getName());
                Glide.with(Objects.requireNonNull(getActivity())).load(messageTemplate.getImage()).into(messageViewHolder.circleImageView);
                messageViewHolder.category.setText(messageTemplate.getCategory());
                messageViewHolder.lastmessage.setText(messageTemplate.getLastmessage());
                if (messageTemplate.getCount().equals("0")) {
                    messageViewHolder.unreadmessage.setVisibility(View.GONE);
                    messageViewHolder.lastmessage.setTypeface(null, Typeface.NORMAL);
                } else {
                    messageViewHolder.unreadmessage.setVisibility(View.VISIBLE);
                    messageViewHolder.unreadmessage.setText(messageTemplate.getCount());
                    messageViewHolder.lastmessage.setTypeface(null, Typeface.BOLD);
                }
                messageViewHolder.itemView.setOnClickListener(view1 -> {
                    DocumentSnapshot snapshot=getSnapshots().getSnapshot(messageViewHolder.getAdapterPosition());
                    String refernceid=snapshot.getId();
                    Intent intent=new Intent(getActivity(),ChatActivity.class);
                    intent.putExtra("name",messageTemplate.getName());
                    intent.putExtra("image",messageTemplate.getImage());
                    intent.putExtra("category",messageTemplate.getCategory());
                    intent.putExtra("posterID",messageTemplate.getPosterID());
                    intent.putExtra("taskerID",messageTemplate.getTaskerID());
                    intent.putExtra("postID",messageTemplate.getPostID());
                    intent.putExtra("is",messageTemplate.getIs());
                    intent.putExtra("reference", refernceid);
                    intent.putExtra("assign_accept","accept");
                    startActivity(intent);
                });
            }

            @NonNull
            @Override
            public MessageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view1 = LayoutInflater.from(getActivity()).inflate(R.layout.chatuser_model, parent, false);
                return new MessageViewHolder(view1);
            }
        };
        recyclerView.setAdapter(adapter_accept);
        adapter_accept.startListening();
    }

    @Override
    public void onStart() {
        super.onStart();
//        adapter_assign.startListening();
 //       adapter_accept.startListening();
    }

    class MessageViewHolder extends  RecyclerView.ViewHolder{
        private CircleImageView circleImageView;
        private TextView name;
        private TextView category;
        private TextView lastmessage;
        private TextView unreadmessage;

        MessageViewHolder(@NonNull View itemView) {
            super(itemView);
            circleImageView=itemView.findViewById(R.id.image);
            name=itemView.findViewById(R.id.name);
            category=itemView.findViewById(R.id.category);
            lastmessage=itemView.findViewById(R.id.lastmesage);
            unreadmessage=itemView.findViewById(R.id.unread_messages);
        }
    }
}