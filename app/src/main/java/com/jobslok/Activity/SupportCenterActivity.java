package com.jobslok.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.MenuItem;

import com.jobslok.Adapter.SupportAdapter;
import com.jobslok.R;
import com.jobslok.ViewModel.Artist;
import com.jobslok.ViewModel.Genre;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class SupportCenterActivity extends AppCompatActivity {

    private SupportAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_support_center);
        Toolbar toolbar = findViewById(R.id.toolbar_support);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_arrow_back_black_24dp);
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycler_view_support);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        RecyclerView.ItemAnimator animator = recyclerView.getItemAnimator();
        if (animator instanceof DefaultItemAnimator) {
            ((DefaultItemAnimator) animator).setSupportsChangeAnimations(false);
        }
        adapter = new SupportAdapter(makeParentQuestion());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
    }
    public static List<Genre> makeParentQuestion() {
        return Arrays.asList(makeParentQuestion1(), makeParentQuestion2(), makeParentQuestion3(),makeParentQuestion4(),makeParentQuestion5(),makeParentQuestion6(),makeParentQuestion7(),makeParentQuestion8(),makeParentQuestion9(),makeParentQuestion10(),makeParentQuestion11(),makeParentQuestion12(),makeParentQuestion13(),makeParentQuestion14(),makeParentQuestion15(),makeParentQuestion16(),makeParentQuestion17(),makeParentQuestion18(),makeParentQuestion19(),makeParentQuestion20(),makeParentQuestion21(),makeParentQuestion22()
        );
    }

    public static Genre makeParentQuestion1() {
        return new Genre("Understanding JobsLok", makeChildQuestion1(), R.drawable.ic_banjo);
    }

    public static Genre makeParentQuestion2() {
        return new Genre("Account administration", makeChildQuestion2(), R.drawable.ic_banjo);
    }
    public static Genre makeParentQuestion3(){
        return new Genre("Profile", makeChildQuestion3(), R.drawable.ic_banjo);
    }
    public static Genre makeParentQuestion4(){
        return new Genre("Badges & Verification", makeChildQuestion4(), R.drawable.ic_banjo);
    }
    public static Genre makeParentQuestion5(){
        return new Genre("TroubleShooting Profile", makeChildQuestion5(), R.drawable.ic_banjo);
    }
    public static Genre makeParentQuestion6(){
        return new Genre("Before Posting", makeChildQuestion6(), R.drawable.ic_banjo);
    }
    public static Genre makeParentQuestion7(){
        return new Genre("After Posting", makeChildQuestion7(), R.drawable.ic_banjo);
    }
    public static Genre makeParentQuestion8(){
        return new Genre("Make an offer", makeChildQuestion8(), R.drawable.ic_banjo);
    }
    public static Genre makeParentQuestion9(){
        return new Genre("Community Guidelines", makeChildQuestion9(), R.drawable.ic_banjo);
    }
    public static Genre makeParentQuestion10(){
        return new Genre("Understanding payments", makeChildQuestion10(), R.drawable.ic_banjo);
    }
    public static Genre makeParentQuestion11(){
        return new Genre("Poster", makeChildQuestion11(), R.drawable.ic_banjo);
    }
    public static Genre makeParentQuestion12(){
        return new Genre("Tasker", makeChildQuestion12(), R.drawable.ic_banjo);
    }
    public static Genre makeParentQuestion13(){
        return new Genre("TroubleShooting Payment", makeChildQuestion13(), R.drawable.ic_banjo);
    }
    public static Genre makeParentQuestion14(){
        return new Genre("Changes", makeChildQuestion14(), R.drawable.ic_banjo);
    }
    public static Genre makeParentQuestion15(){
        return new Genre("Cancellations", makeChildQuestion15(), R.drawable.ic_banjo);
    }
    public static Genre makeParentQuestion16(){
        return new Genre("Poster Safety", makeChildQuestion16(), R.drawable.ic_banjo);
    }
    public static Genre makeParentQuestion17(){
        return new Genre("Tasker Safety", makeChildQuestion17(), R.drawable.ic_banjo);
    }
    public static Genre makeParentQuestion18(){
        return new Genre("Dispute Resolution", makeChildQuestion18(), R.drawable.ic_banjo);
    }
    public static Genre makeParentQuestion19(){
        return new Genre("Communication", makeChildQuestion19(), R.drawable.ic_banjo);
    }
    public static Genre makeParentQuestion20(){
        return new Genre("Rating & Reviews", makeChildQuestion20(), R.drawable.ic_banjo);
    }
    public static Genre makeParentQuestion21(){
        return new Genre("JobsLok Insurance", makeChildQuestion21(), R.drawable.ic_banjo);
    }
    public static Genre makeParentQuestion22(){
        return new Genre("Tax", makeChildQuestion22(), R.drawable.ic_banjo);
    }

    public static List<Artist> makeChildQuestion1() {
        Artist item1 = new Artist("What is the service fee?", false);
        Artist item2 = new Artist("How to get started on JobsLok", false);
        Artist item3 = new Artist("What is JobsLok?", true);
        Artist item4 = new Artist("What type of tasks can I get done with JobsLok?", false);
        Artist item5 = new Artist("What is the booking fee?", false);
        return Arrays.asList(item1,item2,item3,item4,item5);
    }
    public static List<Artist> makeChildQuestion2() {
        Artist item1 = new Artist("How do i sign up?", false);
        Artist item2 = new Artist("How do i change my account settings?", false);
        Artist item3 = new Artist("What account details do I need to add to use JobsLok?", true);
        Artist item4 = new Artist("How do I set up task alerts?", false);
        Artist item5 = new Artist("I forgot my password, how do I reset it?", false);
        return Arrays.asList(item1,item2,item3,item4,item5);
    }
    public static List<Artist> makeChildQuestion3(){
        Artist item1 = new Artist("What should i put in my profile?",false);
        Artist item2 = new Artist("How do i add a profile photo to my account?",false);
        Artist item3 = new Artist("How do i update my skills?",false);
        Artist item4 = new Artist("How do I add a portfolio to my profile?",false);
      return Arrays.asList(item1,item2,item3,item4);
    }

    public static List<Artist> makeChildQuestion4(){
        Artist item1 = new Artist("JobsLok Badges",false);
        Artist item2 = new Artist("What are Badges? How can I get them?",false);
        Artist item3 = new Artist("Do I need a licence or qualification for tasks?",false);
        Artist item4 = new Artist("I can't update my profile because i have multiple badges, what do I do?",false);
        Artist item5 = new Artist("What is the ID Badge?",false);
        return Arrays.asList(item1,item2,item3,item4,item5);
      }

      public static List<Artist> makeChildQuestion5(){
          Artist item1 = new Artist("I can't sign up",false);
          Artist item2 = new Artist("I can't log in",false);
          Artist item3 = new Artist("This app is not working What should I do?",false);
          Artist item4 = new Artist("The site is not working. What should I do?",false);
          Artist item5 = new Artist("How to report a possible technical problem",false);
          return Arrays.asList(item1,item2,item3,item4,item5);
      }

    public static List<Artist> makeChildQuestion6(){
        Artist item1 = new Artist("How do I post a task?",false);
        Artist item2 = new Artist("How do I know how much to pay for a task?",false);
        Artist item3 = new Artist("What is the maximum task price?",false);
        Artist item4 = new Artist("How far ahead can I schedule a task?",false);
        Artist item5 = new Artist("What's a must-have?",false);
        return Arrays.asList(item1,item2,item3,item4,item5);
    }
    public static List<Artist> makeChildQuestion7(){
        Artist item1 = new Artist("I Posted my task, what's next?",false);
        Artist item2 = new Artist("How do I assign a Tasker to my task?",false);
        Artist item3 = new Artist("Why can't I assign my task?",false);
        Artist item4 = new Artist("How do I add photos to my task?",false);
        Artist item5 = new Artist("How do I request a quote from a specific Tasker?",false);
        return Arrays.asList(item1,item2,item3,item4,item5);
    }
    public static List<Artist> makeChildQuestion8() {
        Artist item1 = new Artist("Everything Taskers should know about making an offer\n", false);
        Artist item2 = new Artist("How do I make an offer?", false);
        Artist item3 = new Artist("How do I place my first offer?", false);
        Artist item4 = new Artist("How should I decide what price to offer?", false);
        Artist item5 = new Artist("How can I change my offer?", false);
        return Arrays.asList(item1, item2, item3, item4, item5);
    }
    public static List<Artist> makeChildQuestion9() {
        Artist item1 = new Artist("What are the Community Guidelines?", false);
        Artist item2 = new Artist("How can I report an inappropriate task or comment?", false);
        Artist item3 = new Artist("What happens if I breach the Community Guidelines?", false);
        Artist item4 = new Artist("What is subcontracting?", false);
        Artist item5 = new Artist("Are there any task posting guidelines?", false);
        return Arrays.asList(item1, item2, item3, item4, item5);
    }


    public static List<Artist> makeChildQuestion10() {
        Artist item1 = new Artist("What is JobsLok Pay?", false);
        Artist item2 = new Artist("How can I increase the price once the task is assigned?", false);
        Artist item3 = new Artist("How do I decrease the price?", false);
        Artist item4 = new Artist("I don't agree with an increase price request, what do I do?", false);
        Artist item5 = new Artist("How do I check my payment history?", false);
        return Arrays.asList(item1, item2, item3, item4, item5);
    }
    public static List<Artist> makeChildQuestion11() {
        Artist item1 = new Artist("Poster information about pricing and payments", false);
        Artist item2 = new Artist("How long will my refund take?", false);
        Artist item3 = new Artist("Why do I need to add a payment method to assign a task?", false);
        Artist item4 = new Artist("What are the payment methods on JobsLok?", false);
        Artist item5 = new Artist("When should I release payment?", false);
        return Arrays.asList(item1, item2, item3, item4, item5);
    }
    public static List<Artist> makeChildQuestion12() {
        Artist item1 = new Artist("Tasker information about pricing and payments", false);
        Artist item2 = new Artist("How do I get paid?", false);
        Artist item3 = new Artist("When will I get paid?", false);
        Artist item4 = new Artist("What is tiered pricing?", false);
        Artist item5 = new Artist("The Poster hasn't released payment yet, what do I do?", false);
        return Arrays.asList(item1, item2, item3, item4, item5);
    }
    public static List<Artist> makeChildQuestion13() {
        Artist item1 = new Artist("Why is the payment 'taking a little while to process?", false);
        Artist item2 = new Artist("I have a problem with a coupon code, what do I do?", false);
        Artist item3 = new Artist("My credit card payment didn't go through", false);
        Artist item4 = new Artist("I referred someone but didn't get my JobsLok Credit?", false);
        return Arrays.asList(item1, item2, item3, item4);
    }
    public static List<Artist> makeChildQuestion14() {
        Artist item1 = new Artist("Everything you should know about making changes to tasks", false);
        Artist item2 = new Artist("How do I edit my task?", false);
        Artist item3 = new Artist("How do I reschedule a task?", false);
        Artist item4 = new Artist("What happens if my task is overdue and I need an extension?", false);
        Artist item5 = new Artist("Can I reassign my task to someone else instead?", false);
        return Arrays.asList(item1, item2, item3, item4, item5);
    }

    public static List<Artist> makeChildQuestion15() {
        Artist item1 = new Artist("Everything to consider when cancelling a task", false);
        Artist item2 = new Artist("How do i cancel a task?", false);
        Artist item3 = new Artist("I have received a cancellation notification what should I do?", false);
        Artist item4 = new Artist("I don't agree with the cancellation reason what should I do?", false);
        Artist item5 = new Artist("Can I withdraw my cancellation request?", false);
        return Arrays.asList(item1, item2, item3, item4, item5);
    }
    public static List<Artist> makeChildQuestion16() {
        Artist item1 = new Artist("I posted a task. What are some safety tips I should follow?", false);
        Artist item2 = new Artist("I posted a task and I feel unsafe, what should I do?", false);
        Artist item3 = new Artist("What should I do if someone wants to cancel for a cash payment?", false);
        Artist item4 = new Artist("I posted a task but something doesn't seem right. What should I do?", false);
        Artist item5 = new Artist("What can I do to build trust in my Tasker?", false);
        return Arrays.asList(item1, item2, item3, item4, item5);
    }
    public static List<Artist> makeChildQuestion17() {
        Artist item1 = new Artist("I'm doing tasks. What are some safety tips should follow?", false);
        Artist item2 = new Artist("I'm doing tasks and I feel unsafe, what should I do?", false);
        Artist item3 = new Artist("I'm doing tasks but something doesn't seem right. What should I do?", false);
        Artist item4 = new Artist("Safety checklists for Taskers", false);
        Artist item5 = new Artist("What can I do to build trust in a Poster?", false);
        return Arrays.asList(item1, item2, item3, item4, item5);
    }
    public static List<Artist> makeChildQuestion18() {
        Artist item1 = new Artist("I'm having trouble with my task, what should I do?", false);
        Artist item2 = new Artist("What is JobsLok dispute process?", false);
        Artist item3 = new Artist("What should I do in the dispute process?", false);
        Artist item4 = new Artist("What should I do before starting the dispute process?", false);
        Artist item5 = new Artist("JobsLok's dispute process stage 1 - Let us know what's happened", false);
        return Arrays.asList(item1, item2, item3, item4, item5);
    }
    public static List<Artist> makeChildQuestion19() {
        Artist item1 = new Artist("How to communicate well on JobsLok", false);
        Artist item2 = new Artist("Why can't I post my phone number or email?", false);
        Artist item3 = new Artist("How do I comment on a task?", false);
        Artist item4 = new Artist("How do I discuss a task before assigning it?", false);
        Artist item5 = new Artist("How do I send a private message?", false);
        return Arrays.asList(item1, item2, item3, item4, item5);
    }
    public static List<Artist> makeChildQuestion20() {
        Artist item1 = new Artist("How do reviews work?", false);
        Artist item2 = new Artist("How do I leave a review?", false);
        Artist item3 = new Artist("What should I include in a review?", false);
        Artist item4 = new Artist("Can I change my review?", false);
        Artist item5 = new Artist("What are the JobsLok Review Guidelines?", false);
        Artist item6 = new Artist("What is the difference between the Star Rating & Completion Rate?", false);
        Artist item7 = new Artist("What are completion rates?", false);
        return Arrays.asList(item1, item2, item3, item4, item5,item6,item7);
    }
    public static List<Artist> makeChildQuestion21() {
        Artist item1 = new Artist("How does third party liability insurance on JobsLok work for Posters?", false);
        Artist item2 = new Artist("How does third party liability insurance on JobsLok work for Taskers?", false);
        Artist item3 = new Artist("How does JobsLok Insurance work in the INDIA", false);
        Artist item4 = new Artist("What happens if I get injured on a task?", false);
        return Arrays.asList(item1, item2, item3, item4);
    }
    public static List<Artist> makeChildQuestion22() {
        Artist item1 = new Artist("How do taxes work on JobsLok?", false);
        Artist item2 = new Artist("How does GST work for Taskers?", false);
        Artist item3 = new Artist("What record-keeping should I be doing?\n", false);
        Artist item4 = new Artist("How do I get a payment invoice?", false);
        return Arrays.asList(item1, item2, item3, item4);
    }


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId()==android.R.id.home){
            getFragmentManager().popBackStack();
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        adapter.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        adapter.onRestoreInstanceState(savedInstanceState);
    }
}
