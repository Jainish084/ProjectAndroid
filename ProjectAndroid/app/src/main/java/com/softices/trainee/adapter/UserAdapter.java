package com.softices.trainee.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.softices.trainee.R;
import com.softices.trainee.database.DbHelper;
import com.softices.trainee.models.AppModel;

import java.util.List;

import static com.softices.trainee.models.AppModel.*;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.userViewHolder> {

    public DbHelper dbHelper;
    private Context context;
    private List<AppModel> dataSet;

    public UserAdapter(Context context, DbHelper dbHelper, List<AppModel> data) {
        this.context = context;
        this.dbHelper=dbHelper;
        this.dataSet = data;
    }

    @NonNull
    @Override
    public userViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        View view = inflater.inflate(R.layout.email_list_layout, viewGroup, false);
        return new userViewHolder(view);
    }


    @Override
    public void onBindViewHolder(final userViewHolder userViewHolder, final int i) {
        final AppModel appModel = dataSet.get(i);
        userViewHolder.emailListtxtEmail.setText(appModel.getEmail());
        userViewHolder.emailListButtonDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final boolean isDelete = dbHelper.deleteUser(AppModel.getEmail());
                if (isDelete) {
                    Toast.makeText(context, "Data is Deleted!", Toast.LENGTH_LONG).show();
                    dataSet.remove(i);
                    notifyItemRemoved(i);
                    notifyItemRangeChanged(i, dataSet.size());
                    notifyDataSetChanged();
                } else {
                    Toast.makeText(context, "Data is not Deleted", Toast.LENGTH_LONG).show();
                    Toast.makeText(context, "Something went wrong...!!!", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return dataSet.size();
    }

    public class userViewHolder extends RecyclerView.ViewHolder {
        TextView emailListtxtEmail;
        Button emailListButtonDelete;

        public userViewHolder(@NonNull View itemView) {
            super(itemView);
            emailListtxtEmail = (TextView) itemView.findViewById(R.id.email_list_txt_email);
            emailListButtonDelete = (Button) itemView.findViewById(R.id.email_list_btn_delete);
        }
    }
}
