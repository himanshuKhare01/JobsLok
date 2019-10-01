package com.jobslok.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.TextView;
import com.jobslok.R;
import com.jobslok.ViewModel.Artist;
import com.jobslok.ViewModel.Genre;
import com.thoughtbot.expandablerecyclerview.ExpandableRecyclerViewAdapter;
import com.thoughtbot.expandablerecyclerview.models.ExpandableGroup;
import com.thoughtbot.expandablerecyclerview.viewholders.ChildViewHolder;
import com.thoughtbot.expandablerecyclerview.viewholders.GroupViewHolder;
import java.util.List;
import static android.view.animation.Animation.RELATIVE_TO_SELF;

public class SupportAdapter extends ExpandableRecyclerViewAdapter<SupportAdapter.GenreViewHolder, SupportAdapter.ArtistViewHolder> {

  public SupportAdapter(List<? extends ExpandableGroup> groups) {
    super(groups);
  }

  @Override
  public GenreViewHolder onCreateGroupViewHolder(ViewGroup parent, int viewType) {
    View view = LayoutInflater.from(parent.getContext())
        .inflate(R.layout.list_item_parent, parent, false);
    return new GenreViewHolder(view);
  }

  @Override
  public ArtistViewHolder onCreateChildViewHolder(ViewGroup parent, int viewType) {
    View view = LayoutInflater.from(parent.getContext())
        .inflate(R.layout.list_item_child, parent, false);
    return new ArtistViewHolder(view);
  }

  @Override
  public void onBindChildViewHolder(ArtistViewHolder holder, int flatPosition,
                                    ExpandableGroup group, int childIndex) {

    final Artist artist = ((Genre) group).getItems().get(childIndex);
    holder.setArtistName(artist.getName());
  }

  @Override
  public void onBindGroupViewHolder(GenreViewHolder holder, int flatPosition, ExpandableGroup group) {

    holder.setGenreTitle(group);
  }
  class GenreViewHolder extends GroupViewHolder {

    private TextView genreName;
    private ImageView arrow;


    public GenreViewHolder(View itemView) {
      super(itemView);
      genreName = (TextView) itemView.findViewById(R.id.list_item_parent_name);
      arrow = (ImageView) itemView.findViewById(R.id.list_item_parent_arrow);
     }

    public void setGenreTitle(ExpandableGroup genre) {
      if (genre instanceof Genre) {
        genreName.setText(genre.getTitle());
      }
    }

    @Override
    public void expand() {
      animateExpand();
    }

    @Override
    public void collapse() {
      animateCollapse();
    }

    private void animateExpand() {
      RotateAnimation rotate =
              new RotateAnimation(360, 180, RELATIVE_TO_SELF, 0.5f, RELATIVE_TO_SELF, 0.5f);
      rotate.setDuration(300);
      rotate.setFillAfter(true);
      arrow.setAnimation(rotate);
    }

    private void animateCollapse() {
      RotateAnimation rotate =
              new RotateAnimation(180, 360, RELATIVE_TO_SELF, 0.5f, RELATIVE_TO_SELF, 0.5f);
      rotate.setDuration(300);
      rotate.setFillAfter(true);
      arrow.setAnimation(rotate);
    }
  }
  class ArtistViewHolder extends ChildViewHolder {

    private TextView childTextView;

    public ArtistViewHolder(View itemView) {
      super(itemView);
      childTextView = (TextView) itemView.findViewById(R.id.list_item_child_name);
    }

    public void setArtistName(String name) {
      childTextView.setText(name);
    }
  }
}
