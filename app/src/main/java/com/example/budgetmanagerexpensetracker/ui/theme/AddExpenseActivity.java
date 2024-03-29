package com.example.budgetmanagerexpensetracker.ui.theme;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.budgetmanagerexpensetracker.ExpenseModel;
import com.example.budgetmanagerexpensetracker.R;
import com.example.budgetmanagerexpensetracker.databinding.ActivityAddExpenseBinding;
import com.example.budgetmanagerexpensetracker.databinding.ActivityMainBinding;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Calendar;
import java.util.UUID;

public class AddExpenseActivity extends Activity {
ActivityAddExpenseBinding binding;
private String type;
private ExpenseModel expenseModel;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        binding= ActivityAddExpenseBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        type = getIntent().getStringExtra("type");
        expenseModel = (ExpenseModel) getIntent().getSerializableExtra("model");

        if(type == null){
            type = expenseModel.getType();
            binding.amount.setText(String.valueOf(expenseModel.getAmount()));
            binding.categorySpinner.setSelection(getIndexOfCategory(expenseModel.getCategory()));
            binding.note.setText(expenseModel.getNote());
            binding.day.setText(expenseModel.getDay());
        }

        if(type.equals("Income")){
            binding.incomeRadio.setChecked(true);
        } else{
            binding.expenseRadio.setChecked(true);
        }

        binding.incomeRadio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                type = "Income";
            }
        });

        binding.addExpenseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createExpense();
            }
        });

        binding.expenseRadio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                type = "Expense";
            }
        });


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        if(expenseModel == null){
            menuInflater.inflate(R.menu.add_menu, menu);
        } else{
            menuInflater.inflate(R.menu.update_menu, menu);
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if(id == R.id.saveExpense){
            if(type != null){
                createExpense();
            } else {
                //updateExpense();
            }
            return true;
        }
        if(id == R.id.deleteExpense){
            deleteExpense();
        }
        return false;
    }

    private void deleteExpense() {
        FirebaseFirestore
                .getInstance()
                .collection("expenses")
                .document(expenseModel.getExpenseId())
                .delete();
        finish();
    }


    private void createExpense() {
        String expenseId = UUID.randomUUID().toString();
        long amount = Long.parseLong(binding.amount.getText().toString());
        String note = binding.note.getText().toString();
        String category = binding.categorySpinner.getSelectedItem().toString(); // Get the selected category
        String day = binding.day.getText().toString();

        boolean incomeChecked = binding.incomeRadio.isChecked();
        if (incomeChecked) {
            type = "Income";
        } else {
            type = "Expense";
        }

        if (binding.amount.getText().toString().length() == 0) {
            binding.amount.setError("Empty");
            return;
        }

        ExpenseModel expenseModel = new ExpenseModel(expenseId, note, category, day, amount,
                Calendar.getInstance().getTimeInMillis(), type, FirebaseAuth.getInstance().getUid());

        FirebaseFirestore
                .getInstance()
                .collection("expenses")
                .document(expenseId)
                .set(expenseModel)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        // Show a success message or handle the success as required
                        Toast.makeText(AddExpenseActivity.this, "Expense added!", Toast.LENGTH_SHORT).show();
                        finish();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        // Handle the failure if necessary
                        Toast.makeText(AddExpenseActivity.this, "Failed to add expense.", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private int getIndexOfCategory(String category) {
        String[] categories = getResources().getStringArray(R.array.categories);
        for (int i = 0; i < categories.length; i++) {
            if (categories[i].equals(category)) {
                return i;
            }
        }
        return 0; // Default to the first category if the category is not found
    }

    /*private void updateExpense(){

        String expenseId = expenseModel.getExpenseId();
        String amount = binding.amount.getText().toString();
        String note = binding.note.getText().toString();
        String category = binding.category.getText().toString();

        boolean incomeChecked = binding.incomeRadio.isChecked();
        if(incomeChecked){
            type = "Income";
        }else {
            type = "Expense";
        }

        if(amount.trim().length()==0){
            binding.amount.setError("Empty");
            return;
        }
        ExpenseModel model = new ExpenseModel(expenseId,note,category,type,Long.parseLong(amount),
                expenseModel.getTime(), FirebaseAuth.getInstance().getUid());

        FirebaseFirestore
                .getInstance()
                .collection("expenses")
                .document(expenseId)
                .set(model);
        finish();
    }
    */

}
