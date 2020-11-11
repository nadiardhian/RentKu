package com.example.zulfikaranshari.rentku;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ASUS on 01/03/2018.
 */

public class fragmentProfile extends Fragment {
	private EditText mName, mHp, mAddress;
	private Button mButtonUpdate;
	private FirebaseAuth mAuth;
	private FirebaseUser mUser;
	private DatabaseReference mDatabaseRef;
	List<UserModel> userList = new ArrayList<>();
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
							 Bundle savedInstanceState) {

		View rootView = inflater.inflate(R.layout.fragment_profile, container, false);
		mAuth = FirebaseAuth.getInstance();
		mUser = mAuth.getCurrentUser();
		mDatabaseRef = FirebaseDatabase.getInstance().getReference("user");


		mName = (EditText) rootView.findViewById(R.id.name_field);
		mHp = (EditText) rootView.findViewById(R.id.hp_profile);
		mAddress = (EditText) rootView.findViewById(R.id.address_field);
		mButtonUpdate = (Button) rootView.findViewById(R.id.btnUpdate);

		mButtonUpdate.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				Query query = mDatabaseRef.orderByChild("email").equalTo(mUser.getEmail().toString());
				query.addListenerForSingleValueEvent(new ValueEventListener() {
					@Override
					public void onDataChange(DataSnapshot dataSnapshot) {
						for (DataSnapshot appleSnapshot : dataSnapshot.getChildren()) {
							// messageModel imageUploadInfo = appleSnapshot.getValue(messageModel.class);
							appleSnapshot.getRef().child("name").setValue(mName.getText().toString());
							appleSnapshot.getRef().child("hp").setValue(mHp.getText().toString());
							appleSnapshot.getRef().child("address").setValue(mAddress.getText().toString());

						}
					}

					@Override
					public void onCancelled(DatabaseError databaseError) {

					}
				});
			}
		});
		showData();

		return rootView;
	}

	public void showData() {
		mDatabaseRef.addValueEventListener(new ValueEventListener() {
			@Override
			public void onDataChange(DataSnapshot snapshot) {
				userList.clear();
				for (DataSnapshot postSnapshot : snapshot.getChildren()) { //foreach memanggil data

					UserModel userInfo = postSnapshot.getValue(UserModel.class); //get data dari firebase
					String emailDB = mUser.getEmail().toString();
					String emailModel = userInfo.getEmail().toString();
					if(emailDB.equals(emailModel)){
						userList.add(userInfo); //add data ke listviews
						mName.setText(userInfo.getName());
						mHp.setText(userInfo.getHp());
						mAddress.setText(userInfo.getAddress());
//						Toast.makeText(getContext(),userInfo.getHp(), Toast.LENGTH_LONG).show();

					}


				}

			}

			@Override
			public void onCancelled(DatabaseError databaseError) {

			}
		});

	}

	private void updateData(){

	}


}
