package com.example.zulfikaranshari.rentku;

import android.content.SharedPreferences;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ASUS on 01/03/2018.
 */

public class fragmentAkun extends Fragment {
	private EditText mEmail, mUsername;
	private Button mButtonUpdate;
	private FirebaseAuth mAuth;
	private FirebaseUser mUser;
	private DatabaseReference mDatabaseRef;
	List<UserModel> userList = new ArrayList<>();
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
							 Bundle savedInstanceState) {

		View rootView = inflater.inflate(R.layout.fragment_akun, container, false);
		mAuth = FirebaseAuth.getInstance();
		mUser = mAuth.getCurrentUser();
		mDatabaseRef = FirebaseDatabase.getInstance().getReference("user");

		mEmail = (EditText) rootView.findViewById(R.id.email_field);
		mUsername = (EditText) rootView.findViewById(R.id.username_field);
		mButtonUpdate = (Button) rootView.findViewById(R.id.btnUpdate);

		mEmail.setEnabled(false);
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
						mUsername.setText(userInfo.getUsername());
						mEmail.setText(userInfo.getEmail());
//						Toast.makeText(getContext(),userInfo.getHp(), Toast.LENGTH_LONG).show();

					}


				}

			}

			@Override
			public void onCancelled(DatabaseError databaseError) {

			}
		});

	}
}
