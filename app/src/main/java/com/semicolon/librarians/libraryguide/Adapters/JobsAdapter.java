package com.semicolon.librarians.libraryguide.Adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.ramotion.foldingcell.FoldingCell;
import com.semicolon.librarians.libraryguide.Models.JobsModel;
import com.semicolon.librarians.libraryguide.R;
import com.semicolon.librarians.libraryguide.Services.Tags;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

/**
 * Created by Delta on 22/01/2018.
 */

public class JobsAdapter extends RecyclerView.Adapter<JobsAdapter.ViewHolder>  {

    List<JobsModel> jobsModelList;
    Context context;

    public JobsAdapter(List<JobsModel> jobsModelList, Context context) {
        this.jobsModelList = jobsModelList;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.job_recyclerview_row,parent,false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        JobsModel jobsModel = jobsModelList.get(position);
        holder.BindData(jobsModel);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                holder.foldCell.toggle(false);
            }
        });

    }

    @Override
    public int getItemCount() {
        return jobsModelList.size();
    }


    class ViewHolder extends RecyclerView.ViewHolder
    {
        Target target;

        FoldingCell foldCell;
        ImageView Title_job_image,Content_job_image;
        TextView Title_jobTitle,Title_jobDate,Title_jobContent,Title_jobCompanyName;
        TextView Content_jobTitle,Content_jobStartDate,Content_jobEndDate,Content_jobRequirements,Content_jobCompanyName,Content_jobPhone,Content_jobEmail;
        public ViewHolder(View itemView) {
            super(itemView);

            foldCell = (FoldingCell) itemView.findViewById(R.id.foldCell);
            ////////////////////////////////////////////////////////////
            initTitleView(itemView);

            /////////////////////////////////////////////////////////////
            initContentView(itemView);


        }



        private void initTitleView(View titleView) {
            Title_job_image       = (ImageView) titleView.findViewById(R.id.job_image);
            Title_jobTitle        = (TextView) titleView.findViewById(R.id.job_title);
            Title_jobDate         = (TextView) titleView.findViewById(R.id.job_date);
            Title_jobContent      = (TextView) titleView.findViewById(R.id.job_content);
            Title_jobCompanyName  = (TextView) titleView.findViewById(R.id.job_companyName);
        }

        private void initContentView(View contentView) {
            Content_job_image = (ImageView) contentView.findViewById(R.id.job_image_content);
            Content_jobTitle  = (TextView) contentView.findViewById(R.id.job_title_content);
            Content_jobStartDate  = (TextView) contentView.findViewById(R.id.job_startDate_content);
            Content_jobEndDate = (TextView) contentView.findViewById(R.id.job_endDate_content);
            Content_jobRequirements  = (TextView) contentView.findViewById(R.id.content_job_requirements);
            Content_jobCompanyName  = (TextView) contentView.findViewById(R.id.job_companyName_content);
            Content_jobEmail  = (TextView) contentView.findViewById(R.id.job_companyEmail_content);
            Content_jobPhone  = (TextView) contentView.findViewById(R.id.job_companyPhone_content);



        }
        public void BindData(JobsModel jobsModel)
        {
            target = new Target() {
                @Override
                public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                    Title_job_image.setImageBitmap(bitmap);
                    Content_job_image.setImageBitmap(bitmap);
                }

                @Override
                public void onBitmapFailed(Drawable errorDrawable) {

                }

                @Override
                public void onPrepareLoad(Drawable placeHolderDrawable) {

                }
            };
            Picasso.with(context).load(Uri.parse(Tags.image_path+jobsModel.getJobImage())).into(target);
            Picasso.with(context).load(Uri.parse(Tags.image_path+jobsModel.getJobImage())).into(target);

            Long sd = Long.getLong(jobsModel.getJobStartDate());
            Long ed = Long.getLong(jobsModel.getJobEndDate());
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd,MM,yy");
            /*String s_Date = dateFormat.format(new Date(sd));
            String e_Date = dateFormat.format(new Date(ed));
*/
            Long l_date = Long.parseLong(jobsModel.getJobStartDate().toString());
            String date = new SimpleDateFormat("dd,MMM,yyyy", Locale.getDefault()).format(l_date);

            Title_jobTitle.setText(jobsModel.getJobTitle().toString());
            Title_jobDate.setText(date);
            Title_jobContent.setText(jobsModel.getJobRequirements());
            Title_jobCompanyName.setText(jobsModel.getJob_companyName());



            Content_jobTitle.setText(jobsModel.getJobTitle().toString());
           /* Content_jobStartDate.setText(s_Date);
            Content_jobEndDate.setText(e_Date);
*/
            Content_jobRequirements.setText(jobsModel.getJobRequirements());
            Content_jobCompanyName.setText(jobsModel.getJob_companyName());
            Content_jobPhone.setText(jobsModel.getJobPhone());
            Content_jobEmail.setText(jobsModel.getJobEmail());




        }
    }



}

