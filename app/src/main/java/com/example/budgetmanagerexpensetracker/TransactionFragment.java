package com.example.budgetmanagerexpensetracker;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Calendar;
import java.util.UUID;

public class TransactionFragment extends Fragment {

    private EditText amountEditText;
    private RadioGroup typeRadioGroup;
    private EditText noteEditText;
    private EditText categoryEditText;
    private EditText dayEditText;
    private Button addExpenseButton;

    private Spinner categorySpinner;

    private String[] categories = {"Salary", "Business", "Investment", "Loan", "Other"};


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_add_expense, container, false);

        // Initialize views from the XML layout
        amountEditText = view.findViewById(R.id.amount);
        typeRadioGroup = view.findViewById(R.id.typeRadioGroup);
        noteEditText = view.findViewById(R.id.note);
        categorySpinner = view.findViewById(R.id.categorySpinner);
        dayEditText = view.findViewById(R.id.day);
        addExpenseButton = view.findViewById(R.id.addExpenseButton);

        addExpenseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createExpense();
            }
        });

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(requireContext(), R.layout.spinner_item_category, R.id.categoryName, categories) {
            @Override
            public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
                View view = super.getView(position, convertView, parent);
                ImageView categoryLogo = view.findViewById(R.id.categoryLogo);

                // Set the logo for the category based on its position (you can replace this with your actual logic to set the correct logo)
                switch (position) {
                    case 0:
                        categoryLogo.setImageResource(R.drawable.baseline_attach_money_24);
                        break;
                    case 1:
                        categoryLogo.setImageResource(R.drawable.baseline_credit_card_24);
                        break;
                    case 2:
                        categoryLogo.setImageResource(R.drawable.baseline_bar_chart_24);
                        break;
                    case 3:
                        categoryLogo.setImageResource(R.drawable.baseline_account_balance_24);
                        break;
                    case 4:
                    default:
                        categoryLogo.setImageResource(R.drawable.baseline_account_balance_wallet_24);
                        break;
                }

                return view;
            }
        };
        adapter.setDropDownViewResource(R.layout.spinner_item_category);
        categorySpinner.setAdapter(adapter);

        return view;
    }


    private void createExpense() {
        String expenseId = UUID.randomUUID().toString();
        long amount = Long.parseLong(amountEditText.getText().toString());
        String note = noteEditText.getText().toString();
        String category = categorySpinner.getSelectedItem().toString(); // Get the selected category
        String day = dayEditText.getText().toString();

        int selectedRadioId = typeRadioGroup.getCheckedRadioButtonId();
        RadioButton selectedRadioButton = getView().findViewById(selectedRadioId);
        String type = selectedRadioButton.getText().toString();


        ExpenseModel expenseModel = new ExpenseModel(expenseId, note, category, day, amount,
                Calendar.getInstance().getTimeInMillis(), type, FirebaseAuth.getInstance().getUid());

        FirebaseFirestore.getInstance()
                .collection("expenses")
                .document(expenseId)
                .set(expenseModel)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        // Show a success message or handle the success as required
                        Toast.makeText(getContext(), "Expense added!", Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        // Handle the failure if necessary
                        Toast.makeText(getContext(), "Failed to add expense.", Toast.LENGTH_SHORT).show();
                    }
                });
    }


}

