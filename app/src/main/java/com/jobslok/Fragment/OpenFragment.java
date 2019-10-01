package com.jobslok.Fragment;


import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.provider.MediaStore;
import android.text.Editable;
import android.text.Selection;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.hungnc.expandabletextview.ExpandableTextView;
import com.jobslok.Activity.ChatActivity;
import com.jobslok.R;
import com.jobslok.ViewModel.OfferView;
import com.jobslok.ViewModel.QuestionView;

import org.w3c.dom.Text;

import java.io.ByteArrayOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Objects;
import java.util.UUID;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * A simple {@link Fragment} subclass.
 */
public class OpenFragment extends Fragment {


    private AlertDialog.Builder alertbox;
    private FirestoreRecyclerAdapter<OfferView, OfferViewHolder> adapter;
    private FirestoreRecyclerAdapter<QuestionView,QuestionsViewHolder> adapter2;
    private Bitmap bitmap;
    public OpenFragment() {
        // Required empty public constructor
    }

    private DocumentReference documentReference, documentReferenceToUsers;
    private CollectionReference collectionReferenceInitial;
    private CollectionReference offerReference,questionReference;
    private String type, postId;
    private CircleImageView circleImageView,circleImageView2;
    private TextView name;
    private TextView postDate;
    private TextView location;
    private TextView date;
    private TextView price;
    private TextView details;
    private TextView noOfOffers,noOfQuestions;
    private TextView title;
    private TextView send;
    private Button offer;
    private EditText questions;
    private String uid,userpostId,uri;
    private RecyclerView offerRecyclerView,questionRecyclerView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_open, container, false);
        type = Objects.requireNonNull(getActivity()).getIntent().getStringExtra("type");
        postId = getActivity().getIntent().getStringExtra("pid");
        userpostId=getActivity().getIntent().getStringExtra("uid");
        uri=getActivity().getIntent().getStringExtra("uri");


        documentReference = FirebaseFirestore.getInstance().collection(type).document(postId);
        collectionReferenceInitial=FirebaseFirestore.getInstance().collection("users");
        offerReference = documentReference.collection("offers");
        questionReference=documentReference.collection("questions");
        circleImageView = view.findViewById(R.id.open_imageview);
        circleImageView2=view.findViewById(R.id.current_user_img);
        offer = view.findViewById(R.id.open_offer_button);
        title=view.findViewById(R.id.open_title);
        name = view.findViewById(R.id.open_name);
        postDate = view.findViewById(R.id.postDate);
        location = view.findViewById(R.id.open_locarion);
        date = view.findViewById(R.id.open_date);
        price = view.findViewById(R.id.open_price);
        details = view.findViewById(R.id.open_details);
        offerRecyclerView=view.findViewById(R.id.open_offersRecyclerView);
        questionRecyclerView=view.findViewById(R.id.open_questionsRecyclerView);
        noOfOffers=view.findViewById(R.id.no_of_offers);
        noOfQuestions=view.findViewById(R.id.no_of_question);
        send=view.findViewById(R.id.task_send);
        questions=view.findViewById(R.id.task_questions);
        questions.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                    if(questions.getText().toString().isEmpty())
                        send.setEnabled(false);
                    else
                        send.setEnabled(true);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        offerReference.get().addOnCompleteListener(task -> {
           if(task.isSuccessful()){
               int count=0;
               for(DocumentSnapshot ignored : Objects.requireNonNull(task.getResult())){
                   count++;
               }
               noOfOffers.setText("OFFERS ("+count+")");
           }
        });
        questionReference.get().addOnCompleteListener(task -> {
            if(task.isSuccessful()){
                int count=0;
                for(DocumentSnapshot ignored : Objects.requireNonNull(task.getResult())){
                    count++;
                }
                noOfQuestions.setText("QUESTIONS ("+count+")");
            }
        });

        uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
        documentReferenceToUsers = FirebaseFirestore.getInstance().collection("users").document(uid);
        documentReferenceToUsers.get().addOnCompleteListener(task -> {
            Glide.with(getActivity()).load(task.getResult().get("image")).into(circleImageView2);
        });
        documentReference.get().addOnCompleteListener(task -> {
            title.setText(task.getResult().get("title").toString());
            Glide.with(getActivity())
                    .asBitmap()
                    .load(task.getResult().get("image"))
                    .into(new SimpleTarget<Bitmap>() {
                        @Override
                        public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                            // you can do something with loaded bitmap here
                            circleImageView.setImageBitmap(resource);
                            bitmap= resource;
                        }
                    });
            Glide.with(getActivity()).load(task.getResult().get("image")).into(circleImageView);
            name.setText(task.getResult().get("name").toString());
            postDate.setText(task.getResult().get("postDate").toString());
            details.setText("Details - " + "\n" + task.getResult().get("description").toString());
            if (Boolean.valueOf(task.getResult().get("remotely").toString()))
                location.setText("Location" + "\n" + "Remotely");
            else
                location.setText("Location" + "\n" + task.getResult().get("location").toString());
            date.setText("To be done on" + "\n" + task.getResult().get("date").toString());
            price.setText("\u20B9" + task.getResult().get("budget").toString());
            if (task.getResult().get("uid").toString().equals(uid)) {
                offer.setText("Reviews offers");
           } else {
                offer.setText("Make an offer");
            }
            offer.setOnClickListener(view1 -> {
                  if(!task.getResult().get("uid").toString().equals(uid))
                        alertDialogBox(task.getResult().get("budget").toString());
                    }
            );
        });
        send.setOnClickListener(view12 -> {
            ProgressDialog progressDialog=new ProgressDialog(getActivity());
            progressDialog.setMessage("Please wait...");
            progressDialog.show();
            documentReferenceToUsers.get().addOnCompleteListener(task -> {
                HashMap<String,String> map=new HashMap<>();
                String question=questions.getText().toString();
                String name=task.getResult().get("name").toString();
                String image=task.getResult().get("image").toString();
                String myFormat = "EEE,dd MMM"; //In which you need put here
                SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
                map.put("date", sdf.format(new Date()));
                map.put("question",question);
                map.put("name",name);
                map.put("image",image);
                map.put("uid",uid);
                questionReference.add(map).addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentReference> task) {
                        progressDialog.dismiss();
                        Toast.makeText(getActivity(), "Posted", Toast.LENGTH_SHORT).show();
                    }
                });
            });

        });

        LinearLayoutManager manager=new LinearLayoutManager(getActivity(), RecyclerView.VERTICAL, false);
        offerRecyclerView.setLayoutManager(manager);
        LinearLayoutManager manager1=new LinearLayoutManager(getActivity(), RecyclerView.VERTICAL, false);
        questionRecyclerView.setLayoutManager(manager1);
        FirestoreRecyclerOptions<QuestionView> recyclerOptions=new FirestoreRecyclerOptions.Builder<QuestionView>().
                setQuery(questionReference,QuestionView.class)
                .build();
        adapter2=new FirestoreRecyclerAdapter<QuestionView, QuestionsViewHolder>(recyclerOptions) {
            @Override
            protected void onBindViewHolder(@NonNull QuestionsViewHolder questionsViewHolder, int i, @NonNull QuestionView questionView) {
                questionsViewHolder.name.setText(questionView.getName());
                Glide.with(getActivity()).load(questionView.getImage()).into(questionsViewHolder.imageView);
                questionsViewHolder.question.setText(questionView.getQuestion());
                questionsViewHolder.date.setText(questionView.getDate());
            }

            @NonNull
            @Override
            public QuestionsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view1=LayoutInflater.from(getActivity()).inflate(R.layout.questions_viewlayout,parent,false);
                return new QuestionsViewHolder(view1);
            }
        };
        questionRecyclerView.setAdapter(adapter2);
        adapter2.startListening();

        FirestoreRecyclerOptions<OfferView> options=new FirestoreRecyclerOptions.Builder<OfferView>().
                setQuery(offerReference,OfferView.class)
                .build();

        adapter=new FirestoreRecyclerAdapter<OfferView, OfferViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull OfferViewHolder offerViewHolder, int i, @NonNull OfferView offerView) {
                 offerViewHolder.name.setText(offerView.getName());
                 Glide.with(getActivity()).load(offerView.getImage()).into(offerViewHolder.imageView);
                 offerViewHolder.description.setText(offerView.getDescription());
                 offerViewHolder.postDate.setText(offerView.getPostDate());
                 offerViewHolder.ratedBy.setText("("+offerView.getRatedBy()+")");
                 offerViewHolder.ratingBar.setRating(Float.parseFloat(offerView.getRating()));
                 if(uid.equals(userpostId)){
                     offerViewHolder.linearLayout.setVisibility(View.VISIBLE);
                     offerViewHolder.price.setText(offerView.getPrice());
                 }else
                     offerViewHolder.linearLayout.setVisibility(View.GONE);

                Log.i("OpenFragment",""+offerView.getAccepted());

                 if(offerView.getAccepted().equals("true")){
                     offerViewHolder.accept.setEnabled(false);
                     offerViewHolder.price.setEnabled(false);
                 }
                 else {
                     offerViewHolder.accept.setEnabled(true);
                     offerViewHolder.price.setEnabled(true


                     );
                 }
                 offerViewHolder.completedTask.setText("Task Completed - "+offerView.getTaskCompleted());
                 offerViewHolder.accept.setOnClickListener(view13 -> {
                     DocumentSnapshot snapshot = getSnapshots().getSnapshot(offerViewHolder.getAdapterPosition());
                     String id= snapshot.getId();
                     offerReference.document(id).update("accepted","true");
                     HashMap<String,String> map=new HashMap<>();
                     map.put("image",offerView.getImage());
                     map.put("name",offerView.getName());
                     map.put("category",type);
                     map.put("lastmessage","");
                     map.put("count","0");
                     map.put("is","tasker");
                     map.put("taskerID",id);
                     map.put("postID",postId);
                     map.put("posterID",userpostId);

                     HashMap<String,String> map1=new HashMap<>();
                     map1.put("image",uri);
                     map1.put("name",name.getText().toString());
                     map1.put("category",type);
                     map1.put("is","poster");
                     map1.put("lastmessage","");
                     map1.put("count","0");
                     map1.put("taskerID",id);
                     map1.put("postID",postId);
                     map1.put("posterID",userpostId);
                     collectionReferenceInitial.document(id).collection("accept").add(map1).addOnCompleteListener(task -> {
                  //       sendNotification(id);

                        String referenceid= task.getResult().getId();
                         documentReferenceToUsers.collection("assign").document(referenceid).set(map).addOnCompleteListener(task1 -> {
                             Intent intent=new Intent(getActivity(),ChatActivity.class);
                             intent.putExtra("postID",postId);
                             intent.putExtra("taskerID",id);
                             intent.putExtra("name",name.getText());
                             intent.putExtra("image",uri);
                             intent.putExtra("category",type);
                             intent.putExtra("posterID",userpostId);
                             intent.putExtra("is","tasker");
                             intent.putExtra("reference", referenceid);
                             intent.putExtra("assign_accept","assign");
                             startActivity(intent);
                         });
                     });
                 });
            }

            @NonNull
            @Override
            public OfferViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view1=LayoutInflater.from(getActivity()).inflate(R.layout.offer_viewlayout,parent,false);
                return new OfferViewHolder(view1);
            }
        };
        offerRecyclerView.setAdapter(adapter);
        adapter.startListening();
        return view;
    }

    private void sendNotification(String id) {
        Toast.makeText(getActivity(), "Notification Send "+id, Toast.LENGTH_SHORT).show();
    }



    @Override
    public void onStart() {
        super.onStart();
        adapter.startListening();
        adapter2.startListening();
    }

    class QuestionsViewHolder extends  RecyclerView.ViewHolder{
        CircleImageView imageView;
        TextView name;
        TextView date;
        ExpandableTextView question;
        public QuestionsViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView=itemView.findViewById(R.id.question_imageview);
            name=itemView.findViewById(R.id.question_name);
            date=itemView.findViewById(R.id.question_date);
            question=itemView.findViewById(R.id.question_description);
        }
    }

    class OfferViewHolder extends RecyclerView.ViewHolder{
      CircleImageView imageView;
      TextView name;
      TextView price;
      TextView completedTask;
      TextView accept;
      RatingBar ratingBar;
      TextView ratedBy;
      ExpandableTextView description;
      TextView postDate;
      TextView reply;
      LinearLayout linearLayout;
        public OfferViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView=itemView.findViewById(R.id.offer_imageview);
            name=itemView.findViewById(R.id.offer_name);
            ratingBar=itemView.findViewById(R.id.offer_ratingBar);
            ratedBy=itemView.findViewById(R.id.offer_ratedBy);
            description=itemView.findViewById(R.id.offer_description);
            postDate=itemView.findViewById(R.id.offer_postDate);
            reply=itemView.findViewById(R.id.offer_reply);
            price=itemView.findViewById(R.id.offer_price);
            completedTask=itemView.findViewById(R.id.offer_completedTask);
            accept=itemView.findViewById(R.id.offer_accept);
            linearLayout=itemView.findViewById(R.id.price_offer_layout);
        }

    }

    private void alertDialogBox(String budget) {
        alertbox = new AlertDialog.Builder(getActivity());
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.dialog_offer, null);
        alertbox.setView(view);
        EditText price = view.findViewById(R.id.offer_price);
        Button cancel = view.findViewById(R.id.cancel);
        Button done = view.findViewById(R.id.done);
        EditText description = view.findViewById(R.id.offer_description);
        AlertDialog dialog = alertbox.create();
        dialog.show();
        cancel.setOnClickListener(view1 -> {
            dialog.dismiss();
        });
        price.setText("\u20B9" + budget);
        Selection.setSelection(price.getText(), price.getText().length());
        price.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // TODO Auto-generated method stub

            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {
                // TODO Auto-generated method stub
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (!s.toString().startsWith("\u20B9")) {
                    price.setText("\u20B9");
                    Selection.setSelection(price.getText(), price.getText().length());
                }
            }
        });

        done.setOnClickListener(view1 -> {
            if (!description.getText().toString().isEmpty() && !price.getText().toString().isEmpty()) {
                ProgressDialog progressDialog=new ProgressDialog(getActivity());
                progressDialog.setMessage("Please wait...");
                progressDialog.show();
                String desc = description.getText().toString();
                String pri = price.getText().toString();
                HashMap<String, Object> map = new HashMap<>();

                String myFormat = "EEE,dd MMM"; //In which you need put here
                SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
                map.put("postDate", sdf.format(new Date()));
                map.put("description", desc);
                map.put("price", pri);
                map.put("accepted","false");
                documentReferenceToUsers.get().addOnCompleteListener(task -> {
                    String name = task.getResult().get("name").toString();
                    String image = task.getResult().get("image").toString();
                    String rating = task.getResult().get("rating").toString();
                    String taskCompleted = task.getResult().get("taskCompleted").toString();
                    String ratedBy=task.getResult().get("ratedBy").toString();
                    map.put("name", name);
                    map.put("image", image);
                    map.put("rating", rating);
                    map.put("ratedBy",ratedBy);
                    map.put("taskCompleted", taskCompleted);
                    offerReference.document(uid).set(map).addOnCompleteListener(task1 -> {
                        Toast.makeText(getActivity(), "Congrats! your offer is been added", Toast.LENGTH_SHORT).show();
                        progressDialog.dismiss();
                        dialog.dismiss();
                    });
                });
            } else {
                description.setError("This field can't be empty ");
            }
        });
    }
}